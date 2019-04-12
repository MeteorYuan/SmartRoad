package com.hfxy.video.Tools.EasyDl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hfxy.video.Tools.EasyDl.utils.GsonUtils;
import com.hfxy.video.Tools.EasyDl.utils.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.hfxy.video.Tools.EasyDl.AuthService.AuthService.getAuth;


/**
 * easydl图像分类
 */
public class Main {

    public static String getImageStr(String imgFile) throws IOException {
        InputStream inputStream = null;
        byte[] data = null;
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();

        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String easydlImageClassify(String Path,int urlnumber) {
        String url="";
        // 请求url
        String url1 = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/detection/road_inf";//识别标牌
        String url2 = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/classification/roadtype";//识别道路类型
        switch (urlnumber){
            case 1:url=url1;break;
            case 2:url=url2;break;
        }
        try {
            Map<String, Object> map = new HashMap<>();

            map.put("image", getImageStr(Path));
            map.put("top_num", "5");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        JSONObject jsonObject =new JSONObject(easydlImageClassify("C:\\Users\\好看小哥哥\\Desktop\\挑战杯\\Vid\\VidData\\pic\\1551160907.jpg",2));
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        Float byscore=jsonArray.getJSONObject(0).getFloat("score");
        Float snscore=jsonArray.getJSONObject(1).getFloat("score");
        if(byscore>snscore){
            System.out.println("柏油");
        }else {
            System.out.println("水泥");
        }
    }
}
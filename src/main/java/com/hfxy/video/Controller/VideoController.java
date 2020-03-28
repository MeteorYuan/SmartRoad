package com.hfxy.video.Controller;
import com.hfxy.video.po.VidData;
import com.hfxy.video.serviceImpl.VideoService;
import com.sun.media.jfxmedia.control.VideoDataBuffer;
import com.sun.org.apache.xpath.internal.operations.Bool;
import it.sauronsoftware.jave.EncoderException;


import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class VideoController {
    @Autowired
    VideoService videoService;


    /**
     * 文件上传接口
     * @param request
     * @return
     * @throws IOException
     * @throws EncoderException
     */
    @RequestMapping(value = "/file")
    @ResponseBody
    public String file (HttpServletRequest request) throws IOException, EncoderException {
        videoService.upload(request);
        return null;
    }

    /**
     * 给前端完成获取video列表过程
     * @param request
     * @return
     */
    @RequestMapping(value = "getVideoList")
    @ResponseBody
    public List<VidData> getVideoList(HttpServletRequest request){
        return videoService.getVideoList();
    }


    /**
     * 这个接口调用了百度API，关于百度API
     * 使用的是百度AI的EASYDL，在百度云的图像识别模块里有，
     * 调用量可能会有改变，具体代码实现不用修改，但是建议自己申请一个账号。
     * @param vidData
     * @return
     * @throws Exception
     */
    @RequestMapping(value="analysisVideo")
    @ResponseBody
    public String CutVideo(VidData vidData) throws Exception {
      return videoService.analysisVideo(vidData);
    }


    /**
     * 删除视频
     * @param name
     * @return
     */
    @RequestMapping(value = "delete")
    public String Delete(String name){
        videoService.delete(name);
        return "success";
    }



}

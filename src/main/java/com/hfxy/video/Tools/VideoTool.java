package com.hfxy.video.Tools;

import com.hfxy.video.Mapper.VideoMapper;
import com.hfxy.video.po.GPSData;
import com.hfxy.video.po.Result;
import com.hfxy.video.po.VidData;
import com.hfxy.video.serviceImpl.GPSService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import  static  com.hfxy.video.Tools.EasyDl.Main.easydlImageClassify;

@Component
@Transactional
public class VideoTool {
    @Resource
    VideoMapper videoMapper;



    @Resource
    GPSService gpsService;
    //创建存储文件夹
    public void checkDir(String path){
        File dir = new File(path);
        //判断上传文件的保存目录是否存在
        if (!dir.exists() && !dir.isDirectory()) {
            System.out.println(path+"目录不存在，需要创建");
            //创建目录
            dir.mkdir();
        }
    }

    //获取上传文件的后缀
    public  String[] getInf(String name){

        String[] result = name.split("\\.");
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
        return result;
    }

    //获取视频时长
    public int ReadVideoTime(File source) throws EncoderException {
        Encoder encoder = new Encoder();
        MultimediaInfo m = encoder.getInfo(source);
        int ls =(int)m.getDuration()/1000;
        System.out.println("视频长度为"+ls+"秒");
        System.out.println("结束");
        return ls;
    }





    //将背景时间转换成UTC时间
    public static  String getUTCSecond(String time) throws ParseException {
        double epoch = new java.text.SimpleDateFormat("yyyyMMddHHmmss").parse(time).getTime();
        BigDecimal result=new BigDecimal(epoch);
        String second=result.toString().substring(0,10);
        return second;
    }

    public boolean CheckGps(String gps,int id){
        GPSData gpsData1=gpsService.getGpsbyUtc(gps,id);
        BigDecimal gps2 = new BigDecimal(gps).subtract(new BigDecimal(1));
        GPSData gpsData2=gpsService.getGpsbyUtc(gps2.toString(),id);
        if (gpsData1.getLongitude().equals(gpsData2.getLongitude())&&gpsData1.getLatitude().equals(gpsData2.getLatitude())){
            return false;
        }
        else {
            return  true;
        }


    }
}

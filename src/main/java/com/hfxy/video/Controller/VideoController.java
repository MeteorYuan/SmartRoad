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
    @RequestMapping(value = "/file")
    @ResponseBody
    public String file (HttpServletRequest request) throws IOException, EncoderException {
        videoService.upload(request);
        return null;
    }

    @RequestMapping(value = "getVideoList")
    @ResponseBody
    public List<VidData> getVideoList(HttpServletRequest request){
        return videoService.getVideoList();
    }


    @RequestMapping(value="analysisVideo")
    @ResponseBody
    public String CutVideo(VidData vidData) throws Exception {
      return videoService.analysisVideo(vidData);
    }

    @RequestMapping(value = "delete")
    public String Delete(String name){
        videoService.delete(name);
        return "success";
    }

    @RequestMapping("/test")
    public void SayHello(HttpServletRequest request, HttpServletResponse response){
        System.out.println("hello");
    }





}

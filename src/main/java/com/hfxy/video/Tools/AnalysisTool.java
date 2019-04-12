package com.hfxy.video.Tools;


import com.hfxy.video.Mapper.VideoMapper;
import com.hfxy.video.po.Result;
import com.hfxy.video.serviceImpl.ResultService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.hfxy.video.Tools.EasyDl.Main.easydlImageClassify;

public class AnalysisTool {
    private String videoname;
    private int filelength;
    private VideoMapper videoMapper;
    private ResultService resultService;
    private boolean isover=false;
    public AnalysisTool(String videoname, int filelength){
       this.videoname=videoname;
       this.filelength=filelength;
       this.videoMapper = (VideoMapper)getBeanTool.getBean("videoMapper");
        this.resultService = (ResultService)getBeanTool.getBean("resultService");
    }

    private BlockingQueue<Integer> signQueue = new LinkedBlockingQueue<>(200);
    private BlockingQueue<Integer> roadQueue = new LinkedBlockingQueue<>(200);
    public class CutTool extends Thread{
        public void run()
        {
            try
            {
                VideoTool vt=new VideoTool();
                int utc = Integer.parseInt(vt.getUTCSecond(videoname.substring(0, 14)));
                vt.checkDir("VidData/pic/");
                File file = new File("VidData/" + videoname + ".mp4");
                FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
                ff.start();
                int i = 0;
                Frame frame = null;
                //filelength为视频秒数长度
                while (i < filelength)
                {

                    File targetFile = new File("VidData/pic/" + utc + ".jpg");
                    for (int a = 0; a <= 24; a++) {
                        //每25帧进行一次截图，即一秒一次（视频25帧1秒）
                        frame = ff.grabFrame();
                    }
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage srcBi = converter.getBufferedImage(frame);
                    // 对截取的帧进行等比例缩放
                    int width = 1920;
                    int height =1080;
                    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                    bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
                    System.out.println("生成图片"+targetFile.getName());
                    ImageIO.write(bi, "jpg", targetFile);
                    System.out.println("1"+utc);
                    signQueue.put(utc);
                    System.out.println("2"+utc);
                    roadQueue.put(utc);
                    System.out.println("3"+utc);
                    i++;
                    utc++;
                }
                ff.stop();
                System.out.println("图片截取线程结束");
                isover=true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public class Analysis extends Thread{
        @Override
        public void run() {
            try {
                int id = videoMapper.getRoad_id(videoname);
                //filelength为视频秒数长度
                while (!isover||signQueue.size()!=0) {
                    int utc=signQueue.take();
                    File targetFile = new File("VidData/pic/" + utc + ".jpg");
                    JSONObject jsonObject =new JSONObject(easydlImageClassify(targetFile.toString(),1));//获取识别结果
                    if (!jsonObject.get("results").toString().equals("[]")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        Result result1 = new Result();
                        String gps=resultService.getGpsByUtc(utc).getAmap_latitude()+","+resultService.getGpsByUtc(utc).getAmap_longitude();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            int check = --utc;
                            String name = jsonArray.getJSONObject(a).getString("name");
                            if (name.equals("lim_s60") || name.equals("lim_s50") || name.equals("lim_s40") || name.equals("lim_s30") || name.equals("lim_s120")) {
                                System.out.println("插入限速");
                                result1.setLim_speed(gps);
                            }
                            if (name.equals("no_stop")) {
                                System.out.println("插入禁停");
                                result1.setNo_stop(gps);
                            }
                            if (name.equals("np_left") || name.equals("no_right")) {
                                System.out.println("插入禁止转向");
                                result1.setNo_turn(gps);
                            }
                            if (resultService.getResultbyUtc(String.valueOf(check)) != null) {
                                resultService.Delete(String.valueOf(check));
                                System.out.println("删除");
                            }

                        }
                        result1.setRoad_id(id);
                        result1.setUtc(String.valueOf(utc));
                        resultService.newState(result1);
                    }
                }
                System.out.println("标志牌分析线程结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public class AnalysisType extends Thread{
        @Override
        public void run() {
            try {
                int id = videoMapper.getRoad_id(videoname);
                int type;
                int sn=0;
                int by=0;
                String result;
                int utc=roadQueue.take();
                while(!isover||roadQueue.size()!=0){
                    roadQueue.take();
                    File targetFile = new File("VidData/pic/" + utc + ".jpg");
                    JSONObject jsonObject =new JSONObject(easydlImageClassify(targetFile.toString(),2));
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    type=jsonArray.getJSONObject(0).getFloat("score")>jsonArray.getJSONObject(1).getFloat("score")?1:0;
                    if(type==1){
                        by++;
                    }else {
                        sn++;
                    }
                    result=by>sn?"柏油路":"水泥路";
                    resultService.updateType(id,result);
                    resultService.updateTypeOnGps(id,utc,type);
                    System.out.println(id+" "+utc+" "+type);
                }
                System.out.println("道路类型线程结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}




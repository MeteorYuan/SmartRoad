package com.hfxy.video.serviceImpl;
import com.hfxy.video.Mapper.VideoMapper;
import com.hfxy.video.Tools.AnalysisTool;
import com.hfxy.video.Tools.VideoTool;
import com.hfxy.video.po.VidData;
import it.sauronsoftware.jave.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Iterator;
import java.util.List;
@Service
@Transactional
public class VideoService  {
    @Resource
    private VideoMapper videoMapper;


    @Autowired
    VideoTool videoTool;
    @Autowired
    ResultService resultService;

    @Async
    public Boolean upload(HttpServletRequest request) throws IOException, EncoderException {
        String name;
        String Savepath;
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        Iterator<String> iterator = req.getFileNames();
        videoTool.checkDir("saveFile");
        while (iterator.hasNext())
        {
            MultipartFile file =req.getFile(iterator.next());
            name=file.getOriginalFilename();
            String[] path=videoTool.getInf(name);
            if(!path[1].equals("json")){
                Savepath="VidData";
                videoTool.checkDir(Savepath);
            }
            else if(path[1].equals("json")){
                Savepath="GpsData";
                videoTool.checkDir(Savepath);
            }
            else{
                System.out.println("文件格式错误");
                return false;
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(Savepath + "\\"+file.getOriginalFilename())));
            System.out.println(file.getOriginalFilename());
            InputStream in=file.getInputStream();
            byte buffer[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int len = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while((len=in.read(buffer))>0){
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
            if(!path[1].equals("json")){
                System.out.println(path[0]);
                int videolength=videoTool.ReadVideoTime(new File(Savepath + "\\"+file.getOriginalFilename()));
                if(videoMapper.CheckName(path[0])==null){
                    int id=videoMapper.getNewId();
                    videoMapper.UploadVideo(path[0],Savepath + "\\"+file.getOriginalFilename(),videolength,0,id);
                }
            }
            else if(path[1].equals("json")) {
                videoMapper.AddGps(path[0], Savepath + "\\" + file.getOriginalFilename());
            }

                System.out.println(name + "上传完毕");
        }

        return true;
    }

    public List<VidData> getVideoList(){
        List<VidData> vidDataList=videoMapper.GetVideoList();
        for(VidData vidData:vidDataList){
            System.out.println(vidData);
        }
            return vidDataList;
    }

    public String analysisVideo(VidData vidData) throws Exception {
        videoMapper.UpdateState(vidData.getId(),1);
        System.out.println("开始处理：");

        try{
            AnalysisTool analysisTool=new AnalysisTool(vidData.getName(),vidData.getVideoLength());
            AnalysisTool.Analysis analysis=analysisTool.new Analysis();
            AnalysisTool.CutTool cutTool=analysisTool.new CutTool();
            AnalysisTool.AnalysisType analysisType=analysisTool.new AnalysisType();
            cutTool.start();
            analysis.start();
            analysisType.start();
            int id=videoMapper.getRoad_id(vidData.getName());
            resultService.updateCount(resultService.selectCount(id),id);
            videoMapper.UpdateState(vidData.getId(),2);
            return "识别成功";
        }catch (Exception e){
            videoMapper.UpdateState(vidData.getId(),0);
            return "识别失败";
        }


    }

    public void delete(String name){
        videoMapper.DeleteVideo(name);
    }



}

package com.hfxy.video.serviceImpl;

import com.hfxy.video.Mapper.ResultMapper;
import com.hfxy.video.po.GPSData;
import com.hfxy.video.po.Result;
import com.hfxy.video.po.RoadJson;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ResultService {
    @Resource
    ResultMapper resultMapper;
    public int InsertGpstoResult(Result result){
       return resultMapper.InsertGpstoResult(result);
    }

    public int InsertResult(Result result){
        return resultMapper.InsertResult(result);
    }

    public  int newState(Result result){return  resultMapper.newState(result);}

    public int Delete(String utc){return resultMapper.Deletecf(utc);}

    public GPSData getResultbyUtc(String utc){return resultMapper.getResultbyUtc(utc);}

    public int selectCount(int id){return  resultMapper.selectCount(id);}

    public int updateCount(int number,int id){return resultMapper.updateCount(number,id);}

    public RoadJson getGpsByUtc(int utc){return  resultMapper.getGpsByUtc(utc);}
    public int updateType(int id,String type){
        return resultMapper.updateType(id,type);
    }


    public int updateTypeOnGps(int id, int utc,int type){
        return resultMapper.updateTypeOnGps(id, utc, type);
    }

}

package com.hfxy.video.serviceImpl;

import com.hfxy.video.Mapper.GPSMapper;
import com.hfxy.video.po.GPSData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GPSService {
    @Resource
    GPSMapper gpsMapper;

    public GPSData getGpsbyUtc(String utc,int id){
        return gpsMapper.getGpsbyUtc(utc,id);
    }

}

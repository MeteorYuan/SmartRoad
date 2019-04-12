package com.hfxy.video.Mapper;


import com.hfxy.video.po.GPSData;
import com.hfxy.video.po.VidData;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface VideoMapper {


    @Select("select * from road_json where utc=#{utc}")
    GPSData getGpsbyUtc(@Param("utc")String utc);

    @Insert("insert into  road_vid_data (name,VideoPath,VideoLength,states,road_id) values (#{name},#{VideoPath},#{VideoLength},#{states},#{road_id})")
    int UploadVideo(@Param("name") String name,@Param("VideoPath") String VideoPath,@Param("VideoLength")int VideoLength,@Param("states")int states,@Param("road_id")int road_id);

    @Insert("insert into road_gps_data (name,GpsPath) values (#{name},#{GpsPath})")
    int AddGps(@Param("name") String name,@Param("GpsPath") String GpsPath);

    @Select("select * from road_vid_data")
    List<VidData> GetVideoList();

    @Select("select * from road_vid_data where name=#{name}")
    VidData CheckName(@Param("name") String name);

    @Insert("insert into road_picresult (utc,result) values (#{utc},#{result})")
    int picResult(@Param("utc") String utc,@Param("result") String result);

    @Update("update road_vid_data set states=#{states} where id=#{id}")
    int UpdateState(@Param("id") int id,@Param("states") int states);

    @Delete("delete from road_vid_data where name=#{name}")
    int DeleteVideo(@Param("name")String name);

    @Select("select road_id from road_vid_data where name=#{name}")
    int getRoad_id(@Param("name")String name);

    @Select("SELECT route_id FROM `road_route` ORDER BY route_id desc LIMIT 1")
    int getNewId();



}

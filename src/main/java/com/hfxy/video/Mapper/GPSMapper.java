package com.hfxy.video.Mapper;

import com.hfxy.video.po.GPSData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GPSMapper {

   @Select("select * from road_json where utc=#{utc} and road_id=#{id}")
   GPSData getGpsbyUtc(@Param("utc")String utc,@Param("id")int id);


}

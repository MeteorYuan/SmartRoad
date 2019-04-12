package com.hfxy.video.Mapper;

import com.hfxy.video.po.GPSData;
import com.hfxy.video.po.Result;
import com.hfxy.video.po.RoadJson;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ResultMapper {

    @Select("select * from road_result where utc=#{utc} limit 1")
    GPSData getResultbyUtc(@Param("utc")String utc);
    @Insert("insert into road_result (utc,longitude,latitude) values (#{utc},#{longitude},#{latitude})")
    int InsertGpstoResult(Result result);


    @Insert("insert into road_result (no_turn,no_stop,lim_speed) values (#{no_turn},#{no_stop},#{lim_speed}) where utc=#{utc}")
    int InsertResult(Result result);

    @Insert("insert into road_result  (utc,no_turn,no_stop,lim_speed,road_id) values (#{utc},#{no_turn},#{no_stop},#{lim_speed},#{road_id})")
    int newState(Result result);

    @Select("select amap_longitude,amap_latitude from road_json where utc=#{utc} limit 1")
    RoadJson getGpsByUtc(@Param("utc")int utc);

    @Delete("delete from road_result where utc=#{utc}")
    int Deletecf(@Param("utc")String utc);

    @Select("select count(*) from road_vid_data where road_id =#{id} ")
    int selectCount(@Param("id") int id);

    @Update("update  road_route_information set sign_all_number=#{number} where route_id=#{id}")
    int updateCount(@Param("number")int number,@Param("id")int id);

    @Update("update road_route set road_type = #{type} where route_id=#{id}")
    int updateType(@Param("id")int id,@Param("type")String type);

    @Update("update road_json set type=#{type} where road_id=#{id} and utc=#{utc}")
    int updateTypeOnGps(@Param("id")int id,@Param("utc")int utc,@Param("type")int type);



}

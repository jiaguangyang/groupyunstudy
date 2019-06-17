package com.jk.mapper;

import com.jk.model.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsMapper {
    List<Video> findVideo();
    @Select("select count(*) from t_video")
    Integer findCountVideo();
    @Select("select * from t_video limit #{start},#{rows}")
    List<Video> findAll(@Param("start") Integer start,@Param("rows") Integer rows);
}

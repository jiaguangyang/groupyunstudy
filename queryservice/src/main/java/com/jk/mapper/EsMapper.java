package com.jk.mapper;

import com.jk.model.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsMapper {
    List<Video> findVideo();

    @Select("select * from t_video tv INNER JOIN t_teacher tt on tv.teacherId=tt.id  where tv.id=#{0}")
    Video findVideoById(Integer videoid);
}

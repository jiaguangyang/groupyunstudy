package com.jk.mapper;

import com.jk.model.Ossbean;
import com.jk.model.Video;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface jgyMapper {
    @Select("select * from t_oss where status=1")
    List<Ossbean> findLunBo();

    List<Video> careCurr();

    List<Video> newCurr();

    List<Video> freeCurr();

    @Select("select * from t_video")
    List<Video> queryVideoAll();

}

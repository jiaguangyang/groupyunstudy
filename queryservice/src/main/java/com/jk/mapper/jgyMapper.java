package com.jk.mapper;

import com.jk.model.Ossbean;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface jgyMapper {
    @Select("select * from t_oss where status=1")
    List<Ossbean> findLunBo();
}

package com.jk.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface jgyMapper {

   @Select("select * from t_test")
    List<LinkedHashMap<String, Object>> findtest();
}

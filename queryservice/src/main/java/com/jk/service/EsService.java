package com.jk.service;

import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.HashMap;
import java.util.List;

public interface EsService  {
    List<IndexQuery> findVideo();

    HashMap<String, Object> findAll(Integer page, Integer rows);
}

package com.jk.service;

import com.jk.mapper.EsMapper;
import com.jk.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EsServiceImpl implements EsService{
    @Autowired
    EsMapper esMapper;

    @Override
    public List<IndexQuery> findVideo() {
        List<Video> list = esMapper.findVideo();
        return getQuery(list);
    }

    @Override
    public Video findVideoById(Integer videoid) {
        return esMapper.findVideoById(videoid);
    }


    private List<IndexQuery> getQuery(List<Video> list) {
        List<IndexQuery> queryList = new ArrayList<>();
        for (Video esProduct : list) {
            IndexQuery query = new IndexQueryBuilder().withId(esProduct.getId().toString()).withObject(esProduct).build();
            queryList.add(query);
        }
        return queryList;
    }

}

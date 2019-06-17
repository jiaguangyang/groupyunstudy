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


    private List<IndexQuery> getQuery(List<Video> list) {
        List<IndexQuery> queryList = new ArrayList<>();
        for (Video esProduct : list) {
            IndexQuery query = new IndexQueryBuilder().withId(esProduct.getId().toString()).withObject(esProduct).build();
            queryList.add(query);
        }
        return queryList;
    }
    @Override
    public HashMap<String, Object> findAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer total = esMapper.findCountVideo();
        Integer start = (page-1)*rows;
        List<Video> list = esMapper.findAll(start,rows);
        map.put("total",total);
        map.put("rows",list);
        return map;
    }

}

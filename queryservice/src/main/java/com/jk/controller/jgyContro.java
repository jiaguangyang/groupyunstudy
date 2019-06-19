package com.jk.controller;

import com.jk.model.Video;
import com.jk.service.jgyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class jgyContro {
    @Autowired
    private jgyService jgyservice;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("findLunBo")
    @ResponseBody
    public String findLunBo(){
        return jgyservice.findLunBo();
    }

    @RequestMapping("careCurr")
    @ResponseBody
    public List<Video> careCurr(){
        return  jgyservice.careCurr();
    }

    @RequestMapping("newCurr")
    @ResponseBody
    public List<Video> newCurr(){
        List<Video> videos = jgyservice.newCurr();
        return  videos;
    }

    @RequestMapping("freeCurr")
    @ResponseBody
    public List<Video> freeCurr(){
        List<Video> videos = jgyservice.freeCurr();
        return  videos;
    }

    @RequestMapping("queryVideoAll")
    @ResponseBody
    public void queryVideoAll(){
        jgyservice.queryVideoAll();
    }



}

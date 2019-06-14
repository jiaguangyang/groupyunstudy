package com.jk.controller;

import com.jk.model.Video;
import com.jk.rmi.ThisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;

@RequestMapping("jgy")
@Controller
public class jgyContro {
    @Autowired
    private ThisClient thisClient;

    @RequestMapping("findLunBo")
    @ResponseBody
    public String findLunBo(){
        return thisClient.findLunBo();
    }

    @RequestMapping("find")
    public String find(String url){
        return url;
    }

    @RequestMapping("careCurr")
    @ResponseBody
    public List<Video> careCurr(){
        List<Video> videos = thisClient.careCurr();
        return  videos;
    }

    @RequestMapping("newCurr")
    @ResponseBody
    public List<Video> newCurr(){
        List<Video> videos = thisClient.newCurr();
        return  videos;
    }

    @RequestMapping("freeCurr")
    @ResponseBody
    public List<Video> freeCurr(){
        List<Video> videos = thisClient.freeCurr();
        return  videos;
    }
}

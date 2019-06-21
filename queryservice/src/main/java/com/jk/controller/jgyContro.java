package com.jk.controller;

import com.jk.model.Comment;
import com.jk.model.Coupon;
import com.jk.model.Video;
import com.jk.service.jgyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("queryComments")
    @ResponseBody
    public HashMap<String,Object> queryComments(Integer videoid,Integer page,Integer rows){
        return  jgyservice.queryComments(videoid,page,rows);
    }

    @RequestMapping("dindan")
    @ResponseBody
    public   boolean dindan(String uname,String order, String gmtpayment,String invoiceamount,String videoName, String videourl,Integer videoid){
       return jgyservice.dindan(uname,order,gmtpayment,invoiceamount,videoName,videourl,videoid);
    }

    @RequestMapping("addyouhui")
    @ResponseBody
  public   void addyouhui(String state,String uname){
        jgyservice.addyouhui(state,uname);
    }

    @RequestMapping("youhui")
    @ResponseBody
    public String youhui(String uname, String state){
        return jgyservice.youhui(uname,state);
    }

    @RequestMapping("addyouhui2")
    @ResponseBody
    public Coupon addyouhui2(String uname){
   return jgyservice.addyouhui2(uname);
    }

    @RequestMapping("updyou")
    @ResponseBody
    public  void updyou(String uname){
        jgyservice.updyou(uname);
    }

}

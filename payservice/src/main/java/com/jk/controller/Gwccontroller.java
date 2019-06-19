package com.jk.controller;
import com.alibaba.fastjson.JSON;

import com.jk.model.Video;
import com.jk.service.GwcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.*;

@Controller
public class Gwccontroller {



      @Autowired
    GwcService gwcService;
    @Autowired
    JedisPool jedisPool;

    @RequestMapping("dindan")
  public   void dindan(String uname,String order, String gmtpayment,String invoiceamount,String videoName, String videourl){
        gwcService.dindan(uname,order,gmtpayment,invoiceamount,videoName,videourl);
    }


     @RequestMapping("addgwc")
     @ResponseBody
    public String addgwc(Integer id){
        String str = "";
         Jedis jedis = jedisPool.getResource();
         //Gwc gwc = gwcService.FindGwc(id);
        // Video gwc2=new Video();
         String uname = jedis.get("name");
         List<Video> lists2 = new ArrayList<>();
         List<String> jsonString = jedis.hmget("videolist", "video" + id);
         Video video = JSON.parseObject(jsonString.get(0), Video.class);

         /*List<String> list2=  jedis.lrange("food",0,-1);
         //获取所有的商品
         for (int i=0;i<list2.size();i++){
             Video gwc1 = JSON.parseObject(list2.get(i), Video.class);
             lists2.add(gwc1);
         }
         for (int i=0;i<lists2.size();i++){
             if(lists2.get(i).getId()==id){
                 gwc2=lists2.get(i);
                 break;
             }
         }*/
         List<Video> lists = new ArrayList<>();
         List<String > list = jedis.lrange(uname,0,-1);
         //获取当前购物车的商品
         for (int j=0;j<list.size();j++){
             Video gwc1 = JSON.parseObject(list.get(j), Video.class);
             lists.add(gwc1);
         }
          if(uname!=null){
              if (jedis.exists(uname)){

                  for (int z=0;z<lists.size();z++){
                      if (lists.get(z).getId()==video.getId()){
                          str="3";
                          jedis.close();
                          return  str;
                      }
                  }

                      jedis.rpush(uname,JSON.toJSONString(video));
                      str="2";
                  jedis.close();
                      return  str;

              }else {
                  jedis.rpush(uname,JSON.toJSONString(video));
                  str="2";
                  jedis.close();
                  return  str;
              }
          }else{
              str="1";
              jedis.close();
              return  str;
          }
        //return  str;
     }

    @RequestMapping("findgwc")
    @ResponseBody
      public HashMap<String,Object> findgwc(){

        Jedis jedis = jedisPool.getResource();
         HashMap<String,Object> map = new HashMap<>();
      //  String uname = (String)session.getAttribute("uname");
        String uname = jedis.get("name");
        List<Video> lists = new ArrayList<>();
        if (jedis.exists(uname)){
            List<String > list = jedis.lrange(uname,0,-1);

            for (int i=0;i<list.size();i++){
                Video gwc = JSON.parseObject(list.get(i), Video.class);
                lists.add(gwc);
            }
            map.put("data",lists);
            map.put("code",0);
        }
        jedis.close();
        map.put("code",0);
         return map;
    }
    @RequestMapping("delgwc")
    @ResponseBody
    public  String delgwc(String ids){
        String [] arrs= ids.split(",");

        int[] ints = new int[arrs.length];

        for(int i=0;i<arrs.length;i++){

            ints[i] = Integer.parseInt(arrs[i]);

        }
        System.out.println(ints);


        Jedis jedis = jedisPool.getResource();
        HashMap<String,Object> map = new HashMap<>();
        //String uname = (String)session.getAttribute("uname");
        String uname = jedis.get("name");
        List<Video> lists = new ArrayList<>();
        List<String > list = jedis.lrange(uname,0,-1);
        for (int i=0;i<list.size();i++){
            Video gwc = JSON.parseObject(list.get(i), Video.class);
            lists.add(gwc);
        }
        for (int i=0;i<lists.size();i++){
            for (int z=0;z<ints.length;z++){
           if(lists.get(i).getId()==ints[z]){
               jedis.lrem("zpy",1,JSON.toJSONString(lists.get(i)));
           }
        }
        }
        jedis.close();
         return null;
    }
    @ResponseBody
    @RequestMapping("jieshuan")
    public  double jieshuan(String ids){

        double d = 0;
             if (ids==""){
                 return d;
             }
        Jedis jedis = jedisPool.getResource();
             jedis.set("jie",ids);
             String[] arrs = ids.split(",");

             int[] ints = new int[arrs.length];

             for (int i = 0; i < arrs.length; i++) {

                 ints[i] = Integer.parseInt(arrs[i]);

             }




        HashMap<String,Object> map = new HashMap<>();
       // String uname = (String)session.getAttribute("uname");
        String uname = jedis.get("name");
        List<Video> lists = new ArrayList<>();
        List<String > list = jedis.lrange(uname,0,-1);
        for (int i=0;i<list.size();i++){
            Video gwc = JSON.parseObject(list.get(i), Video.class);
            lists.add(gwc);
        }
        for (int i=0;i<lists.size();i++){
            for (int z=0;z<ints.length;z++){
                if(lists.get(i).getId()==ints[z]){
                    d+= lists.get(i).getVideoPrice();
                }
            }
        }
        jedis.close();



         return d;
    }

@RequestMapping("count")
@ResponseBody
public Integer count(){
    Jedis jedis = jedisPool.getResource();
    HashMap<String,Object> map = new HashMap<>();
    String uname = jedis.get("name");
    //String uname = (String)session.getAttribute("uname");
    List<Video> lists = new ArrayList<>();
    List<String > list = jedis.lrange(uname,0,-1);
    for (int i=0;i<list.size();i++){
        Video gwc = JSON.parseObject(list.get(i), Video.class);
        lists.add(gwc);
    }
    jedis.close();
         return lists.size();
}







}

package com.jk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jk.mapper.jgyMapper;
import com.jk.model.Ossbean;
import com.jk.model.Video;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Service
public class jgyServiceImpl implements jgyService {
    @Autowired
    private jgyMapper jgymapper;

    @Autowired
    private JedisPool jedisPool;


    @Override
    public String findLunBo() {
        Jedis jedis = jedisPool.getResource();
        if (StringUtils.isNotEmpty(jedis.get("lunbo"))){
            String lunbo = jedis.get("lunbo");
            jedis.close();
            return lunbo;
        }else{
           List<Ossbean> list= jgymapper.findLunBo();
            String jsonString = JSON.toJSONString(list);
            jedis.set("lunbo",jsonString);
            jedis.close();
            return jsonString;
        }

    }

    @Override
    public List<Video> careCurr() {
      List<Video> list = new ArrayList<>();
        Jedis jedis = jedisPool.getResource();
        if (StringUtils.isNotEmpty(jedis.get("careCurr"))){
            String careCurr = jedis.get("careCurr");
            list = JSONArray.parseArray(careCurr, Video.class);
        }else{
            list=   jgymapper.careCurr();
            jedis.setex("careCurr",86400000,JSON.toJSONString(list));
        }
        jedis.close();
        Random random = new Random();
        List<Video> list1 = new ArrayList<>();
        int listindex=0;
        for (int i=0;i<5;i++){
            listindex = random.nextInt(list.size() - 1);
            list1.add(list.get(listindex));
            list.remove(listindex);
        }
        return list1;
    }

    @Override
    public List<Video> newCurr() {
        List<Video> list = new ArrayList<>();
        Jedis jedis = jedisPool.getResource();
        if (StringUtils.isNotEmpty(jedis.get("newCurr"))){
            String careCurr = jedis.get("newCurr");
            list = JSONArray.parseArray(careCurr, Video.class);
        }else{
            list=   jgymapper.newCurr();
            jedis.setex("newCurr",86400000,JSON.toJSONString(list));
        }
        jedis.close();
        Random random = new Random();
        List<Video> list2 = new ArrayList<>();
        int listindex=0;
        for (int i=0;i<5;i++){
            listindex = random.nextInt(list.size() - 1);
            list2.add(list.get(listindex));
            list.remove(listindex);
        }
        return list2;
    }

    @Override
    public List<Video> freeCurr() {
        List<Video> list = new ArrayList<>();
        Jedis jedis = jedisPool.getResource();
        if (StringUtils.isNotEmpty(jedis.get("freeCurr"))){
            String careCurr = jedis.get("freeCurr");
            list = JSONArray.parseArray(careCurr, Video.class);
        }else{
            list=   jgymapper.freeCurr();
            jedis.setex("freeCurr",86400000,JSON.toJSONString(list));
        }
        jedis.close();
        Random random = new Random();
        List<Video> list2 = new ArrayList<>();
        int listindex=0;
        for (int i=0;i<5;i++){
            listindex = random.nextInt(list.size() - 1);
            list2.add(list.get(listindex));
            list.remove(listindex);
        }
        return list2;
    }

    @Override
    public void queryVideoAll() {
      List<Video> list= jgymapper.queryVideoAll();
        Jedis jedis = jedisPool.getResource();
        HashMap<String, String> map = new HashMap<>();
        for (Video video:list){
              String jsonString = JSON.toJSONString(video);
              map.put("video"+video.getId(),jsonString);
          }
        jedis.hmset("videolist",map);
        jedis.close();
    }




}

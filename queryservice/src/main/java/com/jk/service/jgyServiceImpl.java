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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
        Jedis jedis = jedisPool.getResource();Integer a =86400000;
        if (StringUtils.isNotEmpty(jedis.get("careCurr"))){
            String careCurr = jedis.get("careCurr");
            list = (List<Video>) JSONArray.parseArray(careCurr, Video.class);
        }else{
            list=   jgymapper.careCurr();
        }
        return list;
    }
}

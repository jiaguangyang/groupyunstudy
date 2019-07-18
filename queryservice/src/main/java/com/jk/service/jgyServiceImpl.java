package com.jk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jk.mapper.jgyMapper;
import com.jk.model.Comment;
import com.jk.model.Coupon;
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

   /* @Autowired
    private MongoTemplate mongoTemplate;*/


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
            listindex = random.nextInt(list.size());
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
            listindex = random.nextInt(list.size());
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
            listindex = random.nextInt(list.size());
            list2.add(list.get(listindex));
            list.remove(listindex);
        }

        return list2;
    }

    @Override
    public void queryVideoAll() {

        Jedis jedis = jedisPool.getResource();
            List<Video> list= jgymapper.queryVideoAll();
            HashMap<String, String> map = new HashMap<>();
            for (Video video:list){
                String jsonString = JSON.toJSONString(video);
                map.put("video"+video.getId(),jsonString);
            }
            jedis.hmset("videolist",map);


        jedis.close();
    }

    @Override
    public HashMap<String,Object> queryComments(Integer videoid,Integer page,Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
       /* Criteria criteria = new Criteria();
        criteria.and("videoid").is(videoid);
        List<Comment> list = mongoTemplate.find(new Query(criteria).with(new Sort(Sort.Direction.DESC, "commentDate")).skip((page - 1) * rows).limit(rows), Comment.class, "Comment");
        long count = mongoTemplate.count(new Query(criteria), "Comment");
        map.put("list",list);
        map.put("total",count);*/
        return map;
    }

    @Override
    public boolean dindan(String uname, String order, String gmtpayment, String invoiceamount, String videoName, String videourl,Integer videoid) {
       boolean flag;
        try{
            jgymapper.dindan(uname,order,gmtpayment,invoiceamount,videoName,videourl,videoid);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    public void addyouhui(String state, String uname) {
        Coupon coupon = new Coupon();
        if (state.equals("1")){
            Date date = new Date();
            long time = date.getTime();
            long day = 7*24*60*60*1000;
            time+=day;
            coupon.setCouponMoney(100.00);
            coupon.setCouponName("全民优惠,限时领取");
            coupon.setState(state);
            coupon.setUserName(uname);
            coupon.setCouponStatus(1);
            coupon.setStartTime(date);
            coupon.setEndTime(new Date(time));
            jgymapper.addyouhui(coupon);
        }

    }

    @Override
    public String youhui(String uname, String state) {
        String yhj ="";
        Coupon coupon = jgymapper.youhui(uname,state);
        if(coupon!=null){
            yhj="1";
        }else {
            yhj="2";
        }
        return yhj;
    }

    @Override
    public Coupon addyouhui2(String uname) {
        Coupon coupon =  jgymapper.addyouhui2(uname);
        return coupon;
    }

    @Override
    public void updyou(String you) {
        jgymapper.updyou(you);
    }

}

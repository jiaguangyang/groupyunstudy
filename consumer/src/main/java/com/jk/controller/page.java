package com.jk.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("page")
public class page {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("find")
    public String find(String url){

        return url;
    }
    @RequestMapping("tologin")
    public String loginPopup(){
        return "login";
    }
    @RequestMapping("toteacherLogin")
    public String teacherLogin(){
        return "teacherLogin";
    }
    @RequestMapping("toregters")
    public String toregters(){
        return "regters";
    }
    @RequestMapping("myVideos")
    public String myVideos(){
        return "myVideos";
    }
    @RequestMapping("addvideo")
    public String addvideo(){
        return "addvideo";
    }
    @RequestMapping("center")
    public String center(){
        Jedis jedis = jedisPool.getResource();
        String teacherId = jedis.get("teacherId");
        String userId = jedis.get("userId");
        String jgId = jedis.get("jgId");
        if (StringUtils.isNotEmpty(teacherId)&&!StringUtils.isNotEmpty(userId)&&!StringUtils.isNotEmpty(jgId)){
            return "redirect:mycenter";
        }
        if (StringUtils.isNotEmpty(userId)&&!StringUtils.isNotEmpty(teacherId)&&!StringUtils.isNotEmpty(jgId)){
            return "redirect:userCenter";
        }
        if (StringUtils.isNotEmpty(jgId)&&!StringUtils.isNotEmpty(teacherId)&&!StringUtils.isNotEmpty(userId)){
            return "redirect:institution";
        }
        if (!StringUtils.isNotEmpty(teacherId)&&!StringUtils.isNotEmpty(userId)&&!StringUtils.isNotEmpty(jgId)){
          return "redirect:tologin";
        }

       /* if (userId==null||userId.equals("")||userId.equals("null")){
            return "redirect:tologin";
        }*/


        jedis.close();
         return null;
    }
    @RequestMapping("myInformation")
    public String myInformation(){
        return "myInformation";
    }
    @RequestMapping("updatePassword")
    public String updatePassword(){
        return "updatePassword";
    }
    @RequestMapping("userCenter")
    public String userCenter(){
        return "userCenter";
    }
    @RequestMapping("mycenter")
    public String mycenter(){
        return "mycenter";
    }
    @RequestMapping("userDatum")
    public String userDatum(){
        return "userDatum";
    }
    @RequestMapping("updateUserPassword")
    public String updateUserPassword(){
        return "updateUserPassword";
    }
    @RequestMapping("institution")
    public String institution(){
        return "institution";
    }
    @RequestMapping("jigouRegister")
    public String jigouRegister(){
        return "jigouRegister";
    }
    @RequestMapping("updateInstitution")
    public String updateInstitution(){
        return "updateInstitution";
    }
    @RequestMapping("updateIsntPassword")
    public String updateIsntPassword(){
        return "updateIsntPassword";
    }
    @RequestMapping("jgteacher")
    public String jgteacher(){
        return "jgteacher";
    }
    @RequestMapping("mydingdan")
    public String mydingdan(){
        return "mydingdan";
    }
}

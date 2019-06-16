package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.jk.model.TeacherBean;
import com.jk.model.User;
import com.jk.rmi.LoginService;

import com.jk.util.AliyunOSSUtil;

import com.jk.util.ConstanConf;
import com.jk.util.Md5Util;
import groovy.transform.ThreadInterrupt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("queryLogin")
    @ResponseBody
    public HashMap<String,Object> queryLogin(String name, String password){
        return loginService.queryLogin(name,password);
    }
    @Transactional(readOnly = true)
    @RequestMapping("qyertUserName")
    @ResponseBody
    public String qyertUserName(){
        Jedis jedis = jedisPool.getResource();
        String loginName = jedis.get("name");
        if (loginName != null&& !loginName.equals("")&& !loginName.equals("null")) {
            User user = JSON.parseObject(loginName, User.class);
            String name = user.getName();
            jedis.close();
            return name;
        }
        jedis.close();
       return null;


    }
    @Transactional(readOnly = true)
    @RequestMapping("addUser")
    @ResponseBody
    public HashMap<String,Object> addUser(TeacherBean teacherBean){

        return loginService.addUser(teacherBean);
    }
    @RequestMapping("sendcode")
    @ResponseBody
    public HashMap<String,Object> sendcode(String phone) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        Jedis jedis = jedisPool.getResource();
        String code = jedis.get("code");
        if (code!=null&&!code.equals("")&&!code.equals("null")) {
            result.put(phone+"code", 2);
            result.put("msg","请在一分钟后发送");
            return result;
        }
        params.put("accountSid", ConstanConf.ACCOUNTSID);
        params.put("to",phone);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        params.put("timestamp", timestamp);
        String sig = Md5Util.getMd532(ConstanConf.ACCOUNTSID+ConstanConf.AUTH_TOKEN+timestamp);
        params.put("sig", sig);
        params.put("templateid",ConstanConf.TEMPLATEID);
        Integer random=(int) (Math.random()*899999+100000);
        System.out.println(random);
        jedis.setex("code",300,random+"");
        //redisTemplate.opsForValue().set(ConstanConf.SMS_LOHIN_CODE+"sss",random,ConstanConf.SMS_CODE_TIME, TimeUnit.MINUTES);
        //session.setAttribute("sss", random);
        params.put("param", random);
       /* String post = HttpClientUtil.post(ConstanConf.SMS_URL,params);
        JSONObject parseObject = JSON.parseObject(post);
        String respCode = parseObject.getString("respCode");
        System.out.println(respCode);*/
        if (ConstanConf.SMS_SUCCESS.equals("00000")) {
          jedis.setex(phone+"code",60,random+"");
            result.put("code", 0);
            result.put("msg","发送成功");
            jedis.close();
            return result;
        }else {
            result.put("code", 1);
            result.put("msg","发送失败");
            jedis.close();
            return result;
        }

    }
    @RequestMapping("phoneCode")
    @ResponseBody
    public HashMap<String,Object> phoneCode(String phone,String code) {
        HashMap<String, Object> result = new HashMap<>();
        Jedis jedis = jedisPool.getResource();
        String code1 = jedis.get("code");
        if (code.equals(code1)) {
            result.put("msg", "手机验证成功");
            jedis.set("phone",phone);
            jedis.close();
            return result;
        } else {
            result.put("msg", "验证码过期");
            jedis.close();
            return result;
        }
    }
    @RequestMapping("imgUpload")
    @ResponseBody
    public  HashMap<String,Object> imgUpload( MultipartFile companyLogo){

        String filename = companyLogo.getOriginalFilename();
        System.out.println(filename);
        HashMap<String, Object> map = new HashMap<>();
        try {

            if (companyLogo != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(companyLogo.getBytes());
                    os.close();
                    companyLogo.transferTo(newFile);
                    // 上传到OSS
                    String uploadUrl = AliyunOSSUtil.upLoad(newFile);

                    map.put("imgId",uploadUrl);
                    return  map;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        map.put("imgId","");
        return  map;
    }
    @Transactional(readOnly = true)
    @RequestMapping("queryZhuying")
    @ResponseBody
    public List<TeacherBean> queryZhuying(){
        return  loginService.queryZhuying();

    }
}

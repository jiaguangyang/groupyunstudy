package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.jk.model.*;
import com.jk.rmi.LoginService;

import com.jk.rmi.ThisClient;
import com.jk.util.AliyunOSSUtil;

import com.jk.util.ConstanConf;
import com.jk.util.Md5Util;
import groovy.transform.ThreadInterrupt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.reflect.generics.tree.Tree;

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

    @RequestMapping("buyVideo")
    public String buyVideo(Integer vid){
        Jedis jedis = jedisPool.getResource();
        String userId = jedis.get("userId");
        if (StringUtils.isNotEmpty(userId)){
            return"";
        }
        return "login";
    }

    @RequestMapping("queryLogin")
    @ResponseBody
    public HashMap<String,Object> queryLogin(String name, String password){
        return loginService.queryLogin(name,password);
    }

    @RequestMapping("queryUserName")
    @ResponseBody
    public String qyertUserName(){
        Jedis jedis = jedisPool.getResource();
        String name = jedis.get("name");
        if (name != null&& !name.equals("")&& !name.equals("null")) {
            //User user = JSON.parseObject(loginName, User.class);

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
        jedis.setex("random"+phone,300,random+"");
        System.out.println(random);

        //redisTemplate.opsForValue().set(ConstanConf.SMS_LOHIN_CODE+"sss",random,ConstanConf.SMS_CODE_TIME, TimeUnit.MINUTES);
        //session.setAttribute("sss", random);
        params.put("param", random);
       /* String post = HttpClientUtil.post(ConstanConf.SMS_URL,params);
        JSONObject parseObject = JSON.parseObject(post);
        String respCode = parseObject.getString("respCode");
        System.out.println(respCode);*/
        if (ConstanConf.SMS_SUCCESS.equals("00000")) {
          jedis.setex(phone+"code",300,random+"");
            jedis.setex("code",60,random+"");
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
        String code1 = jedis.get(phone+"code");
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
    public  HashMap<String,Object> imgUpload( MultipartFile videoImg){

        String filename = videoImg.getOriginalFilename();
        System.out.println(filename);
        HashMap<String, Object> map = new HashMap<>();
        try {

            if (videoImg != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(videoImg.getBytes());
                    os.close();
                    videoImg.transferTo(newFile);
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

    @RequestMapping("queryTeacherLogin")
    @ResponseBody
    public HashMap<String,Object> queryTeacherLogin(TeacherBean teacherBean){
        if (teacherBean.getType()==1) {
            return loginService.queryTeacherLogin(teacherBean);
        }else {
            return loginService.queryInstitutionLogin(teacherBean);
        }
    }
    @RequestMapping("uploadVideo")
    @ResponseBody
    public HashMap<String,Object> addVideo(MultipartFile videourl){
        String filename = videourl.getOriginalFilename();
        System.out.println(filename);
        HashMap<String, Object> map = new HashMap<>();
        try {

            if (videourl != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(videourl.getBytes());
                    os.close();
                    videourl.transferTo(newFile);
                    // 上传到OSS
                    String uploadUrl = AliyunOSSUtil.upLoad(newFile);
                    System.out.println(uploadUrl);

                    map.put("imgId",uploadUrl);
                    map.put("msg","上传成功");
                    return  map;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        map.put("msg","上传失败");
        map.put("imgId","");
        return  map;

    }

    @RequestMapping("addViden")
    @ResponseBody
    public HashMap<String,Object> addViden(VidenBean videnBean){


        return loginService.addViden(videnBean);
    }
    @RequestMapping("queryTree")
    @ResponseBody
    public List<TreeBean> queryTree(){
     return loginService.queryTree();
    }

    @RequestMapping("queryTeatherById")
    @ResponseBody
    public TeacherBean queryTeatherById(){
        return loginService.queryTeatherById();
    }

    @RequestMapping("uploadRegtrs")
    @ResponseBody
    public HashMap<String,Object> uploadRegtrs(TeacherBean teacherBean){
        return loginService.uploadRegtrs(teacherBean);
    }
    @RequestMapping("updatePassword")
    @ResponseBody
    public Boolean updatePassword(String password){
        return loginService.updatePassword(password);
    }
    @RequestMapping("addRegUser")
    @ResponseBody
    public HashMap<String,Object> addRegUser(User user){
      return   loginService.addRegUser(user);
    }
    @RequestMapping("queryUserTree")
    @ResponseBody
    public List<UserTree>queryUserTree(){
        return loginService.queryUserTree();
    }
    @RequestMapping("queryUserById")
    @ResponseBody
    public User queryUserById(){
        return loginService.queryUserById();
    }
    @RequestMapping("updateUserById")
    @ResponseBody
    public HashMap<String,Object> updateUserById(User user){
        return loginService.updateUserById(user);
    }
    @RequestMapping("updateUserPassword")
    @ResponseBody
    public Boolean updateUserPassword(String password){
        return loginService.updateUserPassword(password);
    }
    @RequestMapping("addVideo")
    @ResponseBody
    public Boolean addVideo(Video video){
        if (video.getId()==null){
        return loginService.addVideo(video);}
        else {
            return loginService.updateVideoById(video);
        }

    }
    @RequestMapping("queryProject")
    @ResponseBody
    public  HashMap<String, Object> queryProject(Integer page,Integer limit){
        return loginService.queryProject(page,limit);
    }
    @RequestMapping("addInformation")
    @ResponseBody
    public HashMap<String, Object> addInformation(Institutions institutions){
        return loginService.addInformation(institutions);
    }
    @RequestMapping("queryJgTree")
    @ResponseBody
    public List<JgTree> queryJgTree(){
        return loginService.queryJgTree();
    }
    @RequestMapping("queryupdateInstitution")
    @ResponseBody
    public Institutions queryupdateInstitution(){
        return loginService.queryupdateInstitution();
    }
    @RequestMapping("updateInformation")
    @ResponseBody
    public HashMap<String, Object> updateInformation(Institutions institutions){
        return loginService.updateInformation(institutions);
    }
    @RequestMapping("updateInPassword")
    @ResponseBody
    public Boolean updateInPassword(String password){
        return loginService.updateInPassword(password);
    }
    @RequestMapping("queryJgTeacher")
    @ResponseBody
    public HashMap<String,Object> queryJgTeacher(Integer page,Integer limit ){
        return loginService.queryJgTeacher(page,limit);
    }
    @RequestMapping("queryJgteacher")
    @ResponseBody
    public HashMap<String,Object> queryJgteacher(Integer page,Integer limit ,Integer teacherId){
        return loginService.queryJgteacher(page,limit,teacherId);
    }
    @RequestMapping("phoneLogin")
    @ResponseBody
    public HashMap<String,Object> phoneLogin(String phone,Integer code){
        return loginService.phoneLogin(phone,code);
    }
  @RequestMapping("queryDingdan")
    @ResponseBody
    public HashMap<String,Object> queryDingdan(Integer page,Integer limit){
        return loginService.queryDingdan(page,limit);
    }
    @RequestMapping("queryMyVideoByid")
    @ResponseBody
    public Video queryMyVideoByid(Integer id){
        return loginService.queryMyVideoByid(id);
    }
}

package com.jk.loginservice.controller;

import com.jk.loginservice.service.LoginService;
import com.jk.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;
import sun.reflect.generics.tree.Tree;

import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private JedisPool jedisPool;
    //@Transactional(readOnly = true)
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> login(@RequestParam("name") String name, @RequestParam("password") String password){
        HashMap<String, Object> login = loginService.login(name, password);
        return login ;
    }

    @RequestMapping("queryZhuying")
    @ResponseBody
   public List<TeacherBean> queryZhuying(){
        return loginService.queryZhuying();
    };

    @RequestMapping("addUser")
    @ResponseBody
    public HashMap<String,Object> addUser(@RequestBody TeacherBean teacherBean){
        return loginService.addUser(teacherBean);
    }
    @RequestMapping("queryTeacherLogin")
    @ResponseBody
    public HashMap<String,Object> queryTeacherLogin(@RequestBody TeacherBean teacherBean) {
        return loginService.queryTeacherLogin(teacherBean);
    }
    @RequestMapping("addViden")
    @ResponseBody
    public HashMap<String,Object> addViden(@RequestBody VidenBean videnBean) {
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
    public HashMap<String,Object> uplodRegtrs(@RequestBody TeacherBean teacherBean){
        return loginService.uploadRegtrs(teacherBean);
    }
    @RequestMapping("updatePassword")
    @ResponseBody
    public Boolean updatePassword(@RequestParam("password") String password){
        return loginService.updatePassword(password);
    }
    @RequestMapping("addRegUser")
    @ResponseBody
    public HashMap<String,Object> addRegUser(@RequestBody User user){
        return loginService.addRegUser(user);
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
    public HashMap<String,Object> updateUserById(@RequestBody User user){
        return loginService.updateUserById(user);
    }
    @RequestMapping("updateUserPassword")
    @ResponseBody
    public Boolean updateUserPassword(String password){
        return loginService.updateUserPassword(password);
    }
    @RequestMapping("addVideo")
    @ResponseBody
    public Boolean addVideo(@RequestBody Video video){
        return loginService.addVideo(video);
    }
    @RequestMapping("queryProject")
    @ResponseBody
    public  HashMap<String, Object> queryProject(@RequestParam("page")Integer page,@RequestParam("limit") Integer limit,@RequestParam("teacherId1")Integer teacherId1){
        return loginService.queryProject(page,limit,teacherId1);
    }
    @RequestMapping("queryInstitutionLogin")
    @ResponseBody
    public HashMap<String, Object> queryInstitutionLogin(@RequestBody TeacherBean teacherBean){
       return loginService.queryInstitutionLogin(teacherBean);
    }
   @RequestMapping("addInformation")
    @ResponseBody
    public HashMap<String, Object> addInformation(@RequestBody Institutions institutions){
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
    public HashMap<String, Object> updateInformation(@RequestBody Institutions institutions){
        return loginService.updateInformation(institutions);
    }
    @RequestMapping("updateInPassword")
    @ResponseBody
    public Boolean updateInPassword(@RequestParam("password")String password){
        return loginService.updateInPassword(password);
    }
    @RequestMapping("queryJgTeacher")
    @ResponseBody
    public HashMap<String,Object> queryJgTeacher(@RequestParam("page")Integer page,@RequestParam("limit") Integer limit){
        return loginService.queryJgTeacher(page,limit);
    }
    @RequestMapping("queryJgteacher")
    @ResponseBody
    public HashMap<String,Object> queryJgteacher(@RequestParam("page")Integer page,@RequestParam("limit") Integer limit,@RequestParam("limit")Integer teacherId){
        return loginService.queryJgteacher(page,limit,teacherId);
    }
    @RequestMapping("queryDingdan")
    @ResponseBody
    public HashMap<String,Object> queryDingdan(@RequestParam("page")Integer page,@RequestParam("limit")Integer limit ){
        return loginService.queryDingdan(page,limit);
    }

}

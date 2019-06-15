package com.jk.loginservice.controller;

import com.jk.loginservice.service.LoginService;
import com.jk.model.TeacherBean;
import com.jk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
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
}

package com.jk.loginservice.controller;

import com.jk.loginservice.service.LoginService;
import com.jk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Transactional(readOnly = true)
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> login(User user){
        return loginService.login(user);
    }
}

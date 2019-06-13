package com.jk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("page")
public class page {
    @RequestMapping("find")
    public String find(String url){

        return url;
    }
    @RequestMapping("tologin")
    public String loginPopup(){
        return "login";
    }

}

package com.jk.controller;

import com.jk.service.EsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class EsController {
    @Autowired
    EsService esService;
    @RequestMapping("/searchByKeywordsAndHighlightField")
    @ResponseBody
    public HashMap<String , Object> searchByKeywordsAndHighlightField(String keywords,Integer start,Integer pageSize){
        return esService.searchByKeywordsAndHighlightField(keywords,start,pageSize);
    }


}

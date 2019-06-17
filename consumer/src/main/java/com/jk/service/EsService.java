package com.jk.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@FeignClient("queryservice")
public interface EsService {
    @RequestMapping("/searchByKeywordsAndHighlightField")
    @ResponseBody
    HashMap<String, Object> searchByKeywordsAndHighlightField(@RequestParam("keywords") String keywords,@RequestParam("start") Integer start,@RequestParam("pageSize") Integer pageSize);

}

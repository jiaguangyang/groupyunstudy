package com.jk.rmi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;

@FeignClient("queryservice")
public interface ThisClient {



    @RequestMapping("findLunBo")
    @ResponseBody
    String findLunBo();
}

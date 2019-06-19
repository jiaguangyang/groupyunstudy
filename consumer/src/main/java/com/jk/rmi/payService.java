package com.jk.rmi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@FeignClient("payservice")
public interface payService {

    @RequestMapping("addgwc")
    @ResponseBody
    String addgwc(@RequestParam("id") Integer id);

    @RequestMapping("findgwc")
    @ResponseBody
    HashMap<String, Object> findgwc();

    @RequestMapping("delgwc")
    @ResponseBody
    String delgwc(@RequestParam("ids")String ids);

    @ResponseBody
    @RequestMapping("jieshuan")
    double jieshuan(@RequestParam("ids")String ids);

    @RequestMapping("count")
    @ResponseBody
    Integer count();

    @RequestMapping("dindan")
    void dindan(@RequestParam("uname")String uname, @RequestParam("order")String order,@RequestParam("gmtpayment") String gmtpayment,@RequestParam("invoiceamount") String invoiceamount, @RequestParam("videoName")String videoName, @RequestParam("videourl")String videourl);
}

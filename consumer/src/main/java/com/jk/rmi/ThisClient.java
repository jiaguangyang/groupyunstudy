package com.jk.rmi;

import com.jk.model.Coupon;
import com.jk.model.Comment;
import com.jk.model.Video;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.reflect.generics.tree.Tree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@FeignClient("queryservice")
public interface ThisClient {



    @RequestMapping("findLunBo")
    @ResponseBody
    String findLunBo();

    @RequestMapping("careCurr")
    @ResponseBody
    List<Video> careCurr();

    @RequestMapping("newCurr")
    @ResponseBody
    List<Video> newCurr();

    @RequestMapping("freeCurr")
    @ResponseBody
    List<Video> freeCurr();

    @RequestMapping("queryVideoAll")
    @ResponseBody
    void queryVideoAll();

    @RequestMapping("queryComments")
    @ResponseBody
    HashMap<String,Object> queryComments(@RequestParam("videoid") Integer videoid, @RequestParam("page")Integer page, @RequestParam("rows")Integer rows);

    @RequestMapping("dindan")
    @ResponseBody
    boolean dindan(@RequestParam("uname")String uname, @RequestParam("order")String order,@RequestParam("gmtpayment") String gmtpayment,@RequestParam("invoiceamount") String invoiceamount, @RequestParam("videoName")String videoName, @RequestParam("videourl")String videourl,@RequestParam("videoid")Integer videoid);

    @RequestMapping("addyouhui")
    @ResponseBody
    void addyouhui(@RequestParam("state")String state,@RequestParam("uname") String uname);

    @RequestMapping("youhui")
    @ResponseBody
    String youhui(@RequestParam("uname")String uname,@RequestParam("state") String state);

    @RequestMapping("addyouhui2")
    @ResponseBody
    Coupon addyouhui2(@RequestParam("uname")String uname);

    @RequestMapping("updyou")
    @ResponseBody
    void updyou(@RequestParam("uname")String uname);
}

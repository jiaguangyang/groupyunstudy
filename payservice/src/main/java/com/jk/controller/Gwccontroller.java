package com.jk.controller;
import com.alibaba.druid.util.jdbc.StatementBase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jk.model.Gwc;
import com.jk.model.Video;
import com.jk.service.GwcService;
import com.mysql.jdbc.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.config.ZfbConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import static com.jk.config.ZfbConfig.return_url;

@Controller
public class Gwccontroller {
    String app_id =ZfbConfig.app_id;
    String private_key = ZfbConfig.merchant_private_key;
    String notify_url = ZfbConfig.notify_url;
    String return_url = ZfbConfig.return_url;
    String url = ZfbConfig.gatewayUrl;
    String charset = ZfbConfig.charset;
    String format = ZfbConfig.format;
    String public_key = ZfbConfig.alipay_public_key;
    String signtype = ZfbConfig.sign_type;



   /*   @Autowired
    GwcService gwcService;*/
    @Autowired
    JedisPool jedisPool;



     @RequestMapping("yemian")
      public String  yemian(){
         return "poi";
     }

    @RequestMapping("gwc")
    public String  gwc(){
        return "Gwc";
    }



     /*@RequestMapping("find")
     @ResponseBody
     public HashMap<String,Object> find(HttpSession session){
         String   name = "zzz";
         session.setAttribute("uname","zpy");
         HashMap<String,Object> map = gwcService.find();
         return  map;
     }*/


     @RequestMapping("addgwc")
     @ResponseBody
    public String addgwc(Integer id,HttpSession session ){
        String str = "";
         Jedis jedis = jedisPool.getResource();
         //Gwc gwc = gwcService.FindGwc(id);
         Video gwc2=new Video();
         String uname = (String)session.getAttribute("uname");
         List<Video> lists2 = new ArrayList<>();
         List<String> list2=  jedis.lrange("food",0,-1);
         //获取所有的商品
         for (int i=0;i<list2.size();i++){
             Video gwc1 = JSON.parseObject(list2.get(i), Video.class);
             lists2.add(gwc1);
         }
         for (int i=0;i<lists2.size();i++){
             if(lists2.get(i).getGid()==id){
                 gwc2=lists2.get(i);
                 break;
             }
         }
         List<Video> lists = new ArrayList<>();
         List<String > list = jedis.lrange(uname,0,-1);
         //获取当前购物车的商品
         for (int j=0;j<list.size();j++){
             Video gwc1 = JSON.parseObject(list.get(j), Video.class);
             lists.add(gwc1);
         }
          if(uname!=null){
              if (jedis.exists(uname)){

                  for (int z=0;z<lists.size();z++){
                      if (lists.get(z).getGid()==gwc2.getGid()){
                          str="3";
                          jedis.close();
                          return  str;
                      }
                  }

                      jedis.rpush(uname,JSON.toJSONString(gwc2));
                      str="2";
                  jedis.close();
                      return  str;

              }else {
                  jedis.rpush(uname,JSON.toJSONString(gwc2));
                  str="2";
                  jedis.close();
                  return  str;
              }
          }else{
              str="1";
              jedis.close();
              return  str;
          }
        //return  str;
     }

    @RequestMapping("findgwc")
    @ResponseBody
      public HashMap<String,Object> findgwc(HttpSession session){

        Jedis jedis = jedisPool.getResource();
         HashMap<String,Object> map = new HashMap<>();
        String uname = (String)session.getAttribute("uname");
        List<Gwc> lists = new ArrayList<>();
        if (jedis.exists(uname)){
            List<String > list = jedis.lrange(uname,0,-1);

            for (int i=0;i<list.size();i++){
                Gwc gwc = JSON.parseObject(list.get(i), Gwc.class);
                lists.add(gwc);
            }
            map.put("data",lists);
            map.put("code",0);
        }
        jedis.close();
        map.put("code",0);
         return map;
    }
    @RequestMapping("delgwc")
    @ResponseBody
    public  String delgwc(HttpSession session,String ids){
        String [] arrs= ids.split(",");

        int[] ints = new int[arrs.length];

        for(int i=0;i<arrs.length;i++){

            ints[i] = Integer.parseInt(arrs[i]);

        }
        System.out.println(ints);


        Jedis jedis = jedisPool.getResource();
        HashMap<String,Object> map = new HashMap<>();
        String uname = (String)session.getAttribute("uname");
        List<Gwc> lists = new ArrayList<>();
        List<String > list = jedis.lrange(uname,0,-1);
        for (int i=0;i<list.size();i++){
            Gwc gwc = JSON.parseObject(list.get(i), Gwc.class);
            lists.add(gwc);
        }
        for (int i=0;i<lists.size();i++){
            for (int z=0;z<ints.length;z++){
           if(lists.get(i).getGid()==ints[z]){
               jedis.lrem("zpy",1,JSON.toJSONString(lists.get(i)));
           }
        }
        }
        jedis.close();
         return null;
    }
    @ResponseBody
    @RequestMapping("jieshuan")
    public  Integer jieshuan(String ids,HttpSession session){
        Integer d = 0;
             if (ids==""){
                 return d;
             }

             String[] arrs = ids.split(",");

             int[] ints = new int[arrs.length];

             for (int i = 0; i < arrs.length; i++) {

                 ints[i] = Integer.parseInt(arrs[i]);

             }



        Jedis jedis = jedisPool.getResource();
        HashMap<String,Object> map = new HashMap<>();
        String uname = (String)session.getAttribute("uname");
        List<Gwc> lists = new ArrayList<>();
        List<String > list = jedis.lrange(uname,0,-1);
        for (int i=0;i<list.size();i++){
            Gwc gwc = JSON.parseObject(list.get(i), Gwc.class);
            lists.add(gwc);
        }
        for (int i=0;i<lists.size();i++){
            for (int z=0;z<ints.length;z++){
                if(lists.get(i).getGid()==ints[z]){
                    d+= lists.get(i).getGprive();
                }
            }
        }
        jedis.close();



         return d;
    }

@RequestMapping("count")
@ResponseBody
public Integer count(HttpSession session){
    Jedis jedis = jedisPool.getResource();
    HashMap<String,Object> map = new HashMap<>();
    String uname = (String)session.getAttribute("uname");
    List<Gwc> lists = new ArrayList<>();
    List<String > list = jedis.lrange(uname,0,-1);
    for (int i=0;i<list.size();i++){
        Gwc gwc = JSON.parseObject(list.get(i), Gwc.class);
        lists.add(gwc);
    }
    jedis.close();
         return lists.size();
}

    @RequestMapping("/pay")
    public void pay(HttpServletRequest request, HttpServletResponse response,String money) throws Exception {
        // 模拟从前台传来的数据
        Integer number= (int)(Math.random()*8999+1000);

        String orderNo = new Date()+number.toString(); // 生成订单号// 生成订单号
        System.out.println("生成订单号"+orderNo);
        String totalAmount = money; // 支付总金额
        String subject = "ITAEMBook"; // 订单名称
        System.out.println("订单名称"+subject);
        String body = "reading"; // 商品描述
        // 封装请求客户端
        AlipayClient client = new DefaultAlipayClient(url, app_id, private_key, format, charset, public_key, signtype);


        // 支付请求
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setProductCode("FAST_INSTANT_TRADE_PAY"); // 设置销售产品码
        model.setOutTradeNo(orderNo); // 设置订单号
        model.setSubject(subject); // 订单名称
        model.setTotalAmount(totalAmount); // 支付总金额
        model.setBody(body); // 设置商品描述
        alipayRequest.setBizModel(model);

        String form = client.pageExecute(alipayRequest).getBody(); // 生成表单

        response.setContentType("text/html;charset=" + charset);
        response.getWriter().write(form); // 直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }
    /**
     * 同步跳转
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("returnurl")
    public ModelAndView returnUrl(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();

        // 获取支付宝GET过来反馈信息（官方固定代码）
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }


        boolean signVerified = AlipaySignature.rsaCheckV1(params, public_key, charset, signtype); // 调用SDK验证签名


        signVerified = false;
        if (signVerified) {
            System.out.println("前往支付成功页面");
            mav.setViewName("Poi");
        } else {

            }

        return mav;
    }

    /**
     * 支付宝服务器异步通知
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("notifyurl")
    public void notifyUrl(HttpServletRequest request) throws Exception {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, public_key, charset, signtype); // 调用SDK验证签名

        if (signVerified) { // 验证成功 更新订单信息
            System.out.println("异步通知成功");
            // 商户订单号
            String out_trade_no = request.getParameter("out_trade_no");


            System.out.println("订单号"+out_trade_no );
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            System.out.println("交一状态"+trade_status);
            // 修改数据库
        } else {
            System.out.println("异步通知失败");
        }
    }


    @RequestMapping("tozhifu")
    public  String tozhifu(){

        return "user/zhifu";
    }



}

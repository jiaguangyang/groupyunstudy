package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.jk.model.Coupon;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jk.config.ZfbConfig;
import com.jk.model.Video;
import com.jk.rmi.ThisClient;
import com.jk.rmi.payService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class payController {

    String app_id = ZfbConfig.app_id;
    String private_key = ZfbConfig.merchant_private_key;
    String notify_url = ZfbConfig.notify_url;
    String return_url = ZfbConfig.return_url;
    String url = ZfbConfig.gatewayUrl;
    String charset = ZfbConfig.charset;
    String format = ZfbConfig.format;
    String public_key = ZfbConfig.alipay_public_key;
    String signtype = ZfbConfig.sign_type;

    @Autowired
    private payService payservice;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private ThisClient thisClient;

    @RequestMapping("youhui")
    @ResponseBody
    public  String  youhui(String state){
        Jedis jedis = jedisPool.getResource();

        String str = "";
        String uname = jedis.get("name");

        if (uname!=null){
            String  yhj = thisClient.youhui(uname,state);
            if(yhj.equals("1")){
                str="3";
            }else {
                str="2";
                thisClient.addyouhui(state,uname);
            }
        }else{
            str="1";
            jedis.close();
            return str;

        }
        jedis.close();
        return str;
    }

    @RequestMapping("findyouhui")
    @ResponseBody
    public Coupon findyouhui(){
        Jedis jedis = jedisPool.getResource();
        String uname = jedis.get("name");
        Coupon coupon    = thisClient.addyouhui2(uname);

        return  coupon;
    }

    @RequestMapping("addgwc")
    @ResponseBody
    public String addgwc(Integer id){
       return payservice.addgwc(id);
    }


    @RequestMapping("findgwc")
    @ResponseBody
    public HashMap<String,Object> findgwc(){
        return payservice.findgwc();
    }

    @RequestMapping("delgwc")
    @ResponseBody
    public  String delgwc(HttpSession session, String ids){
        return payservice.delgwc(ids);
    }

    @ResponseBody
    @RequestMapping("jieshuan")
    public  double jieshuan(String ids){
        return payservice.jieshuan(ids);
    }

    @RequestMapping("count")
    @ResponseBody
    public Integer count(){
        return payservice.count();
    }

    @RequestMapping("/pay")
    public void pay(HttpServletResponse response, String money,String id,String you) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String  moneys ="";
        if (you!=null){
            jedis.set("you",you);

            if (you.equals("1")){
                Integer m = Integer.valueOf(money);
                Integer a = m-100;
                moneys  = a.toString();
            }else{
                moneys  = money;
            }

        }else{
            moneys  = money;
        }
        moneys  = money;


        if(StringUtils.isNotEmpty(id)){
            jedis.set("jie",id);
            jedis.close();
        }
        // 模拟从前台传来的数据
        Integer number= (int)(Math.random()*8999+1000);
        long date= new Date().getTime();
        String orderNo =date+number.toString(); // 生成订单号// 生成订单号
        System.out.println("生成订单号"+orderNo);
        String totalAmount = moneys; // 支付总金额
        String subject = "网易云课堂"; // 订单名称
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
    public String returnUrl(HttpServletRequest request) throws Exception {
        // ModelAndView mav = new ModelAndView();

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



        if (signVerified) {
            System.out.println("前往支付成功页面");
            return "show";
        } else {

        }

        return null;
    }

    /**
     * 支付宝服务器异步通知
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("notifyurl")
    public void notifyUrl(HttpServletRequest request,HttpSession session) throws Exception {
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
            String order = request.getParameter("out_trade_no");
            System.out.println("订单号"+order);
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            String invoiceamount = request.getParameter("invoice_amount");//交易金额
            String gmtpayment = request.getParameter("gmt_payment");//交易时间
            System.out.println("交一状态"+trade_status);
            // 修改数据库
            Jedis jedis = jedisPool.getResource();
            String ids =  jedis.get("jie");
            String uname =  jedis.get("name");
            String[] arrs = ids.split(",");

            int[] ints = new int[arrs.length];

            for (int i = 0; i < arrs.length; i++) {

                ints[i] = Integer.parseInt(arrs[i]);

            }
            HashMap<String,Object> map = new HashMap<>();
            List<Video> lists = new ArrayList<>();
            List<String > list = jedis.lrange(uname,0,-1);
            for (int i=0;i<list.size();i++){
                Video gwc = JSON.parseObject(list.get(i), Video.class);
                lists.add(gwc);
            }
            if (lists.size()!=0){
                for (int i=0;i<lists.size();i++){
                    for (int z=0;z<ints.length;z++){
                        if(lists.get(i).getId()==ints[z]){
                            Video video = lists.get(i);
                            jedis.lrem(uname,1, JSON.toJSONString(lists.get(i)));
                            thisClient.dindan(uname,order,gmtpayment,invoiceamount,video.getVideoName(),video.getVideourl(),video.getId());

                        }
                    }
                }
                // jedis.del("name");

            }else{
                List<String> videolist = jedis.hmget("videolist", "video" + ints[0]);
                Video video = JSON.parseObject(videolist.get(0), Video.class);
                thisClient.dindan(uname,order,gmtpayment,invoiceamount,video.getVideoName(),video.getVideourl(),video.getId());
            }
            String you = jedis.get("you");
            if (you.equals("1")){
                thisClient.updyou(uname);
            }
            jedis.del("jie");
            jedis.close();
        } else {
            System.out.println("异步通知失败");
        }
    }
}

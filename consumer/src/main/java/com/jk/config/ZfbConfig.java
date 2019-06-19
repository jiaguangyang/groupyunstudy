package com.jk.config;

import java.io.FileWriter;
import java.io.IOException;

public class ZfbConfig {
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //    netapp映射的地址，，，根据自己的需要修改
    private static String neturl = "http://jkjy.natapp1.cc";
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号 按照我文章图上的信息填写
    public static String app_id = "2016092800618151";

    // 商户私钥，您的PKCS8格式RSA2私钥  刚刚生成的私钥直接复制填写
    public static String merchant_private_key ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCp9FnpRQNEZiTCb7hQYZOtM681JpxjbCBzL6RxUYfqCrsxenW/Gl00ArvdF0tPFptpsdxIzoZgNZrDUC+gFAX09Qe5DKsUQwLnh+zYj6Z5ZLZX94so7VW3HTnGESHaoLm8PLbJu62mzjG0leJMyixzMjw+mJbrwm+uQSb92oEa2WtpKlsTPgo4X25m61vdbI2A3p0mShtRI2xGO8suycYali98dz7aPy6r1tsNVkt+eZOKwnoLJqNfnj8p2ujo5seGMjXrFqGY3pz2upyJ425+BKd9U8cHfkMm1vzeQ3593CMBUE2Qz51gF5e0HBdNvYEShBUDTr6ix54/yeKeKyYNAgMBAAECggEAe9NZctjiYYJeQ//r9TyywN3ISNxh0xpjpijPEfUKJp489va3NU47G/rl71baerwI7YA19CHsy8oigS06FdKg6HFieBPfzbppMlHZYYuACjAznnHwRRhufCTiBApVdRnnWVuO/fossjzHAEC4CaS/sAqo1B6OzyTyA67aftglem5AlzKGqu7QvVf/weQMa2irakOa1h8ojZhF829cRg75PXJsQM+IR5b33lU8WT47arSf/1i52A73CDSLHd4CDT8wrBD3W6ny1trRyWobeJAviUo43sgd21VjiaFpkDkwAtQd6k7NPsV594+8oueD8NV5JFC8NV8dkoZxKfI9UIsrgQKBgQDTXlonoBJl+n/mq/S5g1ZU590VL4HLv9TGMcIiUyleUjhjFQEz6RsCC8jxPDVvmn4+/sTX4pJj8n1M1GFP/Bxh367GlQW16iWbg1ULG4oE8j20/xaae28tMOVrFFp+HYXwr6mw6IrjFZXhTprFFKA8ZUuxQnmyQ5tm4XbYb8E2IQKBgQDN11aRfb7xCt3TIhniLpFugFEX4XR4sixgPo4RCE0yAuHXAJf6zxcFOm81M1cUjiFDdzYppZX2gWGASj4EU/EqYm5r4q1UnGTcZEibSXnBw8iFypW9ivw/IswPcJB1C4OInl5Af2Z2fFC9nWWIttSyaK5inbapD0hrMzjf+O/abQKBgGaqyM3Agc9I+sE3uxo1AkUhjpCQz9IA7sAKQUrc7BYlh8AQ3tGxI249/S+32BjKiRHePAzEv+iOS3B7JH1ubvbXaJSKpAh1oCjGhRNtIABBDdoRoZAN7rWbr4Pwkff6LSn4mUsV2QhuXqveu2yJFT/g0ABc0rHVBvHVCB4fQlEhAoGAauBgRnKv7Z/ixXlzTwk24dUmRev9+Z0phV91jEpvKoRslqHsR4G3RjOtAkaB9F5Hzyvap40ebDuJUH877CtZkNRC2Sl9Le12QSigumBQI9dwCmfBkATWLEH5Chs8JkmwJgN0lLCRlvu9QHa0/WNQ1MGdqf6RlP4mb+LMqR4sC5ECgYEAyeu/eP+pcbzgQ7NNLRg9lDhp0q7jdVMoDEqVTpn/w3dyZIXoC7cGPgVlCiRcjFJpfnTSphYqOFMEpOu4WspzifIpbdIWQHlMrS1/Hg9qSAoo6L3HvsIbuMrUzZ/630d9MT+zzPCmIx56t0wBUXlgI7a96BZmIWaaTiymx3oMAgA=";

    // 支付宝公钥,对应APPID下的支付宝公钥。 按照我文章图上的信息填写支付宝公钥，别填成商户公钥
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw7s67cOfhq+WIrz7MqdJMEt8yyBvqpYBHhIdkOcU6LFEVTOILZW2snHy4EhxZRU4Q3Z6mAdUX9H/Nmv0afCY3ile1A0781DF7+JgC1cAlZr7YiboeENyEdxZgZaTOC5c4h+Lzcvn0Vj9dAfUN9x/EnCHS6Udju2dutszTB8QcWKcQS9/wl4Z2q/1CEquzN4tbAMTBFczlatwYjf9b2RphsF8KR52YMujTaUiy7ZzuBW8SBIg1XMo+GmJmFJ9W/XleA67zo5VQriyZM4mJ1E4jAtxT00FbN+JMvZCimSDsjOGiGLtviK5xY9RcJBoNrUvlrGKGhv8ZKR6UqlR4+mwoQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    public static String notify_url = "http://jkjy.natapp1.cc/notifyurl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    public static String return_url = "http://jkjy.natapp1.cc/returnurl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "D:\\";
    public static String format = "json";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


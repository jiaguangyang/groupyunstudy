package com.jk.service;

import com.jk.model.Comment;
import com.jk.model.Coupon;
import com.jk.model.Video;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface jgyService {


    String findLunBo();

    List<Video> careCurr();

    List<Video> newCurr();

    List<Video> freeCurr();

    void queryVideoAll();

    HashMap<String,Object> queryComments(Integer videoid,Integer page,Integer rows);

    boolean dindan(String uname, String order, String gmtpayment, String invoiceamount, String videoName, String videourl,Integer videoid);

    void addyouhui(String state, String uname);

    String youhui(String uname, String state);

    Coupon addyouhui2(String uname);

    void updyou(String uname);
}

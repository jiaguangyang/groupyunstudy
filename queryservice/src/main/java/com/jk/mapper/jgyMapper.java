package com.jk.mapper;

import com.jk.model.Coupon;
import com.jk.model.Ossbean;
import com.jk.model.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface jgyMapper {
    @Select("select * from t_oss where status=1")
    List<Ossbean> findLunBo();

    List<Video> careCurr();

    List<Video> newCurr();

    List<Video> freeCurr();

    @Select("select * from t_video tv where tv.videoStatus=2 or tv.videoStatus=3")
    List<Video> queryVideoAll();

    void dindan(@Param("uname") String uname, @Param("order") String order, @Param("gmtpayment") String gmtpayment, @Param("invoiceamount") String invoiceamount, @Param("videoName") String videoName, @Param("videourl") String videourl,@Param("videoid")Integer videoid);

    @Select("select *   from  t_coupon where username = #{uname} and couponStatus = 1 ")
    Coupon addyouhui2(@Param("uname")String uname);

    @Select("select *   from  t_coupon where username = #{uname} and state =#{state}")
    Coupon youhui(@Param("uname")String uname,@Param("state") String state);

    void addyouhui(Coupon coupon);

    @Update("update  t_coupon set state = 2 where  userName=#{name}")
    void updyou(String you);
}

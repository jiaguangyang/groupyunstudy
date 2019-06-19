package com.jk.mapper;

import com.jk.model.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GwcMapper {

    void dindan(@Param("uname") String uname, @Param("order") String order, @Param("gmtpayment") String gmtpayment, @Param("invoiceamount") String invoiceamount, @Param("videoName") String videoName, @Param("videourl") String videourl);


}

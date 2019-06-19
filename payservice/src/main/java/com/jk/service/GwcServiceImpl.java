package com.jk.service;

import com.jk.mapper.GwcMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GwcServiceImpl implements GwcService {

    @Autowired
    GwcMapper gwcMapper;

    @Override
    public void dindan(String uname, String order, String gmtpayment, String invoiceamount, String videoName, String videourl) {
        gwcMapper.dindan(uname,order,gmtpayment,invoiceamount,videoName,videourl);
    }


}

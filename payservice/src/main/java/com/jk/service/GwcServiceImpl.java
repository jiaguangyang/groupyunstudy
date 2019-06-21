package com.jk.service;

import com.jk.mapper.GwcMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GwcServiceImpl implements GwcService {

    @Autowired
    GwcMapper gwcMapper;




}

package com.jk.service;

import com.jk.mapper.jgyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class jgyServiceImpl implements jgyService {
    @Autowired
    private jgyMapper jgymapper;

    @Override
    public List<LinkedHashMap<String, Object>> findtest() {
        return jgymapper.findtest();
    }
}

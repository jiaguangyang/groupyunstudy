package com.jk.loginservice.service;

import com.jk.model.TeacherBean;
import com.jk.model.User;

import java.util.HashMap;
import java.util.List;

public interface LoginService {
    HashMap<String, Object> login(String name ,String password);

    List<TeacherBean> queryZhuying();

    HashMap<String, Object> addUser(TeacherBean teacherBean);
}

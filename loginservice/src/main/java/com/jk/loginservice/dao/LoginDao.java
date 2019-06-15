package com.jk.loginservice.dao;

import com.jk.model.TeacherBean;
import com.jk.model.User;

import java.util.List;

public interface LoginDao {
    User queryName(String name);

    List<TeacherBean> queryZhuying();

    String queryTeacher(String teacherName);

    void addUser(TeacherBean teacherBean);
}

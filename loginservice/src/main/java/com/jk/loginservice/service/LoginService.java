package com.jk.loginservice.service;

import com.jk.model.*;

import java.util.HashMap;
import java.util.List;

public interface LoginService {
    HashMap<String, Object> login(String name ,String password);

    List<TeacherBean> queryZhuying();

    HashMap<String, Object> addUser(TeacherBean teacherBean);

    HashMap<String, Object> queryTeacherLogin(TeacherBean teacherBean);

    HashMap<String, Object> addViden(VidenBean videnBean);

    List<TreeBean> queryTree();

    TeacherBean queryTeatherById();

    HashMap<String, Object> uploadRegtrs(TeacherBean teacherBean);

    Boolean updatePassword(String password);

    HashMap<String,Object> addRegUser(User user);

    List<UserTree> queryUserTree();

    User queryUserById();

    HashMap<String, Object> updateUserById(User user);

    Boolean updateUserPassword(String password);

    Boolean addVideo(Video video);

    HashMap<String, Object> queryProject(Integer page, Integer limit);

    HashMap<String, Object> queryInstitutionLogin(TeacherBean teacherBean);


    HashMap<String, Object> addInformation(Institutions institutions);

    List<JgTree> queryJgTree();

    Institutions queryupdateInstitution();

    HashMap<String, Object> updateInformation(Institutions institutions);

    Boolean updateInPassword(String password);

    HashMap<String, Object> queryJgTeacher(Integer page,Integer limit);

    HashMap<String, Object> queryJgteacher(Integer page,Integer limit,Integer teacherId);

    HashMap<String, Object> queryDingdan(Integer page,Integer limit);

    HashMap<String, Object> phoneLogin(String phone, Integer code);

    Video queryMyVideoByid(Integer id);

    Boolean updateVideoById(Video video);

    /*HashMap<String, Object> querySp(Integer page, Integer limit);*/
}

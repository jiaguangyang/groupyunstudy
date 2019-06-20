package com.jk.loginservice.dao;

import com.jk.model.*;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface LoginDao {
    User queryName(String name);

    List<TeacherBean> queryZhuying();

    String queryTeacher(String teacherName);

    void addUser(TeacherBean teacherBean);

    TeacherBean queryTeacherLogin(TeacherBean teacherBean);

    Integer queryChec(String teacherAccount);

    void addViden(VidenBean videnBean);



    List<TreeBean> queryByPid(Integer pid);

    TeacherBean queryTeatherById(int id);

    void uploadRegtrs(TeacherBean teacherBean);

    String queryTeacherById(Integer id);

    void updatePassword(@Param("teacherId") String teacherId,@Param("password") String password);

    void addRegUser(User user);

    User queryUserByName(String name);

    List<UserTree> queryUserTreeByPid(Integer pid);

    User queryUserById(String userId);

    void updateUserById(User user);

    void updateUserPassword(@Param("userId")String userId,@Param("password") String password);

    void addVideo(Video video);

    int queryCount();

    List<Video> queryVideoById(@Param("start") int start,@Param("limit") Integer limit,@Param("teacherId") String teacherId);

    Institutions queryInstitutionLogin(String teacherAccount);

    Institutions queryInformationByName(String account);

   void addInformation(Institutions institutions);

    List<JgTree> queryJgTreeByPid(Integer pid);

    Institutions queryupdateInstitution(String jgId);

    void updateInformation(Institutions institutions);

    void updateInPassword(@Param("jgId")String jgId,@Param("password") String password);

    int queryJgTeacherCount(String teacherId);

    List<TeacherBean> queryJgTeacher(@Param("start") int start,@Param("limit") Integer limit,@Param("teacherId") String teacherId);

    int queryJgTeacherCountByTeachId(Integer teacherId);

    List<Video> queryJgteacherByTeachId(@Param("start") int start,@Param("limit") Integer limit,@Param("teacherId") Integer teacherId);

    int queryDingdanCount(String userId);

    List<Dingdan> queryDingdan(@Param("start") int start,@Param("limit") Integer limit,@Param("userId") String userId);
}

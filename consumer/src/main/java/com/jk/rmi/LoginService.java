package com.jk.rmi;

import com.jk.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.reflect.generics.tree.Tree;

import java.util.HashMap;
import java.util.List;

@FeignClient("loginservice")
public interface LoginService {
    @RequestMapping("login")
    @ResponseBody
    HashMap<String, Object> queryLogin(@RequestParam("name") String name,@RequestParam("password") String password);

    @RequestMapping("queryZhuying")
    List<TeacherBean> queryZhuying();
    @RequestMapping("addUser")
    HashMap<String, Object> addUser(@RequestBody TeacherBean teacherBean);
    @RequestMapping("queryTeacherLogin")
    HashMap<String, Object> queryTeacherLogin(@RequestBody TeacherBean teacherBean);
     @RequestMapping("addViden")
    HashMap<String, Object> addViden(@RequestBody VidenBean videnBean);
    @RequestMapping("queryTree")
    List<TreeBean> queryTree();
    @RequestMapping("queryTeatherById")
    TeacherBean queryTeatherById();
    @RequestMapping("uploadRegtrs")
    HashMap<String, Object> uploadRegtrs(@RequestBody TeacherBean teacherBean);
    @RequestMapping("updatePassword")
    Boolean updatePassword(@RequestParam("password") String password);
    @RequestMapping("addRegUser")
    HashMap<String,Object> addRegUser(@RequestBody User user);
    @RequestMapping("queryUserTree")
    List<UserTree> queryUserTree();
    @RequestMapping("queryUserById")
    User queryUserById();
    @RequestMapping("updateUserById")
    HashMap<String, Object> updateUserById(@RequestBody User user);
    @RequestMapping("updateUserPassword")
    Boolean updateUserPassword(@RequestParam("password") String password);
    @RequestMapping("addVideo")
    Boolean addVideo(@RequestBody Video video);
    @RequestMapping("queryProject")
    HashMap<String, Object> queryProject(@RequestParam("page")Integer page,@RequestParam("limit") Integer limit,@RequestParam("teacherId1")Integer teacherId1);
    @RequestMapping("queryInstitutionLogin")
    HashMap<String, Object> queryInstitutionLogin(TeacherBean teacherBean);
    @RequestMapping("addInformation")
    HashMap<String, Object> addInformation(@RequestBody Institutions institutions);
    @RequestMapping("queryJgTree")
    List<JgTree> queryJgTree();
    @RequestMapping("queryupdateInstitution")
    Institutions queryupdateInstitution();
    @RequestMapping("updateInformation")
    HashMap<String, Object> updateInformation(@RequestBody Institutions institutions);
    @RequestMapping("updateInPassword")
    Boolean updateInPassword(@RequestParam("password")String password);
    @RequestMapping("queryJgTeacher")
    HashMap<String, Object> queryJgTeacher(@RequestParam("page")Integer page,@RequestParam("limit") Integer limit);
    @RequestMapping("queryJgteacher")
    HashMap<String, Object> queryJgteacher(@RequestParam("page")Integer page,@RequestParam("limit") Integer limit,@RequestParam("teacherId")Integer teacherId);
    @RequestMapping("queryDingdan")
    HashMap<String, Object> queryDingdan(@RequestParam("page")Integer page,@RequestParam("limit") Integer limit);
}

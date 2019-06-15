package com.jk.rmi;

import com.jk.model.TeacherBean;
import com.jk.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

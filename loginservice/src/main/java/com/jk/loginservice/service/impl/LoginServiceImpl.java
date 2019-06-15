package com.jk.loginservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.jk.loginservice.dao.LoginDao;
import com.jk.loginservice.service.LoginService;
import com.jk.loginservice.uitle.Md5Util;
import com.jk.model.TeacherBean;
import com.jk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
  @Autowired
  private LoginDao loginDao;
   @Autowired
   private JedisPool jedisPool;
  @Transactional(readOnly = true)
  @Override
  public HashMap<String, Object> login(String name ,String password) {
    HashMap<String, Object> hashMap = new HashMap<>();
    /*        GongSiiXanLu user = */
    User name1 = loginDao.queryName(name);
    if (name1 == null) {
      hashMap.put("code", 1);
      hashMap.put("msg", "账号已存在！");
      return hashMap;
    }
    //String passeword = user.getPassword();
    String username = name1.getName();
    String password1 = name1.getPassword();
    //String md516 = Md5Util.getMd516(user.getPassword());
    if (!password1.equals(password)) {
      hashMap.put("code", 2);
      hashMap.put("msg", "密码错误！");
      return hashMap;
    }
    hashMap.put("code", 0);
    hashMap.put("msg", "登陆成功");
    Jedis jedis = jedisPool.getResource();
    String loginName = JSON.toJSONString(name1);
    jedis.set("name",loginName);
    jedis.close();
    return hashMap;
  }

  @Override
  public List<TeacherBean> queryZhuying() {
    List<TeacherBean> teacherBeans = loginDao.queryZhuying();
    return teacherBeans;
  }

  @Override
  public HashMap<String, Object> addUser(TeacherBean teacherBean) {
    HashMap<String, Object> hashMap = new HashMap<>();
    String teacherName=loginDao.queryTeacher(teacherBean.getTeacherAccount());
    if (teacherName!=null&&!teacherName.equals("")&&!teacherName.equals("null")){
      hashMap.put("code", 2);
      hashMap.put("msg", "用户名已存在");
      return hashMap;
    }
    Jedis jedis = jedisPool.getResource();
    String phone = jedis.get("phone");
    String teacherPhone = teacherBean.getTeacherPhone();
    if (phone==null||!phone.equals(teacherPhone)){
      jedis.del("phone");
      hashMap.put("code", 1);
      hashMap.put("msg", "请确认认证手机");
      return hashMap;
    }
    try {
      loginDao.addUser(teacherBean);
      hashMap.put("code", 0);
      hashMap.put("msg", "注册成功请等待审核");
      return hashMap;

    }catch (Exception e){
      e.printStackTrace();
      hashMap.put("code", 3);
      hashMap.put("msg", "注册失败");
      return hashMap;
    }
  }
}
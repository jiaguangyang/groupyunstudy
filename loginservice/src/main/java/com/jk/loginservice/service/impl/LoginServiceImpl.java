package com.jk.loginservice.service.impl;

import com.jk.loginservice.dao.LoginDao;
import com.jk.loginservice.service.LoginService;
import com.jk.loginservice.uitle.Md5Util;
import com.jk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {
  @Autowired
  private LoginDao loginDao;

  @Transactional(readOnly = true)
  @Override
  public HashMap<String, Object> login(User user) {
    HashMap<String, Object> hashMap = new HashMap<>();
    /*        GongSiiXanLu user = */
    User name = loginDao.queryName(user.getName());
    if (name == null) {
      hashMap.put("code", 1);
      hashMap.put("msg", "账号已存在！");
      return hashMap;
    }
    //String passeword = user.getPassword();
    String username = name.getName();
    String password = name.getPassword();
    //String md516 = Md5Util.getMd516(user.getPassword());
    if (!user.getPassword().equals(password)) {
      hashMap.put("code", 2);
      hashMap.put("msg", "密码错误！");
      return hashMap;
    }
    hashMap.put("code", 0);
    hashMap.put("msg", "登陆成功");
    return hashMap;
  }
}
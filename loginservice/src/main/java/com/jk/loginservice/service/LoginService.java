package com.jk.loginservice.service;

import com.jk.model.User;

import java.util.HashMap;

public interface LoginService {
    HashMap<String, Object> login(User user);
}

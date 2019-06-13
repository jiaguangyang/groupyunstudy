package com.jk.loginservice.dao;

import com.jk.model.User;

public interface LoginDao {
    User queryName(String name);
}

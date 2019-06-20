package com.jk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String userPhone;
    private String userInfo;

}

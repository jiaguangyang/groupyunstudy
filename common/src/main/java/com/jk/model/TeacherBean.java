package com.jk.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class TeacherBean implements Serializable{
    private Integer id;
    private String teacherAccount;
    private String teacherPwd;
    private String teacherPhone;
    private String teacherName;
    private String teacherInfo;
    private String email;
    private String jieshao;

    private String companyLogo;
    private String zy;

}

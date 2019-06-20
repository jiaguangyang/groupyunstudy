package com.jk.model;

import lombok.Data;

import java.util.List;

@Data
public class UserTree {
    private  Integer id;
    private  String text;
    private  String href;
    private  Integer pid;
    private List<UserTree> nodes;
    private Boolean seletetable;
}

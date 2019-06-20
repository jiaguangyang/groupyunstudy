package com.jk.model;

import lombok.Data;

import java.util.List;

@Data
public class TreeBean {
    private  Integer id;
    private  String text;
    private  String href;
    private  Integer pid;
    private List<TreeBean> nodes;
    private Boolean seletetable;
}

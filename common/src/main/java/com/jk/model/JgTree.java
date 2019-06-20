package com.jk.model;

import lombok.Data;

import java.util.List;
@Data
public class JgTree {
    private  Integer id;
    private  String text;
    private  String href;
    private  Integer pid;
    private List<JgTree> nodes;
    private Boolean seletetable;
}

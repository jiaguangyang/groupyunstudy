package com.jk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Ossbean implements Serializable {
private Integer id;
private String url;
private String href;
private String hrefname;
private Integer status;
}

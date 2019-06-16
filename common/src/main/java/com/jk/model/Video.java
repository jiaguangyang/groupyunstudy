package com.jk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Video  {
    private  Integer id;
    private  String videoImg;
    private  String videoName;
    private  Integer teacherId;
    private  String videoTitle;
    private  Double videoPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date videoTime;
    private  Integer videoStatus;
    private  Integer quantity;
    private  String videoinfo;
    private  String forPeople;
    private String summary;
}

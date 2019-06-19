package com.jk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.Date;

@Data
@Document(indexName = "wangyistudy",type = "video",shards = 1,replicas = 0)
public class Video implements Serializable{
    @Id
    private  Integer id;
    @Field(type = FieldType.Keyword)
    private  String videoImg;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private  String videoName;
    private String videourl;
    private  Integer teacherId;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private  String videoTitle;
    private  Double videoPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date videoTime;
    private  Integer videoStatus;
    private  Integer quantity;
    @Field(type = FieldType.Keyword)
    private  String videoinfo;
    private String forPeople;
    private String summary;
    private String teacherName;
    private String teacherInfo;
}

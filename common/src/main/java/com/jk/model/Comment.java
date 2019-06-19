package com.jk.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Collection;
import java.util.Date;

@Data
public class Comment {
    private String commentId;//评论id
    private String  commentName;//评论人
    private String  commentContent;//评论内容
    private Date commentDate;//评论时间
    private Double commentGrade;//评分
    private Integer videoid;
    private Integer pid;
}

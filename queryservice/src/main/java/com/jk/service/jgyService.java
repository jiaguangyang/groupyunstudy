package com.jk.service;

import com.jk.model.Comment;
import com.jk.model.Video;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface jgyService {


    String findLunBo();

    List<Video> careCurr();

    List<Video> newCurr();

    List<Video> freeCurr();

    void queryVideoAll();

    List<Comment> queryComments(Integer videoid,Integer page,Integer rows);
}

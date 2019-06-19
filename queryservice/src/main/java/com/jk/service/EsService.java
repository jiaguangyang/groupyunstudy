package com.jk.service;

import com.jk.model.Comment;
import com.jk.model.Video;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.HashMap;
import java.util.List;

public interface EsService  {
    List<IndexQuery> findVideo();


    Video findVideoById(Integer videoid);

    void addComment(Comment comment);
}

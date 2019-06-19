package com.jk.service;

import com.jk.mapper.EsMapper;
import com.jk.model.Comment;
import com.jk.model.Video;
import com.jk.util.BadWordUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Service
public class EsServiceImpl implements EsService{
    @Autowired
    EsMapper esMapper;

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    JedisPool jedisPool;
    @Override
    public void addComment(Comment comment) {
        Jedis jedis = jedisPool.getResource();
        jedis.setex("name",60,"张三");
        comment.setCommentId(UUID.randomUUID().toString());
        comment.setCommentDate(new Date());
        comment.setCommentName(jedis.get("name"));
        String s1 = BadWordUtil2.replaceBadWord(comment.getCommentContent(), 2, "*");
        comment.setCommentContent(s1);
        mongoTemplate.insert(comment,"Comment");
    }

    @Override
    public List<IndexQuery> findVideo() {
        List<Video> list = esMapper.findVideo();
        return getQuery(list);
    }

    @Override
    public Video findVideoById(Integer videoid) {
        return esMapper.findVideoById(videoid);
    }


    private List<IndexQuery> getQuery(List<Video> list) {
        List<IndexQuery> queryList = new ArrayList<>();
        for (Video esProduct : list) {
            IndexQuery query = new IndexQueryBuilder().withId(esProduct.getId().toString()).withObject(esProduct).build();
            queryList.add(query);
        }
        return queryList;
    }

}

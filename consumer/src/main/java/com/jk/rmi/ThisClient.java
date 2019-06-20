package com.jk.rmi;

import com.jk.model.Comment;
import com.jk.model.Video;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.reflect.generics.tree.Tree;

import java.util.LinkedHashMap;
import java.util.List;

@FeignClient("queryservice")
public interface ThisClient {



    @RequestMapping("findLunBo")
    @ResponseBody
    String findLunBo();

    @RequestMapping("careCurr")
    @ResponseBody
    List<Video> careCurr();

    @RequestMapping("newCurr")
    @ResponseBody
    List<Video> newCurr();

    @RequestMapping("freeCurr")
    @ResponseBody
    List<Video> freeCurr();

    @RequestMapping("queryVideoAll")
    @ResponseBody
    void queryVideoAll();

    @RequestMapping("queryComments")
    @ResponseBody
    List<Comment> queryComments(@RequestParam("videoid") Integer videoid,@RequestParam("page")Integer page,@RequestParam("rows")Integer rows);
}

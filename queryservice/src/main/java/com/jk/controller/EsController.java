package com.jk.controller;

import com.jk.model.Video;
import com.jk.service.EsService;
import com.jk.service.EsServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class EsController {
    @Autowired
    EsService esService;

    @RequestMapping("find")
    public String find(String url){
        return url;
    }


    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @RequestMapping("/searchByKeywordsAndHighlightField")
    @ResponseBody
    public HashMap<String , Object> searchByKeywordsAndHighlightField(String keywords,Integer start,Integer pageSize){
        HashMap<String, Object> map = new HashMap<String, Object>();
        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder searchRequestBuilder = client
                .prepareSearch("wangyistudy")  //指定索引
                .setTypes("video")// 指定类型
                .setFrom(start)  //设置起始条数
                .setSize(pageSize); //设置每页几条;
        if (keywords != null) {
            searchRequestBuilder.setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("videoname", keywords)).should(QueryBuilders.matchQuery("videotitle", keywords))); // 指定条查
        }
        HighlightBuilder highlightBuilder =new HighlightBuilder();
        highlightBuilder.field("videoname").field("videotitle");  //设置高亮字段
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchRequestBuilder.highlighter(highlightBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        map.put("total",hits.totalHits);
        Iterator<SearchHit> iterator = hits.iterator();
        List<Map<String,Object>> list = new ArrayList();
        while (iterator.hasNext()){
            Video video = new Video();
            SearchHit searchHit = iterator.next();
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            // 获取对应的高亮域
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            // 从设定的高亮域中取得指定域
            HighlightField nameField = highlightFields.get("videoname");
            if (nameField !=null) {
                // 取得定义的高亮标签
                Text[] nameText = nameField.fragments();
                // 为name串值增加自定义的高亮标签
                String name = "";
                for (Text text : nameText) {
                    name += text;
                }
                sourceAsMap.put("videoname",name);
            }
            HighlightField subTitleField = highlightFields.get("videotitle");
            if (subTitleField !=null) {
                // 取得定义的高亮标签
                Text[] subTitleText = subTitleField.fragments();
                // 为name串值增加自定义的高亮标签
                String subTitle = "";
                for (Text text : subTitleText) {
                    subTitle += text;
                }
                sourceAsMap.put("videotitle",subTitle);
            }
            list.add(sourceAsMap);
        }
        map.put("rows", list);
        return map;
    }

    /**
     *   与数据库同步
     * @return
     */
    @RequestMapping("/syncDb")
    @ResponseBody
    public boolean syncDb() {
        try {
            List<IndexQuery> list = esService.findVideo();
            elasticsearchTemplate.bulkIndex(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}

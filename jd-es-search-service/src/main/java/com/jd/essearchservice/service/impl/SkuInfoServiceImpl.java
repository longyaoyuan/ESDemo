package com.jd.essearchservice.service.impl;

import com.jd.essearchapi.entity.SkuInfo;
import com.jd.essearchapi.entity.SkuInfoParams;
import com.jd.essearchapi.entity.SkuInfoResult;
import com.jd.essearchinterface.service.SkuInfoService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.dubbo.config.annotation.Service;


import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkuInfoServiceImpl implements SkuInfoService {

    @Autowired
    JestClient jestClient;

    /**
     * 上传mysql数据到es中
     *
     * @param skuInfo
     */
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        Index index = new Index.Builder(skuInfo).index("jd").type("SkuInfo").id(skuInfo.getId() + "").build();
        try {
            System.out.println("----index--->" + index);
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现查询的接口
     *
     * @param skuInfoParams
     * @return
     */
    @Override
    public SkuInfoResult skuSearch(SkuInfoParams skuInfoParams) {
        //（1）定义一个dsl语句
        String query = makeQueryStringForSearch(skuInfoParams);
//（2）创建一个查询对象
        Search search = new Search.Builder(query).addIndex("jd").addType("SkuInfo").build();
        //(3)执行\得到执行的结果
        SearchResult searchResult = null;
        try {
            searchResult = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
//(4)将查询的结果集封装到数据模型中
        SkuInfoResult skuInfoResult = makeResultForSearch(skuInfoParams, searchResult);
        return skuInfoResult;
    }

    /**
     * 封装结果集的方法
     */
    private SkuInfoResult makeResultForSearch(SkuInfoParams skuInfoParams, SearchResult searchResult) {
        SkuInfoResult skuInfoResult = new SkuInfoResult();
        //声明一个集合来存储从ES中获取的对象
        List<SkuInfo> skuInfoList = new ArrayList<>();
        //把从SearchResult 查询的到的数据赋给SkuInfoResult对象
        List<SearchResult.Hit<SkuInfo, Void>> hits = searchResult.getHits(SkuInfo.class);
        //遍历hits,取出每一个字段的值
        for (SearchResult.Hit<SkuInfo, Void> hit : hits) {
            //从es的source取出每一个skuInfo对象，
            SkuInfo skuInfo = hit.source;
            //获取高亮字段来替换不是高亮的字段skuName
            if (hit.highlight != null && hit.highlight.size() != 0) {
                //获取高亮的字段，是多个字段为数组
                List<String> list = hit.highlight.get("skuName");
                //只有一个字段，这才是真正的高亮字段
                String skuNameHightlight = list.get(0);
                skuInfo.setSkuName(skuNameHightlight);

            }

            //添加到集合中
            skuInfoList.add(skuInfo);
        }
        //封装到skuInfoResult对象中
        skuInfoResult.setSkuInfoList(skuInfoList);
        return skuInfoResult;
    }

    /**
     * 定义一个dsl语句
     */
    public String makeQueryStringForSearch(SkuInfoParams skuInfoParams) {
        //(1)创建一个查询器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //(2)创建QueryBuilders对象（对应dsl中的bool）
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//(3)根据查询条件进行查询
        if (skuInfoParams.getKeyword() != null && skuInfoParams.getKeyword().length() != 0) {
            //(4)创建termQuery对象
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuName", skuInfoParams.getKeyword());
            //(5)将termQueryBuilder添加到must中，并把must赋给bool
            boolQueryBuilder.must(termQueryBuilder);


            //(6)对skuName关键字进行高亮设置
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");
            highlightBuilder.field("skuName");
            //把高亮的设置加入到查询对象中
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        //(7)设置三级分类ID
        if (skuInfoParams.getCatalog3Id() != 0) {
            //将三级分类ID添加到查询器中
            TermQueryBuilder termQueryBuilder1 = new TermQueryBuilder("catalog3Id", skuInfoParams.getCatalog3Id());
            //将termQueryBuilder1放入到filter中，在filter赋给bool
            BoolQueryBuilder filter = boolQueryBuilder.filter(termQueryBuilder1);
        }

        //(8)设置分页
//        searchSourceBuilder.size();
//        searchSourceBuilder.from();//(pageNo-1)*pageSize

//(9)执行查询
        SearchSourceBuilder searchSourceBuilder1 = searchSourceBuilder.query(boolQueryBuilder);
        String query = searchSourceBuilder1.toString();
        System.out.println("----query-->" + query);
        return query;
    }


}

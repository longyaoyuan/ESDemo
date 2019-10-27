package com.jd.essearchservice;

import com.jd.essearchapi.entity.SkuInfo;
import com.jd.essearchservice.mapper.SkuInfoMapper;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdEsSearchServiceApplicationTests {

    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() {
    }



    @Test
    public void testSearch(){
        String query="{\n" +
                "  \"query\":{\n" +
                "    \"match\": {\n" +
                "      \"name\": \"牛市\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(query).addIndex("moive_chn").addType("moive").build();
        SearchResult searchResult=null;
        try {
            searchResult= jestClient.execute(search);

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<Map, Void>> hits = searchResult.getHits(Map.class);
        for (SearchResult.Hit<Map, Void> hit:hits) {
            Map map = hit.source;
            System.out.println("数据"+map);
        }
        System.out.println(query);

    }

@Test
    public void testMapper(){
    SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(1);
    System.out.println("---->"+skuInfo.toString());

}

}



package com.danbro.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.PmsSearchSkuInfo;
import com.danbro.gmall.api.bean.PmsSkuInfo;
import com.danbro.gmall.api.service.PmsSkuService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {

    @Autowired
    JestClient jestClient;

    @Reference
    PmsSkuService pmsSkuService;

    @Test
    public void contextLoads() throws IOException {
        List<PmsSkuInfo> pmsSkuInfoList = pmsSkuService.getAllSku(61L);
        List<PmsSearchSkuInfo> searchPmsSkuInfos = new ArrayList<>();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);
            searchPmsSkuInfos.add(pmsSearchSkuInfo);
        }
        for (PmsSearchSkuInfo searchPmsSkuInfo : searchPmsSkuInfos) {
            Index build = new Index.Builder(searchPmsSkuInfo).index("gmall").type("pmsSkuInfo").id(Long.toString(searchPmsSkuInfo.getId())).build();
            jestClient.execute(build);
        }
    }

    @Test
    public void testSearch(){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId","39");
        TermQueryBuilder termQueryBuilder1 = new TermQueryBuilder("skuAttrValueList.valueId", "43");
        boolQueryBuilder.filter(termQueryBuilder);
        boolQueryBuilder.filter(termQueryBuilder1);
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", "华为");
        boolQueryBuilder.must(matchQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.highlighter(null);
        String dsl = searchSourceBuilder.toString();
        System.out.println(dsl);


    }
}

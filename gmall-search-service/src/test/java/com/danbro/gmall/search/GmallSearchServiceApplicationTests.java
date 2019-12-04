package com.danbro.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.api.dto.PmsSkuInfoFromEsDto;
import com.danbro.gmall.api.po.PmsSkuInfoPo;
import com.danbro.gmall.api.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class GmallSearchServiceApplicationTests {

    @Autowired
    JestClient jestClient;

    @Reference
    SkuService skuService;

    /**
     * 更新elasticsearch
     *
     * @throws IOException
     */
    @Test
    void contextLoads() throws IOException {
        List<PmsSkuInfoDto> pmsSkuInfoDtoList = skuService.getAllSku(285L);
        List<PmsSkuInfoFromEsDto> searchPmsSkuInfos = new ArrayList<>();
        for (PmsSkuInfoPo pmsSkuInfoPo : pmsSkuInfoDtoList) {
            PmsSkuInfoFromEsDto pmsSkuInfoFromEsDto = new PmsSkuInfoFromEsDto();
            BeanUtils.copyProperties(pmsSkuInfoPo, pmsSkuInfoFromEsDto);
            searchPmsSkuInfos.add(pmsSkuInfoFromEsDto);
        }
        for (PmsSkuInfoFromEsDto searchPmsSkuInfo : searchPmsSkuInfos) {
            Index build = new Index.Builder(searchPmsSkuInfo).index("gmall").type("PmsSkuInfo").id(Long.toString(searchPmsSkuInfo.getId())).build();
            jestClient.execute(build);
        }
    }

    @Test
    void testSearch() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", "39");
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

    @Test
    void test01() {
        System.out.println("1");

    }

}

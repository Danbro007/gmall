package com.danbro.gmall.search.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.PmsSearchParam;
import com.danbro.gmall.api.bean.PmsSearchSkuInfo;
import com.danbro.gmall.api.bean.PmsSkuAttrValue;
import com.danbro.gmall.api.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Danrbo
 * @date 2019/10/8 16:20
 * description
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> getSkuInfoListByParam(PmsSearchParam pmsSearchParam){
        String dsl = this.getDsl(pmsSearchParam);
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        Search build = new Search.Builder(dsl).addIndex("gmall").addType("pmsSkuInfo").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo source = hit.source;
            if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())){
                Map<String, List<String>> highlight = hit.highlight;
                String skuName = highlight.get("skuName").get(0);
                source.setSkuName(skuName);
            }
            pmsSearchSkuInfos.add(source);
        }
        return pmsSearchSkuInfos;
    }

    /**
     * 通过搜索参数过滤出商品列表
     * @param pmsSearchParam 搜索参数对象
     * @return 过滤后的商品列表
     */
    private String getDsl(PmsSearchParam pmsSearchParam){
        String keyword = pmsSearchParam.getKeyword();
        String[] skuAttrValueList = pmsSearchParam.getValueId();
        Long catalog3Id = pmsSearchParam.getCatalog3Id();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter
        if(skuAttrValueList != null){
            for (String pmsSkuAttrValue : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",pmsSkuAttrValue);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        if (catalog3Id != null){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        //must
        if (StringUtils.isNotBlank(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort("id", SortOrder.DESC);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        return searchSourceBuilder.toString();
    }

}

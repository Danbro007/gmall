package com.danbro.gmall.search.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.dto.PmsSearchParamDto;
import com.danbro.gmall.api.dto.PmsSearchSkuInfoDto;
import com.danbro.gmall.api.dto.PmsSkuAttrValueDto;
import com.danbro.gmall.api.dto.PmsSkuInfoFromEsDto;
import com.danbro.gmall.api.service.SearchService;
import com.danbro.gmall.api.vo.PmsSearchParamVo;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<PmsSearchSkuInfoDto> getSkuInfoListByParam(PmsSearchParamVo pmsSearchParamVo) {
        /*从es获取sku商品数据*/
        PmsSearchParamDto pmsSearchParamDto = new PmsSearchParamDto();
        BeanUtils.copyProperties(pmsSearchParamVo, pmsSearchParamDto);
        String dsl = this.getDsl(pmsSearchParamDto);
        System.out.println(dsl);
        List<PmsSearchSkuInfoDto> pmsSearchSkuInfoDtoList = new ArrayList<>();
        Search build = new Search.Builder(dsl).addIndex("gmall").addType("PmsSkuInfo").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SearchResult.Hit<PmsSkuInfoFromEsDto, Void>> hits = execute.getHits(PmsSkuInfoFromEsDto.class);

        for (SearchResult.Hit<PmsSkuInfoFromEsDto, Void> hit : hits) {
            PmsSkuInfoFromEsDto pmsSkuInfoFromEsDto = hit.source;
            List<String> skuAttrValueStrList = pmsSkuInfoFromEsDto.getSkuAttrValueList();
            PmsSearchSkuInfoDto pmsSearchSkuInfoDto = new PmsSearchSkuInfoDto();
            if (skuAttrValueStrList != null) {
                String[] skuAttrValueList = StringUtils.split(skuAttrValueStrList.get(0), ',');
                ArrayList<PmsSkuAttrValueDto> pmsSkuAttrValueDtoList = new ArrayList<>();
                for (String valueIdStr : skuAttrValueList) {
                    PmsSkuAttrValueDto pmsSkuAttrValueDto = new PmsSkuAttrValueDto();
                    pmsSkuAttrValueDto.setValueId(Long.parseLong(valueIdStr));
                    pmsSkuAttrValueDtoList.add(pmsSkuAttrValueDto);
                    BeanUtils.copyProperties(pmsSkuInfoFromEsDto, pmsSearchSkuInfoDto);
                    pmsSearchSkuInfoDto.setSkuAttrValueList(pmsSkuAttrValueDtoList);
                    //关键词变红
                    if (StringUtils.isNotBlank(pmsSearchParamDto.getKeyword())) {
                        Map<String, List<String>> highlight = hit.highlight;
                        String skuName = highlight.get("skuName").get(0);
                        pmsSkuInfoFromEsDto.setSkuName(skuName);
                    }
                    pmsSearchSkuInfoDtoList.add(pmsSearchSkuInfoDto);
                }
            }
        }
        return pmsSearchSkuInfoDtoList;
    }

    /**
     * 通过搜索参数过滤出商品列表
     *
     * @param pmsSearchParamDto 搜索参数对象
     * @return 过滤后的商品列表
     */
    private String getDsl(PmsSearchParamDto pmsSearchParamDto) {
        String keyword = pmsSearchParamDto.getKeyword();
        String[] skuAttrValueList = pmsSearchParamDto.getValueId();
        Long catalog3Id = pmsSearchParamDto.getCatalog3Id();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter
        if (skuAttrValueList != null) {
            for (String pmsSkuAttrValue : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("valueId", pmsSkuAttrValue);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        if (catalog3Id != null) {
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        //must
        if (StringUtils.isNotBlank(keyword)) {
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
        searchSourceBuilder.size(200);
        return searchSourceBuilder.toString();
    }

}

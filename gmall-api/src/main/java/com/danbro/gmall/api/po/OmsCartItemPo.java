package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author Danrbo
 * @date 2019/9/10 14:17
 * description
 **/
@Data
public class OmsCartItemPo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Long productSkuId;
    private String memberId;
    private Integer quantity;
    private BigDecimal productPrice;
    private String sp1;
    private String sp2;
    private String sp3;
    private String productPic;
    private String productName;
    private String productSubTitle;
    private String productSkuCode;
    private String memberNickname;
    private Date createDate;
    private Date modifyDate;
    private boolean deleteStatus;
    private Long productCategoryId;
    private String productBrand;
    private String productSn;
    private String productAttr;
    private Integer isChecked;

}

package com.danbro.gmall.api.vo;

import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.po.MemberReceiveAddressPo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.List;
/**
 * @author Danrbo
 * @date 2019/9/8 13:46
 * description
 **/
@Data
public class MemberPoInfoVo extends MemberPo {

    private List<MemberReceiveAddressPo> addressList;

}

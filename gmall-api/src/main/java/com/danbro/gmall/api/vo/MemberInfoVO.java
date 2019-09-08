package com.danbro.gmall.api.vo;

import com.danbro.gmall.api.bean.Member;
import com.danbro.gmall.api.bean.MemberReceiveAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.List;
/**
 * @author Danrbo
 * @date 2019/9/8 13:46
 * description
 **/
@Setter
@Getter
@ToString
public class MemberInfoVO extends Member {

    private List<MemberReceiveAddress> addressList;

}

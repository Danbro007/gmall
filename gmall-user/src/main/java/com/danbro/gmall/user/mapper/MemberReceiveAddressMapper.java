package com.danbro.gmall.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.bean.MemberReceiveAddress;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/6 10:00
 * description
 **/
public interface MemberReceiveAddressMapper extends BaseMapper<MemberReceiveAddress> {
    @Select("SELECT * FROM ums_member um LEFT JOIN ums_member_receive_address umr ON um.id = umr.member_id WHERE um.id =#{memberId}")
    List<MemberReceiveAddress> getAddressByMemberId(Long memberId);
}

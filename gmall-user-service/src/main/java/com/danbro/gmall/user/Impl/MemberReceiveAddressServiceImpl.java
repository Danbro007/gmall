package com.danbro.gmall.user.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.po.MemberReceiveAddressPo;
import com.danbro.gmall.api.service.MemberReceiveAddressService;
import com.danbro.gmall.user.mapper.MemberReceiveAddressMapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/6 12:25
 * description
 **/
@Service
public class MemberReceiveAddressServiceImpl implements MemberReceiveAddressService {

    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    public MemberReceiveAddressServiceImpl(MemberReceiveAddressMapper memberReceiveAddressMapper) {
        this.memberReceiveAddressMapper = memberReceiveAddressMapper;
    }

    @Override
    public void insertAddress(MemberReceiveAddressPo memberReceiveAddressPo) {
        memberReceiveAddressMapper.insert(memberReceiveAddressPo);
    }

    @Override
    public void deleteAddress(Long addressId) {
        memberReceiveAddressMapper.deleteById(addressId);
    }

    @Override
    public void updateAddress(MemberReceiveAddressPo memberReceiveAddressPo) {
        memberReceiveAddressMapper.updateById(memberReceiveAddressPo);
    }

    @Override
    public MemberReceiveAddressPo selectAddress(Long addressId) {
        return memberReceiveAddressMapper.selectById(addressId);
    }

    @Override
    public List<MemberReceiveAddressPo> selectAddressByMemberId(Long memberId) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("member_id",memberId);
        return memberReceiveAddressMapper.selectByMap(columnMap);
    }
}

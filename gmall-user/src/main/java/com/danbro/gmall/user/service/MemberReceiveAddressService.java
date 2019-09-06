package com.danbro.gmall.user.service;

import com.danbro.gmall.user.bean.MemberReceiveAddress;
import com.danbro.gmall.user.mapper.MemberReceiveAddressMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/6 12:25
 * description
 **/
@Service
public class MemberReceiveAddressService implements MemberReceiveAddressServiceIml{

    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    public MemberReceiveAddressService(MemberReceiveAddressMapper memberReceiveAddressMapper) {
        this.memberReceiveAddressMapper = memberReceiveAddressMapper;
    }

    @Override
    public void insertAddress(MemberReceiveAddress memberReceiveAddress) {
        memberReceiveAddressMapper.insert(memberReceiveAddress);
    }

    @Override
    public void deleteAddress(Long addressId) {
        memberReceiveAddressMapper.deleteById(addressId);
    }

    @Override
    public void updateAddress(MemberReceiveAddress memberReceiveAddress) {
        memberReceiveAddressMapper.updateById(memberReceiveAddress);
    }

    @Override
    public MemberReceiveAddress selectAddress(Long addressId) {
        return memberReceiveAddressMapper.selectById(addressId);
    }

    @Override
    public List<MemberReceiveAddress> selectAddressByMemberId(Long memberId) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("memberId",memberId);
        return memberReceiveAddressMapper.selectByMap(columnMap);
    }
}

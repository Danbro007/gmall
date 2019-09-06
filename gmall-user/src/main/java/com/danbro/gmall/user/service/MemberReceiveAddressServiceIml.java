package com.danbro.gmall.user.service;

import com.danbro.gmall.user.bean.MemberReceiveAddress;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/6 12:24
 * description
 **/
public interface MemberReceiveAddressServiceIml {

    /**
     * 添加新的地址
     * @param memberReceiveAddress 地址对象
     */
    void insertAddress(MemberReceiveAddress memberReceiveAddress);

    /**
     * 通过地址id删除地址
     * @param addressId 地址id
     */
    void deleteAddress(Long addressId);

    /**
     * 更新地址
     * @param memberReceiveAddress 新的地址信息
     */
    void updateAddress(MemberReceiveAddress memberReceiveAddress);

    /**
     * 通过地址id查找地址
     * @param addressId 地址id
     * @return 地址对象
     */
    MemberReceiveAddress selectAddress(Long addressId);

    /**
     * 通过会员id获得他的所有地址
     * @param memberId 会员id
     * @return 会员的所有地址
     */
    List<MemberReceiveAddress> selectAddressByMemberId(Long memberId);
}
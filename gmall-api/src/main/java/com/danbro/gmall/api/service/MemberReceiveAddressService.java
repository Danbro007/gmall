package com.danbro.gmall.api.service;

import com.danbro.gmall.api.po.MemberReceiveAddressPo;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/6 12:24
 * description
 **/
public interface MemberReceiveAddressService {

    /**
     * 添加新的地址
     * @param memberReceiveAddressPo 地址对象
     */
    void insertAddress(MemberReceiveAddressPo memberReceiveAddressPo);

    /**
     * 通过地址id删除地址
     * @param addressId 地址id
     */
    void deleteAddress(Long addressId);

    /**
     * 更新地址
     * @param memberReceiveAddressPo 新的地址信息
     */
    void updateAddress(MemberReceiveAddressPo memberReceiveAddressPo);

    /**
     * 通过地址id查找地址
     * @param addressId 地址id
     * @return 地址对象
     */
    MemberReceiveAddressPo selectAddress(Long addressId);

    /**
     * 通过会员id获得他的所有地址
     * @param memberId 会员id
     * @return 会员的所有地址
     */
    List<MemberReceiveAddressPo> selectAddressByMemberId(Long memberId);
}

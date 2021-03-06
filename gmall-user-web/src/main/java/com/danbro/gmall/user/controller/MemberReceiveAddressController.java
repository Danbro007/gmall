package com.danbro.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.po.MemberReceiveAddressPo;
import com.danbro.gmall.api.service.MemberReceiveAddressService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Danrbo
 * @date 2019/9/6 12:23
 * description
 **/
@RestController
public class MemberReceiveAddressController {

    @Reference
    MemberReceiveAddressService memberReceiveAddressService;

    @GetMapping("/address/{id}")
    public MemberReceiveAddressPo selectAddress(@PathVariable("id") Long addressId) {
        return memberReceiveAddressService.selectAddress(addressId);
    }

    @PostMapping("/address")
    public void insertAddress(MemberReceiveAddressPo memberReceiveAddressPo) {
        memberReceiveAddressService.insertAddress(memberReceiveAddressPo);
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddress(@PathVariable("id") Long addressId) {
        memberReceiveAddressService.deleteAddress(addressId);
    }

    @PutMapping("/address")
    public void updateAddress(MemberReceiveAddressPo memberReceiveAddressPo) {
        memberReceiveAddressService.updateAddress(memberReceiveAddressPo);
    }
}

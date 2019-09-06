package com.danbro.gmall.user.controller;

import com.danbro.gmall.user.bean.MemberReceiveAddress;
import com.danbro.gmall.user.service.MemberReceiveAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Danrbo
 * @date 2019/9/6 12:23
 * description
 **/
@RestController
public class MemberReceiveAddressController {

private MemberReceiveAddressService memberReceiveAddressService;

    public MemberReceiveAddressController(MemberReceiveAddressService memberReceiveAddressService) {
        this.memberReceiveAddressService = memberReceiveAddressService;
    }

    @GetMapping("/address/{id}")
    public MemberReceiveAddress selectAddress(@PathVariable("id") Long addressId) {
        return memberReceiveAddressService.selectAddress(addressId);
    }

    @PostMapping("/address")
    public void insertAddress(MemberReceiveAddress memberReceiveAddress) {
        memberReceiveAddressService.insertAddress(memberReceiveAddress);
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddress(@PathVariable("id") Long addressId) {
        memberReceiveAddressService.deleteAddress(addressId);
    }

    @PutMapping("/address")
    public void updateAddress(MemberReceiveAddress memberReceiveAddress) {
        memberReceiveAddressService.updateAddress(memberReceiveAddress);
    }
}

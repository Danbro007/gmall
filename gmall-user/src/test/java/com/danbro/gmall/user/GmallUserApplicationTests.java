package com.danbro.gmall.user;

import com.danbro.gmall.api.bean.Member;
import com.danbro.gmall.api.bean.MemberReceiveAddress;
import com.danbro.gmall.api.service.MemberReceiveAddressService;
import com.danbro.gmall.api.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallUserApplicationTests {


    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    private MemberService memberService;

    @Test
    public void getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        members.forEach(System.out::println);
    }
    @Test
    public void getAddressByMemberId() {
        List<MemberReceiveAddress> address = memberReceiveAddressService.selectAddressByMemberId(1L);
        address.forEach(System.out::println);
    }

}

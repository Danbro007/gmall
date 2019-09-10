package com.danbro.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.Member;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.api.vo.MemberInfoVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/5 15:36
 * description 会员控制器
 **/

@RestController
@ResponseBody
public class MemberController {

    @Reference
    MemberService memberService;


    @GetMapping("/members")
    public List<Member> getAllMember() {
        return memberService.getAllMembers();
    }

    @PostMapping("/members")
    public void insertMember(Member member) {
        memberService.insertMember(member);
    }

    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
    }

    @PutMapping("/members")
    public void updateMember(Member member) {
        memberService.updateMember(member);
    }

    @GetMapping("/members/{id}")
    public MemberInfoVO getAddressByMemberId(@PathVariable("id") Long memberId) {
        return memberService.selectMember(memberId);
    }


}

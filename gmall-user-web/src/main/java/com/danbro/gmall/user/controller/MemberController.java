package com.danbro.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.api.vo.MemberPoInfoVo;
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
    public List<MemberPo> getAllMember() {
        return memberService.getAllMembers();
    }

    @PostMapping("/members")
    public void insertMember(MemberPo memberPo) {
        memberService.insertMember(memberPo);
    }

    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
    }

    @PutMapping("/members")
    public void updateMember(MemberPo memberPo) {
        memberService.updateMember(memberPo);
    }

    @GetMapping("/members/{id}")
    public MemberPoInfoVo getAddressByMemberId(@PathVariable("id") Long memberId) {
        return memberService.selectMember(memberId);
    }


}

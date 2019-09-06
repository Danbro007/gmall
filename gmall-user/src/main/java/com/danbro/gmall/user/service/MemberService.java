package com.danbro.gmall.user.service;

import com.danbro.gmall.user.bean.Member;
import com.danbro.gmall.user.mapper.MemberMapper;
import com.danbro.gmall.user.mapper.MemberReceiveAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/5 15:42
 * description
 **/
@Service
public class MemberService implements MemberServiceIml {

    private MemberMapper memberMapper;

    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    public MemberService(MemberMapper memberMapper, MemberReceiveAddressMapper memberReceiveAddressMapper) {
        this.memberMapper = memberMapper;
        this.memberReceiveAddressMapper = memberReceiveAddressMapper;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberMapper.selectList(null);
    }

    @Override
    public void insertMember(Member member) {
        memberMapper.insert(member);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMember(Long memberId) {
        memberMapper.deleteById(memberId);
        HashMap<String, Object> columnMap  = new HashMap<>(16);
        columnMap .put("memberId",memberId);
        memberReceiveAddressMapper.deleteByMap(columnMap);
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateById(member);
    }

    @Override
    public Member selectMember(Long memberId) {
        return memberMapper.selectById(memberId);
    }

}

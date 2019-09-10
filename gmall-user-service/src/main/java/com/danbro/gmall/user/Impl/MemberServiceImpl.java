package com.danbro.gmall.user.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.Member;
import com.danbro.gmall.api.bean.MemberReceiveAddress;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.api.vo.MemberInfoVO;
import com.danbro.gmall.user.mapper.MemberMapper;
import com.danbro.gmall.user.mapper.MemberReceiveAddressMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/5 15:42
 * description
 **/
@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;

    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    public MemberServiceImpl(MemberMapper memberMapper, MemberReceiveAddressMapper memberReceiveAddressMapper) {
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
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("member_id", memberId);
        memberReceiveAddressMapper.deleteByMap(columnMap);
    }


    @Override
    public void updateMember(Member member) {
        memberMapper.updateById(member);
    }

    @Override
    public MemberInfoVO selectMember(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        MemberInfoVO memberInfoVO = new MemberInfoVO();
        BeanUtils.copyProperties(member,memberInfoVO);
        List<MemberReceiveAddress> addressList= memberReceiveAddressMapper.getAddressByMemberId(memberId);
        memberInfoVO.setAddressList(addressList);
        return memberInfoVO;
    }

}
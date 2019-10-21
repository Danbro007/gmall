package com.danbro.gmall.user.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.po.MemberReceiveAddressPo;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.api.vo.MemberPoInfoVo;
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
    public List<MemberPo> getAllMembers() {
        return memberMapper.selectList(null);
    }

    @Override
    public void insertMember(MemberPo memberPo) {
        memberMapper.insert(memberPo);
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
    public void updateMember(MemberPo memberPo) {
        memberMapper.updateById(memberPo);
    }

    @Override
    public MemberPoInfoVo selectMember(Long memberId) {
        MemberPo memberPo = memberMapper.selectById(memberId);
        MemberPoInfoVo memberInfoVO = new MemberPoInfoVo();
        BeanUtils.copyProperties(memberPo,memberInfoVO);
        List<MemberReceiveAddressPo> addressList= memberReceiveAddressMapper.getAddressByMemberId(memberId);
        memberInfoVO.setAddressList(addressList);
        return memberInfoVO;
    }

}

package com.danbro.gmall.api.service;

import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.vo.MemberPoInfoVo;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/5 15:43
 * description
 **/

public interface MemberService {
    /**
     * 获得所有memberDto
     * @return 所有memberDto
     */
    List<MemberPo> getAllMembers();

    /**
     * 添加新的会员
     * @param memberPo 添加的会员数据
     */
    void insertMember(MemberPo memberPo);

    /**
     * 通过会员id删除会员
     * @param memberId 会员id
     */
    void deleteMember(Long memberId);

    /**
     * 更新会员信息
     * @param memberPo 要更新的会员对象
     */
    void updateMember(MemberPo memberPo);

    /**
     * 通过会员id查到会员信息
     * @param memberId 会员id
     * @return 会员对象
     */
    MemberPoInfoVo selectMember(Long memberId);

}

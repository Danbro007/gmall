package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.Member;
import com.danbro.gmall.api.vo.MemberInfoVO;

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
    List<Member> getAllMembers();

    /**
     * 添加新的会员
     * @param member 添加的会员数据
     */
    void insertMember(Member member);

    /**
     * 通过会员id删除会员
     * @param memberId 会员id
     */
    void deleteMember(Long memberId);

    /**
     * 更新会员信息
     * @param member 要更新的会员对象
     */
    void updateMember(Member member);

    /**
     * 通过会员id查到会员信息
     * @param memberId 会员id
     * @return 会员对象
     */
    MemberInfoVO selectMember(Long memberId);

}

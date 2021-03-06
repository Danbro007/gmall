package com.danbro.gmall.user.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.po.MemberReceiveAddressPo;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.api.vo.MemberPoInfoVo;
import com.danbro.gmall.service.utils.util.MqProducerUtil;
import com.danbro.gmall.user.mapper.MemberMapper;
import com.danbro.gmall.user.mapper.MemberReceiveAddressMapper;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Danrbo
 * @date 2019/9/5 15:42
 * description
 **/
@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;

    private RedisTemplate redisTemplate;

    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    @Autowired
    private MqProducerUtil mqProducerUtil;
    public MemberServiceImpl(MemberMapper memberMapper, RedisTemplate redisTemplate, MemberReceiveAddressMapper memberReceiveAddressMapper) {
        this.memberMapper = memberMapper;
        this.redisTemplate = redisTemplate;
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
        BeanUtils.copyProperties(memberPo, memberInfoVO);
        List<MemberReceiveAddressPo> addressList = memberReceiveAddressMapper.getAddressByMemberId(memberId);
        memberInfoVO.setAddressList(addressList);
        return memberInfoVO;
    }

    @Override
    public MemberPo login(String username, String password) {
        String hashPasspord = DigestUtils.md5DigestAsHex(password.getBytes());
        String key = "User:" + username + ":" + hashPasspord;
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return (MemberPo) value;
        }
        MemberPo memberPo = loginFromDb(username, hashPasspord);
        if (memberPo != null) {
            redisTemplate.opsForValue().set(key, memberPo);
            return memberPo;
        }
        return null;
    }

    @Override
    public MemberPo loginFromDb(String username, String password) {
        QueryWrapper<MemberPo> memberPoQueryWrapper = new QueryWrapper<>();
        memberPoQueryWrapper.eq("username", username).eq("password", password);
        return memberMapper.selectOne(memberPoQueryWrapper);
    }

    @Override
    public void addUserToken(Long memberId, String token,String cartList) {
        String key = "User:" + memberId + "token";
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, token, 60 * 60 * 12, TimeUnit.SECONDS);
        if (!StringUtils.isEmpty(cartList)){
            ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
            try {
                mapMessage.setLong("memberId",memberId);
                mapMessage.setString("cartList",cartList);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            mqProducerUtil.setMessage("CART_MERGE",mapMessage);
        }
    }

    @Override
    public MemberPo addOauthUser(MemberPo memberPo) {
        memberMapper.insert(memberPo);
        return memberPo;
    }

    @Override
    public MemberPo checkOauthUser(MemberPo memberPo) {
        QueryWrapper<MemberPo> memberPoQueryWrapper = new QueryWrapper<>();
        memberPoQueryWrapper.eq("source_type", memberPo.getSourceType()).eq("source_uid", memberPo.getSourceUid());
        return memberMapper.selectOne(memberPoQueryWrapper);
    }

}

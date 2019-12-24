package com.danbro.gmall.cart.service.listener;

import com.alibaba.fastjson.JSON;
import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.List;

/**
 * @Classname CartListener
 * @Description TODO
 * @Date 2019/12/4 21:07
 * @Author Danrbo
 */
@Component
public class CartListener {

    @Autowired
    CartService cartService;

    /**
     * 通过消息队列接收 合并cookie和数据库里的购物车信息
     * @param mapMessage 消息队列
     */
    @JmsListener(destination = "CART_MERGE",containerFactory = "jmsQueueListener")
    public void mergeCart(MapMessage mapMessage){
        try {
            List<OmsCartItemDto> cartListFromCache = JSON.parseArray(mapMessage.getString("cartList"), OmsCartItemDto.class);
            Long memberId = mapMessage.getLong("memberId");
            List<OmsCartItemDto> cartListFromDb = cartService.getCartListByMemberId(memberId, false);
            for (OmsCartItemDto omsCartItemDto : cartListFromCache) {
                omsCartItemDto.setMemberId(memberId.toString());
                for (OmsCartItemDto cartItemDto : cartListFromDb) {
                    //商品id一样更新数据库里购物车里的数量
                    if (cartItemDto.getProductSkuId().equals(omsCartItemDto.getProductSkuId())){
                        cartItemDto.setQuantity(cartItemDto.getQuantity() + omsCartItemDto.getQuantity());
                        cartService.updateItemCart(cartItemDto);
                    }else {
                        //把cookie里的商品信息添加到数据库的购物车里的
                        cartService.updateItemCart(omsCartItemDto);
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

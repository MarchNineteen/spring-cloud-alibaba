package com.tuling.controller;

import com.tuling.entity.OrderInfo;
import com.tuling.entity.ProductInfo;
import com.tuling.mapper.OrderInfoMapper;
import com.tuling.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderInfoController {

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    public static final String uri = "http://localhost:8081/selectProductInfoById/";

    @RequestMapping("/selectOrderInfoById/{orderNo}")
    public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo) {

        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
        if (null == orderInfo) {
            return "根据orderNo:" + orderNo + "查询没有该订单";
        }
        ResponseEntity<ProductInfo> responseEntity = restTemplate.getForEntity(uri + orderInfo.getProductNo(), ProductInfo.class);

        ProductInfo productInfo = responseEntity.getBody();

        if (productInfo == null) {
            return "没有对应的商品";
        }

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(orderInfo.getOrderNo());
        orderVo.setUserName(orderInfo.getUserName());
        orderVo.setProductName(productInfo.getProductName());
        orderVo.setProductNum(orderInfo.getProductCount());

        return orderVo;
    }

}

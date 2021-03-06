package com.tuling.controller;

import com.tuling.entity.OrderInfo;
import com.tuling.entity.ProductInfo;
import com.tuling.feignapi.productcenter.ProductCenterFeignApi;
import com.tuling.mapper.OrderInfoMapper;
import com.tuling.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@Slf4j
public class OrderInfoController {

    @Autowired
    private ProductCenterFeignApi productCenterFeignApi;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @RequestMapping("/selectOrderInfoById/{orderNo}")
    public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo) {

        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
        if (null == orderInfo) {
            return "根据orderNo:" + orderNo + "查询没有该订单";
        }

        ProductInfo productInfo = productCenterFeignApi.selectProductInfoById(orderNo);

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

    /**
     * 方法实现说明:模仿  流控模式【关联】  读接口
     *
     * @param orderNo
     */
    @RequestMapping("/findById/{orderNo}")
    public Object findById(@PathVariable("orderNo") String orderNo) {
        log.info("orderNo:{}", "执行查询操作");
        return orderInfoMapper.selectOrderInfoById(orderNo);
    }

    /**
     * 方法实现说明:模仿流控模式【关联】   写接口(优先)
     */
    @RequestMapping("/saveOrder")
    public String saveOrder() {
        log.info("执行保存操作,模仿返回订单ID");
        return UUID.randomUUID().toString();
    }

    @RequestMapping("/findAll")
    public String findAll() throws InterruptedException {
//        Thread.sleep(2000);
        this.orderServiceImpl.common();
        return "findAll";
    }

    @RequestMapping("/findAllByCondtion")
    public String findAllByCondtion() {
        this.orderServiceImpl.common();
        return "findAllByCondition";
    }

}

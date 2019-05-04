package com.fjdakj.esjestclient.controller;

import com.fjdakj.esjestclient.dto.InsertOrderListParam;
import com.fjdakj.esjestclient.dto.QueryOrderParam;
import com.fjdakj.esjestclient.entity.Order;
import com.fjdakj.esjestclient.service.OrderService;
import com.fjdakj.esjestclient.utils.CommonUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @CliassName: OrderController
 * @Author: DZ5343
 * @Date: 2019/4/28 10:13
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/insertOrder")
    public Map insertOrder(@RequestBody Order order){
        if (order.getFullName()==null ||order.getPhone()==null ||order.getAddress()==null){
            return CommonUtils.fail("请输入完整的订单",5010);
        }
        String message =null;
        if (orderService.insertOrder(order)){
             message = "添加订单成功";
        }
        return CommonUtils.success(message);
    }

    @PostMapping("/insertOrderList")
    public Map insertOrderList(@RequestBody InsertOrderListParam param){
        List<Order> orderList = param.getOrderList();
        List<Order> orders = new ArrayList<>();
        for (Order order : orderList) {
            if ( order.getFullName()!=null && order.getPhone()!=null && order.getAddress()!=null){
                orders.add(order);
            }
        }
        if (orders.size()==0){
            return CommonUtils.fail("请输入正确的订单格式",5010);
        }
        orderService.insertOrderList(orders);
        return CommonUtils.success("添加"+orders.size()+"条订单成功");
    }


    @RequestMapping("/updateOrder")
    public Map updateOrder(@RequestBody Order order){
        if (order.getFullName()==null ||order.getPhone()==null ||order.getAddress()==null){
            return CommonUtils.fail("请输入正确的订单",5010);
        }
        if (orderService.updateOrder(order)){
            return CommonUtils.operateSuccess();
        }
        return CommonUtils.fail("更新失败,可能是没有这条数据",5010);
    }

    @PostMapping("/listOrders")
    public Map<String, Object> listOrders(@RequestBody QueryOrderParam param){
        if (param.getPageIndex()==null){
            param.setPageIndex(1);
        }
        if (param.getPageSize()==null){
            param.setPageSize(10);
        }
        List<Order> orders = orderService.listOrders(param);
        return CommonUtils.success(orders);
    }
}

package com.fjdakj.esjestclient.service.Impl;

import com.fjdakj.esjestclient.daoes.OrderDaoEs;
import com.fjdakj.esjestclient.dto.QueryOrderParam;
import com.fjdakj.esjestclient.entity.Order;
import com.fjdakj.esjestclient.service.OrderService;
import com.fjdakj.esjestclient.utils.CommonUtils;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @CliassName: OrderServiceImpl
 * @Author: DZ5343
 * @Date: 2019/4/28 10:16
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDaoEs orderDaoEs;


    @Override
    public boolean insertOrder(Order order) {
        return orderDaoEs.insertOrder(order);
    }



    @Override
    public boolean updateOrder(Order order) {
        return orderDaoEs.updateByJson(CommonUtils.removeNullValue(order));
    }


    @Override
    public JestResult insertOrderList(List<Order> orderList) {
        return orderDaoEs.insertOrderList(orderList);
    }

    @Override
    public List<Order> listOrders(QueryOrderParam param) {
        SearchResult searchResult = orderDaoEs.listOrders(param);
        if (!searchResult.isSucceeded()){
            return null;
        }
        List<SearchResult.Hit<Order, Void>> hits = searchResult.getHits(Order.class);
        ArrayList<Order> orders = new ArrayList<>();

        for (SearchResult.Hit<Order, Void> hit : hits) {
            orders.add(hit.source);
        }

        return orders;


    }


}

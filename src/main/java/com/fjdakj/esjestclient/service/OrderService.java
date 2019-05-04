package com.fjdakj.esjestclient.service;

import com.fjdakj.esjestclient.dto.QueryOrderParam;
import com.fjdakj.esjestclient.entity.Order;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;

import java.util.List;

public interface OrderService {
    public boolean insertOrder(Order order);

    public boolean updateOrder(Order order);

    public JestResult insertOrderList(List<Order> orderList);

    public List<Order> listOrders(QueryOrderParam param);
}

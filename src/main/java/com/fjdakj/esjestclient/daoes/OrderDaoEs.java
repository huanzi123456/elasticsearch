package com.fjdakj.esjestclient.daoes;


import com.alibaba.fastjson.JSONObject;
import com.fjdakj.esjestclient.dto.QueryOrderParam;
import com.fjdakj.esjestclient.entity.Order;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;


/**
 * @CliassName: OrderDaoEs
 * @Author: DZ5343
 * @Date: 2019/4/28 10:02
 * @Description:
 */
@Slf4j
@Component
public class OrderDaoEs {
    private static final String INDEX = "order";
    private static final String TYPE = "express";
    @Autowired
    JestClient jestClient;
    
    
    /**
     *@author DZ5343
     *@date 2019/4/28 10:12
     *@desc 创建索引以及添加订单,自动mapping 类型
     */
    public boolean insertOrder(Order order){
        Index build = new Index.Builder(order).index(INDEX).type(TYPE).build();
        try{
            JestResult result = jestClient.execute(build);
            if(result == null || !result.isSucceeded()){
                throw new Exception(result.getErrorMessage()+"创建索引失败!");
            }
            return result.isSucceeded() ;
        }catch(Exception e) {
            return false;
        }
    }

    /**
     *@author DZ5343
     *@date 2019/4/28 10:43
     *@desc 批量添加订单 //修改,需要返回list
     */
    public JestResult insertOrderList(List<Order> orderList){
        Bulk.Builder builder = new Bulk.Builder();
        for (Order order : orderList) {
            builder.addAction(new Index.Builder(order).index(INDEX).type(TYPE).build());
        }
        try {
            BulkResult execute = jestClient.execute(builder.build());
            return execute;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     *@author DZ5343
     *@date 2019/4/28 10:54
     *@desc 更新订单
     */
    public JestResult updateOrder(Order order){
        String id = order.getId();
        Update update = new Update.Builder(order).index(INDEX).type(TYPE).id(id).build();
        try {
            return jestClient.execute(update);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("更新发生错误"+e.getMessage());
            return null;
        }
    }

    /***
     *@author DZ5343
     *@date 2019/4/28 19:38
     *@desc 订单多条件查询
     */
    public SearchResult listOrders(QueryOrderParam param) {
        //组合查询器
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //按照订单号,
        if (param.getId()!=null){
            queryBuilder.must(QueryBuilders.termQuery("orderId",param.getId()));
        }
        //按照手机号精准查询
        if (param.getPhone()!=null){
            queryBuilder.must(QueryBuilders.termQuery("phone",param.getPhone()));
        }
        //按照备注模糊查询
        if (!StringUtils.isEmpty(param.getComment())){
            queryBuilder.must(QueryBuilders.matchQuery("comment","*"+param.getComment()+"*"));
        }
        //按照收货人模糊查询
        if (!StringUtils.isEmpty(param.getFullName())){
            queryBuilder.must(QueryBuilders.matchQuery("fullName","*"+param.getFullName()+"*"));
        }

        //语句构造
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        String string = sourceBuilder.query(queryBuilder).from((param.getPageIndex() - 1) * param.getPageSize()).size(param.getPageSize()).toString();


        Search build = new Search.Builder(string).addIndex(INDEX).addType(TYPE).build();
        try {
            SearchResult execute = jestClient.execute(build);
            return execute;
        } catch (IOException e) {
            log.error("查询出错"+e.getMessage());
            return null;
        }

    }


    //更新项目
    public boolean updateByJson(JSONObject jsonObject) {

        String id = jsonObject.get("id").toString();
        JSONObject doc = new JSONObject();
        doc.put("doc", jsonObject);
        Update update = new Update.Builder(doc).index(INDEX).type(TYPE).id(id).refresh(true).build();
        try {
            DocumentResult execute = jestClient.execute(update);
            boolean succeeded = execute.isSucceeded();
            if (!succeeded) {
                log.warn("dao层-update方法发生异常->,id:{}, 错误码:{}, msg:{} .", id, execute.getResponseCode(), execute.getErrorMessage());
            }
            return succeeded;
        } catch (IOException e) {
            log.error("dao层-->update方法发生异常->", e);
            return false;
        }

    }

























}

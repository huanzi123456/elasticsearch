package com.fjdakj.esjestclient.daoes;

import com.fjdakj.esjestclient.dto.QueryCommentParam;

import com.fjdakj.esjestclient.dto.QueryOrderParam;
import com.fjdakj.esjestclient.entity.Comment;
import com.fjdakj.esjestclient.entity.Order;
import com.fjdakj.esjestclient.entity.ResultOrder;
import io.searchbox.client.JestClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @CliassName: CommentEs
 * @Author: DZ5343
 * @Date: 2019/4/28 19:15
 * @Description:
 */
@Slf4j
@Component
public class CommentEs {
    private static final String INDEX = "comment";
    private static final String TYPE = "express";

    @Autowired
    JestClient jestClient;

    @Autowired
    OrderDaoEs orderDaoEs;
    /***
     *@author DZ5343
     *@date 2019/4/28 19:18
     *@desc 添加评论
     */
    public boolean insertComment(Comment comment){
        Index build = new Index.Builder(comment).index(INDEX).type(TYPE).build();
        try{
            DocumentResult execute = jestClient.execute(build);
            if(execute == null || !execute.isSucceeded()){
                throw new Exception(execute.getErrorMessage()+"创建索引失败!");
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }

    //通过订单id查询评论集合
    public List<Comment> findCommentByOrderId(String orderId){
        //按照订单号
        String string = null;
        if (orderId!=null){
            MatchQueryBuilder orderId1 = QueryBuilders.matchQuery("orderId", orderId);
            //语句构造
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            string = sourceBuilder.query(orderId1).toString();
        }
        Search build = new Search.Builder(string).addIndex(INDEX).addType(TYPE).build();

        try {
            SearchResult execute = jestClient.execute(build);
            List<SearchResult.Hit<Comment, Void>> hits3 = execute.getHits(Comment.class);
            ArrayList<Comment> comments = new ArrayList<>();
            for (SearchResult.Hit<Comment, Void> hit : hits3) {
                comments.add(hit.source);
            }
            return comments;
        } catch (IOException e) {
            log.error("查询出错"+e.getMessage());
            return null;
        }


    }

    //评价多条件查询
    public List<ResultOrder> listComment(QueryCommentParam param ){
        ArrayList<ResultOrder> resultOrders = new ArrayList<>();



        List<Comment> comments = new ArrayList<>();

        //查询订单参数处理
        QueryOrderParam queryOrderParam = new QueryOrderParam();
        queryOrderParam.setId(param.getOrderId());
        queryOrderParam.setPhone(param.getPhone());
        queryOrderParam.setComment(param.getComment());
        queryOrderParam.setFullName(param.getFullName());
        queryOrderParam.setAddress(param.getAddress());
        queryOrderParam.setPageIndex(param.getPageIndex());
        queryOrderParam.setPageSize(param.getPageSize());
        SearchResult searchResult = orderDaoEs.listOrders(queryOrderParam);

        List<Comment> commentByOrderId =null;
        List<SearchResult.Hit<Order, Void>> hits = searchResult.getHits(Order.class);
        for (SearchResult.Hit<Order, Void> hit : hits) {
            ResultOrder resultOrder = new ResultOrder();
            commentByOrderId = findCommentByOrderId(hit.source.getId());

            resultOrder.setOrderId(hit.source.getId());
            resultOrder.setPhone(hit.source.getPhone());
            resultOrder.setComment(hit.source.getComment());
            resultOrder.setFullName(hit.source.getFullName());
            resultOrder.setAddress(hit.source.getAddress());

            resultOrder.setContent(commentByOrderId);
            resultOrders.add(resultOrder);
        }
        if (StringUtils.isEmpty(param.getContent())){
            return resultOrders;
        }else {

            //按照评价内容查询
            MatchQueryBuilder builder = QueryBuilders.matchQuery("content", "*" + param.getContent() + "*");
            String querystring = new SearchSourceBuilder().query(builder).from((param.getPageIndex() - 1) * param.getPageSize()).size(param.getPageSize()).toString();
            Search build = new Search.Builder(querystring).addIndex(INDEX).addType(TYPE).build();
            try {
                SearchResult execute = jestClient.execute(build);
                List<SearchResult.Hit<Comment, Void>> hits1 = execute.getHits(Comment.class);
                for (SearchResult.Hit<Comment, Void> comment : hits1) {
                    for (Comment comment1 : commentByOrderId) {
                        if (comment.source.getOrderId() == comment1.getOrderId() )
                            comments.add(comment1);
                    }
                }

            } catch (IOException e) {
                log.error("查询出错"+e.getMessage());
                e.printStackTrace();
            }
        }

        return resultOrders;
    }
}

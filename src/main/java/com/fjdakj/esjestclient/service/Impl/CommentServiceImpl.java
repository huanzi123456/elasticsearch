package com.fjdakj.esjestclient.service.Impl;

import com.fjdakj.esjestclient.daoes.CommentEs;
import com.fjdakj.esjestclient.dto.QueryCommentParam;
import com.fjdakj.esjestclient.entity.Comment;
import com.fjdakj.esjestclient.entity.ResultOrder;
import com.fjdakj.esjestclient.service.CommentService;
import io.searchbox.client.JestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @CliassName: CommentServiceImpl
 * @Author: DZ5343
 * @Date: 2019/4/28 19:21
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    JestClient jestClient;

    @Autowired
    CommentEs commentEs;
    /***
     *@author DZ5343
     *@date 2019/4/28 19:22
     *@desc 添加评论
     */
    @Override
    public boolean insertComment(Comment comment) {
        boolean b = commentEs.insertComment(comment);
        return b;
    }

    @Override
    public List<ResultOrder> listComment(QueryCommentParam param) {
        List<ResultOrder> resultOrders = commentEs.listComment(param);
        return resultOrders;
    }
}

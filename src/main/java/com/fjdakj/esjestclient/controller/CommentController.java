package com.fjdakj.esjestclient.controller;

import com.fjdakj.esjestclient.dto.QueryCommentParam;
import com.fjdakj.esjestclient.entity.Comment;
import com.fjdakj.esjestclient.dto.RequestInsertComment;
import com.fjdakj.esjestclient.entity.ResultOrder;
import com.fjdakj.esjestclient.service.CommentService;
import com.fjdakj.esjestclient.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @CliassName: CommentController
 * @Author: DZ5343
 * @Date: 2019/4/28 19:24
 * @Description:
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/insertComment")
    public Map insertComment(@RequestBody RequestInsertComment requestInsertComment){
        if (requestInsertComment.getOrderId()==null || requestInsertComment.getContent()==null){
            return CommonUtils.fail("请输入完整的请求参数",5010);
        }
        Comment comment = new Comment();
        comment.setOrderId(requestInsertComment.getOrderId());;
        comment.setContent(requestInsertComment.getContent());
        if (commentService.insertComment(comment)){
            return CommonUtils.success("添加评论成功");
        }
        return CommonUtils.fail("新增评论出错",5010);
    }

    //评价多条件查询
    @PostMapping("/listComment")
    public List<ResultOrder> listComment(@RequestBody QueryCommentParam param){
        if (param.getPageIndex()==null ){
            param.setPageIndex(1);
        }
        if (param.getPageSize()==null){
            param.setPageSize(10);
        }
        return commentService.listComment(param);
    }
}


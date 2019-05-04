package com.fjdakj.esjestclient.service;

import com.fjdakj.esjestclient.dto.QueryCommentParam;
import com.fjdakj.esjestclient.entity.Comment;
import com.fjdakj.esjestclient.entity.ResultOrder;

import java.util.List;

public interface CommentService {
    public boolean insertComment(Comment comment);

    public List<ResultOrder> listComment(QueryCommentParam param );
}

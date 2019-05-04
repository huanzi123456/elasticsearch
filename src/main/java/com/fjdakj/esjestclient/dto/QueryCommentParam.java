package com.fjdakj.esjestclient.dto;

import io.searchbox.annotations.JestId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @CliassName: QueryCommentParam
 * @Author: DZ5343
 * @Date: 2019/4/29 8:28
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCommentParam {
    private String orderId;
    //收货人手机号
    private String phone;
    //订单备注
    private String comment=null;
    //收货人姓名
    private String fullName=null;
    //收货人地址
    private String address=null;


    private Long commentId;
    //评论内容
    private String content;

    private Integer pageIndex;//页码
    private Integer pageSize;//分页大小
}

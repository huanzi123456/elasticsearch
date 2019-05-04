package com.fjdakj.esjestclient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @CliassName: Result
 * @Author: DZ5343
 * @Date: 2019/4/29 9:03
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultOrder implements Serializable{
    private String orderId;
    //收货人手机号
    private String phone;
    //订单备注

    private String comment;
    //收货人姓名

    private String fullName=null;
    //收货人地址

    private String address=null;
    //评论内容
    private List<Comment> content;
}

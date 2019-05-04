package com.fjdakj.esjestclient.entity;

import io.searchbox.annotations.JestId;
import jdk.internal.instrumentation.TypeMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.lang.NonNull;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * @CliassName: Order
 * @Author: DZ5343
 * @Date: 2019/4/28 9:51
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    /**
     * 订单号
     */
    @JestId
    @Field(type = FieldType.Keyword)
    private String id;
    //收货人手机号
    private String phone;
    //订单备注
    private String comment;
    //收货人姓名
    private String fullName=null;
    //收货人地址
    private String address=null;
}

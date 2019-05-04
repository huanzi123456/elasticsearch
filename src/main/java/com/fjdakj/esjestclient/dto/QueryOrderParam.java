package com.fjdakj.esjestclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @CliassName: QueryOrderParam
 * @Author: DZ5343
 * @Date: 2019/4/28 14:47
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrderParam {
    private String id;
    //收货人手机号
    private String phone;
    //订单备注
    private String comment=null;
    //收货人姓名
    private String fullName=null;
    //收货人地址
    private String address=null;


    private Integer pageIndex;//页码
    private Integer pageSize;//分页大小
}

package com.fjdakj.esjestclient.dto;

import com.fjdakj.esjestclient.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @CliassName: insertOrderListParam
 * @Author: DZ5343
 * @Date: 2019/4/29 15:09
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertOrderListParam implements Serializable{
    List<Order> orderList;
}

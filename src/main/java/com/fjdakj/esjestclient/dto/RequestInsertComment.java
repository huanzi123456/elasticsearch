package com.fjdakj.esjestclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @CliassName: requestInsertComment
 * @Author: DZ5343
 * @Date: 2019/4/30 14:34
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestInsertComment implements Serializable{
    private String orderId;
    private String content;
}

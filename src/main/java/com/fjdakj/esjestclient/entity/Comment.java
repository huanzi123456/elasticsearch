package com.fjdakj.esjestclient.entity;

import io.searchbox.annotations.JestId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @CliassName: Comment
 * @Author: DZ5343
 * @Date: 2019/4/28 19:10
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @JestId
    private String id;

    private String orderId;

    private String content;

}

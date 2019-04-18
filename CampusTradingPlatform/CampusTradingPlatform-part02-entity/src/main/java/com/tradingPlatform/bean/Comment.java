package com.tradingPlatform.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class Comment implements Serializable {

    private Integer commentId;

    private Integer userId;

    private Integer itemId;

    private Integer type;

    private boolean look;

    private String content;

    private Date time;


}

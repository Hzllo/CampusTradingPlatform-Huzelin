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
public class Item implements Serializable {

    private Integer itemId;

    private Integer userId;

    private Date time;

    private String content;

    private String imageUrl;

}

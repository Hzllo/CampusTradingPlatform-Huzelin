package com.tradingPlatform.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class TbItemDetailVO implements Serializable {

    private Integer itemId;

    private Integer userId;

    private TbUser user;

    private List<TbComment> comments;

    private Date time;

    private String content;

    private String title;

    private String imageUrl;

}

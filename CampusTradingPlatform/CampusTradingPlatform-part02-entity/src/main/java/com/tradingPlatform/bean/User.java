package com.tradingPlatform.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class User implements Serializable {

    private Integer userId;

    private String avatar;

    private String nickname;

    private String password;

    private String phone;

}

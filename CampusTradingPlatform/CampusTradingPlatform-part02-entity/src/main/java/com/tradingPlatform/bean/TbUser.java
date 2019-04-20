package com.tradingPlatform.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Table(name = "tb_user")
public class TbUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String image;

    private String nickname;

    private String password;

    private String phone;

    public Integer getUserId() {
        return userId;
    }

    public TbUser setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getImage() {
        return image;
    }

    public TbUser setImage(String image) {
        this.image = image;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public TbUser setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public TbUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public TbUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }


}

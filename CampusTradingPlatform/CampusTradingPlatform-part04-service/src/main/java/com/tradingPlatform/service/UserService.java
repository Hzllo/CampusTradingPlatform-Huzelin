package com.tradingPlatform.service;

import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.util.BaseService;

public interface UserService extends BaseService<TbUser> {

    TbUser findUserByUserName(String phone);

    TbUser findUserByPhone(String phone);

    void updateUser(TbUser user);
}

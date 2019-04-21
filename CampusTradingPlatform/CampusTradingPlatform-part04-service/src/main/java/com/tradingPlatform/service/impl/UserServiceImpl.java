package com.tradingPlatform.service.impl;

import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.impl.BaseServiceImpl;
import com.tradingPlatform.mapper.UserMapper;
import com.tradingPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl extends BaseServiceImpl<TbUser> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名获取用户
     *
     * @param name
     * @return
     */
    @Override
    public TbUser findUserByUserName(String name) {
        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("username", name);
        TbUser user = userMapper.selectOneByExample(example);
        return user;
    }

    /**
     * 根据手机号码获取用户
     *
     * @param phone
     * @return
     */
    @Override
    public TbUser findUserByPhone(String phone) {

        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("phone", phone);
        TbUser user = userMapper.selectOneByExample(example);
        return user;
    }
}

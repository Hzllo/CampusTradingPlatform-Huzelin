package com.tradingPlatform.service.impl;

import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.impl.BaseServiceImpl;
import com.tradingPlatform.mapper.UserMapper;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
        example.createCriteria().andEqualTo("phone", phone);
        TbUser user = userMapper.selectOneByExample(example);
        return user;
    }

    /**
     * 更新用户
     *
     * @param user 新用户数据
     */
    @Override
    public void updateUser(TbUser user) {
        TbUser tbUser = findByPrimaryKeyService(user.getUserId());
        if (!StringUtils.isEmpty(user.getUsername())) {
            tbUser.setUsername(user.getUsername());
        }
        if (!StringUtils.isEmpty(user.getImage())) {
            tbUser.setImage(user.getImage());
        }
        if (!StringUtils.isEmpty(user.getPassword())) {
            String md5password = MD5Util.md5(user.getPassword());
            tbUser.setPassword(md5password);
        }
        updateService(tbUser);
    }
}

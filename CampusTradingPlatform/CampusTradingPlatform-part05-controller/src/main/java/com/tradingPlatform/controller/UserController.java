package com.tradingPlatform.controller;

import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.util.MD5Util;
import com.tradingPlatform.util.PhoneFormatCheckUtils;
import com.tradingPlatform.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/user"})
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前登录的用户名
     *
     * @return 用户名
     */
    @GetMapping("/getUsername")
    public Map<String, Object> getUsername() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("username", user.getUsername());
        return map;
    }

    /**
     * 查一个用户
     *
     * @return
     */
    @GetMapping("/getUser")
    public TbUser findById() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByUserName(user.getUsername()).setPassword(null);
    }

    /**
     * 增加一个用户
     *
     * @param user
     * @return
     */
    @PostMapping("reg")
    public ResultInfo save(@RequestBody TbUser user) {
        ResultInfo resultInfo = new ResultInfo(false, "注册失败", null);
        if (!PhoneFormatCheckUtils.isPhoneLegal(user.getPhone())) {
            resultInfo.setMessage("手机号码格式错误!");
        } else {
            if (user.getUsername().length() > 7) {
                resultInfo.setMessage("用户名太长!");
                return resultInfo;
            }
            if (userService.findUserByUserName(user.getUsername()) != null) {
                resultInfo.setMessage("用户名已注册!");
                return resultInfo;
            }
            if (userService.findUserByPhone(user.getPhone()) != null) {
                resultInfo.setMessage("该手机号已经被注册!");
                return resultInfo;
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                resultInfo.setMessage("密码为空!");
                return resultInfo;
            }
            if (StringUtil.isEmpty(user.getImage())) {
                user.setImage("https://avatar.csdnimg.cn/b/5/c/1_hzllo_.jpg");
            }
            String md5password = MD5Util.md5(user.getPassword());
            userService.addService(user.setPassword(md5password));
            TbUser tbUser = userService.findByPrimaryKeyService(user.getUserId());
            if (tbUser != null) {
                resultInfo.setStatus(true).setMessage("注册成功").setObject(tbUser);
            }
        }
        return resultInfo;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @PostMapping("update")
    public ResultInfo update(@RequestBody TbUser user) {
        ResultInfo resultInfo = ResultInfo.failure("修改失败！");
        userService.updateService(user);
        TbUser tbUser = userService.findByPrimaryKeyService(user.getUserId());
        if (tbUser != null) {
            resultInfo = ResultInfo.success("修改成功!", tbUser);
        }
        return resultInfo;
    }

}

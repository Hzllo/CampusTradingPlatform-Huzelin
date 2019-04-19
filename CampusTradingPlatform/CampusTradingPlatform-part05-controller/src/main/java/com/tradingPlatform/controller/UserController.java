package com.tradingPlatform.controller;

import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

@RestController
@RequestMapping(value = {"/user"})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String test(@RequestParam Integer id) {
        TbUser user = new TbUser();
        user.setNickname("用户" + id).setPhone("158785859" + id).setPassword("12345678" + id);
        userService.addService(user);
        return "{'1':'你好'}";
    }

    /**
     * 查一个用户
     *
     * @param userId
     * @return
     */
    @GetMapping
    public TbUser findById(@RequestParam("userId") Integer userId) {
        return userService.findByPrimaryKeyService(userId);
    }

    /**
     * 增加一个用户
     *
     * @param user
     * @return
     */
    @PostMapping("reg")
    public TbUser save(@RequestBody TbUser user) {
        if (StringUtil.isEmpty(user.getAvatar())) {
            user.setAvatar("https://avatar.csdnimg.cn/b/5/c/1_hzllo_.jpg");
        }
        userService.addService(user);

        return userService.findByPrimaryKeyService(user.getUserId());
    }

    /**
     * 删除用户
     *
     * @param userId
     */
    @DeleteMapping
    public void delete(@RequestParam Integer userId) {
        userService.deleteByPrimaryKeyService(userId);
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @PutMapping
    public TbUser update(@RequestBody TbUser user) {
        userService.updateService(user);
        return userService.findByPrimaryKeyService(user.getUserId());
    }

}

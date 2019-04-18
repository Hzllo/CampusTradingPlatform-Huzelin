package com.tradingPlatform.controller;

import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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

}

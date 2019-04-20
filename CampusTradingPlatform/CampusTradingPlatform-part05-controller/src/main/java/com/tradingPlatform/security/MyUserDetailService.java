package com.tradingPlatform.security;

import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        TbUser user = userService.findByPrimaryKeyService(Integer.valueOf(s));

        if (user != null) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User
                    (user.getNickname(), "{NONE}" + user.getPassword(), authorityList);
        }
        return null;
    }
}

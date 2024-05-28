package com.example.yxy.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

/**
 * 重写用户查询信息 因为Activiti默认的用户查询是从数据库中查询，这里我们重写用户查询信息，直接返回固定值
 * @author yxy
 * @date 2024/05/28
 */
//@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername(username)
                .password("123")
                .authorities("admin")
                .build();
        //这里可以根据实际情况进行数据库查询，这里只是返回了一个固定值
        inMemoryUserDetailsManager.createUser(user);
        return null;
    }
}

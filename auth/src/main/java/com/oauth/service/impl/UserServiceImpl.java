package com.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;


    private List<User> userList;



    @PostConstruct
    public void initUser() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("kbq", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("frl", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("love", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(users)) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
        return users.get(0);
    }
}

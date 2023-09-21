package com.taskmanger.app.tm.security;

import com.taskmanger.app.tm.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ExtendUser extends org.springframework.security.core.userdetails.User{

    private User user;

    public ExtendUser(User user,Collection<? extends GrantedAuthority> roles){
        super(user.getEmail(), user.getPassword(), roles);
        this.user = user;

    }
    public User getUser(){
        return this.user;
    }

}

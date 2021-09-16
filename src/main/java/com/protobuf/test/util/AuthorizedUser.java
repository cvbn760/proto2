package com.protobuf.test.util;


import com.protobuf.test.Model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoleSet());
        this.user = user;
    }

    public long getId(){
        return user.getId();
    }

//    public void update(User newUser){
//        user = newUser;
//    }
//
//    public User getUser(){
//        return user;
//    }
}

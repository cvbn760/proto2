package com.protobuf.test.controllers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protobuf.test.Model.User;
import com.protobuf.test.Service.UserService;
import com.protobuf.test.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = AuthController.USER_URL)
public class AuthController {
    static final String USER_URL = "/users/*";

    @Autowired
    private UserService userService;

    // Достать пользователя по id (Для авторизированных пользователей)
    @RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
    public User get(@PathVariable long id) throws InvalidProtocolBufferException {
        return userService.getUserById(id);
    }

    // Достать всех пользователей (Доступно администраторам)
    @RequestMapping(value = "/adm", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // Удалить пользователя (Доступно только самому пользователю)
    @RequestMapping(value = "/auth/delete_user", method = RequestMethod.DELETE)
    public void delete(){
        userService.deleteUserById(SecurityUtil.authUserId());
    }

    // Сохранить или обновить пользователя (Сохранение доступно только анонимным пользователям. Обновление доступно только владельцу аккаунта)
    @RequestMapping(value = "/all/create", method = RequestMethod.POST)
    public HttpStatus createOrUpdate(@RequestBody User user) {
        if (SecurityUtil.canEdit(user)) {
            userService.createOrUpdate(user);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    // Получить заготовку пользователя (Только для анонимных пользователей)
    @RequestMapping(value = "/anon/new_user", method = RequestMethod.GET)
    public User getNewUser(){
        return new User();
    }
}

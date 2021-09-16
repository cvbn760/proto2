package com.protobuf.test.Service;


import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.protobuf.test.Model.Role;
import com.protobuf.test.Model.User;
import com.protobuf.test.Repository.UserRepository;

import com.protobuf.test.util.AuthorizedUser;

import com.protobuf.test.util.ProtoUtil;
import com.protobuf.test.util.SecurityUtil;
import com.protobuf.test.util.UserSerializer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Сохранить или обновить пользователя
    public User createOrUpdate(User user){
        // Приняли пользователя с email, protoData
        user = ProtoUtil.deser(user.getProtoData());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Достать пользователя по id
    public User getUserById(long id) throws InvalidProtocolBufferException {
        // Достали юзера с id, email, protodata
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new BadCredentialsException("User " + id + " is not found");
        }
        User fullUser = ProtoUtil.deser(user.getProtoData());
        fullUser.setId(user.getId());
        fullUser.setEmail(user.getEmail());
        return fullUser;
    }

    // Достать всех пользователей
    public List<User> getAllUsers() {
        // Достали много юзеров с id, email, protodata
        List<User> userList = userRepository.findAll();
        userList.stream().map(user -> ProtoUtil.deser(user.getProtoData())).collect(Collectors.toList());
        return userList;
    }

    // Удалить пользователя
    public void deleteUserById(long id){
        userRepository.deleteById(id);
    }

    // Достать пользователя по email
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Достали юзера с id, email, protodata
        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new BadCredentialsException("User by " + email + " is not found");
        }

        User fullUser = ProtoUtil.deser(user.getProtoData());
        fullUser.setId(user.getId());
        fullUser.setEmail(user.getEmail());
        return new AuthorizedUser(fullUser);
    }

}

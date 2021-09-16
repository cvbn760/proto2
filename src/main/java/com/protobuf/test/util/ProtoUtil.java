package com.protobuf.test.util;

import com.protobuf.test.Model.Role;
import com.protobuf.test.Model.User;
import lombok.SneakyThrows;

import java.util.stream.Collectors;

public class ProtoUtil {

    // Сериализация
    private static byte[] serial(User user){
        UserSerializer.Model.Builder builder = UserSerializer.Model.newBuilder();
        builder.setFirstName(user.getFirstName()).setLastName(user.getLastName())
                .setAge(user.getAge()).setHeight(user.getHeight()).setWeight(user.getWeight())
                .setPassword(user.getPassword()).setTelNumber(user.getTelNumber());

        user.getRoleSet().forEach(role -> builder.addRoles(role.toString()));

        UserSerializer.Model userSer = builder.build();

        return userSer.toByteArray();
    }


    // Десериализация
    @SneakyThrows
    public static User deser(byte[] protoData) {
        User fullUser = new User();

        UserSerializer.Model userProto = UserSerializer.Model.parseFrom(protoData);
        fullUser.setFirstName(userProto.getFirstName());
        fullUser.setLastName(userProto.getLastName());
        fullUser.setAge(userProto.getAge());
        fullUser.setHeight(userProto.getHeight());
        fullUser.setWeight(userProto.getWeight());
        fullUser.setPassword(userProto.getPassword());
        fullUser.setTelNumber(userProto.getTelNumber());
        fullUser.setRoleSet((userProto.getRolesList().stream().map(Role::valueOf).collect(Collectors.toSet())));
        fullUser.setProtoData(protoData);
        return fullUser;
    }
}

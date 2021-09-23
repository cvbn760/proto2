package com.protobuf.test.util;

import com.protobuf.test.Model.User;
import lombok.SneakyThrows;

import java.io.IOException;

public class ProtoUtil {

//    // Сериализация
    private static byte[] serial(User user) throws IOException {
        UserSerializer.Builder builder = UserSerializer.newBuilder();
        builder.setFirstName(user.getFirstName()).setLastName(user.getLastName())
                .setAge(user.getAge()).setHeight(user.getHeight()).setWeight(user.getWeight())
                .setPassword(user.getPassword()).setTelNumber(user.getTelNumber());

        user.getRoleSet().forEach(role -> builder.addElementRoles(role.toString()));

        UserSerializer userSer = builder.build();

        return userSer.toByteArray();
    }


    // Десериализация
    @SneakyThrows
    public static User deser(byte[] protoData) {
        User fullUser = new User();

        UserSerializer userProto = UserSerializer.parseFrom(protoData);
        fullUser.setFirstName(userProto.getFirstName());
        fullUser.setLastName(userProto.getLastName());
        fullUser.setAge(userProto.getAge());
        fullUser.setHeight(userProto.getHeight());
        fullUser.setWeight(userProto.getWeight());
        fullUser.setPassword(userProto.getPassword());
        fullUser.setTelNumber(userProto.getTelNumber());
        fullUser.setRoleSet(userProto.getRoles());
        fullUser.setProtoData(protoData);
        return fullUser;
    }
}

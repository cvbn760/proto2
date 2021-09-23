import net.jarlehansen.proto2javame.Proto2JavaMe;

public class Proto2GiveMyClass {
    public static void main(String...args){
        String[] strings = {"--java_out=s:/hdd/java/proto2/proto2/src/main/java/com/protobuf/test/util",
                "s:/hdd/java/proto2/proto2/src/main/resources/proto/user.proto"};

        Proto2JavaMe.main(strings);
    }
}

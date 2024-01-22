package com.example.yxy;
         
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
         
@SpringBootTest
class EncryptTests {
         
    private static final String ALGORITHM_INFO = "PBEWithMD5AndDES";         
    private static final String PASSWORD_INFO = "yxyyxyyxy";
         
    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        encryptPwd();
    }
//    @Autowired
//    StringEncryptor stringEncryptor;
         
    @Test
    public static void encryptPwd() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new     StandardPBEStringEncryptor();
        //配置文件中配置如下的算法
        standardPBEStringEncryptor.setAlgorithm(ALGORITHM_INFO);
        //配置文件中配置的password
        standardPBEStringEncryptor.setPassword(PASSWORD_INFO);
        //要加密的文本
        String name = standardPBEStringEncryptor.encrypt("root");
        String password = standardPBEStringEncryptor.encrypt("123456789");
        String redisPassword = standardPBEStringEncryptor.encrypt("123456");
        //将加密的文本写到配置文件中
        System.out.println("name=" + name);
        System.out.println("password=" + password);
        System.out.println("redisPassword=" + redisPassword);
         
        //要解密的文本
        String name2 = standardPBEStringEncryptor.decrypt(name);
        String password2 = standardPBEStringEncryptor.decrypt(password);
        String redisPassword2 = standardPBEStringEncryptor.decrypt(redisPassword);
//解密后的文本
        System.out.println("name2=" + name2);
        System.out.println("password2=" + password2);
        System.out.println("redisPassword2=" + redisPassword2);         
    }
         
}
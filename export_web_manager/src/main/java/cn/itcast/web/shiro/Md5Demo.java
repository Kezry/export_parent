package cn.itcast.web.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5Demo {

    public static void main(String[] args) {
        String password = "123";
        String salt = "lw@export.com";
        //shiro给我们提供了加盐加密类
        Md5Hash md5Hash = new Md5Hash(password,salt);

        System.out.println("加密后的数据："+ md5Hash);
    }
}

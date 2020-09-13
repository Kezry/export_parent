package cn.itcast.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

/*
该类的作用：自定义一个md5加盐加密密码匹配器


 */
public class CustomCredentialsMatcher  extends SimpleCredentialsMatcher {

    /*
     方法的内部编写加盐加密密码对比过程
        token： 用户输入邮箱与密码
        AuthenticationInfo : 在realm中的返回值，返回对象包含了该用户在数据库中的密码
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //1.把token转换为usernamePasswordToke
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        //2. 从token中获取到邮箱与密码
        String email = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        //3. 使用MD5Hash类对用户输入的密码进行加盐加密
        Md5Hash md5Hash = new Md5Hash(password,email);
        String slatPassword = md5Hash.toString();

        //4. 获取该用户在数据库中的密码
        Object dbPassword = info.getCredentials();

        //5. 对比密码
        return dbPassword.equals(slatPassword);
    }
}

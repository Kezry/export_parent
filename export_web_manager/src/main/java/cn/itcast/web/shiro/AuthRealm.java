package cn.itcast.web.shiro;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    /*
        认证的方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.  把token转换为UsernamePasswordToken，因为可以使用子类特有的方法
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //2. 从token中获取用户名(邮箱)
        String email = usernamePasswordToken.getUsername();
//        usernamePasswordToken.getPassword()
//        usernamePasswordToken.getPassword();//密码： 用户传入的密码  123

        //3. 使用用户名去查询一个用户，如果为null，则返回null，如果该方法返回null，那么login方法马上会收到UnknownAccountException
        User user = userService.findByEmail(email);
        if(user==null){
            return null;
        }
        //4. 创建AuthenticationInfo对象，然后把该用户的数据库密码传入，然后shiro自己从token中获取密码进行对比
        /*
     SimpleAuthenticationInfo方法参数的说明：
                               1. Object principal, 登录成功返回的对象
                               2. Object credentials, 该用户在数据库中的密码
                               3. String realmName  不需管
         */
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),"");
        return simpleAuthenticationInfo;
    }


    /*
        用户登录成功之后就会调用该方法给用户去授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1. 得到登陆成功用户对象
        User user = (User) principals.getPrimaryPrincipal();
        //2. 得到用户拥有的权限
        List<Module> moduleList = moduleService.findModuleByUser(user);

        //3. 把用户权限的标记添加到AuthorizationInfo(集合)
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Module module : moduleList) {
            simpleAuthorizationInfo.addStringPermission(module.getName()); //权限标记一般我们使用菜单名字
        }
        return simpleAuthorizationInfo;
    }


}

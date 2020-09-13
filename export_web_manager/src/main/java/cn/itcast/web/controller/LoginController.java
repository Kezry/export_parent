package cn.itcast.web.controller;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends  BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;



    //该方法任务： 跳转到后台首页  home/main
    @RequestMapping("/login")
    public String login(String email,String password){
        //1. 判断邮箱与密码是否为空，如果为空跳回登录页面
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(password)){
            request.setAttribute("error","用户名或者密码不能为空");
            //request域存储数据，我们需要请求转发
            return "forward:/login.jsp";
        }

        try {
            //2. 获取subject对象
            Subject subject = SecurityUtils.getSubject();

            //3. 把用户名与密码封装到一个Token对象中
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email,password);

            //4. 使用subject对象把token发出登陆认证请求,其实本质上就是token传递给了realm
            subject.login(usernamePasswordToken);

            //5. realm认证完毕之后会给你返回一个登陆成功用户对象， shiro在你登陆成功后shiro自己会在session中设置很多登录成功标记
            User loginUser = (User) subject.getPrincipal();

            //shiro本质上已经做了登录成功标记，但是由于我们自己的代码也需要使用登陆者的信息，所以我们自己也会在session中做登陆者信息存储。
            session.setAttribute("loginUser",loginUser);
            //一旦登录成功，，马上查询该用户的菜单
            List<Module> moduleList = moduleService.findModuleByUser(loginUser);
            //存储到域中  存储到哪个域？ request（）， session，servletContext
            session.setAttribute("modules",moduleList);
            return "home/main";
        } catch (UnknownAccountException e) {
           //一旦出现了UnknownAccountException带用户名不存在
            request.setAttribute("error","用户名不存在");
            return "forward:/login.jsp";
        }catch (IncorrectCredentialsException e){
            //一旦出现IncorrectCredentialsException 代表密码错误
            request.setAttribute("error","密码错误");
            return "forward:/login.jsp";
        }
    }


/*

    //该方法任务： 跳转到后台首页  home/main
    @RequestMapping("/login")
    public String login(String email,String password){
        //1. 判断邮箱与密码是否为空，如果为空跳回登录页面
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(password)){
            request.setAttribute("error","用户名或者密码不能为空");
            //request域存储数据，我们需要请求转发
            return "forward:/login.jsp";
        }





        //2. 根据邮箱(用户名)去查询用户,如果用户对象为空，用户名不存在
        User user= userService.findByEmail(email);
        if(user!=null){
            //3.  如果用户可以查询到，我们就对比密码
            if(user.getPassword().equals(password)){
                //登陆成功,登陆成功需要session中存储登陆成功标记
                session.setAttribute("loginUser",user);

                //一旦登录成功，，马上查询该用户的菜单
                List<Module> moduleList = moduleService.findModuleByUser(user);
                //存储到域中  存储到哪个域？ request（）， session，servletContext
                session.setAttribute("modules",moduleList);

                return "home/main";  //  完整路径： /WEB-INF/pages/home/main.jsp
            }else{
                //不存在该用户名
                request.setAttribute("error","密码错误");
                return "forward:/login.jsp";
            }
        }else{
            //不存在该用户名
            request.setAttribute("error","用户名不存在");
            return "forward:/login.jsp";
        }
    }

*/







    /*
        url: /logout.do
        参数： 没有
        作用： 退出登录
        返回值： 登陆页面
     */
    @RequestMapping("/logout")
    public String logout(){
        //1. 先使用shiro的方法销毁shiro的登录成功标记
        Subject subject = SecurityUtils.getSubject();
        subject.logout(); //销毁shiro在session的标记，然后你才能摧毁session

        ///退出登录的本质就是销毁session的登录成功标记
        session.invalidate(); //直接把整个session销毁
        //如果request域没有存储数据，我们可以使用请求重定向， 如果request域存储了数据，我们一定要使用请求转发。
        return "redirect:/login.jsp";
    }




    //该方法任务： 让后台首页的内容区域去到  home/home.jsp
    @RequestMapping("/home")
    public String home(){
        return "home/home";  //  完整路径： /WEB-INF/pages/home/home.jsp
    }
}

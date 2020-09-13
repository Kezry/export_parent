package cn.itcast.web.controller;

import cn.itcast.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    //获取登陆者
    public User getLoginUser(){
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser;
    }



    //获取登陆者企业id
    public String getLoginCompanyId(){
        return getLoginUser().getCompanyId();
    }


    //获取登陆者企业名称
    public String getLoginCompanyName(){
        return getLoginUser().getCompanyName();
    }
}



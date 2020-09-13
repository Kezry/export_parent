package cn.itcast.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
自定义异常处理器步骤：
    1. 自定一个一个类实现HandlerExceptionResolver接口
    2. 实现接口的方法
    3. 创建自定义异常处理器对象，交给spring。
    4. 自定义错误页面
 */
@Component
public class CustomerExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        1. 创建ModelAndView
        ModelAndView mv = new ModelAndView();
        //2. 把异常信息存储到域中
        mv.addObject("errMsg",e.getMessage());
        //3. 设置转发视图页面
        mv.setViewName("error");

        return mv;
    }
}

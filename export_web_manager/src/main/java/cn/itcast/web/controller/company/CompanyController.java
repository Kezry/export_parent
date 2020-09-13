package cn.itcast.web.controller.company;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/*

----CompanyService  接口
-----------| CompanyServiceImpl  实现类
----------| CompanyServiceProxy  实现类

CompanyService c = new CompanyServiceImpl() || CompanyServiceProxy（）；

CompanyServiceImpl c = new CompanyServiceProxy（）
 */


@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Reference
    private CompanyService companyService;
    //private CompanyServiceImpl companyService; //业务代码都需要添加事务管理，，而事务管理本质上是通过动态代理去实现的。
    //spring底层是创建业务层代理对象  Proxy.newProxyInstance(classLoader , CompanyService.class  ,处理器） ，
    //spring容器里面存储的是service的代理对象，而不是实现类的对象。

    @RequestMapping("/list")
    @RequiresPermissions("企业管理")
    public  String list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "5") int pageSize){
     /*   Subject subject = SecurityUtils.getSubject();
         //发出了企业管理的权限检查请求
        subject.checkPermission("企业管理");*/


        //List<Company> list = companyService.findAll();
        PageInfo<Company> pageInfo =  companyService.findByPage(pageNum,pageSize);

        //把企业存储到域中
        request.setAttribute("pageInfo",pageInfo);
        return "company/company-list";  // 完整路径： /pages/company/company-list.jsp
    }


    /*
      url:/company/toAdd.do
      作用： 进入添加页面
      参数： 无
     */
    @RequestMapping("/toAdd")
    public  String toAdd(){
        return "company/company-add";
    }


    /*
        url:/company/edit.do
        作用： 新增企业||更新企业
        参数： 企业对象
        返回值：企业列表页面
       */
    @RequestMapping("/edit")
    public  String edit(Company company){
        //if(company.getId()==null||company.getId().equals("")){
        if(StringUtils.isEmpty(company.getId())){
            //没有带id过来 , 新增操作
            companyService.save(company);
        }else{
            //更新   update xx set xx=xx  xx=xx where id = xx  更新是一定要带着id过来
            companyService.update(company);
        }
        //更新或者新增成功都要回到企业列表页面
       // return "/company/list.do"; //  普通字符串是会经过视图解析器，  /WEB-INF/pages/company/list.do.jsp
        return "redirect:/company/list.do";  //添加redirect开头的返回值则不会经过视图解析器
    }


    /*
     url:/company/toUpdate.do?id=${item.id}
     作用： 进入企业修改页面
     参数：企业id
     返回值：企业更新页面
    */
    @RequestMapping("/toUpdate")
    public ModelAndView toUpdate(String id){
        ModelAndView mv = new ModelAndView();
        //根据id查找到页面
       Company company = companyService.findById(id);
       //保存到域
        mv.addObject("company",company);
        //设置返回的jsp页面
        mv.setViewName("company/company-update");
        return mv;
    }


    /*
   url:/company/delete.do?id="+id;
   作用：删除企业
   参数：企业id
   返回值：企业列表
  */
    @RequestMapping("/delete")
    public String delete(String id){
        companyService.delete(id);
        return "redirect:/company/list.do";
    }


}

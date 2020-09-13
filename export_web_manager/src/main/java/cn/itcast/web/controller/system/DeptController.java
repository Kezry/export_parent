package cn.itcast.web.controller.system;

import cn.itcast.domain.company.Company;
import cn.itcast.domain.system.Dept;
import cn.itcast.service.company.CompanyService;
import cn.itcast.service.system.DeptService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/list")
    public  String list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "5") int pageSize){
        PageInfo<Dept> pageInfo = deptService.findByPage(pageNum, pageSize, getLoginCompanyId());
        //存储到域对象
        request.setAttribute("pageInfo",pageInfo);
        //返回到部门分页列表
        return "system/dept/dept-list";
    }



    /*
      url:/system/dept/toAdd.do
      作用： 进入添加部门页面
      参数： 无
     */
    @RequestMapping("/toAdd")
    public  String toAdd(){
        //1. 查询所有的部门
        String companyId= getLoginCompanyId();
       List<Dept> deptList = deptService.findAll(companyId);
        //2. 把部门的信息存储到域中
        request.setAttribute("deptList", deptList);
        return "system/dept/dept-add";
    }



    /*
       url: /system/dept/edit.do
       作用： 新增部门||更新部门
       参数： 部门对象
       返回值：部门列表页面
      */
    @RequestMapping("/edit")
    public  String edit(Dept dept){
        //补全部门信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        if(StringUtils.isEmpty(dept.getId())){
            //没有带id过来 , 新增操作
            deptService.save(dept);
        }else{
            //更新   update xx set xx=xx  xx=xx where id = xx  更新是一定要带着id过来
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";  //添加redirect开头的返回值则不会经过视图解析器
    }



    /*
     url: /system/dept/toUpdate.do?id=100
     作用： 进入更新部门页面
     参数： 部门的id
     返回值：部门更新页面
    */
    @RequestMapping("/toUpdate")
    public  String toUpdate(String id){
        //1.根据id查询当前部门信息
        Dept dept = deptService.findById(id);

        //2， 查询所有部门信息
        String companyId = getLoginCompanyId();
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("dept",dept);
        request.setAttribute("deptList",deptList);


        return "system/dept/dept-update";
    }



    /*
    url: /system/dept/delete.do?id="+id,
    作用： 删除部门
    参数： 部门的id
    返回值：map对象的json字符串    {"flag":true|false ,"message":"删除成功"||"删除失败，因为有子部门"}
   */
    @RequestMapping("/delete")
    @ResponseBody
    public  Map<String,Object> delete(String id){
        Map<String,Object> resultMap = new HashMap<>();
        boolean flag =  deptService.delete(id);
        if(flag){
            resultMap.put("message","删除成功");
        }else{
            resultMap.put("message","删除失败，因为有子部门");
        }
        //删除失败
        resultMap.put("flag",flag);
        return resultMap;
    }




}

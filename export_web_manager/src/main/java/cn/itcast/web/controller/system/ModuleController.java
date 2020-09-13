package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.service.system.ModuleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/list")
    public  String list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "5") int pageSize){
        PageInfo<Module> pageInfo = moduleService.findByPage(pageNum, pageSize);
        //存储到域对象
        request.setAttribute("pageInfo",pageInfo);
        //返回到模块分页列表
        return "system/module/module-list";
    }



    /*
      url:/system/module/toAdd.do
      作用： 进入添加模块页面
      参数： 无
     */
    @RequestMapping("/toAdd")
    public  String toAdd(){
        //1. 查询所有的模块
        String companyId= getLoginCompanyId();
       List<Module> moduleList = moduleService.findAll();
        //2. 把模块的信息存储到域中
        request.setAttribute("menus", moduleList);
        return "system/module/module-add";
    }



    /*
       url: /system/module/edit.do
       作用： 新增模块||更新模块
       参数： 模块对象
       返回值：模块列表页面
      */
    @RequestMapping("/edit")
    public  String edit(Module module){

        if(StringUtils.isEmpty(module.getId())){
            //没有带id过来 , 新增操作
            moduleService.save(module);
        }else{
            //更新   update xx set xx=xx  xx=xx where id = xx  更新是一定要带着id过来
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";  //添加redirect开头的返回值则不会经过视图解析器
    }



    /*
     url: /system/module/toUpdate.do?id=100
     作用： 进入更新模块页面
     参数： 模块的id
     返回值：模块更新页面
    */
    @RequestMapping("/toUpdate")
    public  String toUpdate(String id){
        //1.根据id查询当前模块信息
        Module module = moduleService.findById(id);

        //2， 查询所有模块信息
        String companyId = getLoginCompanyId();
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("module",module);
        request.setAttribute("menus", moduleList);


        return "system/module/module-update";
    }



    /*
    url: /system/module/delete.do?id="+id,
    作用： 删除模块
    参数： 模块的id
    返回值：map对象的json字符串    {"flag":true|false ,"message":"删除成功"||"删除失败，因为有子模块"}
   */
    @RequestMapping("/delete")

    public  String delete(String id){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }




}

package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.RoleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;


    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/list")
    public  String list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "5") int pageSize){
        PageInfo<Role> pageInfo = roleService.findByPage(pageNum, pageSize, getLoginCompanyId());
        //存储到域对象
        request.setAttribute("pageInfo",pageInfo);
        //返回到角色分页列表
        return "system/role/role-list";
    }



    /*
      url:/system/role/toAdd.do
      作用： 进入添加角色页面
      参数： 无
     */
    @RequestMapping("/toAdd")
    public  String toAdd(){
        //1. 查询所有的角色
        String companyId= getLoginCompanyId();
       List<Role> roleList = roleService.findAll(companyId);
        //2. 把角色的信息存储到域中
        request.setAttribute("roleList", roleList);
        return "system/role/role-add";
    }



    /*
       url: /system/role/edit.do
       作用： 新增角色||更新角色
       参数： 角色对象
       返回值：角色列表页面
      */
    @RequestMapping("/edit")
    public  String edit(Role role){
        //补全角色信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if(StringUtils.isEmpty(role.getId())){
            //没有带id过来 , 新增操作
            roleService.save(role);
        }else{
            //更新   update xx set xx=xx  xx=xx where id = xx  更新是一定要带着id过来
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";  //添加redirect开头的返回值则不会经过视图解析器
    }



    /*
     url: /system/role/toUpdate.do?id=100
     作用： 进入更新角色页面
     参数： 角色的id
     返回值：角色更新页面
    */
    @RequestMapping("/toUpdate")
    public  String toUpdate(String id){
        //1.根据id查询当前角色信息
        Role role = roleService.findById(id);

        //2， 查询所有角色信息
        String companyId = getLoginCompanyId();
        List<Role> roleList = roleService.findAll(companyId);
        request.setAttribute("role",role);
        request.setAttribute("roleList",roleList);


        return "system/role/role-update";
    }



    /*
    url: /system/role/delete.do?id="+id,
    作用： 删除角色
    参数： 角色的id
    返回值：map对象的json字符串    {"flag":true|false ,"message":"删除成功"||"删除失败，因为有子角色"}
   */
    @RequestMapping("/delete")

    public  String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }



    /*
   url: /system/role/roleModule.do?roleid=4028a1c34ec2e5c8014ec2ebf8430001
   作用： 进入角色权限列表页面
   参数： 角色的id
   返回值：角色权限列表页面
  */
    @RequestMapping("/roleModule")
    public  String roleModule(String roleid){
        //1. 根据角色id查找出角色
        Role role = roleService.findById(roleid);
        request.setAttribute("role",role);
        return "system/role/role-module";
    }


    /*
         json格式：
            [
                { id:1, pId:0, name:"saas管理", open:true}, map
                { id:11, pId:1, name:"企业管理"},
                { id:111, pId:1, name:"模块管理"}
          ]

         url: /system/role/getTreeNodes.do?roleid=4028a1c34ec2e5c8014ec2ebf8430001
         作用： 查询所有的权限以及该角色的权限，返回json
         参数： 角色的id
         返回值：权限列表json
    */
    @RequestMapping("/getTreeNodes")
    @ResponseBody
    public  List<Map<String,Object>> getTreeNodes(String roleid){
        //1. 查找到所有的权限
        List<Module> moduleList = moduleService.findAll();

        //2. 查询出当前角色已经具备的权限
       List<Module> roleModuleList  = moduleService.findRoleModuleByRoleId(roleid);

        //定义一个集合存储结果
        List<Map<String,Object>> resultList = new ArrayList<>();

        //遍历所有的权限，每一个权限对象就是一个Map对象
        for (Module module : moduleList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",module.getId());
            map.put("pId",module.getParentId());
            map.put("name",module.getName());
            map.put("open",true);

            //判断当前角色的权限是否包含了指定的模块，如果有则添加checked属性
            if(roleModuleList.contains(module)){ //contains 底层equals方法
                map.put("checked",true);
            }

            //把map添加到List集合中
            resultList.add(map);
        }

        return  resultList;
    }



    /*
        url: /system/role/updateRoleModule.do
        作用： 更新角色的权限
        参数： roleid,moduleIds
        返回值：角色列表页面

        参数存在的多个值：格式：1,2,3,4,5..
            接收参数的方式有两种：
                    1. 你可以使用字符串接收 ， 接收到就是一个字符串
                    2. 你也可以使用字符串数组接收， 接收到的就是一个字符串数组，springmvc自动帮你切割好
    */
    @RequestMapping("/updateRoleModule")
    public  String updateRoleModule(String roleid,String[] moduleIds){
        System.out.println("=============>"+moduleIds);
        //roleService.updateRoleModule(roleid,moduleIds);
        return "redirect:/system/role/list.do";
    }

}

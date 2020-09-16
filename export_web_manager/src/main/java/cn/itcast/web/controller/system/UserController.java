package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.RoleService;
import cn.itcast.service.system.UserService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.shiro.AuthRealm;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private AuthRealm authRealm;

    @RequestMapping("/list")
    @RequiresPermissions("用户管理")
    public  String list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "5") int pageSize){
        PageInfo<User> pageInfo = userService.findByPage(pageNum, pageSize, getLoginCompanyId());
        //存储到域对象
        request.setAttribute("pageInfo",pageInfo);
        //返回到用户分页列表
        return "system/user/user-list";
    }



    /*
      url:/system/user/toAdd.do
      作用： 进入添加用户页面
      参数： 无
     */
    @RequestMapping("/toAdd")
    public  String toAdd(){
        //1. 查询所有部门信息
        String companyId= getLoginCompanyId();
       List<Dept> deptList = deptService.findAll(getLoginCompanyId());
        //2. 把用户的信息存储到域中
        request.setAttribute("deptList", deptList);
        return "system/user/user-add";
    }



    @Autowired
    private RabbitTemplate rabbitTemplate;
    /*
       url: /system/user/edit.do
       作用： 新增用户||更新用户
       参数： 用户对象
       返回值：用户列表页面
      */
    @RequestMapping("/edit")
    public  String edit(User user){
        //补全用户信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if(StringUtils.isEmpty(user.getId())){
            //没有带id过来 , 新增操作
            userService.save(user);
            //向mq发送消息
            rabbitTemplate.convertAndSend("email_exchange","user.add",user);

        }else{
            //更新   update xx set xx=xx  xx=xx where id = xx  更新是一定要带着id过来
            userService.update(user);
        }
        return "redirect:/system/user/list.do";  //添加redirect开头的返回值则不会经过视图解析器
    }



    /*
     url: /system/user/toUpdate.do?id=100
     作用： 进入更新用户页面
     参数： 用户的id
     返回值：用户更新页面
    */
    @RequestMapping("/toUpdate")
    public  String toUpdate(String id){
        //1.根据id查询当前用户信息
        User user = userService.findById(id);

        //2. 查询所有部门信息
        String companyId= getLoginCompanyId();
        List<Dept> deptList = deptService.findAll(getLoginCompanyId());
        //3. 把用户的信息存储到域中
        request.setAttribute("deptList", deptList);
        request.setAttribute("user",user);


        return "system/user/user-update";
    }



    /*
    url: /system/user/delete.do?id="+id,
    作用： 删除用户
    参数： 用户的id
    返回值：map对象的json字符串    {"flag":true|false ,"message":"删除成功"||"删除失败，因为有子用户"}
   */
    @RequestMapping("/delete")
    @ResponseBody
    public  Map<String,Object> delete(String id){
        Map<String,Object> resultMap = new HashMap<>();
        boolean flag =  userService.delete(id);
        if(flag){
            resultMap.put("message","删除成功");
        }else{
            resultMap.put("message","删除失败，因为被分配了角色");
        }
        //删除失败
        resultMap.put("flag",flag);
        return resultMap;
    }



    /*
   url: /system/user/roleList.do?id=002108e2-9a10-4510-9683-8d8fd1d374ef
   作用： 进入用户角色页面
   参数： 用户的id
   返回值：用户角色页面
  */
    @RequestMapping("/roleList")
    public  String roleList(String id){
        //1. 根据用户id查找用户
        User user = userService.findById(id);
        request.setAttribute("user",user);

        //2. 查找所有的角色
        List<Role> roleList = roleService.findAll(getLoginCompanyId());
        request.setAttribute("roleList",roleList);

        //3. 查找该用户具备的角色
        List<Role> userRoleList =  roleService.findUserRoleByUserId(id);
        request.setAttribute("userRoleList",userRoleList);

        return "system/user/user-role";
    }



    /*
     url: /system/user/changeRole.do
     作用： 修改用户的角色
     参数： userid, roleIds
     返回值：用户列表页面
    */
    @RequestMapping("/changeRole")
    public  String changeRole(String userid,String[] roleIds) {
        userService.changeRole(userid,roleIds);
        return "redirect:/system/user/list.do";
    }


}

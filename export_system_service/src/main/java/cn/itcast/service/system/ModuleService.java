package cn.itcast.service.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {


    //查询模块列表，返回的是一个页面对象
    PageInfo<Module> findByPage(int pageNum, int pageSize);

    //查找所有模块
    List<Module> findAll();

    //保存模块
    void save(Module module);

    //更新模块
    void update(Module module);

    //根据id查找模块
    Module findById(String id);

    //根据id删除模块
    void delete(String id);

    //根据角色id查找权限
    List<Module> findRoleModuleByRoleId(String roleid);

    //根据用户查询模块信息(菜单信息)
    List<Module> findModuleByUser(User user);
}

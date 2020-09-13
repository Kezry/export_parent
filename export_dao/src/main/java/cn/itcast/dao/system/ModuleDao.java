package cn.itcast.dao.system;

import cn.itcast.domain.system.Module;

import java.util.List;

public interface ModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    void delete(String moduleId);

    //添加
    void save(Module module);

    //更新
    void update(Module module);

    //查询全部
    List<Module> findAll();

    //根据角色id查找权限
    List<Module> findRoleModuleByRoleId(String roleid);

    //根据用户等级查看
    List<Module> findModuleByDegree(Integer degree);

    //普通用户是需要根据用户的角色查看
    List<Module> findModuleByUserId(String id);
}
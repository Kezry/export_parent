package cn.itcast.service.system;

import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {


    //查询角色列表，返回的是一个页面对象
    PageInfo<Role> findByPage(int pageNum, int pageSize, String companyId);

    //查找所有角色
    List<Role> findAll(String companyId);

    //保存角色
    void save(Role role);

    //更新角色
    void update(Role role);

    //根据id查找角色
    Role findById(String id);

    //根据id删除角色
    void delete(String id);

    //更新角色的权限
    void updateRoleModule(String roleid, String[] moduleIds);

    //根据用户id查找角色
    List<Role> findUserRoleByUserId(String id);

    //
    List<Role> findUserIsRoleByUserId(String id);

}

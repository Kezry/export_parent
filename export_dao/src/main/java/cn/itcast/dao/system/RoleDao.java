package cn.itcast.dao.system;

import cn.itcast.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleDao {

    //根据id查询
    Role findById(String id);

    //查询全部
    List<Role> findAll(String companyId);

	//根据id删除
    void delete(String id);

	//添加
    void save(Role role);

	//更新
    void update(Role role);

    // 先删除当前角色权限
    void deleteRoleModuleByRoleId(String roleid);

    //2. 给当前角色添加权限 , 注意： 如果dao的方法有两个参数就一定要使用@Param指定参数的名字
    void addRoleModules(@Param("roleid") String roleid,@Param("moduleIds") String[] moduleIds);


    //根据用户id查找角色
    List<Role> findUserRoleByUserId(String id);
}
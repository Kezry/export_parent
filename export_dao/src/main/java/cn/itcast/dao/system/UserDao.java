package cn.itcast.dao.system;

import cn.itcast.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {

	//根据企业id查询全部
	List<User> findAll(String companyId);

	//根据id查询
    User findById(String userId);

	//根据id删除
	void delete(String userId);

	//保存
	void save(User user);

	//更新
	void update(User user);

	//查询该用户是否被分配了角色
    long findUserRoleByUserId(String id);

	//1. 先删除用户的角色
    void deleteUserRoleByUserId(String userid);

	//2. 给当前用户添加对应角色
	void addUserRoles(@Param("userid") String userid,@Param("roleIds") String[] roleIds);

	//根据邮箱查找用户
    User findByEmail(String email);
}
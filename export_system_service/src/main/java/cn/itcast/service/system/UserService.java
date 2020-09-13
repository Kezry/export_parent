package cn.itcast.service.system;

import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {    //ctrl+R 


    //查询用户列表，返回的是一个页面对象
    PageInfo<User> findByPage(int pageNum, int pageSize, String companyId);

    //查找所有用户
    List<User> findAll(String companyId);

    //保存用户
    void save(User user);

    //更新用户
    void update(User user);

    //根据id查找用户
    User findById(String id);

    //根据id删除用户
    boolean delete(String id);

    //修改用户的角色
    void changeRole(String userid, String[] roleIds);

    //根据邮箱查找用户
    User findByEmail(String email);
}

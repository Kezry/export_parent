package cn.itcast.service.system.impl;

import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo<User> findByPage(int pageNum, int pageSize, String companyId) {
        //1.设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //2. 查询全部
        List<User> list = userDao.findAll(companyId);
        //3. 构造一个PageInfo对象
        PageInfo<User> pageInfo = new PageInfo(list);

        return pageInfo;
    }

    //查询所有用户信息
    @Override
    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    //保存用户
    @Override
    public void save(User user) {
        //设置主键
        user.setId(UUID.randomUUID().toString());
        //加盐加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),user.getEmail());
        String saltPassword = md5Hash.toString();
        //给用户设置密码
        user.setPassword(saltPassword);

        userDao.save(user);
    }

    //更新用户
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    //根据id 查找用户
    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }


    //根据用户id删除用户
    @Override
    public boolean delete(String id) {
        //1. 查询该用户是否已经被分配了角色
        long count = userDao.findUserRoleByUserId(id);
        if(count>0){
            return false;
        }

        //2. 如果没有分配角色，直接删除该用户
        userDao.delete(id);
        return true;
    }

    @Override
    public void changeRole(String userid, String[] roleIds) {
        //1. 先删除用户的角色
        userDao.deleteUserRoleByUserId(userid);
        //2. 给当前用户添加对应角色
        userDao.addUserRoles(userid,roleIds);
    }


    //根据邮箱查找用户
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}

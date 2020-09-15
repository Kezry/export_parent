package cn.itcast.service.system.impl;

import cn.itcast.dao.system.RoleDao;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo<Role> findByPage(int pageNum, int pageSize, String companyId) {
        //1.设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //2. 查询全部
        List<Role> list = roleDao.findAll(companyId);
        //3. 构造一个PageInfo对象
        PageInfo<Role> pageInfo = new PageInfo(list);

        return pageInfo;
    }

    //查询所有角色信息
    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    //保存角色
    @Override
    public void save(Role role) {
        //设置主键
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    //更新角色
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    //根据id 查找角色
    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }


    //根据角色id删除角色
    @Override
    public void delete(String id) {
        //2. 如果没有子角色，我们就可以删除该角色
        roleDao.delete(id);
    }


    //更新角色的权限
    @Override
    public void updateRoleModule(String roleid, String[] moduleIds) {
        //1. 先删除当前角色权限
        roleDao.deleteRoleModuleByRoleId(roleid);
        //2. 给当前角色添加权限
        /**
         * 修复权限为空时的bug
         */
        if (!(moduleIds.toString() == ""||moduleIds==null||moduleIds.length==0)){
        roleDao.addRoleModules(roleid,moduleIds);
        }
    }


    //根据用户id查找角色
    @Override
    public List<Role> findUserRoleByUserId(String id) {
        return roleDao.findUserRoleByUserId(id);
    }

    @Override
    public List<Role> findUserIsRoleByUserId(String id) {
        return roleDao.findUserIsRoleByUserId(id);
    }

}

package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo<Module> findByPage(int pageNum, int pageSize) {
        //1.设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //2. 查询全部
        List<Module> list = moduleDao.findAll();
        //3. 构造一个PageInfo对象
        PageInfo<Module> pageInfo = new PageInfo(list);

        return pageInfo;
    }

    //查询所有模块信息
    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    //保存模块
    @Override
    public void save(Module module) {
        //设置主键
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    //更新模块
    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    //根据id 查找模块
    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }


    //根据模块id删除模块
    @Override
    public void delete(String id) {
        //2. 如果没有子模块，我们就可以删除该模块
        moduleDao.delete(id);
    }


    //根据角色id查找权限
    @Override
    public List<Module> findRoleModuleByRoleId(String roleid) {
        return moduleDao.findRoleModuleByRoleId(roleid);
    }

    @Override
    public List<Module> findModuleByUser(User user) {
        //1.获取用户的等级
        Integer degree = user.getDegree();
        List<Module> resultList = null;

        //2. 判断用户的等级0||1||普通的用户
        if(degree==0||degree==1){
            //根据用户等级查看
            resultList = moduleDao.findModuleByDegree(degree);
        }else{
            //普通用户是需要根据用户的角色查看
            resultList = moduleDao.findModuleByUserId(user.getId());
        }
        return resultList;
    }
}

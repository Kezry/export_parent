package cn.itcast.service.system.impl;

import cn.itcast.dao.system.DeptDao;
import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public PageInfo<Dept> findByPage(int pageNum, int pageSize, String companyId) {
        //1.设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //2. 查询全部
        List<Dept> list = deptDao.findAll(companyId);
        //3. 构造一个PageInfo对象
        PageInfo<Dept> pageInfo = new PageInfo(list);

        return pageInfo;
    }

    //查询所有部门信息
    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    //保存部门
    @Override
    public void save(Dept dept) {
        //设置主键
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }

    //更新部门
    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    //根据id 查找部门
    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }


    //根据部门id删除部门
    @Override
    public boolean delete(String id) {
        //1. 查询该部门是否有子部门
       long count =  deptDao.findDeptByParentId(id);

       if(count>0){
           //代表该部门有子部门
           return false;
       }


        //2. 如果没有子部门，我们就可以删除该部门
        deptDao.delete(id);
        return true;
    }
}

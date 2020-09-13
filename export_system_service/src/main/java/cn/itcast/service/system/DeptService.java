package cn.itcast.service.system;

import cn.itcast.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {


    //查询部门列表，返回的是一个页面对象
    PageInfo<Dept> findByPage(int pageNum,int pageSize,String companyId);

    //查找所有部门
    List<Dept> findAll(String companyId);

    //保存部门
    void save(Dept dept);

    //更新部门
    void update(Dept dept);

    //根据id查找部门
    Dept findById(String id);

    //根据id删除部门
    boolean delete(String id);
}

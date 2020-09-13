package cn.itcast.dao.system;

import cn.itcast.domain.system.Dept;

import java.util.List;

public interface DeptDao {


    //查询所有部门一定要根据当前登陆者企业id
    List<Dept> findAll(String companyId);

    //根据id 查询当前部门
    Dept findById(String id);

    //保存部门
    void save(Dept dept);

    //更新部门
    void update(Dept dept);

    //查询一个部门是否被其他部门引用
    long findDeptByParentId(String id);

    //删除部门
    void delete(String id);
}

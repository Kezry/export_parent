package cn.itcast.dao.company;

import cn.itcast.domain.company.Company;

import java.util.List;

public interface CompanyDao {

    //查询所有的企业
    List<Company> findAll();

    //新增企业
    void save(Company company);

    //更新企业
    void update(Company company);


    //根据id查找企业
    Company findById(String id);

    //根据id删除企业
    void delete(String id);
}

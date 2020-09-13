package cn.itcast.service.company;

import cn.itcast.domain.company.Company;
import com.github.pagehelper.PageInfo;

import java.util.List;

//企业管理的服务接口
public interface CompanyService {

    public List<Company> findAll();

    //新增企业
    void save(Company company);

    //更新企业
    void update(Company company);

    //根据id查找企业
    Company findById(String id);

    //根据id删除企业
    void delete(String id);


    //企业分页
    public PageInfo<Company> findByPage(int pageNum, int pageSize);
}

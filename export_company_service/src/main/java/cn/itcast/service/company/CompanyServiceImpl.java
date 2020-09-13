package cn.itcast.service.company;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

//创建Service对象，并且放入spring的容器
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired  //自动注入
    private CompanyDao companyDao;


    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    //新增企业
    @Override
    public void save(Company company) {
        //由于company目前还没有id，新增的时候需要设置id，这里的id不是int类型自增，是使用uuid.
        //uuid的作用：保证一个数据的唯一性，不是加密。
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);

    }

    //更新企业
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    //根据id查找企业
    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }


    //根据id删除企业
    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageInfo<Company> findByPage(int pageNum, int pageSize) {
        //1. 设置当前页与页面大小
        PageHelper.startPage(pageNum,pageSize);

        //2. 查询企业的所有
        List<Company> list = companyDao.findAll();

        //3. 构建一个PageInfo对象
        PageInfo<Company> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }
}

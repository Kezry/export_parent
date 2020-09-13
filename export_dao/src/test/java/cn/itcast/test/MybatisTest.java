package cn.itcast.test;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class MybatisTest {

    @Autowired
    private CompanyDao companyDao;


    @Test
    public void test01(){
        List<Company> list = companyDao.findAll();
        System.out.println("企业列表:"+ list);
    }

    /*
        目的： 测试PageHelper
        使用PageHelper要注意的事项：
            1. 不需要我们自己创建Pagebean ,直接使用它内部PageInfo.

     */
    @Test
    public void testPage(){
        //1.设置当前页，页面大小
        PageHelper.startPage(1,3);
        //2. 查询页面的所有数据
        List<Company> list = companyDao.findAll();
        //3. 构建一个PageInfo对象
        PageInfo<Company> pageInfo = new PageInfo<>(list);
        System.out.println("当前页："+ pageInfo.getPageNum());
        System.out.println("页面大小："+ pageInfo.getPageSize());
        System.out.println("总记录数："+ pageInfo.getTotal());
        System.out.println("总页数："+ pageInfo.getPages());
        System.out.println("页面数据："+ pageInfo.getList());
    }

}

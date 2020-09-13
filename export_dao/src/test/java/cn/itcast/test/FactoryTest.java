package cn.itcast.test;


import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class FactoryTest {

    @Autowired
    private FactoryDao factoryDao;  // 看一个类的所有方法 alt+7

    @Test
    public void test01(){
        //查询
    /*    Factory factory = factoryDao.selectByPrimaryKey("1");
        System.out.println("工厂："+ factory);*/

        //插入
        Factory factory1 =  new Factory();
        factory1.setId("001");
        factory1.setFactoryName("a传智工厂");
        factory1.setCreateTime(new Date());
        factory1.setUpdateTime(new Date());
     /*   factory1.setAddress("广州");
        factory1.setCreateTime(new Date());
        factory1.setUpdateTime(new Date());*/

        //insert的方法不管实体类有哪些的字段，全部都插入，如果没有数据全部插入null.(全字段插入)
       // factoryDao.updateByPrimaryKey(factory1);

        // insertSelective 的方法特点：如果实体类为空的字段不会生成insert语句插入。(非空部分插入)
        //factoryDao.insertSelective(factory1);
    }


    /*
      逆向工程的情况下多条件查询
     */
    @Test
    public void test02(){
        //查询的条件都全部应该封装到FactoryExample，其实本质还需要添加到Criteria中，Criteria是属于Example静态内部类
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria = factoryExample.createCriteria();
     //   criteria.andFactoryNameEqualTo("传智程序员工厂");

        //模糊查询
        criteria.andFullNameLike("%祁县%");

        List<Factory> factoryList = factoryDao.selectByExample(factoryExample);//selectByExample 条件查询的方法
        System.out.println("查询条件："+ factoryList);
    }




}

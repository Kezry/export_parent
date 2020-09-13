package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.FactoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;

    /*
     工厂分页
     */
    @Override
    public PageInfo<Factory> findByPage(FactoryExample factoryExample, int pageNum, int pageSize) {
        //设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<Factory> factoryList = factoryDao.selectByExample(factoryExample);
        //
        PageInfo<Factory> pageInfo = new PageInfo<>(factoryList);
        return pageInfo;
    }

    //查询全部
    @Override
    public List<Factory> findAll(FactoryExample factoryExample) {
        List<Factory> factoryList = factoryDao.selectByExample(factoryExample);
        return factoryList;
    }

    //根据主键查询
    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    // 保存工厂
    @Override
    public void save(Factory factory) {
        factory.setId(UUID.randomUUID().toString());
        //工厂创建时间
        factory.setCreateTime(new Date());
        //工厂的更新时间
        factory.setUpdateTime(new Date());
        factoryDao.insertSelective(factory);
    }

    //更新工厂
    @Override
    public void update(Factory factory) {
        //工厂的更新时间
        factory.setUpdateTime(new Date());
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    //删除工厂
    @Override
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }

    //根据工厂的名字去找工厂
    @Override
    public Factory findByName(String factoryName) {
        return factoryDao.findByName(factoryName);
    }
}

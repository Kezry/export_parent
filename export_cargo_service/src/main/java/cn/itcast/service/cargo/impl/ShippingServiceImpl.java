package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ExportDao;
import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.dao.cargo.ShippingDao;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ShippingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 装箱管理业务实现类
 *
 * @author lwj
 */
@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private PackingDao packingDao;

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ShippingDao shippingDao;

    @Override
    public Shipping findById(String id) {
        return shippingDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Shipping> findAll(ShippingExample shippingExample) {
        return null;
    }

    /**
     * 添加委托单
     * @param shipping
     * @return
     */
    @Override
    public void save(Shipping shipping,String id) {
        shipping.setShippingOrderId(id);//委托单编号
        //给生成的委托单添加状态
        shipping.setState(0);
        shippingDao.insertSelective(shipping);
        //修改装箱单状态为1，表示已生成委托
        Packing packing = packingDao.selectByPrimaryKey(id);
        packing.setState(1);
        packingDao.updateByPrimaryKeySelective(packing);
    }

    /**
     * 更新委托单
     *
     * @param shipping
     */
    @Override
    public void update(Shipping shipping) {
        shippingDao.updateByPrimaryKey(shipping);
    }

    @Override
    public void delete(String id) {
        shippingDao.deleteByPrimaryKey(id);
        //修改装箱单的状态
        Packing packing = packingDao.selectByPrimaryKey(id);
        packing.setState(0);
        packingDao.updateByPrimaryKeySelective(packing);
    }

    @Override
    public PageInfo<Shipping> findByPage(ShippingExample shippingExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<Shipping>(shippingDao.selectByExample(shippingExample));
    }

    @Override
    public List<Shipping> getByCreateTime(String loginCompanyId, String createTime) {
        return shippingDao.getByCreateTime(loginCompanyId,createTime);
    }
}

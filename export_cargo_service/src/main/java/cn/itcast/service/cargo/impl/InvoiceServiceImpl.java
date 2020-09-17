package cn.itcast.service.cargo.impl;


import cn.itcast.dao.cargo.InvoiceDao;
import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.service.cargo.InvoiceService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 发票管理业务实现类
 *
 * @author lwj
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {



    @Autowired
    private InvoiceDao invoiceDao;

    /**
     * 通过id查询发票
     * @param id
     * @return
     */
    @Override
    public Invoice findById(String id) {
        return invoiceDao.selectByPrimaryKey(id);
    }

    /**
     * 查询全部发票
     * @param invoiceExample
     * @return
     */
    @Override
    public List<Invoice> findAll(InvoiceExample invoiceExample) {
        return invoiceDao.selectByExample(invoiceExample);
    }

    /**
     * 添加发票单
     * @param invoice
     * @return
     */
    @Override
    public void save(Invoice invoice) {
        invoiceDao.insertSelective(invoice);
    }

    /**
     * 更新发票
     *
     * @param invoice
     */
    @Override
    public void update(Invoice invoice) {
        invoiceDao.updateByPrimaryKey(invoice);
    }

    /**
     * 通过id删除发票
     * @param id
     */
    @Override
    public void delete(String id) {
        invoiceDao.deleteByPrimaryKey(id);
    }

    /**
     * 分页展示发票列表
     * @param invoiceExample
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Invoice> findByPage(InvoiceExample invoiceExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<Invoice>(invoiceDao.selectByExample(invoiceExample));
    }

   
}

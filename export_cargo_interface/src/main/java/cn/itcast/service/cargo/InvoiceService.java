package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 发票管理业务接口
 * @author lwj
 */
public interface InvoiceService {
    /**
     * 通过id查询
     * @param id
     * @return
     */
    Invoice findById(String id);

    /**
     * 条件查询全部
     * @param invoiceExample
     * @return
     */
    List<Invoice> findAll(InvoiceExample invoiceExample);

    /**
     * 新增
     * @param invoice
     */
    void save(Invoice invoice);

    /**
     * 更新发票
     * @param invoice
     */
    void update(Invoice invoice);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 条件分页查询
     * @param invoiceExample
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Invoice> findByPage(InvoiceExample invoiceExample, int pageNum, int pageSize);

}

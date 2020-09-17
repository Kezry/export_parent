package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 委托管理业务接口
 * @author lwj
 */
public interface ShippingService {
    /**
     * 通过id查询
     * @param id
     * @return
     */
    Shipping findById(String id);

    /**
     * 条件查询全部
     * @param shippingExample
     * @return
     */
    List<Shipping> findAll(ShippingExample shippingExample);

    /**
     * 新增
     * @param shipping
     */
    void save(Shipping shipping,String id);

    /**
     * 更新委托单
     * @param shipping
     */
    void update(Shipping shipping);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 条件分页查询
     * @param shippingExample
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Shipping> findByPage(ShippingExample shippingExample, int pageNum, int pageSize);

    List<Shipping> getByCreateTime(String loginCompanyId, String createTime);
}

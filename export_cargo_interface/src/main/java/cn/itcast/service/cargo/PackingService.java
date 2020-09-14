package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 装箱管理业务接口
 * @author lwj
 */
public interface PackingService {
    /**
     * 通过id查询
     * @param id
     * @return
     */
    Packing findById(String id);

    /**
     * 条件查询全部
     * @param packingExample
     * @return
     */
    List<Packing> findAll(PackingExample packingExample);

    /**
     * 新增
     * @param packing
     */
    void save(Packing packing);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 条件分页查询
     * @param packingExample
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Packing> findByPage(PackingExample packingExample, int pageNum, int pageSize);
}

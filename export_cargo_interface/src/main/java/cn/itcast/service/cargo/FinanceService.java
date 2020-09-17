package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 财务报运单管理业务接口
 * @author lwj
 */
public interface FinanceService {
    /**
     * 通过id查询
     * @param id
     * @return
     */
    Finance findById(String id);

    /**
     * 条件查询全部
     * @param financeExample
     * @return
     */
    List<Finance> findAll(FinanceExample financeExample);

    /**
     * 新增
     * @param finance
     */
    void save(Finance finance);

    /**
     * 更新财务报运单
     * @param finance
     */
    void update(Finance finance);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 条件分页查询
     * @param financeExample
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Finance> findByPage(FinanceExample financeExample, int pageNum, int pageSize);

}

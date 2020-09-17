package cn.itcast.service.cargo.impl;


import cn.itcast.dao.cargo.FinanceDao;
import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;
import cn.itcast.service.cargo.FinanceService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 财务单管理业务实现类
 *
 * @author lwj
 */
@Service
public class FinanceServiceImpl implements FinanceService {


    @Autowired
    private FinanceDao financeDao;

    /**
     * 通过id查询财务单
     * @param id
     * @return
     */
    @Override
    public Finance findById(String id) {
        return financeDao.selectByPrimaryKey(id);
    }

    /**
     * 查询全部财务单
     * @param financeExample
     * @return
     */
    @Override
    public List<Finance> findAll(FinanceExample financeExample) {
        return financeDao.selectByExample(financeExample);
    }

    /**
     * 添加财务单单
     * @param finance
     * @return
     */
    @Override
    public void save(Finance finance) {
        financeDao.insertSelective(finance);
    }

    /**
     * 更新财务单
     *
     * @param finance
     */
    @Override
    public void update(Finance finance) {
        financeDao.updateByPrimaryKey(finance);
    }

    /**
     * 通过id删除财务单
     * @param id
     */
    @Override
    public void delete(String id) {
        financeDao.deleteByPrimaryKey(id);
    }

    /**
     * 分页展示财务单列表
     * @param financeExample
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Finance> findByPage(FinanceExample financeExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<Finance>(financeDao.selectByExample(financeExample));
    }

   
}

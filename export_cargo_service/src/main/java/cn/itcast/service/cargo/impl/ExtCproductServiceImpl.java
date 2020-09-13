package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.service.cargo.ExtCproductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    /*
     附件分页
     */
    @Override
    public PageInfo<ExtCproduct> findByPage(ExtCproductExample extCproductExample, int pageNum, int pageSize) {
        //设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        //
        PageInfo<ExtCproduct> pageInfo = new PageInfo<>(extCproductList);
        return pageInfo;
    }

    //查询全部
    @Override
    public List<ExtCproduct> findAll(ExtCproductExample extCproductExample) {
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        return extCproductList;
    }

    //根据主键查询
    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    // 保存附件
    @Override
    public void save(ExtCproduct extCproduct) {
        extCproduct.setId(UUID.randomUUID().toString());
        //附件创建时间
        extCproduct.setCreateTime(new Date());
        //附件的更新时间
        extCproduct.setUpdateTime(new Date());

//        1.计算附件总价（单价*数量）
        double amount = 0;
        if(extCproduct.getCnumber()!=null && extCproduct.getPrice()!=null){
            amount = extCproduct.getCnumber()*extCproduct.getPrice();
            extCproduct.setAmount(amount);
        }
//        2.插入一条附件表记录
        extCproductDao.insertSelective(extCproduct);

//        3.计算新合同的总价（原合同总价+附件总价）
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()+amount);

//        4.计算合同的附件数量（原合同的附件数+1）
        if(contract.getExtNum()!=null) {
            contract.setExtNum(contract.getExtNum() + 1);
        }else{
            contract.setExtNum(1);
        }
//        5.更新合同表的数据
        contractDao.updateByPrimaryKeySelective(contract);
    }

    //更新附件
    @Override
    public void update(ExtCproduct extCproduct) {
        //更新之前的附件的对象
        ExtCproduct oldExtCproduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());

        //附件的更新时间
        extCproduct.setUpdateTime(new Date());
//        1.计算附件总价（单价*数量）
        double amount = 0;
        if(extCproduct.getCnumber()!=null && extCproduct.getPrice()!=null){
            amount = extCproduct.getCnumber()*extCproduct.getPrice();
            extCproduct.setAmount(amount);
        }

//        2.更新附件表一条记录
        extCproductDao.updateByPrimaryKeySelective(extCproduct);

//        3.计算合同的总价（原合同的总价-原附件总价+新附件总价）
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() - oldExtCproduct.getAmount() + amount);

//        4.更新合同表的记录
        contractDao.updateByPrimaryKeySelective(contract);
    }

    //删除附件
    @Override
    public void delete(String id) {
        //删除之前下能找到附件
        ExtCproduct dbExtCproduct = extCproductDao.selectByPrimaryKey(id);
//        1.删除附件表的一条记录
        extCproductDao.deleteByPrimaryKey(id);

//        2.计算合同的总价（原合同的总价-附件总价）
        Contract contract = contractDao.selectByPrimaryKey(dbExtCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-dbExtCproduct.getAmount());

//        3.计算合同的附件数量（原附件数量-1）
        contract.setExtNum(contract.getExtNum()-1);

//        4.更新合同表的记录
        contractDao.updateByPrimaryKeySelective(contract);
    }


}

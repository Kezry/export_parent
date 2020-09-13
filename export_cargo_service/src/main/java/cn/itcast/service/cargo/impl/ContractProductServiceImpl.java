package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ContractProductDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.vo.ContractPrudctVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;


    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    /*
     货物分页
     */
    @Override
    public PageInfo<ContractProduct> findByPage(ContractProductExample contractProductExample, int pageNum, int pageSize) {
        //设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);
        //
        PageInfo<ContractProduct> pageInfo = new PageInfo<>(contractProductList);
        return pageInfo;
    }

    //查询全部
    @Override
    public List<ContractProduct> findAll(ContractProductExample contractProductExample) {
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);
        return contractProductList;
    }

    //根据主键查询
    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    // 保存货物
    @Override
    public void save(ContractProduct contractProduct) {
        contractProduct.setId(UUID.randomUUID().toString());
        //货物创建时间
        contractProduct.setCreateTime(new Date());
        //货物的更新时间
        contractProduct.setUpdateTime(new Date());

//        1. 计算货物的总价
        double amount = 0;
        //由于单价与数量我们都使用了包装类型，所以可以使用null进行判断
        if(contractProduct.getPrice()!=null && contractProduct.getCnumber()!=null){
            amount = contractProduct.getPrice()*contractProduct.getCnumber();
            contractProduct.setAmount(amount);
        }
//        2. 插入货物的数据
        contractProductDao.insertSelective(contractProduct);

//        3. 购销合同的货物的种类数量+1
        //找到购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        if(contract.getProNum()!=null) {
            contract.setProNum(contract.getProNum() + 1);
        }else{
            contract.setProNum(1);
        }
//        4. 更新购销合同的总价（加上货物总价）
        if(contract.getTotalAmount()!=null){
            contract.setTotalAmount(contract.getTotalAmount()+amount);
        }else{
            //如果是该购销合同的第一个货物，那么购销合同总价就等于货物的总价
            contract.setTotalAmount(amount);
        }
//        5. 更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);

    }

    //更新货物
    @Override
    public void update(ContractProduct contractProduct) {
        //在更新货物之前就把货物的数据查询出来。
        ContractProduct oldContractProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());

        //货物的更新时间
        contractProduct.setUpdateTime(new Date());
//        1. 计算货物的总价
        double amount = 0;
        //由于单价与数量我们都使用了包装类型，所以可以使用null进行判断
        if(contractProduct.getPrice()!=null && contractProduct.getCnumber()!=null){
            amount = contractProduct.getPrice()*contractProduct.getCnumber();
            contractProduct.setAmount(amount);
        }

//        2. 更新货物的数据
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
//        3. 更新购销合同的总价
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //购销合同的总价 = 原购销合同的总价- 以前货物的总价+ 当前的总价
        contract.setTotalAmount(contract.getTotalAmount() - oldContractProduct.getAmount() + amount);


//        4. 更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    //删除货物
    @Override
    public void delete(String id) {
        //删除货物之前先把货物取出来
        ContractProduct dbContractProduct = contractProductDao.selectByPrimaryKey(id);

//        1. 删除货物
        contractProductDao.deleteByPrimaryKey(id);
//        2. 删除附件， 一个货物可能有多个附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        //添加，该货物的附件
        extCproductExample.createCriteria().andContractProductIdEqualTo(id);
        //该货物的所有附件
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        //定义一个变量存储该货物下的所有附件的总价
        double extTotalAmount  = 0;
        if(extCproductList!=null){
            //遍历删除
            for (ExtCproduct extCproduct : extCproductList) {
                extTotalAmount+=extCproduct.getAmount();
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }

//        3. 更新购销合同的货物种类数量
        Contract contract = contractDao.selectByPrimaryKey(dbContractProduct.getContractId());
        contract.setProNum(contract.getProNum()-1);
//        4. 更新购销合同附件的种类数量
        contract.setExtNum(contract.getExtNum()- extCproductList.size());
//        5. 更新购销合同的总价  = 原购销合同的总价-货物的总价-附件的总价
        contract.setTotalAmount(contract.getTotalAmount()- dbContractProduct.getAmount()-extTotalAmount);
//        6. 更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }


    //出货表的sql
    @Override
    public List<ContractPrudctVo> findByShipTime(String companyId, String shipTime) {

        return contractProductDao.findByShipTime(companyId,shipTime);
    }
}

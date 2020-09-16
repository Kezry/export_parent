package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    /*
     购销合同分页
     */
    @Override
    public PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize) {
        //设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<Contract> contractList = contractDao.selectByExample(contractExample);
        //
        PageInfo<Contract> pageInfo = new PageInfo<>(contractList);
        return pageInfo;
    }

    //查询全部
    @Override
    public List<Contract> findAll(ContractExample contractExample) {
        List<Contract> contractList = contractDao.selectByExample(contractExample);
        return contractList;
    }

    //根据主键查询
    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    // 保存购销合同
    @Override
    public void save(Contract contract) {
        contract.setId(UUID.randomUUID().toString());
        //购销合同创建时间
        contract.setCreateTime(new Date());
        //购销合同的更新时间
        contract.setUpdateTime(new Date());
        contractDao.insertSelective(contract);
    }

    //更新购销合同
    @Override
    public void update(Contract contract) {
        //购销合同的更新时间
        contract.setUpdateTime(new Date());
        contractDao.updateByPrimaryKeySelective(contract);
    }

    //删除购销合同
    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }

    //大区经理查看购销合同
    @Override
    public PageInfo<Contract> findPageDeptId(String companyId, String deptId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Contract> contractList = contractDao.findByDeptId(companyId,deptId);
        return new PageInfo<>(contractList);
    }


    //查询审查合同的用户
    @Override
    public List<User> findContractReviewUser() {
        return contractDao.findContractReviewUser();
    }
   //删除货物
    @Override
    public void deleteCargo(String id) {
        contractDao.deleteCargo(id);
    }
   //删除附件
    @Override
    public void deleteAccessory(String id) {
        contractDao.deleteAccessory(id);
    }


}

package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 购销合同模块
 */
public interface ContractService {

    /**
     * 分页查询
     * @param contractExample 分页查询的参数
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @return
     */
    PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize);

    /**
     * 查询所有
     */
    List<Contract> findAll(ContractExample contractExample);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Contract findById(String id);

    /**
     * 新增
     */
    void save(Contract contract);

    /**
     * 修改
     */
    void update(Contract contract);

    /**
     * 删除部门
     */
    void delete(String id);

    //大区经理查看购销合同
    PageInfo<Contract> findPageDeptId(String companyId, String deptId, int pageNum, int pageSize);


    //查询审查合同的用户
    List<User> findContractReviewUser();

    //删除货物
    void deleteCargo(String id);
    //删除附件
    void deleteAccessory(String id);


}












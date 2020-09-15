package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    ////大区经理查看购销合同
    List<Contract> findByDeptId(@Param("companyId") String companyId,@Param("deptId") String deptId);


    //查询审查合同的用户
    List<User> findContractReviewUser();

    //查询3天后到期的购销合同
    List<Contract> findContractAfter3Days();
}
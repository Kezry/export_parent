package cn.itcast.dao.cargo;

import cn.itcast.vo.ContractUserVo;

import java.util.List;

public interface ContractUserVoDao {

    /*查询三天后到期的购销合同和创建用户邮箱*/
    List<ContractUserVo> findContractsAfter3Days();
}

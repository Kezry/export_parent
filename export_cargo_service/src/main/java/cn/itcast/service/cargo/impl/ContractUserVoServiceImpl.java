package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractUserVoDao;
import cn.itcast.service.cargo.ContractUserVoService;
import cn.itcast.vo.ContractUserVo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class ContractUserVoServiceImpl implements ContractUserVoService {


    @Autowired
    private ContractUserVoDao contractUserVoDao;


    /*查询三天后到期的购销合同和创建用户邮箱*/
    @Override
    public List<ContractUserVo> findContractsAfter3Days() {
        return contractUserVoDao.findContractsAfter3Days();
    }
}

package cn.itcast.service.stat.impl;

import cn.itcast.dao.stat.StatDao;
import cn.itcast.service.stat.StatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatDao statDao;

    @Override
    public List<Map<String, Object>> getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }

    @Override
    public List<Map<String, Object>> getSellData(String companyId) {
        return statDao.getSellData(companyId);
    }

    //系统压力图
    @Override
    public List<Map<String, Object>> getOnlineData() {
        return statDao.getOnlineData();
    }

    /**
     * 获取所有厂家的地址
     *
     * @return
     */
    @Override
    public List<String> getAllAddress() {
        return statDao.getAllAddress();
    }

    /**
     * 按所在地统计指定生产类型的厂家数量
     *
     * @param ctype 厂家生产类型
     * @return
     */
    @Override
    public List<Map<String, Object>> getAddressData(String ctype) {
        return statDao.getAddressData(ctype);
    }
}

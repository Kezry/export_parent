package cn.itcast.service.stat;

import java.util.List;
import java.util.Map;

public interface StatService {

    //获取厂家销售数据
    List<Map<String,Object>> getFactoryData(String companyId);

    //获取产品销售额前五名
    List<Map<String,Object>> getSellData(String companyId);

    //根据小时统计访问次数
    List<Map<String,Object>> getOnlineData();

}


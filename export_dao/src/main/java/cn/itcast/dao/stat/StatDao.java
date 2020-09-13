package cn.itcast.dao.stat;

import java.util.List;
import java.util.Map;

//统计的dao
public interface StatDao {

    //获取厂家销售数据
    public List<Map<String,Object>> getFactoryData(String companyId);



    //获取产品的销售数据
    public List<Map<String,Object>> getSellData(String companyId);


    //根据小时统计在线人数
    public List<Map<String,Object>> getOnlineData();

}

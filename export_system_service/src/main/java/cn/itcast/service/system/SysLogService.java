package cn.itcast.service.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.SysLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysLogService {


    //查询日志列表，返回的是一个页面对象
    PageInfo<SysLog> findByPage(int pageNum, int pageSize, String companyId);


    //保存日志
    void save(SysLog sysLog);

}

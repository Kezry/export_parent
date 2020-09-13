package cn.itcast.service.system.impl;

import cn.itcast.dao.system.SysLogDao;
import cn.itcast.domain.system.SysLog;
import cn.itcast.service.system.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public PageInfo<SysLog> findByPage(int pageNum, int pageSize, String companyId) {
        //1.设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //2. 查询全部
        List<SysLog> list = sysLogDao.findAll(companyId);
        //3. 构造一个PageInfo对象
        PageInfo<SysLog> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    //保存日志
    @Override
    public void save(SysLog sysLog) {
        //设置主键
        sysLog.setId(UUID.randomUUID().toString());
        sysLogDao.save(sysLog);
    }


}

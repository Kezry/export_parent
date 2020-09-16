package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ExportDao;
import cn.itcast.dao.cargo.ExportProductDao;
import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import cn.itcast.service.cargo.PackingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 装箱管理业务实现类
 *
 * @author lwj
 */
@Service
public class PackingServiceImpl implements PackingService {

    @Autowired
    private PackingDao packingDao;

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ExportProductDao exportProductDao;

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Override
    public Packing findById(String id) {
        return packingDao.selectByPrimaryKey(id);
    }

    /**
     * 条件查询全部
     *
     * @param packingExample
     * @return
     */
    @Override
    public List<Packing> findAll(PackingExample packingExample) {
        return packingDao.selectByExample(packingExample);
    }

    /**
     * 新增
     *
     * @param packing
     */
    @Override
    public void save(Packing packing) {
        //设置集装箱编号
        packing.setPackingListId(UUID.randomUUID().toString());
        //设置状态
        packing.setState(0);
        //设置创建时间
        packing.setCreateTime(new Date());
        //插入数据库
        packingDao.insertSelective(packing);

        //报运单状态改为3
        String[] exportIds = packing.getExportIds().split(",");
        ExportExample exportExample = new ExportExample();
        exportExample.createCriteria().andIdIn(Arrays.asList(exportIds));
        List<Export> exports = exportDao.selectByExample(exportExample);
        for (Export export : exports) {
            export.setState(3);
            exportDao.updateByPrimaryKeySelective(export);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        packingDao.deleteByPrimaryKey(id);
    }

    /**
     * 条件分页查询
     *
     * @param packingExample
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Packing> findByPage(PackingExample packingExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(packingDao.selectByExample(packingExample));
    }
}

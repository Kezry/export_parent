package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ExportProductDao;
import cn.itcast.domain.cargo.ExportProduct;
import cn.itcast.domain.cargo.ExportProductExample;
import cn.itcast.service.cargo.ExportProductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ExportProductServiceImpl implements ExportProductService {

    @Autowired
    private ExportProductDao exportProductDao;

    /*
     报运商品分页
     */
    @Override
    public PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample, int pageNum, int pageSize) {
        //设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<ExportProduct> exportProductList = exportProductDao.selectByExample(exportProductExample);
        //
        PageInfo<ExportProduct> pageInfo = new PageInfo<>(exportProductList);
        return pageInfo;
    }

    //查询全部
    @Override
    public List<ExportProduct> findAll(ExportProductExample exportProductExample) {
        List<ExportProduct> exportProductList = exportProductDao.selectByExample(exportProductExample);
        return exportProductList;
    }

    //根据主键查询
    @Override
    public ExportProduct findById(String id) {
        return exportProductDao.selectByPrimaryKey(id);
    }

    // 保存报运商品
    @Override
    public void save(ExportProduct exportProduct) {
        exportProduct.setId(UUID.randomUUID().toString());
        //报运商品创建时间
        exportProduct.setCreateTime(new Date());
        //报运商品的更新时间
        exportProduct.setUpdateTime(new Date());
        exportProductDao.insertSelective(exportProduct);
    }

    //更新报运商品
    @Override
    public void update(ExportProduct exportProduct) {
        //报运商品的更新时间
        exportProduct.setUpdateTime(new Date());
        exportProductDao.updateByPrimaryKeySelective(exportProduct);
    }

    //删除报运商品
    @Override
    public void delete(String id) {
        exportProductDao.deleteByPrimaryKey(id);
    }

}

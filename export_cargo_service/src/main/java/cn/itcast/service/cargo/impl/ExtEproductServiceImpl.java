package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ExtEproductDao;
import cn.itcast.domain.cargo.ExtEproduct;
import cn.itcast.domain.cargo.ExtEproductExample;
import cn.itcast.service.cargo.ExtEproductService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lwj
 */
@Service
public class ExtEproductServiceImpl implements ExtEproductService {

    @Autowired
    private ExtEproductDao extEproductDao;

    /**
     * 根据报运单id查询报运附件
     *
     * @param exportId
     * @return
     */
    @Override
    public List<ExtEproduct> findByExportId(String exportId) {
        ExtEproductExample extEproductExample = new ExtEproductExample();
        extEproductExample.createCriteria().andExportIdEqualTo(exportId);
        return extEproductDao.selectByExample(extEproductExample);
    }
}

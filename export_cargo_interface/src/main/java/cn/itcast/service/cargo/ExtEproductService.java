package cn.itcast.service.cargo;


import cn.itcast.domain.cargo.ExtEproduct;

import java.util.List;

/**
 * 报运附件业务接口
 * @author lwj
 */
public interface ExtEproductService {
    /**
     * 根据报运单id查询报运附件
     * @param exportId
     * @return
     */
    List<ExtEproduct> findByExportId(String exportId);
}

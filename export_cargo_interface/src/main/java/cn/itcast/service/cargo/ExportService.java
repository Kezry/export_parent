package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.vo.ExportResult;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ExportService {

    Export findById(String id);

    List<Export> findAll(ExportExample example);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo<Export> findByPage(ExportExample example, int pageNum, int pageSize);

	//根据海关返回报运结果更新报运单的信息以及商品的税收
    void updateExport(ExportResult exportResult);

    /**
     * 判断每个报运单是否已经报运，已报运返回ture,否则返回false
     * @param ids 报运单id数组
     * @return
     */
    boolean isExported(String[] ids);

    /**
     * 判断多个已报运合同的装运港(shipmentPort)、目的港(destinationPort)、收货人(consignee)是否一致
     * @param ids 报运单id数组
     * @return
     */
    boolean isSameSDC(String[] ids);
}

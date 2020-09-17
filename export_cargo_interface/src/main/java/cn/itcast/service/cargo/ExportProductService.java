package cn.itcast.service.cargo;


import cn.itcast.domain.cargo.ExportProduct;
import cn.itcast.domain.cargo.ExportProductExample;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ExportProductService {

	ExportProduct findById(String id);

	List<ExportProduct> findAll(ExportProductExample example);

	void save(ExportProduct exportProduct);

	void update(ExportProduct exportProduct);

	void delete(String id);

	PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample, int pageNum, int pageSize);

	/**
	 * 根据报运单id查询报运货物
	 * @param exportId
	 * @return
	 */
    List<ExportProduct> finByExportId(String exportId);
}

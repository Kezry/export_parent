package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.vo.LSDCTVo;

import java.util.List;

public interface ExportDao {
    /**
     * 根据id删除
     */
    void deleteByPrimaryKey(String id);

    /**
     * 保存
     */
    void insertSelective(Export record);

    /**
     * 条件查询
     */
    List<Export> selectByExample(ExportExample example);

    /**
     * 根据id查询
     */
    Export selectByPrimaryKey(String id);

    /**
     * 更新
     */
    void updateByPrimaryKeySelective(Export record);

    /**
     * 去重复查询SDCVo[装运港(shipmentPort)、目的港(destinationPort)、收货人(consignee)]
     *
     * @param ids 报运单id数组
     * @return
     */
    List<LSDCTVo> getLSDCTVoByIds(String[] ids);
}
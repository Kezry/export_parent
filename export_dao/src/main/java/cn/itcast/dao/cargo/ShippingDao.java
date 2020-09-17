package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Shipping;
import cn.itcast.domain.cargo.ShippingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    long countByExample(ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int deleteByExample(ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String shippingOrderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int insert(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int insertSelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    List<Shipping> selectByExample(ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    Shipping selectByPrimaryKey(String shippingOrderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Shipping record, @Param("example") ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Shipping record, @Param("example") ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Shipping record);

    List<Shipping> getByCreateTime(@Param("companyId") String loginCompanyId, @Param("createTime") String createTime);
}
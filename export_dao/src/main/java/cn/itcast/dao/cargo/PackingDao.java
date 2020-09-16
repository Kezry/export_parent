package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackingDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    long countByExample(PackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int deleteByExample(PackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String packingListId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int insert(Packing record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int insertSelective(Packing record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    List<Packing> selectByExample(PackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    Packing selectByPrimaryKey(String packingListId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Packing record, @Param("example") PackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Packing record, @Param("example") PackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Packing record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_packing_list
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Packing record);
}
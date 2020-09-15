package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.*;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductResult;
import cn.itcast.vo.ExportResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;


    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExportProductDao exportProductDao;

    @Autowired
    private ExtCproductDao extCproductDao; //购销合同的附件Dao

    @Autowired
    private ExtEproductDao extEproductDao; //报运单的附件Dao

    /*
     报运单分页
     */
    @Override
    public PageInfo<Export> findByPage(ExportExample exportExample, int pageNum, int pageSize) {
        //设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<Export> exportList = exportDao.selectByExample(exportExample);
        //
        PageInfo<Export> pageInfo = new PageInfo<>(exportList);
        return pageInfo;
    }

    //根据海关返回报运结果更新报运单的信息以及商品的税收
    @Override
    public void updateExport(ExportResult exportResult) {
        Export export = exportDao.selectByPrimaryKey(exportResult.getExportId());
        //把报运结果的状态赋予给报运单
        export.setState(exportResult.getState());
        //备注信息
        export.setRemark(exportResult.getRemark());
        exportDao.updateByPrimaryKeySelective(export);

        //获取到报运商品
        Set<ExportProductResult> products = exportResult.getProducts();
        if(products!=null){
            for (ExportProductResult product : products) {
                //查找到对应的商品
                ExportProduct exportProduct = exportProductDao.selectByPrimaryKey(product.getExportProductId());
                //给商品设置税收
                exportProduct.setTax(product.getTax());
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }

    }

    //查询全部
    @Override
    public List<Export> findAll(ExportExample exportExample) {
        List<Export> exportList = exportDao.selectByExample(exportExample);
        return exportList;
    }

    //根据主键查询
    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    // 保存报运单
    @Override
    public void save(Export export) {
        export.setId(UUID.randomUUID().toString());

//        1）往报运单表插入一条记录
          //给报运单补全数据
        export.setInputDate(new Date());
        //查找合同号
        //找到该报运单下面的所有购销合同
        String[] contractIds = export.getContractIds().split(",");
        ContractExample contractExample = new ContractExample();

        contractExample.createCriteria().andIdIn(Arrays.asList(contractIds));  //Arrays.asList(contractIds) 把数组转换为集合
        List<Contract> contractList = contractDao.selectByExample(contractExample);






        //定义一个变量存储合同号
        String contractNos = "";
        //存储所有的购销合同的货物种类属性
        int totalProNum = 0;
        //存储所有的购销合同的附件种类属性
        int totalExtNum = 0;
        //遍历所有的购销合同
        for (Contract contract : contractList) {
            contractNos+=contract.getContractNo()+" ";
            totalProNum+= contract.getProNum();
            totalExtNum += contract.getExtNum();
            //        2）报运单下面的购销合同的状态从1改为2 ,  （购销合同的状态：0（草稿，草稿状态下的合同不能申请报运） 1(允许被报运了) ，  2（已经生成报运单合同））
            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);

        }
        //把合同号设置到报运单中
        export.setCustomerContract(contractNos);
        //设置报运单的状态 ， 报运单状态有三种 0(草稿)  1(已提交，提交的报运单可以向海关报运)  2(已经向海关报运的报运单)
        export.setState(0);
        //设置货物种类数量
        export.setProNum(totalProNum);
        //设置附件种类数量
        export.setExtNum(totalExtNum);
        //设置创建时间更新时间
        export.setCreateTime(new Date());
        export.setUpdateTime(new Date());
        exportDao.insertSelective(export);


//        3）把购销合同下的所有货物数据导入到   报运商品表中
        //找到购销合同下面的所有货物
        ContractProductExample contractProductExample = new ContractProductExample();
        //添加购销合同下的货物添加
        contractProductExample.createCriteria().andContractIdIn(Arrays.asList(contractIds));
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);

        //定义一个Map集合存储 购销合同货物的id与报运货物的id
        Map<String,String> map =new HashMap<>();

        //遍历所有的购销合同的货物
        for (ContractProduct contractProduct : contractProductList) {
            //一个购销合同的货物就是对应一个报运单的商品数据
            ExportProduct exportProduct = new ExportProduct();
            //把contractProduct的属性数据拷贝到exportProduct对象，要求属性名一致即可
            BeanUtils.copyProperties(contractProduct,exportProduct);
            //报运商品的id
             exportProduct.setId(UUID.randomUUID().toString());
            //报运商品所属的报运单
            exportProduct.setExportId(export.getId());

            //map的key：购销合同货物的id  ， value： 报运商品的id
            map.put(contractProduct.getId(),exportProduct.getId());

            //把报运商品插入到数据库中
            exportProductDao.insertSelective(exportProduct);
        }

//        4）把报运单下的购销合同下的所有货物的附件数据导入到   报运商品附件表中
        //找到购销合同的所有附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        //条件：购销合同的id
        extCproductExample.createCriteria().andContractIdIn(Arrays.asList(contractIds));
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        //遍历购销合同的所有附件
        if(extCproductList!=null) {
            for (ExtCproduct extCproduct : extCproductList) { //购销合同的附件的外键： 购销合同的id， 货物的id
                //购销合同的一个附件就对应报运单表的一个附件
                ExtEproduct extEproduct = new ExtEproduct();
                //拷贝属性
                BeanUtils.copyProperties(extCproduct,extEproduct);
                //报运附件设置id
                extEproduct.setId(UUID.randomUUID().toString());
                //报运附件设置所属的报运单
                extEproduct.setExportId(export.getId());
                //报运附件设置所属报运商品
                extEproduct.setExportProductId(map.get(extCproduct.getContractProductId()));
                //插入数据
                extEproductDao.insertSelective(extEproduct);
            }
        }

    }

    //更新报运单
    @Override
    public void update(Export export) {
        //1. 更新报运单
        export.setUpdateTime(new Date());
        exportDao.updateByPrimaryKeySelective(export);

        //2 更新报运商品

        List<ExportProduct> exportProducts = export.getExportProducts();
        if(exportProducts!=null) {
            for (ExportProduct exportProduct : exportProducts) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    //删除报运单
    @Override
    public void delete(String id) {
        exportDao.deleteByPrimaryKey(id);
    }

    /**
     * 判断多个已报运合同的装运港(shipmentPort)、目的港(destinationPort)、收货人(consignee)是否一致
     *
     * @param ids 报运单id数组
     * @return
     */
    @Override
    public boolean isSameSDC(String[] ids) {
        //获取查询出的SDCVo个数
        int count = exportDao.getSDCVoByIds(ids).size();
        if (count == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断每个报运单是否已经报运，已报运返回ture,否则返回false
     *
     * @param ids 报运单id数组
     * @return
     */
    @Override
    public boolean isExported(String[] ids) {
        //根据id数组获取报运单集合
        ExportExample exportExample = new ExportExample();
        exportExample.createCriteria().andIdIn(Arrays.asList(ids));
        List<Export> exports = exportDao.selectByExample(exportExample);
        //遍历报运单
        for (Export export : exports) {
            if (export.getState() != 2) {
                return false;
            }
        }
        return true;
    }


}

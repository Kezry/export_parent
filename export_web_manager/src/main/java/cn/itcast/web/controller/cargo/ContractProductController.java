package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.ContractProduct;
import cn.itcast.domain.cargo.ContractProductExample;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.FileUploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {


    @Reference
    private ContractProductService contractProductService;


    @Reference
    private FactoryService factoryService;

    /*
     url:  //cargo/contractProduct/list.do?contractId=cf30c274-fa17-4371-8e47-8db30c2d06bd
     参数： contractId : 购销合同的id
     作用： 进入货物的列表页面
     返回值: 货物的列表页面
     */

    @RequestMapping("/list")
    public  String list(String contractId , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        //1. 货物的列表数据
        ContractProductExample productExample = new ContractProductExample();
        //添加条件
        productExample.createCriteria().andContractIdEqualTo(contractId);
        PageInfo<ContractProduct> pageInfo = contractProductService.findByPage(productExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);

        //2. 生成货物的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //3. 购销合同的id
        request.setAttribute("contractId",contractId);

        return "cargo/product/product-list";
    }


    @Autowired
    private FileUploadUtil fileUploadUtil;



    /*
      url: cargo/contractProduct/edit.do
      作用： 新增货物||更新货物
      参数： 货物
      返回值：货物列表页面
     */
    @RequestMapping("/edit")
    public  String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {
        //补全货物信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        contractProduct.setCompanyId(companyId);
        contractProduct.setCompanyName(companyName);
        //补全购销创建人与创建人所属的货物
        contractProduct.setCreateBy(getLoginUser().getId()); //登陆者的id
        contractProduct.setCreateDept(getLoginUser().getDeptId()); //登陆者所属的货物

        //文件上传
        if (productPhoto!=null){
            String url = fileUploadUtil.upload(productPhoto); //返回的就是上传图片到地址
            contractProduct.setProductImage("http://"+url); //路径并没有存储http的，所以前面添加http
        }


        if(StringUtils.isEmpty(contractProduct.getId())){
            contractProductService.save(contractProduct);
        }else{
            contractProductService.update(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractProduct.getContractId();  //添加redirect开头的返回值则不会经过视图解析器
    }



    /*
    url: /cargo/contractProduct/toUpdate.do?id=0e353ecb-7e46-4e8a-bc24-72d0575653e0
    作用： 进入更新货物页面
    参数： 货物的id
    返回值：货物更新页面
   */
    @RequestMapping("/toUpdate")
    public  String toUpdate(String id){
        //1.根据id查询当前货物信息
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct",contractProduct);

        //2. 生成货物的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        return "cargo/product/product-update";
    }



    /*
    url: /cargo/contractProduct/delete.do?id="+id&contractId,
    作用： 删除货物
    参数： 货物的id，购销合同的id
    返回值：货物列表页面
   */
    @RequestMapping("/delete")
    public String delete(String id,String contractId) {
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }


    /*
     url: /cargo/contractProduct/toImport.do?contractId=c6fb1a2b-09b6-43c9-b0bc-16f4d55d6ac2
     作用： 进入导入货物的页面
     参数： 购销合同的id
     返回值：导入货物的页面
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId) {
        request.setAttribute("contractId",contractId);
        return "/cargo/product/product-import";
    }



    /*
    url: /cargo/contractProduct/import.do
    作用： 保存上传的货物
    参数： 购销合同的id，excel文件  file
    返回值：货物列表页面
    */
    @RequestMapping("/import")
    public String importExcel(String contractId,MultipartFile file) throws IOException {
         //1. 创建工作薄
        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        //2. 得到工作单
        Sheet sheet = workbook.getSheetAt(0);

        //3 得到行数
        int rows = sheet.getPhysicalNumberOfRows();

        //遍历读取每一行的数据，每一行就是一个货物的对象数据
        for (int i = 1; i < rows; i++) {
            //把行的数据封装到ContractProduct对象中
            Row row = sheet.getRow(i);
            //每一行对应一个货物
            ContractProduct contractProduct  = new ContractProduct();

            //生产厂家
            if(row.getCell(1)!=null){
                String factoryName = row.getCell(1).getStringCellValue();
                contractProduct.setFactoryName(factoryName);
            }

            //货号
            if(row.getCell(2)!=null){
                String productNo = row.getCell(2).getStringCellValue();
                contractProduct.setProductNo(productNo);
            }

            //数量
            if(row.getCell(3)!=null){
                double cnumber = row.getCell(3).getNumericCellValue();
                contractProduct.setCnumber((int)cnumber);
            }

            //包装单位
            if(row.getCell(4)!=null){
                String packingUnit = row.getCell(4).getStringCellValue();
                contractProduct.setPackingUnit(packingUnit);
            }


            //装率
            if(row.getCell(5)!=null){
                double loadingRate = row.getCell(5).getNumericCellValue();
                contractProduct.setLoadingRate(loadingRate+"");
            }

            //箱数
            if(row.getCell(6)!=null){
                double boxNum = row.getCell(6).getNumericCellValue();
                contractProduct.setBoxNum((int)boxNum);
            }


            //单价
            if(row.getCell(7)!=null){
                double price = row.getCell(7).getNumericCellValue();
                contractProduct.setPrice(price);
            }


            //货物描述
            if(row.getCell(8)!=null){
                String productDesc = row.getCell(8).getStringCellValue();
                contractProduct.setProductDesc(productDesc);
            }

            //要求
            if(row.getCell(9)!=null){
                String productRequest = row.getCell(9).getStringCellValue();
                contractProduct.setProductRequest(productRequest);
            }

            //补全信息
            contractProduct.setContractId(contractId); //该货物所属的购销合同
            //货物的厂家id
             Factory factory =   factoryService.findByName(contractProduct.getFactoryName());
            contractProduct.setFactoryId(factory.getId());
            // ,插入数据
            contractProductService.save(contractProduct);
        }

        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }




}

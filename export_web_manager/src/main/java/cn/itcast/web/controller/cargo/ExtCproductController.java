package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.ExtCproductService;
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
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {


    @Reference
    private ExtCproductService extCproductService;


    @Reference
    private FactoryService factoryService;

    /*
     url:  /cargo/extCproduct/list.do?contractId=c6fb1a2b-09b6-43c9-b0bc-16f4d55d6ac2&contractProductId=cc254579-20d8-4cda-8a69-baf1f1459c87
     参数： contractId : 购销合同的id ，contractProductId 附件的id
     作用： 进入附件的列表页面
     返回值: 附件的列表页面
     */

    @RequestMapping("/list")
    public  String list(String contractId ,String contractProductId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        //1. 附件的列表数据
        ExtCproductExample productExample = new ExtCproductExample();
        //添加条件
        productExample.createCriteria().andContractProductIdEqualTo(contractProductId);
        PageInfo<ExtCproduct> pageInfo = extCproductService.findByPage(productExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);

        //2. 生成附件的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //3. 购销合同的id
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);

        return "cargo/extc/extc-list";
    }


    @Autowired
    private FileUploadUtil fileUploadUtil;



    /*
      url: cargo/extCproduct/edit.do
      作用： 新增附件||更新附件
      参数： 附件
      返回值：附件列表页面
     */
    @RequestMapping("/edit")
    public  String edit(ExtCproduct extCproduct, MultipartFile productPhoto) throws Exception {
        //补全附件信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        extCproduct.setCompanyId(companyId);
        extCproduct.setCompanyName(companyName);
        //补全购销创建人与创建人所属的附件
        extCproduct.setCreateBy(getLoginUser().getId()); //登陆者的id
        extCproduct.setCreateDept(getLoginUser().getDeptId()); //登陆者所属的附件

        //文件上传
        if (productPhoto!=null){
            String url = fileUploadUtil.upload(productPhoto); //返回的就是上传图片到地址
            extCproduct.setProductImage("http://"+url); //路径并没有存储http的，所以前面添加http
        }


        if(StringUtils.isEmpty(extCproduct.getId())){
            extCproductService.save(extCproduct);
        }else{
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do?contractId="+extCproduct.getContractId()+"&contractProductId="+extCproduct.getContractProductId();  //添加redirect开头的返回值则不会经过视图解析器
    }



    /*
    url: /cargo/extCproduct/toUpdate.do?id=4c01ce8a-21f2-40a4-8440-2e55690e7dd7&contractId=c6fb1a2b-09b6-43c9-b0bc-16f4d55d6ac2&contractProductId=cc254579-20d8-4cda-8a69-baf1f1459c87
    作用： 进入更新附件页面
    参数： 附件的id ， contractId 购销合同的id,  contractProductId 货物的id
    返回值：附件更新页面
   */
    @RequestMapping("/toUpdate")
    public  String toUpdate(String id,String contractId,String contractProductId){
        //1.根据id查询当前附件信息
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct",extCproduct);

        //2. 生成附件的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //购销合同的id
        request.setAttribute("contractId",contractId);
        //货物的id
        request.setAttribute("contractProductId",contractProductId);
        return "cargo/extc/extc-update";
    }



    /*
    url: /cargo/extCproduct/delete.do?id=${o.id}&contractId=${contractId}&contractProductId=${o.contractProductId}
    作用： 删除附件
    参数： 附件的id ， contractId 购销合同的id,  contractProductId 货物的id
    返回值：附件列表页面
   */
    @RequestMapping("/delete")
    public String delete(String id,String contractId,String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }









}

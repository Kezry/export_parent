package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductVo;
import cn.itcast.vo.ExportResult;
import cn.itcast.vo.ExportVo;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.BeanMapUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ExportService exportService;


    @Reference
    private ContractService contractService;


    @Reference
    private ExportProductService exportProductService;

    /*
      url: /cargo/export/contractList.do
      作用： 进入合同管理页面
      参数： 无
      返回值：合同管理页面
     */
    @RequestMapping("/contractList")
    public  String contractList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        ContractExample contractExample = new ContractExample();
        contractExample.setOrderByClause("create_time desc");
        contractExample.createCriteria().andStateEqualTo(1).andCompanyIdEqualTo(getLoginCompanyId());
        PageInfo<Contract> pageInfo = contractService.findByPage(contractExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "cargo/export/export-contractList";
    }




    /*
    url: /cargo/export/list.do
    作用： 报运单列表
    参数： 无
    返回值：报运单列表
   */
    @RequestMapping("/list")
    public  String list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        ExportExample exportExample = new ExportExample();
        exportExample.setOrderByClause("create_time desc");
        exportExample.createCriteria().andCompanyIdEqualTo(getLoginCompanyId());
        PageInfo<Export> pageInfo = exportService.findByPage(exportExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "cargo/export/export-list";
    }



    /*
   url: /cargo/export/toExport.do
   作用： 进入生成报运单页面
   参数： 多个报运单的id
   返回值：生成报运单页面
  */
    @RequestMapping("/toExport")
    public  String toExport(String id){ //1,2,3
        request.setAttribute("id",id);
        return "cargo/export/export-toExport";
    }


    /*
    url: cargo/export/edit.do
    作用： 新增报运单||更新报运单
    参数： 报运单
    返回值：报运单列表页面
   */
    @RequestMapping("/edit")
    public  String edit(Export export){
        //补全报运单信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);
        //补全购销创建人与创建人所属的报运单
        export.setCreateBy(getLoginUser().getId()); //登陆者的id
        export.setCreateDept(getLoginUser().getDeptId()); //登陆者所属的报运单

        if(StringUtils.isEmpty(export.getId())){
            exportService.save(export);
        }else{
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";  //添加redirect开头的返回值则不会经过视图解析器
    }


    /*
   url: /cargo/export/toUpdate.do?id=5a3680c7-c3eb-4e22-a8f1-f733fb4f1a9e
   作用： 进入报运单的修改页面
   参数： 报运单的id
   返回值：报运单的修改页面
  */
    @RequestMapping("/toUpdate")
    public  String toUpdate(String id){
        //1. 查找到对应的报运单
        Export export = exportService.findById(id);
        request.setAttribute("export",export);

        //2 查找报运单对应的货物数据
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        request.setAttribute("eps",exportProductList);

        return "cargo/export/export-update";
    }


    /*
      url: /cargo/export/toView.do?id=03ccb8e8-64b4-4b8b-8d5e-52983493e325
      作用： 进入报运单的详细页面
      参数： 报运单的id
      返回值：报运单的详细页面
 */
    @RequestMapping("/toView")
    public  String toView(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export",export);
        return "cargo/export/export-view";

    }


    /*
      url: /cargo/export/submit.do?id=03ccb8e8-64b4-4b8b-8d5e-52983493e325
      作用： 修改报运状态为提交
      参数： 报运单的id

 */
    @RequestMapping("/submit")
    public  String submit(String id) {
        Export export = exportService.findById(id);
       export.setState(1);
       exportService.update(export);
        return "redirect:/cargo/export/list.do";

    }


    /*
    url: /cargo/export/cancel.do?id=03ccb8e8-64b4-4b8b-8d5e-52983493e325
    作用： 修改报运状态为提交
    参数： 报运单的id

*/
    @RequestMapping("/cancel")
    public  String cancel(String id) {
        Export export = exportService.findById(id);
        export.setState(0);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";

    }


    /*
   url: /cargo/export/exportE.do?id=03ccb8e8-64b4-4b8b-8d5e-52983493e325
   作用： 电子电子（把报运单的信息提交给海关去审核）
   参数： 报运单的id
   返回值： 报运单列表
*/
    @RequestMapping("/exportE")
    public  String exportE(String id) {
        //1. 根据id查找报运单
        Export export = exportService.findById(id);
        //2. 由于海关不接受你的Export，只接受ExportVo对象,所以你需要把Export的数据转换为ExportVo对象.
        ExportVo exportVo = new ExportVo();
        BeanUtils.copyProperties(export,exportVo);
        //该报运的信息所属报运单
        exportVo.setExportId(export.getId());

        //3.查找该报运单的商品，然后把商品信息封装到ExportProductVo对象，然后把ExportProductVo对象添加ExportVo对象中
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        for (ExportProduct exportProduct : exportProductList) {
            //一个报运单商品就是一个ExportProductVo对象
            ExportProductVo exportProductVo = new ExportProductVo();
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            //设置所属的报运单
            exportProductVo.setExportId(export.getId());
            //所属报运商品的id
            exportProductVo.setExportProductId(exportProduct.getId());
            //报运的商品添加到报运单的Vo
            exportVo.getProducts().add(exportProductVo);
        }

        //4. 调用海关平台提交报运单的方法
        WebClient.create("http://localhost:9090/ws/export/user").post(exportVo);

        //5. 查看报运单审核的状态
        ExportResult exportResult = WebClient.create("http://localhost:9090/ws/export/user/" + export.getId()).get(ExportResult.class);

        //6. 更新本地的报运单的信息（状态，商品税）
        exportService.updateExport(exportResult);

        return "redirect:/cargo/export/list.do";

    }



    @Autowired
    private DataSource dataSource;

    /*
   url:/cargo/export/exportPdf.do?id=5a3680c7-c3eb-4e22-a8f1-f733fb4f1a9e
   作用： 下载电子报运单pdf版本
   参数： 报运单的id
   返回值： 下载
    */
    @RequestMapping("/exportPdf")
    @ResponseBody
    public  void exportPdf(String id) throws Exception {
        //通知浏览器以文件下载的方式处理内容
        response.setHeader("content-disposition","attachment;filename=export.pdf");

        //1. 找到模板的输入流
        InputStream inputStream = session.getServletContext().getResourceAsStream("/jasper/orderTicket.jasper");

        //根据报运单id找到报运单
        Export export = exportService.findById(id);
        //需要把export的对象转换为Map， key：属性名  value：属性值
        Map<String, Object> map =BeanMapUtils.beanToMap(export);


        //找到报运单的商品数据
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(exportProductExample);
        //把list的数据放入数据源中
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, dataSource);

        //3. 把pdf输出
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }

    @RequestMapping("/createPacking")
    @ResponseBody
    public String createPacking(String[] ids){
        //1.判断选中的合同是否已报运
        if (exportService.isExported(ids)) {
            //2.判断多个已报运合同的装运港(shipmentPort)、目的港(destinationPort)、收货人(consignee)是否一致
            if (exportService.isSameSDC(ids)) {
                return "2";
            }else {
                return "1";
            }
        }
        return "0";
    }

}

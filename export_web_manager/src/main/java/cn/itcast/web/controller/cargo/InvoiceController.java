package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.*;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.FileUploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 发票管理控制器
 * @author lwj
 */
@Controller
@RequestMapping("/cargo/invoice")
public class InvoiceController extends BaseController {

    @Reference
    private InvoiceService invoiceService;

    @Reference
    private PackingService packingService;

    @Reference
    private ShippingService shippingService;

    @Reference
    private ExportService exportService;

    @Reference
    private ContractService contractService;

    @Reference
    private ExportProductService exportProductService;

    @Reference
    private ExtEproductService extEproductService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * 分页展示发票列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize){
        InvoiceExample invoiceExample = new InvoiceExample();
        invoiceExample.setOrderByClause("create_time desc");
        PageInfo<Invoice> pageInfo = invoiceService.findByPage(invoiceExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "/cargo/invoice/invoice-list";
    }

    /**
     * 进入添加发票页面
     * @param shippingId 委托单id
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(String shippingId) {
        /*
            需要带入的数据
                报运合同号:来自装箱单
                贸易条款:来自合同 装箱单-->报运单(多个)-->合同(多个)
                发票金额：报运金额(报运货物总价+报运附件总价)+装箱金额+委托金额
            考虑使用invoice对象封装带入到添加页面
         */
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(shippingId);

        //获取装箱单
        Packing packing = packingService.findById(shippingId);
        if (packing != null) {
            //获取并设置报运合同号
            invoice.setScNo(packing.getExportNos());
        }

        //获取报运单id数组
        String[] exportIds = packing.getExportIds().split(",");
        String tradeTerms = "";

        //报运金额
        long exportMoney = 0;
        //装箱金额
        long packingMoney = packing.getPackingMoney() == null ? 0 :packing.getPackingMoney();
        //委托金额,这里写死了
        long shippingMoney = 100;

        for (String exportId : exportIds) {
            //获取报运单
            Export export = exportService.findById(exportId);
            //获取合同id数组
            String[] contractIds = export.getContractIds().split(",");
            for (int i = 0; i < contractIds.length; i++) {
                //获取合同
                Contract contract = contractService.findById(contractIds[i]);
                if (i != contractIds.length - 1){
                    tradeTerms += contract.getTradeTerms() + ",";
                }else{
                    tradeTerms += contract.getTradeTerms();
                }
            }
            //获取报运货物
            List<ExportProduct> exportProductList = exportProductService.finByExportId(exportId);
            long totalProductMoney = 0;
            for (ExportProduct exportProduct : exportProductList) {
                int cnumber = exportProduct.getCnumber() == null ? 0 :exportProduct.getCnumber();
                double price = exportProduct.getPrice() == null ? 0 : exportProduct.getPrice();
                totalProductMoney += cnumber * price;
            }

            //获取报运附件
            List<ExtEproduct> extEproductList = extEproductService.findByExportId(exportId);
            long totalExtMoney = 0;
            for (ExtEproduct extEproduct : extEproductList) {
                Double amount = extEproduct.getAmount() == null ? 0 :extEproduct.getAmount();
                totalExtMoney += amount;
            }
            exportMoney = totalProductMoney + totalExtMoney;

        }
        //封装贸易条款
        invoice.setTradeTerms(tradeTerms);
        //封装发票金额
        invoice.setInvoiceMoney(exportMoney + packingMoney + shippingMoney);
        request.setAttribute("invoice",invoice);
        //跳转到添加页面
        return "/cargo/invoice/invoice-add";
    }

    @RequestMapping("/add")
    public String add(Invoice invoice, MultipartFile pickUpPhoto) {
        //补全数据
        invoice.setCreateBy(getLoginUser().getId());
        invoice.setCreateDept(getLoginUser().getDeptId());
        invoice.setCompanyId(getLoginCompanyId());
        invoice.setCompanyName(getLoginCompanyName());
        invoice.setCreateTime(new Date());
        invoice.setStatus(0);
        try {
            String upload = fileUploadUtil.upload(pickUpPhoto);
            invoice.setPickupPhoto("http://" + upload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //插入数据库
        invoiceService.save(invoice);
        //更改委托单状态为1
        Shipping shipping = shippingService.findById(invoice.getInvoiceId());
        shipping.setState(1);
        shippingService.update(shipping);
        return "redirect:/cargo/invoice/list.do";
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public String deleteBatch(String[] ids){
        for (String id : ids) {
            Invoice invoice = invoiceService.findById(id);
            if (invoice.getStatus() == 1){
                return "0";
            }
        }

        for (String id : ids) {
            invoiceService.delete(id);
        }
        return "1";
    }

    /**
     * 批量更改发票状态为已支付状态
     * @param idsStr
     * @return
     */
    @RequestMapping("/payBatch")
    public String payBatch(String idsStr){
        String[] invoiceIds = idsStr.split(",");
        for (String invoiceId : invoiceIds) {
            Invoice invoice = invoiceService.findById(invoiceId);
            invoice.setStatus(1);
            invoiceService.update(invoice);
        }
        return "redirect:/cargo/invoice/list.do";
    }

    /**
     * 进入查看页面
     * @param invoiceId
     * @return
     */
    @RequestMapping("toView")
    public String toView(String invoiceId){
        Invoice invoice = invoiceService.findById(invoiceId);
        request.setAttribute("invoice",invoice);
        return "/cargo/invoice/invoice-view";
    }

    /**
     * 获取发票状态
     * @param id
     * @return
     */
    @RequestMapping("/getInvoiceStatus")
    @ResponseBody
    public Integer getInvoiceStatus(String id){
        Invoice invoice = invoiceService.findById(id);
        return invoice.getStatus();
    }

}

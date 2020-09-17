package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.*;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 财务单管理控制器
 *
 * @author lwj
 */
@Controller
@RequestMapping("/cargo/finance")
public class FinanceController extends BaseController {

    @Reference
    private FinanceService financeService;

    @Reference
    private InvoiceService invoiceService;

    @Reference
    private PackingService packingService;


    @Reference
    private ExportService exportService;


    /**
     * 分页展示财务单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize) {
        FinanceExample financeExample = new FinanceExample();
        financeExample.setOrderByClause("create_time desc");
        PageInfo<Finance> pageInfo = financeService.findByPage(financeExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "/cargo/finance/finance-list";
    }

    /**
     * 进入添加财务单页面
     *
     * @param invoiceId 发票id
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(String invoiceId) {
        System.out.println("==================");
        System.out.println(invoiceId);

        /*
            发票金额  选择发票时候从发票中自动带过来
            发票时间  发票时间
            报运合同号 数据来源于装箱表
            装运港    数据来源于报运单表
            目的港    数据来源于报运单表
            收货人    数据来源于报运单表
         */
        Finance finance = new Finance();
        finance.setInvoiceId(invoiceId);
        //获取发票
        Invoice invoice = invoiceService.findById(invoiceId);
        //设置发票金额
        finance.setInvoiceMoney(invoice.getInvoiceMoney());
        //发票时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String invoiceTime = dateFormat.format(invoice.getInvoiceTime());
        request.setAttribute("invoiceTime", invoiceTime);
        //获取装箱单
        Packing packing = packingService.findById(invoiceId);
        //设置报运合同号
        finance.setExportNos(packing.getExportNos());
        //获取报运单
        String[] exportIds = packing.getExportIds().split(",");
        Export export = exportService.findById(exportIds[0]);
        //设置装运港，目的港，收货人
        finance.setShipmentPort(export.getShipmentPort());
        finance.setDestinationPort(export.getDestinationPort());
        finance.setConsignee(export.getConsignee());
        //放入请求域
        request.setAttribute("finance", finance);
        return "/cargo/finance/finance-add";
    }

    /**
     * 新增财务报运单
     *
     * @param finance
     * @return
     */
    @RequestMapping("/add")
    public String add(Finance finance) {
        //补全数据
        finance.setCreateBy(getLoginUser().getId());
        finance.setCreateDept(getLoginUser().getDeptId());
        finance.setCompanyId(getLoginCompanyId());
        finance.setCompanyName(getLoginCompanyName());
        finance.setCreateTime(new Date());
        finance.setFinanceId(UUID.randomUUID().toString());
        financeService.save(finance);
        return "redirect:/cargo/finance/list.do";
    }

    /**
     * 导出Excel
     * @param idsStr
     */
    @RequestMapping("/printExcel")
    @ResponseBody
    public void printExcel(String idsStr) throws IOException {

        //获取模板输入流
        InputStream inputStream = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTFINANCE.xlsx");

        //使用模板输入流创建工作簿
        Workbook workbook = new XSSFWorkbook(inputStream);
        //获取工作单
        Sheet sheet = workbook.getSheetAt(0);

        //获取第一行
        Row row = sheet.getRow(0);
        //获取单元格
        Cell cell = row.getCell(1);
        //设置内容
        cell.setCellValue("财务报运单");

        //提取内容行的每一格的样式存储到数组
        row = sheet.getRow(2);
        CellStyle[] cellStyles = new CellStyle[9];
        for (int i = 0; i < cellStyles.length; i++) {
            cell = row.getCell(i + 1);
            cellStyles[i] = cell.getCellStyle();
        }

        String[] ids = idsStr.split(",");
        FinanceExample financeExample = new FinanceExample();
        financeExample.createCriteria().andFinanceIdIn(Arrays.asList(ids));
        List<Finance> financeList = financeService.findAll(financeExample);
        int index = 2;
        if (financeList != null){
            for (Finance finance : financeList) {
                row = sheet.createRow(index++);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                //编号
                cell = row.createCell(1);
                if (finance.getFinanceId() != null) {
                    cell.setCellStyle(cellStyles[0]);
                    cell.setCellValue(finance.getFinanceId());
                }

                //制单时间
                cell = row.createCell(2);
                if (finance.getInputDate() != null) {
                    cell.setCellStyle(cellStyles[1]);
                    cell.setCellValue(dateFormat.format(finance.getInputDate()));
                }

                //发票号
                cell = row.createCell(3);
                if (finance.getInvoiceId() != null) {
                    cell.setCellStyle(cellStyles[2]);
                    cell.setCellValue(finance.getInvoiceId());
                }

                //发票金额
                cell = row.createCell(4);
                if (finance.getInvoiceMoney() != null) {
                    cell.setCellStyle(cellStyles[3]);
                    cell.setCellValue(finance.getInvoiceMoney());
                }

                //发票时间
                cell = row.createCell(5);
                if (finance.getInvoiceTime() != null) {
                    cell.setCellStyle(cellStyles[4]);
                    cell.setCellValue(dateFormat.format(finance.getInvoiceTime()));
                }

                //报运合同号
                cell = row.createCell(6);
                if (finance.getExportNos() != null) {
                    cell.setCellStyle(cellStyles[5]);
                    cell.setCellValue(finance.getExportNos());
                }

                //装运港
                cell = row.createCell(7);
                if (finance.getShipmentPort() != null) {
                    cell.setCellStyle(cellStyles[6]);
                    cell.setCellValue(finance.getShipmentPort());
                }

                //目的港
                cell = row.createCell(8);
                if (finance.getDestinationPort() != null) {
                    cell.setCellStyle(cellStyles[7]);
                    cell.setCellValue(finance.getDestinationPort());
                }

                //收货人
                cell = row.createCell(9);
                if (finance.getConsignee() != null) {
                    cell.setCellStyle(cellStyles[8]);
                    cell.setCellValue(finance.getConsignee());
                }

            }
        }

        //写出到浏览器，以附件形式下载
        response.setContentType("application/octet-stream;charset=utf-8");
        response.addHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("财务报运单.xlsx", "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();

    }


}

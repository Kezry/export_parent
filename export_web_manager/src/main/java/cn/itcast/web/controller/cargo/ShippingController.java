package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.Shipping;
import cn.itcast.domain.cargo.ShippingExample;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.service.cargo.ShippingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/cargo/shipping")
public class ShippingController extends BaseController {
    @Reference
    private PackingService packingService;

    @Reference
    private ShippingService shippingService;

    @Reference
    private ExportService exportService;

    /**
     * 显示委托列表
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize){
        ShippingExample shippingExample = new ShippingExample();
        shippingExample.setOrderByClause("create_time desc");
        PageInfo<Shipping> pageInfo = shippingService.findByPage(shippingExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "/cargo/shipping/shipping-list";
    }

    /**
     * 获取添加委托单页面
     * @param id
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(String id) throws Exception {
        //獲取包運單數據，回顯頁面
        Packing packing = packingService.findById(id);
        Export export = exportService.findById(packing.getExportIds());
        //储存到请求域中
        request.setAttribute("export",export);
        request.setAttribute("id",id);
        //跳转到添加页面
        return "/cargo/shipping/shipping-add";
    }

    /**
     * 获取装箱单状态
     * @param id
     * @return
     */
    @RequestMapping("/createPacking")
    @ResponseBody
    public Integer createPacking(String id){
        //根据装箱号id获取对象
        Packing packing = packingService.findById(id);
        Integer state = packing.getState();
        return state;
    }
    /**
     * 获取装箱单状态
     * @param id
     * @return
     */
    @RequestMapping("/createShipping")
    @ResponseBody
    public Integer createShipping(String id){
        //根据装箱号id获取对象
        Shipping shipping = shippingService.findById(id);
        Integer state = shipping.getState();
        return state;
    }

    /**
     * 获取委托单状态
     * @param id
     * @return
     */
    @RequestMapping("/getShippingState")
    @ResponseBody
    public Integer getShippingState(String id){
        Shipping shipping = shippingService.findById(id);
        return shipping.getState();
    }


    /**
     * 添加委托单
     * @param shipping
     * @return
     */
    @RequestMapping("/add")
    public String add(Shipping shipping){
        //补全信息
        shipping.setCreateBy(getLoginUser().getId());//创建人
        shipping.setCreateDept(getLoginUser().getDeptId());//创建人
        shipping.setCreateTime(new Date());//创建时间
        shipping.setCompanyId(getLoginCompanyId());
        shipping.setCompanyName(getLoginCompanyName());
        //调用业务层
        String id = request.getParameter("id");
        shippingService.save(shipping,id);
        //返回到列表页面
        return "redirect:/cargo/shipping/list.do";
    }

    /**
     * 委托单删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        shippingService.delete(id);
        return "redirect:/cargo/shipping/list.do";
    }
    /**
     * 进入导出excel表
     *
     * @return
     */
    @RequestMapping("/toExcel")
    public String toExcel() {
        return "/cargo/shipping/shipping-excel";
    }


    @RequestMapping("/printExcel")
    @ResponseBody
    public void printExcel(String createTime) throws Exception {
        //获取模板输入流
        InputStream inputStream = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTSHIPPING.xlsx");

        //使用模板输入流创建工作簿
        Workbook workbook = new XSSFWorkbook(inputStream);
        //获取工作单
        Sheet sheet = workbook.getSheetAt(0);

        //获取第一行
        Row row = sheet.getRow(0);
        //获取单元格
        Cell cell = row.getCell(1);
        //设置内容
        cell.setCellValue(createTime.replaceAll("-0", "年").replaceAll("-", "年") + "月份委托单");

        //提取内容行的每一格的样式存储到数组
        row = sheet.getRow(2);
        CellStyle[] cellStyles = new CellStyle[19];
        for (int i = 0; i < cellStyles.length; i++) {
            cell = row.getCell(i + 1);
            cellStyles[i] = cell.getCellStyle();
        }

        //查询委托书数据
        List<Shipping> shippingList = shippingService.getByCreateTime(getLoginCompanyId(), createTime);
        int index = 2;
        if (shippingList != null) {
            for (Shipping shipping : shippingList) {
                row = sheet.createRow(index++);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                //运输方式
                cell = row.createCell(1);
                if (shipping.getOrderType() != null) {
                    cell.setCellStyle(cellStyles[0]);
                    cell.setCellValue(shipping.getOrderType());
                }

                //船号
                cell = row.createCell(2);
                if (shipping.getSeaNo() != null) {
                    cell.setCellStyle(cellStyles[1]);
                    cell.setCellValue(shipping.getSeaNo());
                }

                //飞机号
                cell = row.createCell(3);
                if (shipping.getAirNo() != null) {
                    cell.setCellStyle(cellStyles[2]);
                    cell.setCellValue(shipping.getAirNo());
                }

                //货主
                cell = row.createCell(4);
                if (shipping.getConsignee() != null) {
                    cell.setCellStyle(cellStyles[3]);
                    cell.setCellValue(shipping.getConsignee());
                }

                //提单抬头
                cell = row.createCell(5);
                if (shipping.getNotifyParty() != null) {
                    cell.setCellStyle(cellStyles[4]);
                    cell.setCellValue(shipping.getNotifyParty());
                }

                //信用证
                cell = row.createCell(6);
                if (shipping.getLcNo() != null) {
                    cell.setCellStyle(cellStyles[5]);
                    cell.setCellValue(shipping.getLcNo());
                }

                //效期
                cell = row.createCell(7);
                if (shipping.getLimitDate() != null) {
                    cell.setCellStyle(cellStyles[6]);
                    cell.setCellValue(dateFormat.format(shipping.getLimitDate()));
                }

                //唛头
                cell = row.createCell(8);
                if (shipping.getMarks() != null) {
                    cell.setCellStyle(cellStyles[7]);
                    cell.setCellValue(shipping.getMarks());
                }

                //装运港
                cell = row.createCell(9);
                if (shipping.getPortOfLoading() != null) {
                    cell.setCellStyle(cellStyles[8]);
                    cell.setCellValue(shipping.getPortOfLoading());
                }

                //是否转运
                cell = row.createCell(10);
                if (shipping.getIsTrans() != null) {
                    cell.setCellStyle(cellStyles[9]);
                    if (shipping.getIsTrans().equals("1")){
                        cell.setCellValue("是");
                    }else{
                        cell.setCellValue("否");
                    }
                }

                //转运港
                cell = row.createCell(11);
                if (shipping.getPortOfTrans() != null) {
                    cell.setCellStyle(cellStyles[10]);
                    cell.setCellValue(shipping.getPortOfTrans());
                }

                //卸货港
                cell = row.createCell(12);
                if (shipping.getPortOfDischar() != null) {
                    cell.setCellStyle(cellStyles[11]);
                    cell.setCellValue(shipping.getPortOfDischar());
                }

                //装运日期

                cell = row.createCell(13);
                if (shipping.getLoadingDate() != null) {
                    cell.setCellStyle(cellStyles[12]);
                    cell.setCellValue(dateFormat.format(shipping.getLoadingDate()));
                }

                //是否分批
                cell = row.createCell(14);
                if (shipping.getIsBatch() != null) {
                    cell.setCellStyle(cellStyles[13]);
                    if (shipping.getIsBatch().equals("1")){
                        cell.setCellValue("是");
                    }else{
                        cell.setCellValue("否");
                    }
                }

                //运输要求
                cell = row.createCell(15);
                if (shipping.getSpecialCondition() != null) {
                    cell.setCellStyle(cellStyles[14]);
                    cell.setCellValue(shipping.getSpecialCondition());
                }

                //运费说明
                cell = row.createCell(16);
                if (shipping.getFreight() != null) {
                    cell.setCellStyle(cellStyles[15]);
                    cell.setCellValue(shipping.getFreight());
                }

                //扼要说明
                cell = row.createCell(17);
                if (shipping.getRemark() != null) {
                    cell.setCellStyle(cellStyles[16]);
                    cell.setCellValue(shipping.getRemark());
                }

                //委托份数
                cell = row.createCell(18);
                if (shipping.getCopyNum() != null) {
                    cell.setCellStyle(cellStyles[17]);
                    cell.setCellValue(shipping.getCopyNum());
                }

                //复核人
                cell = row.createCell(19);
                if (shipping.getCheckBy() != null) {
                    cell.setCellStyle(cellStyles[18]);
                    cell.setCellValue(shipping.getCheckBy());
                }
            }
        }

        //写出到浏览器，以附件形式下载
        response.setContentType("application/octet-stream;charset=utf-8");
        response.addHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("委托单.xlsx", "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
    }


}

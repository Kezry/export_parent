package cn.itcast.web.controller.cargo;


import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.vo.ContractPrudctVo;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.DownloadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class OutProductController extends BaseController{


    @Reference
    private ContractProductService contractProductService;


    /*
      url:/cargo/contract/print.do
      作用： 进入出货表的页面
      参数： 无
     */
    @RequestMapping("/print")
    public  String print(){
        return "cargo/print/contract-print";
    }


    /*
    url:/cargo/contract/printExcel.do?inputDate=2015-01
    作用： 导出出货表
    参数： inputDate 出货月份
    springmvc无返回值有两种情况： 直接输出一个文件，下载
   */
   /* @RequestMapping("/printExcel")
    @ResponseBody  //下载的时候一定要添加ResponseBody
    public  void printExcel(String inputDate) throws IOException {
        //1. 读取到模板的输入流
        InputStream inputStream = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");

        //2. 模板的输入流交给工作簿
        Workbook workbook = new XSSFWorkbook(inputStream);

        //3. 获取工作单
        Sheet sheet = workbook.getSheetAt(0);

        //4. 获取第一行
        Row row = sheet.getRow(0);
        //inputDate=2015-01   2015-12
        String title = inputDate.replaceAll("-0","年").replaceAll("-","年")+"月份出货表";
        Cell cell = row.getCell(1);
        //给单元格设置内容
        cell.setCellValue(title);


        //由于数据都需要使用第三行的样式，所以这里我们可以在设置数据之前提取样式存储到数组中.
        row = sheet.getRow(2);
        CellStyle[] cellStyles = new CellStyle[8]; //存储样式的数组
        for (int i = 0; i < cellStyles.length; i++) {
            cell  = row.getCell(i+1);
            cellStyles[i] = cell.getCellStyle();
        }


        // 数据
        List<ContractPrudctVo> contractPrudctVoList = contractProductService.findByShipTime(getLoginCompanyId(), inputDate);
        int index = 2; //开始写数据的行
        for (int i = 0; i < 5800; i++) {
            for (ContractPrudctVo contractPrudctVo : contractPrudctVoList) {
                row = sheet.createRow(index++);

                //客户信息
                cell = row.createCell(1);
                //设置样式
                cell.setCellStyle(cellStyles[0]);
                if(contractPrudctVo.getCustomName()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getCustomName());
                }

                //订单
                cell = row.createCell(2);
                //设置样式
                cell.setCellStyle(cellStyles[1]);
                if(contractPrudctVo.getContractNo()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getContractNo());
                }


                //货号
                cell = row.createCell(3);
                //设置样式
                cell.setCellStyle(cellStyles[2]);
                if(contractPrudctVo.getProductNo()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getProductNo());
                }

                //数量
                cell = row.createCell(4);
                //设置样式
                cell.setCellStyle(cellStyles[3]);
                if(contractPrudctVo.getCnumber()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getCnumber());
                }


                //工厂
                cell = row.createCell(5);
                //设置样式
                cell.setCellStyle(cellStyles[4]);
                if(contractPrudctVo.getFactoryName()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getFactoryName());
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                //交货日期
                cell = row.createCell(6);
                //设置样式
                cell.setCellStyle(cellStyles[5]);
                if(contractPrudctVo.getDeliveryPeriod()!=null){
                    //设置数据
                    cell.setCellValue(dateFormat.format(contractPrudctVo.getDeliveryPeriod()));
                }

                //船期
                cell = row.createCell(7);
                //设置样式
                cell.setCellStyle(cellStyles[6]);
                if(contractPrudctVo.getShipTime()!=null){
                    //设置数据
                    cell.setCellValue(dateFormat.format(contractPrudctVo.getShipTime()));
                }



                //贸易协议
                cell = row.createCell(8);
                //设置样式
                cell.setCellStyle(cellStyles[7]);
                if(contractPrudctVo.getTradeTerms()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getTradeTerms());
                }

            }
        }
        String filename ="出货表.xlsx";
        //对中文文件名进行url编码
      *//*  filename =   URLEncoder.encode(filename,"utf-8");
        //通知浏览器下载使用哪个代码？
        response.setHeader("content-disposition","attachement;filename="+filename);
        //
        workbook.write(response.getOutputStream());*//*

        //输出字节流， 该类的本质就是内部维护了一个字节数组而已。
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        //把workbook的数据写入到数组中
        workbook.write(byteOutputStream);

        DownloadUtil.download(byteOutputStream,response,filename);

    }*/


    /*
    url:/cargo/contract/printExcel.do?inputDate=2015-01
    作用： 导出出货表
    参数： inputDate 出货月份
    springmvc无返回值有两种情况： 直接输出一个文件，下载
   */
    @RequestMapping("/printExcel")
    @ResponseBody  //下载的时候一定要添加ResponseBody
    public  void printExcel(String inputDate) throws IOException {
        //1. 创建工作薄
        Workbook workbook = new SXSSFWorkbook();
        //2. 创建工作单
        Sheet sheet = workbook.createSheet("出货表");
        //3. 合并单元格

        /*    CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
                firstRow： 开始行
                lastRow: 结束行
                firstCol: 开始列
                lastCol: 结束列
        */
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
        //设置单元格的宽度
        sheet.setColumnWidth(1,26*256);
        sheet.setColumnWidth(2,12*256);
        sheet.setColumnWidth(3,26*256);
        sheet.setColumnWidth(4,12*256);
        sheet.setColumnWidth(5,12*256);
        sheet.setColumnWidth(6,12*256);
        sheet.setColumnWidth(7,12*256);
        sheet.setColumnWidth(8,12*256);

        //创建第一行
        Row row = sheet.createRow(0);
        //inputDate=2015-01   2015-12
        String title = inputDate.replaceAll("-0","年").replaceAll("-","年")+"月份出货表";
        Cell cell = row.createCell(1);
        //给单元格设置样式
        cell.setCellStyle(bigTitle(workbook));
        //给单元格设置内容
        cell.setCellValue(title);


        //第二行
        String[] titles = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        row = sheet.createRow(1);
        for (int i = 0; i < titles.length; i++) {
            String headName = titles[i]; //表头的名字
            cell = row.createCell(i+1);
            //设置单元格的样式
            cell.setCellStyle(title(workbook));
            //设置单个元内容
            cell.setCellValue(headName);
        }

        // 数据
        List<ContractPrudctVo> contractPrudctVoList = contractProductService.findByShipTime(getLoginCompanyId(), inputDate);
        int index = 2; //开始写数据的行
        for (int i = 0; i <5800 ; i++) {
            for (ContractPrudctVo contractPrudctVo : contractPrudctVoList) {
                row = sheet.createRow(index++);

                //客户信息
                cell = row.createCell(1);
                if(contractPrudctVo.getCustomName()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getCustomName());
                }

                //订单
                cell = row.createCell(2);
                if(contractPrudctVo.getContractNo()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getContractNo());
                }


                //货号
                cell = row.createCell(3);
                if(contractPrudctVo.getProductNo()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getProductNo());
                }

                //数量
                cell = row.createCell(4);
                if(contractPrudctVo.getCnumber()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getCnumber());
                }


                //工厂
                cell = row.createCell(5);
                if(contractPrudctVo.getFactoryName()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getFactoryName());
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                //交货日期
                cell = row.createCell(6);
                if(contractPrudctVo.getDeliveryPeriod()!=null){
                    //设置数据
                    cell.setCellValue(dateFormat.format(contractPrudctVo.getDeliveryPeriod()));
                }

                //船期
                cell = row.createCell(7);
                if(contractPrudctVo.getShipTime()!=null){
                    //设置数据
                    cell.setCellValue(dateFormat.format(contractPrudctVo.getShipTime()));
                }



                //贸易协议
                cell = row.createCell(8);
                if(contractPrudctVo.getTradeTerms()!=null){
                    //设置数据
                    cell.setCellValue(contractPrudctVo.getTradeTerms());
                }

            }
        }
      String filename ="出货表.xlsx";
        //对中文文件名进行url编码
        /*  filename =   URLEncoder.encode(filename,"utf-8");
        //通知浏览器下载使用哪个代码？
        response.setHeader("content-disposition","attachement;filename="+filename);
        //
        workbook.write(response.getOutputStream());*/

      //输出字节流， 该类的本质就是内部维护了一个字节数组而已。
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        //把workbook的数据写入到数组中
        workbook.write(byteOutputStream);

        DownloadUtil.download(byteOutputStream,response,filename);

    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}

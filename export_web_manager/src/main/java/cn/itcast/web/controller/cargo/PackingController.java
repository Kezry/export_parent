package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 装箱管理控制器
 *
 * @author lwj
 */
@Controller
@RequestMapping("/cargo/packing")
public class PackingController extends BaseController {

    @Reference
    private PackingService packingService;

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    /**
     * 展示装箱单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize) {
        PackingExample packingExample = new PackingExample();
        packingExample.setOrderByClause("create_time desc");
        PageInfo<Packing> pageInfo = packingService.findByPage(packingExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "/cargo/packing/packing-list";
    }

    /**
     * 添加装箱单
     * @param packing
     * @return
     */
    @RequestMapping("/add")
    public String add(Packing packing) {
        packing.setCreateBy(getLoginUser().getId());
        packing.setCreateDept(getLoginUser().getDeptId());
        packing.setCompanyId(getLoginCompanyId());
        packing.setCompanyName(getLoginCompanyName());
        packingService.save(packing);
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 进入生成装箱单错误页面
     *
     * @return
     */
    @RequestMapping("/toError")
    public String toError() {
        return "/cargo/packing/packing-error";
    }

    /**
     * 进入添加装箱单页面
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(String ids) {

        //报运单id(可能多个)
        request.setAttribute("exportIds", ids);

        Packing packing = new Packing();
        String[] exportIds = ids.split(",");
        ExportExample exportExample = new ExportExample();
        exportExample.createCriteria().andIdIn(Arrays.asList(exportIds));
        List<Export> exports = exportService.findAll(exportExample);
        String exportNos = "";
        if (exports != null) {
            for (int i = 0; i < exports.size(); i++) {
                if (i == 0) {
                    exportNos += exports.get(i).getCustomerContract();
                } else {
                    exportNos += " " + exports.get(i).getCustomerContract();
                }
            }
            packing.setExportNos(exportNos.trim());
        }

        //计算总体积, 总净重,总毛重,装箱费用
        long totalVolume = 0;
        long netWeights = 0;
        long grossWeights = 0;
        //查询出所有报运单的所有货物
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdIn(Arrays.asList(exportIds));
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        if (exportProductList != null) {
            //遍历货物
            for (ExportProduct exportProduct : exportProductList) {
                Double length = exportProduct.getSizeLength() == null ? 0 : exportProduct.getSizeLength();
                Double width = exportProduct.getSizeWidth() == null ? 0 : exportProduct.getSizeWidth();
                Double height = exportProduct.getSizeHeight() == null ? 0 : exportProduct.getSizeHeight();
                totalVolume += length * width * height;
                Double netWeight = exportProduct.getNetWeight() == null ? 0 : exportProduct.getNetWeight();
                netWeights += netWeight;
                Double grossWeight = exportProduct.getGrossWeight() == null ? 0 : exportProduct.getGrossWeight();
                grossWeights += grossWeight;
            }
            packing.setTotalVolume(totalVolume);
            packing.setNetWeights(netWeights);
            packing.setGrossWeights(grossWeights);
            packing.setPackingMoney(grossWeights * 520);
        }
        request.setAttribute("packing",packing);
        return "/cargo/packing/packing-add";
    }

    /**
     * 批量删除装箱单
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public String deleteBatch(String[] ids){
        PackingExample packingExample = new PackingExample();
        packingExample.createCriteria().andPackingListIdIn(Arrays.asList(ids));
        List<Packing> packings = packingService.findAll(packingExample);
        for (Packing packing : packings) {
            //存在已委托的装箱单直接结束并返回0
            if (packing.getState() == 1) {
                return "0";
            }
        }
        //删除装箱单
        for (String id : ids) {
            packingService.delete(id);
        }
        //更新报运单状态为2
        for (Packing packing : packings) {
            //每个装箱单可能有多个报运单
            String[] exportIds = packing.getExportIds().split(",");
            ExportExample exportExample = new ExportExample();
            exportExample.createCriteria().andIdIn(Arrays.asList(exportIds));
            List<Export> exports = exportService.findAll(exportExample);
            for (Export export : exports) {
                export.setState(2);
                exportService.update(export);
            }
        }
        return "1";
    }


}

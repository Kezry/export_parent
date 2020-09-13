package cn.itcast.web.controller.stat;

import cn.itcast.domain.system.Dept;
import cn.itcast.service.stat.StatService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @Reference
    private StatService statService;

    /*
        url:/stat/toCharts.do?chartsType=factory
        作用： 进入报表页面
        参数： chartsType 报表页面的类型
       */
    @RequestMapping("/toCharts")
    public  String toCharts(String chartsType){
        return "/stat/stat-"+chartsType;
    }



    /*
      url:/stat/getFactoryData.do
      作用： 返回展示厂家销售数据的json数据

     */
    @RequestMapping("/getFactoryData")
    @ResponseBody
    public  List<Map<String,Object>> getFactoryData(){

       return  statService.getFactoryData(getLoginCompanyId());
    }


    /*
   url:/stat/getSellData.do
   作用： 返回展示产品销售前五名数据的json数据

  */
    @RequestMapping("/getSellData")
    @ResponseBody
    public  List<Map<String,Object>> getSellData(){

        return  statService.getSellData(getLoginCompanyId());
    }


    /*
        url:/stat/getOnlineData.do
        作用： 系统压力图
    */
    @RequestMapping("/getOnlineData")
    @ResponseBody
    public  List<Map<String,Object>> getOnlineData(){

        return  statService.getOnlineData();
    }
}

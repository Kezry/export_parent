package cn.itcast.web.controller.stat;

import cn.itcast.domain.system.Dept;
import cn.itcast.service.stat.StatService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    /**
     * 按所在地统计指定生产类型的厂家数量
     *
     * @return
     */
    @RequestMapping("/getAddressData")
    @ResponseBody
    public List<List<Object>> getAddressData() {
        /*
            数据格式--> [ {name:广州,value:19}...]
         */
        List<Map<String, Object>> productList = statService.getAddressData("货物");
        List<Map<String, Object>> extList = statService.getAddressData("附件");

        List<String> addressList = statService.getAllAddress();//所有厂家所在地地址集

        // 返回的结果集List<List<Object>>>
        List<List<Object>> lists = new ArrayList<>();
        //第一组数据
        List<Object> first = new ArrayList<>();
        first.add("product");
        first.add("货物");
        first.add("附件");
        lists.add(first);

        for (String address : addressList) {
            List list = new ArrayList();
            list.add(address);
            for (int i = 0; i < productList.size(); i++){
                if (productList.get(i).get("name").equals(address)) {
                    list.add(productList.get(i).get("value"));
                    break;
                }
                if (i == productList.size() - 1){
                    list.add(0);
                }
            }

            for (int j = 0; j < extList.size(); j++){
                if (extList.get(j).get("name").equals(address)) {
                    list.add(extList.get(j).get("value"));
                    break;
                }
                if (j == extList.size() - 1){
                    list.add(0);
                }
            }
            lists.add(list);
        }

        return lists;
    }
}

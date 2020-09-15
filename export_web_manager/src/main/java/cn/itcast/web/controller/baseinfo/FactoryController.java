package cn.itcast.web.controller.baseinfo;

import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 厂家信息控制器
 * @author lwj
 */
@Controller
@RequestMapping("/baseinfo/factory")
public class FactoryController extends BaseController {

    @Reference
    private FactoryService factoryService;

    /**
     * 进入选择页面
     * @return
     */
    @RequestMapping("/toChoice")
    public String toChoice(){
        return "baseinfo/factory/factory-choice";
    }

    /**
     * 分页展示工厂列表
     * @param ctype 工厂生产类型
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return
     */
    @RequestMapping("/list")
    public String list(String ctype,@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "5") int pageSize) {
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.setOrderByClause("create_time desc");
        switch (ctype){
            case "货物":
                factoryExample.createCriteria().andCtypeEqualTo("货物");
                break;
            case "附件":
                factoryExample.createCriteria().andCtypeEqualTo("附件");
                break;
        }
        PageInfo<Factory> pageInfo = factoryService.findByPage(factoryExample,pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("ctype",ctype);
        return "/baseinfo/factory/factory-list";
    }

    /**
     * 进入厂家添加页面
     * @param ctype
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(String ctype){
        request.setAttribute("ctype",ctype);
        return "/baseinfo/factory/factory-add";
    }

    /**
     * 添加或更新工厂信息
     * @param factory
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Factory factory) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(factory.getId())) {
            //添加
            factory.setCreateBy(getLoginUser().getId());
            factory.setCreateDept(getLoginUser().getDeptId());
            factoryService.save(factory);
        }else{
            //更新
            factory.setUpdateBy(getLoginUser().getId());
            factoryService.update(factory);
        }
        return "redirect:/baseinfo/factory/list.do?ctype=" + URLEncoder.encode(factory.getCtype(),"utf-8");
    }

    /**
     * 进入更新页面(先查后改)
     * @param id 工厂id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Factory factory = factoryService.findById(id);
        request.setAttribute("factory",factory);
        request.setAttribute("ctype",factory.getCtype());
        return "/baseinfo/factory/factory-add";
    }

    /**
     * 删除厂家
     * @param id
     * @param ctype
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/delete")
    public String delete(String id,String ctype) throws UnsupportedEncodingException {
        factoryService.delete(id);
        return "redirect:/baseinfo/factory/list.do?ctype=" + URLEncoder.encode(ctype,"utf-8");
    }

}

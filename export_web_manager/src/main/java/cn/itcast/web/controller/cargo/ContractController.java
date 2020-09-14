package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.company.Company;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {


    @Reference
    private ContractService contractService;

    /*
     url:  /cargo/contract/list.do
     参数： 无
     作用： 进入购销合同的列表页面
     返回值: 购销合同的列表页面

     */

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {
        ContractExample contractExample = new ContractExample();
        //在逆向工程中如果需要根据时间排序 ， 不是条件所以不需要创建Criteria对象
        contractExample.setOrderByClause("create_time desc");
        ContractExample.Criteria criteria = contractExample.createCriteria();

        //货物当前登陆者
        User loginUser = getLoginUser();
        //得到登陆者的等级
        Integer degree = loginUser.getDegree();
        if (degree == 4) {
            //普通员工
            criteria.andCreateByEqualTo(loginUser.getId());

        } else if (degree == 3) {
            //部门经理
            criteria.andCreateDeptEqualTo(loginUser.getDeptId());
        } else if (degree == 2) {
            //大区经理
            PageInfo<Contract> pageInfo =
                    contractService.findPageDeptId(loginUser.getCompanyId(), loginUser.getDeptId(), pageNum, pageSize);
            request.setAttribute("pageInfo", pageInfo);
            return "cargo/contract/contract-list";
        } else if (degree == 1) {
            //系统管理员
            criteria.andCompanyIdEqualTo(loginUser.getCompanyId());
        }

        PageInfo<Contract> pageInfo = contractService.findByPage(contractExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/contract/contract-list";
    }


    /*
    url:cargo/contract/contract-add
    作用： 进入添加页面
    参数： 无
   */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }


    /*
      url: cargo/contract/edit.do
      作用： 新增购销合同||更新购销合同
      参数： 购销合同
      返回值：购销合同列表页面
     */
    @RequestMapping("/edit")
    public String edit(Contract contract) {
        //补全购销合同信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        contract.setCompanyId(companyId);
        contract.setCompanyName(companyName);
        //补全购销创建人与创建人所属的购销合同
        contract.setCreateBy(getLoginUser().getId()); //登陆者的id
        contract.setCreateDept(getLoginUser().getDeptId()); //登陆者所属的购销合同

        if (StringUtils.isEmpty(contract.getId())) {
            contractService.save(contract);
        } else {
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list.do";  //添加redirect开头的返回值则不会经过视图解析器
    }


    /*
    url: /system/Contract/toUpdate.do?id=100
    作用： 进入更新购销合同页面
    参数： 购销合同的id
    返回值：购销合同更新页面
   */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.根据id查询当前购销合同信息
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-update";
    }


    /*
    url: /system/Contract//delete.do?id="+id,
    作用： 删除购销合同
    参数： 购销合同的id
    返回值：购销合同列表页面
   */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String id) {
        Map<String, Object> map = new HashMap<>();
        //根据ID查询购销合同
        Contract contract = contractService.findById(id);
        //判断购销合同的状态
        if (contract.getState() != 0) {
            map.put("boolean",false);
        } else {
            //根据ID删除购销合同
            contractService.delete(id);
            //根据ID删除附件
            contractService.deleteCargo(id);
            //根据ID删除货物
            contractService.deleteAccessory(id);
            map.put("boolean",true);
        }
        return map;
    }


    /*
      url: /cargo/contract/toView.do?id=4bc7ae89-f02c-4cb0-b74b-ef0e4170adb2
      作用： 查看购销合同
      参数： 购销合同的id
      返回值：购销合同的查看页面
 */
    @RequestMapping("/toView")
    public String toView(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-view";
    }


    /*
     url: /cargo/contract/submit.do?id=4bc7ae89-f02c-4cb0-b74b-ef0e4170adb2
     作用： 提交购销合同
     参数： 购销合同的id
     返回值：购销合同列表
*/
    @RequestMapping("/submit")
    public String submit(String id) {
        Contract contract = contractService.findById(id);
        contract.setState(1);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }


    /*
     url: /cargo/contract/cancel.do?id=4bc7ae89-f02c-4cb0-b74b-ef0e4170adb2
     作用： 取消交购销合同
     参数： 购销合同的id
     返回值：购销合同列表
*/
    @RequestMapping("/cancel")
    public String cancel(String id) {
        Contract contract = contractService.findById(id);
        contract.setState(0);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }
}

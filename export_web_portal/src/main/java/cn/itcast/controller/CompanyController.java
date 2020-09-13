package cn.itcast.controller;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CompanyController {

    @Reference
    private CompanyService companyService;


    @RequestMapping("/apply")
    @ResponseBody
    public String apply(Company company){
        try {
            //企业在刚刚申请的时候状态为0
            company.setState(0);
            //保存该企业
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();

            return "0";//表示失败
        }

    }
}

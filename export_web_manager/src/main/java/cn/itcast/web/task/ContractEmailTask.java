package cn.itcast.web.task;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service  //错误:没有加注解
public class ContractEmailTask {

    @Reference
    private ContractService contractService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmail() {
        System.out.println("sendEmail()方法执行");

        /*1.获取3天后到期的购销合同*/
        List<Contract> contracts = contractService.findContractAfter3Days();

        /*2.遍历购销合同列表,获取购销合同状态和创建者信息*/
        if (contracts != null) {

            for (Contract contract : contracts) {
                /*3.将state对象放入消息队列*/  //刘:不是email_queue
                System.out.println(contract+"======================");
                rabbitTemplate.convertAndSend("email_exchange", "contract.email.find", contract);

            }

        }

    }

}

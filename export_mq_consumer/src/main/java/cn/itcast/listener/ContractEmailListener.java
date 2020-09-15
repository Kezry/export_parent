package cn.itcast.listener;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import cn.itcast.service.system.impl.UserServiceImpl;
import cn.itcast.utils.MailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;


public class ContractEmailListener implements MessageListener {

    private ObjectMapper mapper = new ObjectMapper();




    @Override
    public void onMessage(Message message) {
        UserService userService = new UserServiceImpl();

        /*1.获取消息内容*/
        byte[] body = message.getBody();


        System.out.println("监听器监听到的内容:"+ body.toString());
        try {

        /*2.将消息转换成list对象*/
            Contract contract = mapper.readValue(body, Contract.class);


            Integer state = contract.getState();
            System.out.println(contract.getCreateBy());
            User createUser =new User();
            createUser = userService.findById(contract.getCreateBy());

            /*3.获取创建用户邮箱*/
            String email = createUser.getEmail();
//            String email = "1784756780@qq.com";

            /*4.根据合同状态确定邮件发送内容*/

                if (state == 1) {
                    MailUtil.sendMsg(email, "交期提醒@未报运", "只有3天就到交货日期了哦,您还未报运,请及时确认购销合同!");
                    System.out.println("发送邮件成功...");
                }else if(state == 2){
                    MailUtil.sendMsg(email, "交期提醒@已报运", "只有3天就到交货日期了哦,请及时处理!");
                    System.out.println("发送邮件成功...");
                }else if(state ==0){
                    MailUtil.sendMsg(email, "交期提醒@草稿", "只有3天就到交货日期了哦,请及时确认是否为有效合同");
                    System.out.println("发送邮件成功...");
                }
            } catch (Exception e) {
                e.printStackTrace();

        }
    }
}

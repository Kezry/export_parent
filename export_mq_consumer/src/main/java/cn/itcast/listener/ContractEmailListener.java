package cn.itcast.listener;

import cn.itcast.utils.MailUtil;
import cn.itcast.vo.ContractUserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;


public class ContractEmailListener implements MessageListener {

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public void onMessage(Message message) {

        /*1.获取消息内容*/
        byte[] body = message.getBody();


        System.out.println("监听器监听到的内容:"+ body.toString());
        try {

        /*2.将消息转换成ContractUserVo对象*/
            ContractUserVo contract = mapper.readValue(body, ContractUserVo.class);

            /*3.获取购销合同状态*/
            Integer state = contract.getState();

            /*4.获取创建用户邮箱*/
            String email = contract.getEmail();

           /* String email = "1784756780@qq.com";*/

            /*5.根据合同状态确定邮件发送内容*/

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

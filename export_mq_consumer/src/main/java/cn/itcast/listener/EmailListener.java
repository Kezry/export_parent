package cn.itcast.listener;

import cn.itcast.domain.system.User;
import cn.itcast.utils.MailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

public class EmailListener implements MessageListener {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        //1. 得到消息
        byte[] body = message.getBody();

        //2. 把消息转换为User对象
        try {
            User user = objectMapper.readValue(body, User.class);
            //3. 发送邮件
            MailUtil.sendMsg(user.getEmail(),"欢迎您进入传智播客","这里没有996，只有007");
            System.out.println("发送邮件成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

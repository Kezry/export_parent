package cn.itcast.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CompanyProvider {

    public static void main(String[] args) throws IOException {
        //注意： service在启动的时候由于service是依赖了dao的，所以一定要把dao的文件一并去加载
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        context.start();
        System.in.read();
    }
}

package cn.itcast.test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//注意： 加载配置文件的时候，一定要加载dao包的配置文件，因为你的service是依赖dao。
//注意： 如果需要加载多个文件，我们需要使用classpath*:
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class CompanyServiceTest {

}

package cn.itcast.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*

  定义日期类型转换器的步骤：
          1. 自定义一个类实现Converter接口
          2. 实现接口，把字符串转换为Date类型
          3. 创建自定义类型转换器的对象
          4. 把自定义类型转换器交给转换器工厂
          5. 再把类型转换器工厂交给注解驱动去启动*/
@Component   //该类对象在spring中中的id= stringToDateConverter
public class StringToDateConverter implements Converter<String,Date> {
    @Override
    public Date convert(String source) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

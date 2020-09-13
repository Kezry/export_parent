package cn.itcast.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;

//我的任务类，我的任务，输出当前系统时间
public class MyTask {

    public void showTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前系统时间："+ dateFormat.format(new Date()));
    }

}

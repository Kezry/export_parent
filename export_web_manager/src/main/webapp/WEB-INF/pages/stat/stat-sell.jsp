<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            统计分析
            <small>厂家销量统计</small>
        </h1>
    </section>
    <section class="content">
        <div class="box box-primary">
            <div id="main" style="width: 1000px;height:400px;"></div>
        </div>
    </section>
</div>
</body>

<script src="../plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="../../plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
    $(function () {
        //发出异步请求
        $.ajax({
            url:"/stat/getSellData.do",
            type:"get",
            dataType:"json",
            success:function(resultData){
                //数据成功返回之后，展示报表
                // 第三: 找到div标签
                var myChart = echarts.init(document.getElementById('main'));

                //遍历json对象，取出一个json对象，然后把json对象的name与value分离存储到两个不同的数组中
                var keys = [];  //用于存储所有的key
                var values = []; //用于存储所有的value

                for(var i =0 ; i<resultData.length ; i++){
                    var obj = resultData[i];
                    keys[i] = obj.name;
                    values[i] = obj.value;
                }

                option = {
                    xAxis: {
                        type: 'category',
                        data: keys
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: values,
                        type: 'bar'
                    }]
                };




                //把报表的数据与div整合形成一个报表
                myChart.setOption(option);
            }
        });
    });
</script>

</html>
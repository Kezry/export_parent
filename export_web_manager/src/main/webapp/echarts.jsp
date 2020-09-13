<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--第一步： 引入js--%>
    <script src="/plugins/echarts/echarts.min.js"></script>
</head>
<body>

<%--第二步：准备一个区域展示报表--%>
<div id="main" style="width: 600px;height:400px;"></div>

<script>
    // 第三: 找到div标签
    var myChart = echarts.init(document.getElementById('main'));
    // 第四：定义报表数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };


    //把报表的数据与div整合形成一个报表
    myChart.setOption(option);
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lwj
  Date: 2020/9/13
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--自定义弹框-->
<html lang="zh">
<head>
    <link rel="stylesheet" type="text/css" href="/css/mbox.css"/>  <!--背景样式 弹框样式-->
    <script type="text/javascript" src="/plugins/jQuery/jquery-2.2.3.min.js" ></script><!--jquery库-->
    <script type="text/javascript" src="/js/jm-qi.js" ></script><!--自定义弹框大小，提示信息，样式，icon。。。。-->

</head>
<body>
<script>
    $(function () {
        choice();
    });
    function choice(){
        $.mbox({
            area: [ "600px", "auto" ], //弹框大小
            border: [ 0, .5, "#666" ],
            dialog: {
                msg: "请选择要查看的厂家的生产类型",
                btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
                type: 2,   //1:对钩   2：问号  3：叹号
                btn: [ "货物", "附件"],  //自定义按钮
                yes: function() {  //点击左侧按钮：成功
                    location.href="/baseinfo/factory/list.do?ctype=货物";
                },
                no: function() { //点击右侧按钮：失败
                    location.href="/baseinfo/factory/list.do?ctype=附件";
                }
            }
        });
    }
</script>

</body>
</html>

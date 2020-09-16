<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../base.jsp"%>

<head>
    <style>
        .award{width:100%;height:700px;background-color: #a5cefd;}
    </style>

    <script type="text/javascript" src="${ctx}/js/index/indexditu.js"></script>
    <script>
        document.getElementsByTagName("html")[0].style.fontSize=window.screen.width/10+"px";
        document.addEventListener('touchstart',function(){},false);
    </script>

</head>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            欢迎来到进出口贸易平台
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="#">全球货物动态</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">全球货物动态</h3>
            </div>

            <body2>

            <section class="award">
                <div id="container" style="width: 100%;height:100%;float: left;" >
                </div>
            </section>

            <!--百度地图API-->
            <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
            <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
            <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
            <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
            <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=qWmCfqDAdPBzy3YoHxGnimKT"></script>
            <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
                <script type="text/javascript" src="${ctx}/js/index/indexditu.js"></script>
            </body2>
        </div>
    </section>
    <!-- 正文区域 /-->
</div>

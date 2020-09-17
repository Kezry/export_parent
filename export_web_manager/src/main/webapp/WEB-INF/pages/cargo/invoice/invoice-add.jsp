<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../base.jsp"%>
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
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>发票管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">发票管理</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">发票信息</div>
            <form id="editForm" action="${ctx}/cargo/invoice/add.do" method="post" enctype="multipart/form-data">

                <input type="hidden" name="invoiceId" value="${invoice.invoiceId}">

                <div class="row data-type" style="margin: 0px">

                    <div class="col-md-2 title">发票时间</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="发票时间"  name="invoiceTime" class="form-control pull-right"
                                    id="invoiceTime">
                        </div>
                    </div>

                    <div class="col-md-2 title">报运合同号</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="scNo" value="${invoice.scNo}">
                        ${invoice.scNo}
                    </div>

                    <div class="col-md-2 title">贸易条款</div>
                    <div class="col-md-4 data">
                        <input type="hidden"  name="tradeTerms" value="${invoice.tradeTerms}">
                        ${invoice.tradeTerms}
                    </div>

                    <div class="col-md-2 title">发票金额</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="invoiceMoney" value="${invoice.invoiceMoney}">
                        ${invoice.invoiceMoney}
                    </div>

                    <div class="col-md-2 title">提单凭证(重要)</div>
                    <div class="col-md-4 data">
                        <input type="file" class="form-control" placeholder="选择文件" name="pickUpPhoto" >
                    </div>


                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#invoiceTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>
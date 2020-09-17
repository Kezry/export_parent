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
            <small>财务管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">财务管理</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">财务信息</div>
            <form id="editForm" action="${ctx}/cargo/finance/add.do" method="post" >

                <div class="row data-type" style="margin: 0px">

                    <div class="col-md-2 title">制单时间</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="制单时间"  name="inputDate" class="form-control pull-right"
                                   id="inputDate">
                        </div>
                    </div>

                    <div class="col-md-2 title">发票号</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="invoiceId" value="${finance.invoiceId}">
                        ${finance.invoiceId}
                    </div>

                    <div class="col-md-2 title">发票金额</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="invoiceMoney" value="${finance.invoiceMoney}">
                        ${finance.invoiceMoney}
                    </div>

                    <div class="col-md-2 title">发票时间</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="invoiceTime" value="${invoiceTime}">
                        ${invoiceTime}
                    </div>

                    <div class="col-md-2 title">报运合同号</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="exportNos" value="${finance.exportNos}">
                        ${finance.exportNos}
                    </div>

                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="shipmentPort" value="${finance.shipmentPort}">
                        ${finance.shipmentPort}
                    </div>

                    <div class="col-md-2 title">目的港</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="destinationPort" value="${finance.destinationPort}">
                        ${finance.destinationPort}
                    </div>

                    <div class="col-md-2 title">收货人</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="consignee" value="${finance.consignee}">
                        ${finance.consignee}
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
    $('#inputDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>
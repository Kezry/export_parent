<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp" %>
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
    <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<script>

    function printExcel(){
        let length = $("input[name=id]:checked").length;
        if (length == 0) {
            alert("请勾选要导出excel的财务报运单");
        } else {
            //获取选中的报运单id(可能有多个)
            let ids = new Array();
            $("input[name=id]:checked").each(function () {
                ids.push($(this).val());
            });
            let idsStr = ids.join(",");
            location.href = "/cargo/finance/printExcel.do?idsStr="+idsStr;
        }
    }

    $(function () {
        $("#checkAll").click(function () {
            $("input[name=id]").prop("checked", $(this).prop("checked"));
        });
    })

</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            货运管理
            <small>财务管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">财务管理列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick='window.location.reload()'><i
                                        class="fa  fa-eye-slash"></i> 刷新
                                </button>

                                <button type="button" class="btn btn-default" title="导出PDF" onclick='printExcel()'><i
                                        class="fa fa-file-o"></i> 导出Excel
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="box-tools pull-right">
                        <div class="has-feedback">
                            <input type="text" class="form-control input-sm" placeholder="搜索">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </div>
                    <!--工具栏/-->

                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;">
                                <input type="checkbox" id="checkAll"/>
                            </th>

                            <th class="sorting">财务单编号</th>
                            <th class="sorting">制单时间</th>
                            <th class="sorting">发票号</th>
                            <th class="sorting">发票金额</th>
                            <th class="sorting">发票时间</th>
                            <th class="sorting">报运合同号</th>
                            <th class="sorting">装运港</th>
                            <th class="sorting">目的港</th>
                            <th class="sorting">收货人</th>


                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="o" varStatus="status">
                            <tr>
                                <td><input type="checkbox" name="id" value="${o.financeId}"/></td>
                                <td>${o.financeId}</td>
                                <td><fmt:formatDate value="${o.inputDate}" pattern="yyyy-MM-dd"/></td>
                                <td>${o.invoiceId}</td>
                                <td>${o.invoiceMoney}</td>
                                <td><fmt:formatDate value="${o.invoiceTime}" pattern="yyyy-MM-dd"/></td>
                                <td>${o.exportNos}</td>
                                <td>${o.shipmentPort}</td>
                                <td>${o.destinationPort}</td>
                                <td>${o.consignee}</td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                    <!--数据列表/-->

                    <!--工具栏/-->

                </div>
                <!-- 数据表格 /-->


            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="/cargo/finance/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->


        </div>

    </section>
</div>
</body>

</html>
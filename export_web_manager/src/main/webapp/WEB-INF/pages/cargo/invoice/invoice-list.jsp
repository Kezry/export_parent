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
    function deleteBatch() {
        //判断是否选中
        let length = $("input[name=id]:checked").length;
        if (length == 0) {
            alert("请勾选要删除的发票");
        } else {
            //获取选中的发票id(可能有多个)
            let ids = new Array();
            $("input[name=id]:checked").each(function () {
                ids.push($(this).val());
            });
            //异步请求
            $.ajax({
                method: "get",
                url: "/cargo/invoice/deleteBatch.do",
                data: {"ids": ids},
                traditional: true,
                dataType: "text",
                success: function (result) {
                    /*
                        1 --> 删除成功
                        0 --> 存在已委托单
                     */
                    if (result == "1") {
                        alert("删除成功")
                        location.reload();
                    } else if (result == "0") {
                        alert("已经支付的发票不能删除!")
                    }
                }
            });
        }
    }

    function payBatch() {
        //判断是否选中
        let length = $("input[name=id]:checked").length;
        if (length == 0) {
            alert("请勾选要更改支付状态的发票");
        } else {
            if (confirm("你确定要执行此操作吗?")) {
                //获取选中的发票id(可能有多个)
                let ids = new Array();
                $("input[name=id]:checked").each(function () {
                    ids.push($(this).val());
                });
                let idsStr = ids.join(",");
                location.href = "/cargo/invoice/payBatch.do?idsStr=" + idsStr;
            }
        }
    }

    /*
        生成发票按钮点击事件函数
     */
    function createFinance() {
        var id = getCheckId();
        if (id) {
            $.ajax({
                method: "get",
                url: "/cargo/invoice/getInvoiceStatus.do?",
                data: {"id":id},
                dataType: "json",
                success: function (result) {
                    if (result == 1) {
                        //已支付状态,进入添加页面
                        location.href = "/cargo/finance/toAdd.do?invoiceId=" + id;
                    } else if (result == 0) {
                        //未支付状态，弹框提示
                        alert("该发票未支付")
                    }
                }
            });
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个");
        }
    }


    /*
        进入excel导出页面
     */
    function toExcel() {
        location.href = "/cargo/shipping/toExcel.do";
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
            <small>发票管理</small>
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
                <h3 class="box-title">发票管理列表</h3>
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
                                <button type="button" class="btn btn-default" title="支付"
                                        onclick='payBatch()'><i
                                        class="fa  fa-eye-slash"></i> 支付
                                </button>
                                <button type="button" class="btn btn-default" title="删除" onclick='deleteBatch()'><i
                                        class="fa fa-trash-o"></i> 删除
                                </button>
                                <button type="button" class="btn btn-default" title="导出PDF" onclick='toPdf()'><i
                                        class="fa fa-file-o"></i> 导出PDF
                                </button>
                                <button type="button" class="btn btn-default" title="财务报运单" onclick='createFinance()'><i
                                        class="fa fa-file-o"></i> 财务报运单
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
                            <th class="sorting">序号</th>
                            <th class="sorting">发票编号</th>
                            <th class="sorting">报运合同号</th>
                            <th class="sorting">贸易条款</th>
                            <th class="sorting">发票金额</th>
                            <th class="sorting">发票时间</th>
                            <th class="sorting">发票状态</th>
                            <th class="sorting">操作</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="o" varStatus="status">
                            <tr>
                                <td><input type="checkbox" name="id" value="${o.invoiceId}"/></td>
                                <td>${status.count + ((pageInfo.pageNum -1) * pageInfo.pageSize)}</td>
                                <td>${o.invoiceId}</td>
                                <td>${o.scNo}</td>
                                <td>${o.tradeTerms}</td>
                                <td>${o.invoiceMoney}</td>
                                <td><fmt:formatDate value="${o.invoiceTime}" pattern="yyyy-MM-dd"/></td>

                                <td>
                                    <c:if test="${o.status==0}"><font color="red">未支付</font></c:if>
                                    <c:if test="${o.status==1}"><font color="green">已支付</font></c:if>
                                </td>
                                <td>
                                    <a href="${ctx }/cargo/invoice/toView.do?invoiceId=${o.invoiceId}">[查看]</a>
                                </td>
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
                    <jsp:param value="/cargo/invoice/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->


        </div>

    </section>
</div>
</body>

</html>
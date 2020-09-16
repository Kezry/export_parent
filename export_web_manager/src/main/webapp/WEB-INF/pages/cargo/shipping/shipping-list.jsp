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
            alert("请勾选要删除的装箱单");
        } else {
            //获取选中的报运单id(可能有多个)
            let ids = new Array();
            $("input[name=id]:checked").each(function () {
                ids.push($(this).val());
            });
            //异步请求
            $.ajax({
                method: "get",
                url: "/cargo/shipping/deleteBatch.do",
                data: {"ids": ids},
                traditional: true,
                dataType: "text",
                success:function (result) {
                    /*
                        1 --> 删除成功
                        0 --> 存在已委托单
                     */
                    if (result == "1") {
                        alert("删除成功")
                        location.reload();
                    }else if (result == "0") {
                        alert("已经开票的委托单不能删除!")
                    }
                }
            });
        }
    }

    function view(){

    }

    function toExcel(){
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
            <small>委托管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}/home.do"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">委托管理列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="刷新" onclick='window.location.reload()'><i
                                        class="fa  fa-eye-slash"></i> 刷新
                                </button>
                                <button type="button" class="btn btn-default" title="删除" onclick='deleteBatch()'><i
                                        class="fa fa-trash-o"></i> 删除
                                </button>
                                <button type="button" class="btn btn-default" title="导出Excel" onclick='toExcel()'><i
                                        class="fa fa-trash-o"></i> 导出Excel
                                </button>
                                <button type="button" class="btn btn-default" title="生成发票" onclick=''><i
                                        class="fa fa-trash-o"></i> 生成发票
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
                            <th class="sorting">运输方式</th>
                            <th class="sorting">船号</th>
                            <th class="sorting">飞机号</th>
                            <th class="sorting">货主</th>
                            <th class="sorting">提单抬头</th>
                            <th class="sorting">正本通知人</th>
                            <th class="sorting">信用证号</th>
                            <th class="sorting">效期</th>
                            <th class="sorting">装期</th>
                            <th class="sorting">装运港</th>
                            <th class="sorting">卸货港</th>
                            <th class="sorting">是否转船</th>
                            <th class="sorting">转运港</th>
                            <th class="sorting">扼要说明</th>
                            <th class="sorting">状态</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="o" varStatus="status">
                            <tr>
                                <td><input type="checkbox" name="id" value="${o.shippingOrderId}"/></td>
                                <td>${status.count + ((pageInfo.pageNum -1) * pageInfo.pageSize)}</td>
                                <td>
                                    <c:if test="${o.orderType=='SEA'}"><font color="blue">海运</font></c:if>
                                    <c:if test="${o.orderType=='AIR'}"><font color="blue">空运</font></c:if>
                                </td>
                                <td>${o.seaNo}</td>
                                <td>${o.airNo}</td>
                                <td>${o.shipper}</td>
                                <td>${o.consignee}</td>
                                <td>${o.notifyParty}</td>
                                <td>${o.lcNo}</td>
                                <td><fmt:formatDate value="${o.limitDate}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${o.loadingDate}" pattern="yyyy-MM-dd"/></td>
                                <td>${o.portOfLoading}</td>
                                <td>${o.portOfDischar}</td>
                                <td>
                                    <c:if test="${o.isTrans==0}"><font color="red">否</font></c:if>
                                    <c:if test="${o.isTrans==1}"><font color="green">是</font></c:if>
                                </td>
                                <td>${o.portOfTrans}</td>
                                <td>${o.remark}</td>
                                <td>
                                    <c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已开票</font></c:if>
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
                    <jsp:param value="/cargo/shipping/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->


        </div>

    </section>
</div>
</body>

</html>
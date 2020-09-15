<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</head>
<script>
    /*
        删除点击事件
     */
    function deleteById() {
        let id = getCheckId()
        if (id) {
            if (confirm("你确认要删除此条记录吗？")) {
                location.href = "/baseinfo/factory/delete.do?id=" + id + "&ctype=${ctype}";
            }
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }


</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            基础信息
            <small>厂家信息</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">厂家信息</h3>
            </div>
            <div class="box-body">
                <div class="table-box">
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">

                                <button type="button" class="btn btn-default" title="新建"
                                        onclick='location.href="/baseinfo/factory/toAdd.do?ctype=${ctype}"'><i class="fa fa-file-o"></i> 新建
                                </button>
                                <button type="button" class="btn btn-default" title="删除" onclick='deleteById()'><i
                                        class="fa fa-trash-o"></i> 删除
                                </button>
                                <button type="button" class="btn btn-default" title="刷新"
                                        onclick="window.location.reload();"><i class="fa fa-refresh"></i> 刷新
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
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;">
                                <input type="checkbox" id="checkAll"/>
                            </th>
                            <th class="sorting">序号</th>
                            <th class="sorting">全称</th>
                            <th class="sorting">简称</th>
                            <th class="sorting">生产类型</th>
                            <th class="sorting">联系人</th>
                            <th class="sorting">联系电话</th>
                            <th class="sorting">手机号</th>
                            <th class="sorting">传真</th>
                            <th class="sorting">地址</th>
                            <th class="sorting">审查</th>
                            <th class="sorting">订单编号</th>
                            <th class="sorting">状态</th>
                            <th class="sorting">备注</th>
                            <th class="text-center">操作</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="item" varStatus="row">
                            <tr>
                                <td><input name="id" value="${item.id}" type="checkbox"></td>
                                <td>${row.count + ((pageInfo.pageNum -1) * pageInfo.pageSize)}</td>
                                <td>${item.fullName}</td>
                                <td>${item.factoryName }</td>
                                <td>${item.ctype }</td>
                                <td>${item.contacts }</td>
                                <td>${item.mobile}</td>
                                <td>${item.phone }</td>
                                <td>${item.fax==null ? '无' : item.fax }</td>
                                <td>${item.address}</td>
                                <td>${item.inspector==null ? '略' : item.inspector}</td>
                                <td>${item.orderNo == null ? '无' : item.orderNo}</td>
                                <td>${item.state  ==0?'停用':'启用'}</td>
                                <td>${item.remark==null ? '无' : item.remark}</td>
                                <th class="text-center">
                                    <button type="button" class="btn bg-olive btn-xs"
                                            onclick='location.href="/baseinfo/factory/toUpdate.do?id=${item.id}"'>编辑
                                    </button>
                                </th>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="${ctx}/baseinfo/factory/list.do?ctype=${ctype}" name="pageUrl"/>
                </jsp:include>
            </div>
        </div>

    </section>
</div>
</body>

</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
            基础信息
            <small>厂家信息添加</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="/baseinfo/factory/list.do?ctype=${ctype}">厂家信息</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">货物生产厂家信息</div>
            <form id="editForm" action="/baseinfo/factory/edit.do" method="post">

                <input type="hidden" name="id" value="${factory.id}">

                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">厂家全称</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="厂家全称" name="fullName" value="${factory.fullName}">
                    </div>

                    <div class="col-md-2 title">厂家简称</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="厂家简称" name="factoryName" value="${factory.factoryName}">
                    </div>

                    <div class="col-md-2 title">生产类型</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="ctype" value="${ctype}">
                        ${ctype}
                    </div>

                    <div class="col-md-2 title">联系人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="联系人" name="contacts" value="${factory.contacts}">
                    </div>

                    <div class="col-md-2 title">联系电话</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="联系电话" name="mobile" value="${factory.mobile}">
                    </div>

                    <div class="col-md-2 title">手机号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="手机号" name="phone" value="${factory.phone}">
                    </div>

                    <div class="col-md-2 title">传真</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="传真" name="fax" value="${factory.fax}">
                    </div>

                    <div class="col-md-2 title">地址</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="地址" name="address" value="${factory.address}">
                    </div>

                    <div class="col-md-2 title">审查人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="审查人" name="inspector" value="${factory.inspector}">
                    </div>


                    <div class="col-md-2 title">状态</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${factory.state==0?'checked':''} name="state" value="0" checked>停用</label></div>
                            <div class="radio"><label><input type="radio" ${factory.state==1?'checked':''} name="state" value="1">启用</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">备注</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="备注" name="remark" value="${factory.remark}">
                    </div>

                    <div class="col-md-2 title">订单编号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="订单编号" name="orderNo" value="${factory.orderNo}">
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
</html>
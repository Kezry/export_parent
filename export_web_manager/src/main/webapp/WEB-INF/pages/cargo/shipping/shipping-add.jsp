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
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>新增委托书</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}/home.do"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">新增委托书</li>
        </ol>
    </section>
    <!-- 正文区域 -->
    <section class="content">
        <form action="/cargo/shipping/add.do" method="post">
            <div class="panel panel-default">
                <div class="panel-heading">对【${id}】生成委托书</div>

                <input type="hidden" name="id" value="${id}">

                <div class="row data-type" style="margin: 0px">

                    <div class="col-md-2 title">运输方式</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline"  >
                            <div class="radio" style="display: ${export.transportMode=="SEA"?'':'none'} "><label><input type="radio" ${export.transportMode=="SEA"?'':'checked'} name="orderType"  value="SEA">海运</label></div>
                            <div class="radio" style="display: ${export.transportMode!="SEA"?'':'none'} "><label><input type="radio" ${export.transportMode=="AIR"?'checked':''} name="orderType" value="AIR">空运</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">船号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="船号" name="seaNo" />
                    </div>

                    <div class="col-md-2 title">飞机号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="飞机号" name="airNo" />
                    </div>

                    <div class="col-md-2 title">货主</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="shipper" value="${export.consignee}">
                        ${export.consignee}
                    </div>

                    <div class="col-md-2 title">提单抬头</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="consignee" value="${export.consignee}">
                        ${export.consignee}
                    </div>

                    <div class="col-md-2 title">正本通知人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="正本通知人" name="notifyParty" />
                    </div>

                    <div class="col-md-2 title">信用证号</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="lcNo" value="${export.lcno}">
                    ${export.lcno}
                    </div>

                    <div class="col-md-2 title">信用证有效期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="信用证有效期" name="limitDate" class="form-control pull-right"
                                   id="limitDate">
                        </div>
                    </div>

                    <div class="col-md-2 title">唛头</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="marks" value="${export.marks}">
                        ${export.marks}
                    </div>

                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="portOfLoading" value="${export.shipmentPort}">
                        ${export.shipmentPort}
                    </div>

                    <div class="col-md-2 title">是否转运</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" name="isTrans" value="1" checked>是</label></div>
                            <div class="radio"><label><input type="radio"  name="isTrans" value="0">否</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">转运港</div>
                    <div class="col-md-4 data">
                        <input type="text" name="portOfTrans" class="form-control" placeholder="转运港"/>
                    </div>

                    <div class="col-md-2 title">卸货港</div>
                    <div class="col-md-4 data">
                        <input type="hidden" name="portOfDischar" value="${export.destinationPort}">
                        ${export.destinationPort}
                    </div>

                    <div class="col-md-2 title">装运日期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="装运日期" name="loadingDate" class="form-control pull-right"
                                   id="loadingDate">
                        </div>
                    </div>


                    <div class="col-md-2 title">是否分批</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio"  name="isBatch" value="1" checked>是</label></div>
                            <div class="radio"><label><input type="radio"  name="isBatch" value="0">否</label></div>
                        </div>
                    </div>



                    <div class="col-md-2 title">运输要求</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control"  name="specialCondition" placeholder="运输要求"/>
                    </div>

                    <div class="col-md-2 title">运费说明</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control"  name="freight" placeholder="运费说明"/>
                    </div>

                    <div class="col-md-2 title">扼要说明</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control"  name="remark" placeholder="扼要说明"/>
                    </div>

                    <div class="col-md-2 title">委托份数</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control"  name="copyNum" placeholder="扼要说明"/>
                    </div>

                    <div class="col-md-2 title">复核人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control"  name="checkBy" placeholder="复核人"/>
                    </div>

                </div>
            </div>
            <!--工具栏-->
            <div class="box-tools text-center">
                <button type="submit"  class="btn bg-maroon">保存</button>
                <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
            </div>
        </form>
        <!--工具栏/-->
    </section>
</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#limitDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });

    $('#loadingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>
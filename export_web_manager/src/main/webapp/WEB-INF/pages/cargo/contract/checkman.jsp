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
  <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>

  <link rel="stylesheet" href="${ctx}/css/checkman/normalize.min.css">
  <link rel="stylesheet" href="${ctx}/css/checkman/style.css">
</head>

<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
  <section class="content-header">
    <h1>
      货运管理
      <small>合同管理员</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="${ctx}/home.do"><i class="fa fa-dashboard"></i> 首页</a></li>
    </ol>
  </section>
  <!-- 正文区域 -->
  <section class="content">
<body2>
    <!-- .box-body -->
    <div class="box box-primary">
      <!-- /.box-body -->

      <div class="container">
      <c:forEach items="${contractReviewUser}" var="item">

      <!-- partial:index.partial.html -->
        <div class="card" style="--cards:5;">
          <div class="child">
            <h2>${item.userName}</h2>
            <p>提交了5份合同</p>
          </div>
          <div class="child"></div>
          <div class="child"></div>
          <div class="child"></div>
          <div class="child"></div>
        </div>
      </c:forEach>
      </div>
      <!-- partial -->
    </div>
</body2>



  </section>
</div>
  
</body>
</html>
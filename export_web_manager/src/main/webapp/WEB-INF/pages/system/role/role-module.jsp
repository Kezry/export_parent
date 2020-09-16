<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <base href="${ctx}/">
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <link rel="stylesheet" href="plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>

    <SCRIPT type="text/javascript">
        //页面加载完毕然后发出异步请求
        $(function(){
            $.ajax({
                url:"/system/role/getTreeNodes.do",  //发送url
                type:"get",                          //请求的方式
                data:{"roleid":$("#roleid").val()}, //发送的数据
                dataType:"json", //服务器返回的数据是json格式的。
                success:function(treeData){  //返回来的就是树形结构要的数据

                    //1. 定义全局参数
                    var setting = {
                        check: {
                            enable: true    //复选框是否要可选
                        },
                        data: {
                            simpleData: {
                                enable: true   // 是否要展示成树形结构
                            }
                        }
                    };

                    //2.树的结构要数据
                    var zNodes = treeData;

                    //3. 使用全局参数、树结构数据、html元素组合成一颗树
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                }
            });

        });


        //保存按钮触发的方法
        function  submitCheckedNodes() {
            //1. 获取当前树选中的权限
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getCheckedNodes(true);

            //2. 遍历所有选中的id，拼接成一个字符串
            var moduleIds = "";  // 字符串的样式： 1,2,3,4,5
            for(var index = 0; index<nodes.length ; index++){
                var node = nodes[index];
                if(index!=nodes.length-1){
                    moduleIds+=node.id+",";
                }else{
                    moduleIds+=node.id;
                }
            }
            //3. 把moduleids的字符串设置给input标签
            $("#moduleIds").val(moduleIds);

            //4. icform表单提交
            $("#icform").submit();
        }


    </SCRIPT>
</head>

<body style="overflow: visible;">
<div id="frameContent" class="content-wrapper" style="margin-left:0px;height: 1200px" >
    <section class="content-header">
        <h1>
            菜单管理
            <small>菜单列表</small>
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
                <h3 class="box-title">角色 [${role.name}] 权限列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--工具栏-->
                    <div class="box-tools text-left">
                        <button type="button" class="btn bg-maroon" onclick="submitCheckedNodes();">保存</button>
                        <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
                    </div>
                    <!--工具栏/-->
                    <!-- 树菜单 -->
                    <form id="icform" name="icform" method="post" action="/system/role/updateRoleModule.do">
                        <input type="hidden" id="roleid" name="roleid" value="${role.id}"/>
                        <input type="hidden" id="moduleIds" name="moduleIds" value=""/>
                        <div class="content_wrap">
                            <div class="zTreeDemoBackground left" style="overflow: visible">
                                <ul id="treeDemo" class="ztree"></ul>
                            </div>
                        </div>

                    </form>
                    <!-- 树菜单 /-->

                </div>
                <!-- /数据表格 -->

            </div>
            <!-- /.box-body -->
        </div>
    </section>
</div>
</body>
</html>
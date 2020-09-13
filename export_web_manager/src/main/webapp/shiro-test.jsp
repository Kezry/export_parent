<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    
<shiro:hasPermission name="用户管理">
    <a href="#">用户管理</a>
</shiro:hasPermission>

<shiro:hasPermission name="企业管理">
    <a href="#">企业管理</a>
</shiro:hasPermission>
</body>
</html>

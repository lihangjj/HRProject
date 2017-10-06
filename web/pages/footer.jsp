<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="css/mldn.css">
    <script src="js/mldn.js" type="text/javascript"></script>
    <title>微商城项目</title>
</head>
<body>

<div>
    mldn提供技术支持
</div>
</body>
</html>

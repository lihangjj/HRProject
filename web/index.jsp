<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/17 0017
  Time: 下午 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
$END$好啊
<c:if test="${test!=null}" var="res">
    好的
</c:if>
<%=request.getAttribute("test")%>
</body>
</html>

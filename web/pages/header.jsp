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
    <img src="images/logo.png" style="width: 207px;height: 72px">
    <a href="front/index.jsp">商城首页</a>
    <a href="/pages/front/goods/GoodsFrontServlet/list">商品列表</a>
    <a href="/pages/front/goods/GoodsFrontServlet/myShopCar">我的购物车</a>
    <a href="/pages/MemberFrontServlet/orderlist">我的订单</a>
    <c:if test="${memberid==null}" var="res">
        <a href="pages/member_login.jsp">登陆</a>
        <a href="pages/regist.jsp">注册</a>
    </c:if>

    <c:if test="${memberid!=null}" var="res">
        <a href="/pages/MemberFrontServlet/updatePre">个人中心</a>
        <img src="/upload/member/${photo}" style="width: 64px;height: 64px">
        ${memberid}
        <a href="/pages/MemberFrontServlet/writeOff">退出重新登陆</a>
</c:if>

</div>

</body>
</html>

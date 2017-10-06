<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String loginUrl = basePath + "/AdminLoginFrontServlet/login";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
    <link href="css/mldn.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script src="js/mldn.js" type="text/javascript"></script>
    <script src="js/jquery-1.6.min.js" type="text/javascript"></script>
    <script src="js/front/login.js" type="text/javascript"></script>
    <title>人力资源管理系统</title>
</head>
<body>
<div id="logo"><img src="images/logo-head.jpg"></div>
<form id="slick-login" action="<%=loginUrl%>" onsubmit="return validateLogin()" >
    <label for="admin.aid">admin.aid</label><input type="text" id="admin.aid" name="admin.aid" class="placeholder"
                                                   placeholder="用户名"
                                                   onblur="validateEmpty(this.id)"><span id="admin.aidMsg"></span>
    <label for="admin.password">admin.password</label><input type="password" name="admin.password" id="admin.password"
                                                             onblur="validateEmpty(this.id)" class="placeholder"
                                                             placeholder="密码"><span id="admin.passwordMsg"></span>
    <label for="code">code</label><input class="placeholder" placeholder="验证码" id="code" name="code" type="text"
                                         maxLength="4" size="10"
                                         onblur="validateRegex(this.id,/^[A-Za-z0-9]{4}$/)"><span id="codeMsg"></span>
    <img class="" src="pages/image.jsp" onclick="changeCode(this)">
    <input type="submit" value="登录">
</form>
</body>
</html>

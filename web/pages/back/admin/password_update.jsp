<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String updatePasswordUrl = basePath + "/AdminLoginFrontServlet/updatePassword";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
    <link href="css/mldn.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script src="/js/mldn.js" type="text/javascript"></script>
    <script src="js/jquery-1.6.min.js" type="text/javascript"></script>
    <script src="js/front/login.js" type="text/javascript"></script>
    <title>人力资源管理系统</title>
</head>
<body>
<div id="logo"><img src="images/logo-head.jpg"></div>
<form id="slick-login" action="<%=updatePasswordUrl%>" onsubmit="return validateUpdatePassword()">
    <label for="oldPassword">oldPassword</label><input type="password" id="oldPassword" name="oldPassword" class="placeholder"
                                                   placeholder="原密码"
                                                   onblur="validateEmpty(this.id)"><span id="oldPasswordMsg"></span>
    <label for="admin.password">admin.password</label><input type="password" name="admin.password" id="admin.password"
                                                             onblur="validateRegex(this.id,/^[0-9a-zA-Z]{2,20}$/)"
                                                             class="placeholder"
                                                             placeholder="新密码"><span id="admin.passwordMsg"></span>
    <label for="newPassword">newPassword</label><input class="placeholder" placeholder="重复新密码" id="newPassword"
                                                       name="newPassword"
                                                       type="password"
                                                       onblur="validateSame('admin.password',this.id)"><span
        id="newPasswordMsg"></span>
    <input type="submit" value="修改">
</form>
</body>
</html>

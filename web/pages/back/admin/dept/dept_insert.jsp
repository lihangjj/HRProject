<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String insertUrl = basePath + "/pages/back/admin/dept/DeptBackServlet/insert";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
    <link href="css/mldn.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script src="js/mldn.js" type="text/javascript"></script>
    <script src="js/jquery-1.6.min.js" type="text/javascript"></script>
    <title>人力资源管理系统</title>
</head>
<body>
<h1 class="bigTitle">部门增加</h1>
<form action="<%=insertUrl%>" method="post"
      onsubmit="return validateEmpty('dept.dname')&&validateRegex('dept.current',/^[0]$/)">

    <div align="center" style="margin: 25px">
        <table class="insertTable">
            <tr>
                <td>部门名称</td>
                <td>部门人数</td>
            </tr>
            <tr>
                <td><input type="text" name="dept.dname" id="dept.dname" onblur="validateEmpty(this.id)"><span
                        id="dept.dnameMsg"></span></td>
                <td><input type="text" name="dept.current" id="dept.current" value="0"
                           onblur="validateRegex(this.id,/^[0]$/)"><span id="dept.currentMsg"></span>
                </td>
            </tr>
        </table>
        <button type="submit" style="height: 40px;width: 100px">部门增加</button>
    </div>

</form>


</body>
</html>

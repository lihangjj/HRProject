<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String insertUrl = basePath + "/pages/back/admin/jobs/JobsBackServlet/insert";
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
<h1 class="bigTitle">职位增加</h1>
<form action="<%=insertUrl%>" method="post"
      onsubmit="return validateEmpty('jobs.title')">

    <div align="center" style="margin: 25px">
        <table class="insertTable">
            <tr style="height: 30px" >
                <td>职位名称：<input type="text" name="jobs.title" id="jobs.title" onblur="validateEmpty(this.id)"><span
                        id="jobs.titleMsg"></span></td>
            </tr>
            <tr style="height: 30px">
                <td>职位介绍：<textarea rows="3" cols="50" name="jobs.note" id="jobs.note"></textarea></td>
            </tr>
        </table>
        <button type="submit" style="height: 40px;width: 100px">增加职位</button>
    </div>

</form>


</body>
</html>

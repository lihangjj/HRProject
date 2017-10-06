<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String insertUrl = basePath + "/pages/back/admin/level/LevelBackServlet/insert";
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
<h1 class="bigTitle">工资级别增加</h1>
<form action="<%=insertUrl%>" method="post"
      onsubmit="return validateEmpty('level.title')">

    <div align="center" style="margin: 25px">
        <table class="insertTable">
            <tr style="height: 30px">
                <td>工资级别：<input type="text" name="level.title" id="level.title" onblur="validateEmpty(this.id)"><span
                        id="level.titleMsg"></span></td>
            </tr>
            <tr style="height: 30px">
                <td>最低工资：<input type="text" name="level.losal" id="level.losal"
                                onblur="validateRegex(this.id,/^\d+(\.\d{1,2})?$/)"><span
                        id="level.losalMsg"></span></td>
            </tr>
            <tr style="height: 30px">
                <td>最高工资：<input type="text" name="level.hisal" id="level.hisal"
                                onblur="validateHisal('level.losal',this.id,/^\d+(\.\d{1,2})?$/)"><span
                        id="level.hisalMsg"></span></td>
            </tr>
        </table>
        <button type="submit" style="height: 40px;width: 100px">增加工资级别</button>
    </div>

</form>


</body>
</html>

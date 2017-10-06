<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String updateUrl = basePath + "/pages/back/admin/jobs/JobsBackServlet/update";
    String deleteUrl = basePath + "/pages/back/admin/jobs/JobsBackServlet/delete";
    String insertPreUrl = basePath + "/pages/back/admin/jobs/level_insert.jsp";
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
<h1 class="bigTitle">职位列表</h1>
<form action="<%=updateUrl%>" method="post" id="jobsList"
      onsubmit="return validateAllNotEmpty('jobs-title','${validateItem}')">
    <div align="center" style="margin: 25px">
        <table class="insertTable">
            <tr>
                <td><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this.id,'checkItem')">全选
                </td>
                <td>职位名称</td>
                <td>职位介绍</td>
            </tr>
            <c:if test="${allJobs!=null}" var="res">
                <c:forEach items="${allJobs}" var="jobs">
                    <tr>
                        <td><input type="checkbox" name="checkItem" id="checkItem" value="${jobs.jid}">操作</td>
                        <td><input type="text" name="jobs-title${jobs.jid}" id="jobs-title${jobs.jid}"
                                   value="${jobs.title}"
                                   onblur="validateEmpty(this.id)"><span
                                id="jobs-title${jobs.jid}Msg"></span></td>
                        <td><textarea name="jobs-note${jobs.jid}" id="jobs-note${jobs.jid}"
                                      >${jobs.note}</textarea></td>
                    </tr>
                </c:forEach>

            </c:if>
        </table>
        <button type="button" style="height: 40px;width: 100px"><a href="<%=insertPreUrl%>">增加职位</a></button>
        <button type="submit" style="height: 40px;width: 100px">修改职位信息</button>
        <button type="button" style="height: 40px;width: 100px" onclick="manualSubmitForm('<%=deleteUrl%>','jobsList')">
            批量删除职位
        </button>
    </div>

</form>


</body>
</html>

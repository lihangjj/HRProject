<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String updateUrl = basePath + "/pages/back/admin/dept/DeptBackServlet/update";
    String deleteUrl = basePath + "/pages/back/admin/dept/DeptBackServlet/delete";
    String insertPreUrl = basePath + "/pages/back/admin/dept/level_insert.jsp";
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
<h1 class="bigTitle">部门列表</h1>
<form action="<%=updateUrl%>" method="post" id="deptList"
      onsubmit="return validateAllNotEmpty('dept-dname','${validateItem}')&&validateAllRegex('dept-current','${validateItem}',/^\d+$/)">
    <div align="center" style="margin: 25px">
        <table class="insertTable">
            <tr>
                <td><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this.id,'checkItem')">全选
                </td>
                <td>部门名称</td>
                <td>部门人数</td>
            </tr>
            <c:if test="${allDept!=null}" var="res">
                <c:forEach items="${allDept}" var="dept">
                    <tr>
                        <td><input type="checkbox" name="checkItem" id="checkItem" value="${dept.did}">操作</td>
                        <td><input type="text" name="dept-dname${dept.did}" id="dept-dname${dept.did}"
                                   value="${dept.dname}"
                                   onblur="validateEmpty(this.id)"><span
                                id="dept-dname${dept.did}Msg"></span></td>
                        <td><input type="text" name="dept-current${dept.did}" id="dept-current${dept.did}"
                                   value="${dept.current}"
                                   onblur="validateRegex(this.id,/^\d+$/)"><span id="dept-current${dept.did}Msg"></span>
                        </td>
                    </tr>
                </c:forEach>

            </c:if>
        </table>
        <button type="button" style="height: 40px;width: 100px"><a href="<%=insertPreUrl%>">增加部门</a></button>
        <button type="submit" style="height: 40px;width: 100px">修改部门数据</button>
        <button type="button" style="height: 40px;width: 100px" onclick="manualSubmitForm('<%=deleteUrl%>','deptList')">
            批量删除部门
        </button>
    </div>

</form>


</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String updateUrl = basePath + "/pages/back/admin/level/LevelBackServlet/update";
    String deleteUrl = basePath + "/pages/back/admin/level/LevelBackServlet/delete";
    String insertPreUrl = basePath + "/pages/back/admin/level/level_insert.jsp";
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
<h1 class="bigTitle">工资等级列表</h1>
<form action="<%=updateUrl%>" method="post" id="levelList"
      onsubmit="return validateAllNotEmpty('level-title','${validateItem}')&&validateAllRegex('level-losal','${validateItem}',/^\d+(\.\d{1,2})?$/)&&validateAllHisalAndReges('level-losal','level-hisal','${validateItem}',/^\d+(\.\d{1,2})?$/)">
    <div align="center" style="margin: 25px">
        <table class="LevelTable">
            <tr>
                <td><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this.id,'checkItem')">全选
                </td>
                <td>工资等级</td>
                <td>最低工资</td>
                <td>最高工资</td>
            </tr>
            <c:if test="${allLevel!=null}" var="res">
                <c:forEach items="${allLevel}" var="level">
                    <tr>
                        <td><input type="checkbox" name="checkItem" id="checkItem" value="${level.levid}">操作</td>
                        <td><input type="text" name="level-title${level.levid}" id="level-title${level.levid}"
                                   value="${level.title}"
                                   onblur="validateEmpty(this.id)"><span
                                id="level-title${level.levid}Msg"></span></td>
                        <td><input name="level-losal${level.levid}" id="level-losal${level.levid}"
                                   value="${level.losal}"
                                   onblur="validateRegex('level-losal${level.levid}',/^\d+(\.\d{1,2})?$/)"><span
                                id="level-losal${level.levid}Msg"></span>
                        </td>
                        <td><input name="level-hisal${level.levid}" id="level-hisal${level.levid}"
                                   value="${level.hisal}"
                                   onblur="validateHisal('level-losal${level.levid}','level-hisal${level.levid}',/^\d+(\.\d{1,2})?$/)"><span
                                id="level-hisal${level.levid}Msg"></span>
                        </td>
                    </tr>
                </c:forEach>

            </c:if>
        </table>
        <button type="button" style="height: 40px;width: 100px"><a href="<%=insertPreUrl%>">增加工资等级</a></button>
        <button type="submit" style="height: 40px;width: 100px">修改工资信息</button>
        <button type="button" style="height: 40px;width: 100px"
                onclick="manualSubmitForm('<%=deleteUrl%>','levelList')">
            批量删除工资等级
        </button>
    </div>

</form>


</body>
</html>

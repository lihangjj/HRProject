<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/19 0019
  Time: 下午 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>雇员信息系统</title>
</head>
<body>
<%
    int allRecorders;
    int currentPage;
    int lineSize;
    String url;
    String keyWord = "";
    String column = "empon";
    keyWord = request.getAttribute("keyWord") == null ? "" : (String) request.getAttribute("keyWord");
    column = request.getAttribute("column") == null ? "empon" : (String) request.getAttribute("column");
    url = request.getAttribute("url") == null ? null : (String) request.getAttribute("url");
    currentPage = request.getAttribute("currentPage") == null ? 1 : (int) request.getAttribute("currentPage");
    lineSize = request.getAttribute("lineSize") == null ? 10 : (int) request.getAttribute("lineSize");
    allRecorders = request.getAttribute("allRecorders") == null ? 0 : (int) request.getAttribute("allRecorders");
    String selectItems = request.getAttribute("selectItems") == null ? "雇员编号:empno|雇员名字:ename|雇员职位:job|雇员工资:sal" : (String) request.getAttribute("selectItems");
    String items1[] = selectItems.split("\\|");
    String items2[];
%>
<form action="<%=url%>">

    <div style="padding: 10px">
        请选择查询方式：<select id="column" name="column">

        <%
            for (int x = 0; x < items1.length; x++) {
                items2 = items1[x].split(":");
        %>
        <option value="<%=items2[1]%>" <%=column.equals(items2[1]) ? "selected" : ""%>><%=items2[0]%>
        </option>
        <%
            }
        %>
    </select>&nbsp请输入查询关键字<input style="font-weight: bold" type="text" id="keyWord" name="keyWord" value="<%=keyWord%>"
                                 onblur="validateEmpty('keyWord')">
        <input type="submit" value="检索">&nbsp一共查询到<%=allRecorders%>
        条数据
        <input type="hidden" value="<%=currentPage%>" name="currentPage">
        <input type="hidden" value="<%=lineSize%>" name="lineSize">
        <input type="hidden" value="<%=column%>" name="column">
        <input type="hidden" value="<%=keyWord%>" name="keyWord">
    </div>
</form>


</body>
</html>

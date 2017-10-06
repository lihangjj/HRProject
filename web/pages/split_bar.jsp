<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/19 0019
  Time: 下午 20:23
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
    <link href="<%=basePath%>css/mldn.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/mldn.js" type="text/javascript"></script>
</head>
<body>
<%
    int pageSize;
    int allRecorders;
    int currentPage;
    int lineSize;
    String url;
    String keyWord ;
    String column ;
    String parameterName;
    String parameterValue;
    column = request.getAttribute("column") == null ? null : (String) request.getAttribute("column");
    keyWord = request.getAttribute("keyWord") == null ? "" : (String) request.getAttribute("keyWord");
    parameterName = request.getAttribute("parameterName") == null ? null : (String) request.getAttribute("parameterName");
    parameterValue = request.getAttribute("parameterValue") == null ? null : (String) request.getAttribute("parameterValue");
    url = request.getAttribute("url") == null ? null : (String) request.getAttribute("url");
    currentPage = request.getAttribute("currentPage") == null ? 1 : (int) request.getAttribute("currentPage");
    lineSize = request.getAttribute("lineSize") == null ? 10 : (int) request.getAttribute("lineSize");
    allRecorders = request.getAttribute("allRecorders") == null ? 0 : (int) request.getAttribute("allRecorders");
    pageSize = (allRecorders + lineSize - 1) / lineSize;

%>

<div>
    到第<select id="selectPages"
              onchange="goSplit('<%=url%>',this.value,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)">
    <%
        for (int x = 1; x <= pageSize; x++) {
    %>
    <option value="<%=x%>" <%=currentPage == x ? "selected" : ""%>><%=x%>
    </option>
    <%
        }

    %>
</select>页

    <input type="button" value="首页"
           onclick="goSplit('<%=url%>',<%=1%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)" <%=currentPage==1?"disabled":""%>>
    <input type="button" value="上一页"
           onclick="goSplit('<%=url%>',<%=currentPage-1%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)" <%=currentPage==1?"disabled":""%> >
    <input type="button" value="下一页"
           onclick="goSplit('<%=url%>',<%=currentPage+1%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)" <%=currentPage==pageSize?"disabled":""%>
    >
    <input type="button" value="尾页"
           onclick="goSplit('<%=url%>',<%=pageSize%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)" <%=currentPage==pageSize?"disabled":""%>>
    每页显示<select id="selectLineSize"
                onchange="goSplit('<%=url%>',1,this.value,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)">

    <%
        for (int x = 1; x <= 20; x++) {
    %>
    <option value="<%=x%>" <%=lineSize == x ? "selected" : ""%>><%=x%>
    </option>
    <%
        }

    %>
</select>行

</div>
</body>
</html>

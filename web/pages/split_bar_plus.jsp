<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>雇员信息系统</title>
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/emp.js" type="text/javascript"></script>
</head>
<body>


<%
    int pageSize;
    int allRecorders;
    int currentPage;
    int lineSize;
    String url;
    String keyWord = "";
    String column = "empon";
    String parameterName;
    String parameterValue;
    keyWord = request.getAttribute("keyWord") == null ? "" : (String) request.getAttribute("keyWord");
    column = request.getAttribute("column") == null ? null : (String) request.getAttribute("column");
    parameterName = request.getAttribute("parameterName") == null ? null : (String) request.getAttribute("parameterName");
    parameterValue = request.getAttribute("parameterValue") == null ? null : (String) request.getAttribute("parameterValue");
    url = request.getAttribute("url") == null ? null : (String) request.getAttribute("url");
    currentPage = request.getAttribute("currentPage") == null ? 1 : (int) request.getAttribute("currentPage");
    lineSize = request.getAttribute("lineSize") == null ? 10 : (int) request.getAttribute("lineSize");
    allRecorders = request.getAttribute("allRecorders") == null ? 0 : (int) request.getAttribute("allRecorders");
    pageSize = (allRecorders + lineSize - 1) / lineSize;

%>
<div>
    <ul class="pagination">
        <li class=<%=currentPage == 1 ? "active" : ""%>><a
                onclick="goSplit('<%=url%>',1,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)">1</a>
        </li>

        <%
            if (pageSize < 10) {
                for (int x = 2; x < pageSize; x++) {
        %>
        <li class=<%=currentPage == x ? "active" : ""%>><a
                onclick="goSplit('<%=url%>',<%=x%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)"><%=x%>
        </a></li>
        <%
            }
        } else {
            if (currentPage < 6) {
                for (int x = 2; x < currentPage + 4; x++) {
        %>
        <li class=<%=currentPage == x ? "active" : ""%>><a
                onclick="goSplit('<%=url%>',<%=x%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)"><%=x%>
        </a></li>

        <%
            }
        %>
        <li><a>...</a></li>
        <%

        } else {
        %>
        <li><a>...</a></li>
        <%
            if (currentPage + 4 < pageSize) {
                for (int x = currentPage - 3; x < currentPage + 4; x++) {
        %>
        <li class=<%=currentPage == x ? "active" : ""%>><a
                onclick="goSplit('<%=url%>',<%=x%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)"><%=x%>
        </a></li>
        <%

            }
        %>
        <li><a>...</a></li>
        <%
        } else {
            for (int x = currentPage - 3; x < pageSize; x++) {
        %>
        <li class=<%=currentPage == x ? "active" : ""%>><a
                onclick="goSplit('<%=url%>',<%=x%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)"><%=x%>
        </a></li>
        <%
                    }
                }
            }
        %>
        <%
            }
        %>
        <%
            //如果页面总数小于2页，就只显示第一页
            if (pageSize < 2) {
            } else {
        %>
        <li class=<%=currentPage == pageSize ? "active" : ""%>>
            <a onclick="goSplit('<%=url%>',<%=pageSize%>,<%=lineSize%>,'<%=column%>','<%=keyWord%>','<%=parameterName%>',<%=parameterValue%>)"><%=pageSize%>
            </a></li>
        <%

            }
        %>

    </ul>

</div>

</body>
</html>

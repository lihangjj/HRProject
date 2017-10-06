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
    <title>微商城跳转</title>
</head>
<body>
<script type="text/javascript">
    alert("${msg}");
    window.location = "<%=basePath%>"+"${url}";
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String insertUrl = "/pages/front/employee/EmployeeFrontServlet/insert";
    String update = "/pages/front/employee/EmployeeFrontServlet/update";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
    <link href="/css/mldn.css" rel="stylesheet" type="text/css"/>
    <link href="/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="/js/mldn.js" type="text/javascript"></script>
    <script src="/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/index.js"></script>
    <title>人力资源管理系统</title>
</head>
<body>
<jsp:include page="/pages/menu_bar.jsp"></jsp:include>

<div class="nav-down">
    <div class="rightcon">
        <div class="right_con">
            <p style="text-align:left; margin-top:50px">
            <h3>员工列表资料</h3>
        </div>
        <jsp:include page="/pages/search.jsp"></jsp:include>
        <div class="right_con">
            <form action="<%=update%>" onsubmit="return true" method="post" enctype="multipart/form-data">
                <c:if test="${allEmployee!=null}" var="resa">

                    <c:forEach items="${allEmployee}" var="employee">
                        <div style="margin-bottom: 30px;text-align: center">
                            <table class="listTable">
                                <tr>
                                    <td>编号:</td>
                                    <td><input size="10" name="employee.eid" id="employee.eid" type="text"
                                               value="${employee.eid}"/>
                                    </td>
                                    <td>入职日期:</td>
                                    <td><input size="10" name="employee.indate" id="employee.indate" type="text"
                                               value="${employee.indate}"/></td>

                                    <td>性别:</td>
                                    <td><input size="10" name="employee.sex" id="employee.sex" type="text"
                                               value="${employee.sex}"/></td>
                                    <td>生日:</td>
                                    <td><input size="10" name="employee.birthday" id="employee.birthday" type="text"
                                               value="${employee.birthday}"/></td>
                                    <td>在职状态</td>
                                    <td><select name="employee.status">
                                        <option value="1" ${employee.status==1?"selected":""}>在职</option>
                                        <option value="0" ${employee.status==0?"selected":""}>离职</option>
                                    </select>
                                    </td>
                                    <td>毕业院校:</td>
                                    <td><input size="10" name="employee.school" id="employee.school" type="text"
                                               value="${employee.school}"/></td>
                                    <td>专业:</td>
                                    <td><input size="10" name="employee.profession" id="employee.profession" type="text"
                                               value="${employee.profession}"/></td>
                                    <td>学历:</td>
                                    <td><select name="employee.edu">
                                        <option  ${employee.edu=="专科以下"?"selected":""}>专科以下</option>
                                        <option  ${employee.edu=="专科"?"selected":""}>专科</option>
                                        <option  ${employee.edu=="本科"?"selected":""}>本科</option>
                                        <option  ${employee.edu=="硕士"?"selected":""}>硕士</option>
                                        <option  ${employee.edu=="博士"?"selected":""}>博士</option>
                                    </select></td>


                                    <td>身份证号码:</td>
                                    <td><input size="18" name="employee.idcard" id="employee.idcard" type="text"
                                               value="${employee.idcard}"/></td>


                                </tr>
                                <tr>
                                    <td>姓名:</td>
                                    <td><input size="10" name="employee.ename" id="employee.ename" type="text"
                                               value="${employee.ename}"/></td>

                                    <td>离职日期:</td>
                                    <td><input size="10" name="employee.outdate" id="employee.outdate" type="text"
                                               value="${employee.outdate}"/></td>
                                    <td>职位:</td>
                                    <td><select name="employee.job">
                                        <c:if test="${allJobs!=null}" var="res">
                                            <c:forEach items="${allJobs}" var="jobs">
                                                <option ${employee.job==jobs.title?"selected":""}>${jobs.title}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select></td>
                                    <td>工资:</td>
                                    <td><input size="10" name="employee.sal" id="employee.sal" type="text"
                                               value="${employee.sal}"/></td>
                                    <td>工资等级:</td>
                                    <td>
                                        <select name="employee.levid.title">
                                            <c:if test="${allLevel!=null}" var="res">
                                                <c:forEach items="${allLevel}" var="level">
                                                    <option ${employee.levid.title==level.title?"selected":""}>${level.title}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>

                                    </td>
                                    <td>所在部门:</td>
                                    <td><select name="employee.dname">
                                        <c:if test="${allDept!=null}" var="res">
                                            <c:forEach items="${allDept}" var="dept">
                                                <option ${employee.dname==dept.dname?"selected":""}>${dept.dname}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select></td>
                                    <td>毕业日期:</td>
                                    <td><input size="10" name="employee.grad" id="employee.grad" type="text"
                                               value="${employee.grad}"/></td>
                                    <td>备注:</td>
                                    <td><textarea name="employee.note" id="employee.note" cols="10"
                                                  rows="3">${employee.note}</textarea></td>
                                    <td>照片:</td>
                                    <td><input type="file" size="10" name="photo" id="photo" onchange="preview(this)">
                                        <div id="preview">
                                            <img src="/upload/employee/${employee.photo}"/>
                                        </div>

                                    </td>

                                </tr>

                            </table>
                            <input style="width: 100px;height: 30px;" name="" type="submit" value="修改该雇员的信息"/></div>
                    </c:forEach>
                </c:if>
            </form>
        </div>
        <jsp:include page="/pages/split_bar.jsp"></jsp:include>
        <jsp:include page="/pages/split_bar_plus.jsp"></jsp:include>
    </div>
</div>
</body>
</html>

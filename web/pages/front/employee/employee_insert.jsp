<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String insertUrl = "/pages/front/employee/EmployeeFrontServlet/insert";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
    <link href="/css/mldn.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script src="/js/mldn.js" type="text/javascript"></script>
    <script src="js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/index.js"></script>
    <script type="text/javascript" src="js/data.js"></script>
    <title>人力资源管理系统</title>
</head>
<body>
<jsp:include page="/pages/menu_bar.jsp"></jsp:include>

<div class="nav-down">
    <div class="rightcon">
        <div class="right_con">
            <p style="text-align:left; margin-top:50px">
            <h3>增加员工资料</h3>
        </div>

        <div class="right_con">
            <form action="<%=insertUrl%>" onsubmit="return true" method="post" enctype="multipart/form-data">


                <div class="form_add">
                    <span class="list">雇员编号</span>
                    <input name="employee.eid" id="employee.eid" type="text"/>
                </div>
                <div class="form_add">
                    <span class="list">姓名</span>
                    <input name="employee.ename" id="employee.ename" type="text"/>
                </div>
                <div class="form_add">
                    <span class="list">性别</span>
                    男<input name="employee.sex" type="radio" value="男"/>女<input name="employee.sex" type="radio"
                                                                                value="女"/>
                </div>
                <div class="form_add">
                    <span class="list">身份证</span>
                    <input name="employee.idcard" id="employee.idcard" type="text"/>
                </div>

                <div class="form_add">
                    <span class="list">雇员生日</span>
                    <INPUT TYPE="text" name="employee.birthday" id="employee.birthday"  onclick='popUpCalendar(this, this, "yyyy-mm-dd")' size="15" maxlength="15" readonly="true">
                </div>
                <div class="form_add">
                    <span class="list">毕业院校</span>
                    <input name="employee.school" id="employee.school" type="text"/>
                </div>
                <div class="form_add">
                    <span class="list">所学专业</span>
                    <input name="employee.profession" id="employee.profession" type="text"/>
                </div>
                <div class="form_add">
                    <span class="list">毕业日期</span>
                    <INPUT TYPE="text" name="employee.grad" id="employee.grad"  onclick='popUpCalendar(this, this, "yyyy-mm-dd")' size="15" maxlength="15" readonly="true">

                </div>
                <div class="form_add">
                    <span class="list">学历</span>
                    <select name="employee.edu">
                        <option>专科以下</option>
                        <option>专科</option>
                        <option selected="selected">本科</option>
                        <option>硕士</option>
                        <option>博士</option>
                    </select>

                </div>
                <div class="form_add">
                    <span class="list">入职日期</span>
                    <input name="employee.indate" id="employee.indate" type="text"/>
                </div>
                <div class="form_add">
                    <span class="list">所在部门</span>
                    <select name="employee.dname">
                        <c:if test="${allDept!=null}" var="res">
                            <c:forEach items="${allDept}" var="dept">
                                <option>${dept.dname}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="form_add">
                    <span class="list">职位</span>
                    <select name="employee.job">
                        <c:if test="${allJobs!=null}" var="res">
                            <c:forEach items="${allJobs}" var="jobs">
                                <option>${jobs.title}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="form_add">
                    <span class="list">工资级别</span>
                    <select name="employee.levid.title" id="employee.levid.title" onchange="changeSalRange(this.id,'salRange')">
                        <c:if test="${allLevel!=null}" var="res">
                            <c:forEach items="${allLevel}" var="level">
                                <option value="${level.losal}-${level.hisal}">${level.title}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <span id="salRange">${allLevel[0].losal}-${allLevel[0].hisal}</span>

                </div>
                <div class="form_add">
                    <span class="list">税前月薪</span>
                    <input name="employee.sal" type="text"/>元
                </div>

                <div class="form_add">
                    <span class="list">照片</span>
                    <input type="file" name="photo" id="photo" onchange="preview(this)">
                    <div id="preview"></div>
                </div>
                <div class="form_add">
                    <span class="list">备注</span>
                    <textarea name="employee.note" cols="100" rows="5"></textarea>
                </div>
                <div class="form_add">
                    <span class="list"> </span>
                    <input name="" type="submit" value="添加"/>
                    <input type="button" name="button1" id="button1" value="返回"
                           onclick="history.go(-1)">
                </div>
                <input type="hidden" name="employee.status" value="1">
            </form>
        </div>
    </div>
</div>
</body>
</html>

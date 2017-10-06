<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String logoutUrl = basePath + "/AdminLoginFrontServlet/logout" ;
    String updatePasswordUrl = basePath + "/pages/back/admin/password_update.jsp" ;
%>
<html>
<body>
<div class="nav-top">
    <span>个人后台管理系统</span>
    <div class="nav-topright">
        <p>上次登陆时间：${fadmin.lastdate} 登陆IP：${fadmin.ip}</p>
        <span>您好：${fadmin.aid}</span><span onclick="logout('<%=logoutUrl%>')"><button>注销</button></span>
    </div>
</div>
<div class="nav-down">
    <div class="leftmenu1">
        <div class="menu-oc"><img src="images/menu-all.png"/></div>
        <ul>
            <li>
                <a class="a_list a_list1">全局设置</a>
                <div class="menu_list menu_list_first">
                    <a class="lista_first" href="<%=updatePasswordUrl%>">密码修改</a>

                </div>
            </li>
            <li>
                <a class="a_list a_list2">员工基本信息</a>
                <div class="menu_list">
                    <a href="addemp.html">员工入职</a>
                    <a href="emplist.html">员工列表</a>
                    <a href="Position.html">职位变更</a>
                    <a href="salary.html">待遇变更</a>
                </div>
            </li>
            <li>
                <a class="a_list a_list3">培训管理</a>
                <div class="menu_list">
                    <a href="addcourse.html">发布课程</a>
                    <a href="managecourse.html">课程管理</a>


                </div>
            </li>

        </ul>
    </div>
    <div class="leftmenu2">
        <div class="menu-oc1"><img src="images/menu-all.png"/></div>
        <ul>
            <li>
                <a class="j_a_list j_a_list1"></a>
                <div class="j_menu_list j_menu_list_first">
                    <span class="sp1"><i></i>全局设置</span>
                    <a class="lista_first" href="<%=updatePasswordUrl%>">安全设置</a>

                </div>
            </li>
            <li>
                <a class="j_a_list j_a_list2"></a>
                <div class="j_menu_list">
                    <span class="sp2"><i></i>员工基本信息</span>
                    <a href="addemp.html">员工入职</a>
                    <a href="emplist.html">员工列表</a>
                    <a href="Position.html">职位变更</a>
                    <a href="salary.html">待遇变更</a>
                </div>
            </li>
            <li>
                <a class="j_a_list j_a_list3"></a>
                <div class="j_menu_list">
                    <span class="sp3"><i></i>培训管理</span>
                    <a href="addcourse.html">发布课程</a>
                    <a href="managecourse.html">课程管理</a>
                </div>
            </li>
        </ul>
    </div>
    <div class="rightcon">
        <div class="right_con">
            <p style="text-align:left; margin-top:50px">右侧内容自适应哦！我是左对齐</p>
            <p style="text-align:center">右侧内容自适应哦！我是居中</p>
            <p style="text-align:right">右侧内容自适应哦！我是右对齐</p>
            <h1>我是标题1。。。</h1>
            <h2>我是标题2。。。</h2>
            <h3>我是标题3。。。</h3>
            <h4>我是标题4。。。</h4>
            <h5>我是标题5。。。</h5>
        </div>
    </div>
</div>
</body>
</html>

package servlet;

import factory.ServiceBackFactory;
import util.servlet.DispatcherServlet;
import vo.Dept;

import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet(name = "DeptBackServlet", urlPatterns = "/pages/back/admin/dept/DeptBackServlet/*")
public class DeptBackServlet extends DispatcherServlet {
    private final String insertValidate = "dept.dname|";
    Dept dept = new Dept();

    public Dept getDept() {
        return dept;
    }

    String insert() {
        try {
            title = "部门";
            System.out.println(dept);
            if (ServiceBackFactory.getDeptServiceBackImpl().insert(dept)) {
                setMsgAndUrl("insert.success.msg", "back.dept.list.servlet");
            } else {
                setMsgAndUrl("insert.failure.msg", "back.dept.list.servlet");
            }
            title = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forwardPath;
    }

    String insertPre() {
        return "back.dept.insert.page";
    }

    String list() {
        try {
            List<Dept> allDept = ServiceBackFactory.getDeptServiceBackImpl().list();
            StringBuffer validateItem = new StringBuffer();
            for (Dept x : allDept) {
                validateItem.append(x.getDid()).append(",");
            }
            validateItem.delete(validateItem.length() - 1, validateItem.length());
            request.setAttribute("validateItem", validateItem);
            request.getSession().setAttribute("allDept", allDept);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "back.dept.list.page";
    }


    String update() {
        List<Dept> list = (List<Dept>) request.getSession().getAttribute("allDept");
        Enumeration<String> enumeration = request.getParameterNames();
        int count = 0;
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            if (parameterName.startsWith("dept-dname")) {
                Dept d = new Dept();
                d.setDid(Integer.valueOf(parameterName.substring("dept-dname".length())));
                d.setDname(request.getParameter(parameterName));
                d.setCurrent(Integer.valueOf(request.getParameter("dept-current" + d.getDid())));
                try {
                    if (ServiceBackFactory.getDeptServiceBackImpl().update(d)) {
                        count += 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        title = "部门批量";
        if (count == list.size()) {
            setMsgAndBack("update.success.msg");
        } else {
            setMsgAndBack("update.failure.msg");
        }

        return forwardPath;
    }

    String delete() {
        title = "部门批量";
        String[] ids = request.getParameterValues("checkItem");
        if (ids != null) {
            Set<Integer> set = new HashSet<>();
            for (String x : ids) {
                set.add(Integer.valueOf(x));
            }
            try {
                if (ServiceBackFactory.getDeptServiceBackImpl().delete(set)) {
                    setMsgAndBack("delete.success.msg");
                } else {
                    setMsgAndBack("delete.failure.msg");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setMsgAndBack("delete.failure.all");
        }
        title = null;
        return forwardPath;
    }

    @Override
    public String getUploadDirectory() {
        return null;
    }



}

package servlet;

import factory.ServiceFrontFactory;
import util.servlet.DispatcherServlet;
import vo.Admin;
import vo.Employee;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

@WebServlet(name = "EmployeeFrontServlet", urlPatterns = "/pages/front/employee/EmployeeFrontServlet/*")
public class EmployeeFrontServlet extends DispatcherServlet {
    public Employee employee = new Employee();

    public Employee getEmployee() {
        return employee;
    }

    String list() {
        selectItems = "雇员编号:eid|雇员名字:ename|雇员职位:job|雇员工资:sal";
        handleSplit("front.employee.list.servlet");
        try {
            Map<String, Object> map = ServiceFrontFactory.getEmployeeServiceFrontImpl().list(currentPage, lineSize, column, keyWord);
            request.setAttribute("allEmployee", map.get("allEmployee"));
            request.setAttribute("allRecorders", map.get("allCount"));
            request.setAttribute("allDept", map.get("allDept"));
            request.setAttribute("allLevel", map.get("allLevel"));
            request.setAttribute("allJobs", map.get("allJobs"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "front.employee.list.page";
    }

    String listStatus() {

        return "front.employee.list.page";
    }

    String insert() {
        String photoName = createSingleFileName();
        save(photoName);
        employee.setPhoto(photoName);
        employee.setAid((Admin) request.getSession().getAttribute("fadmin"));
        try {
            title = "雇员";
            if (ServiceFrontFactory.getEmployeeServiceFrontImpl().insert(employee)) {
                setMsgAndUrl("insert.success.msg", "front.employee.list.servlet");
            } else {
                setMsgAndUrl("insert.failure.msg", "front.employee.list.servlet");
            }
        } catch (Exception e) {
            setMsgAndUrl("insert.failure.msg", "front.employee.list.servlet");
            e.printStackTrace();
        }
        return forwardPath;
    }

    String insertPre() {
        try {
            request.setAttribute("allJobs", ServiceFrontFactory.getEmployeeServiceFrontImpl().insertPre().get("allJobs"));
            request.setAttribute("allLevel", ServiceFrontFactory.getEmployeeServiceFrontImpl().insertPre().get("allLevel"));
            request.setAttribute("allDept", ServiceFrontFactory.getEmployeeServiceFrontImpl().insertPre().get("allDept"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "front.employee.insert.page";
    }

    String update() throws Exception {
        title = "雇员信息";
        if (request.getSession().getAttribute("fadmin") == null) {
            title="雇员信息修改失败,";
            setMsgAndUrl("haveNoLogin.msg","login.page");
        } else {
            Map<Integer, Object> list = autoSetSameVo();
            int x = 0;
            for (Object vo : list.values()) {
                String photoName = createSingleFileName();
                save(photoName);
                ((Employee) vo).setPhoto(photoName);
                if (ServiceFrontFactory.getEmployeeServiceFrontImpl().update((Employee) vo)) {
                    x++;
                }

            }
            if (x == list.size()) {
                setMsgAndBack("update.success.msg");
            } else {
                setMsgAndBack("update.failure.msg");
            }
        }

        return forwardPath;
    }

    @Override
    public String getUploadDirectory() {
        return "/upload/employee/";
    }

}


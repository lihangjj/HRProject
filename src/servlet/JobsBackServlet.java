package servlet;

import factory.ServiceBackFactory;
import util.servlet.DispatcherServlet;
import vo.Jobs;

import javax.servlet.annotation.WebServlet;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "JobsBackServlet", urlPatterns = "/pages/back/admin/jobs/JobsBackServlet/*")
public class JobsBackServlet extends DispatcherServlet {
    private Jobs jobs = new Jobs();

    public Jobs getJobs() {
        return jobs;
    }

    String insert() {
        try {
            title = "职位";
            if (ServiceBackFactory.getJobsServiceBackImpl().insert(jobs)) {
                setMsgAndUrl("insert.success.msg", "back.jobs.list.servlet");
            } else {
                setMsgAndUrl("insert.failure.msg", "back.jobs.list.servlet");
            }
            title = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forwardPath;
    }


    String list() {
        try {
            List<Jobs> allJobs = ServiceBackFactory.getJobsServiceBackImpl().list();
            StringBuffer validateItem = new StringBuffer();
            for (Jobs x : allJobs) {
                validateItem.append(x.getJid()).append(",");
            }
            validateItem.delete(validateItem.length() - 1, validateItem.length());
            request.setAttribute("validateItem", validateItem);
            request.getSession().setAttribute("allJobs", allJobs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "back.jobs.list.page";
    }

    String delete() {
        String[] ids = request.getParameterValues("checkItem");
        title = "职位批量";
        if (ids != null) {
            Set<Integer> set = new HashSet<>();
            for (String x : ids) {
                set.add(Integer.valueOf(x));
            }
            try {
                if (ServiceBackFactory.getJobsServiceBackImpl().delete(set)) {
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

        return forwardPath;
    }

    String update() {
        List<Jobs> allJobs = (List<Jobs>) request.getSession().getAttribute("allJobs");
        Enumeration<String> enumeration = request.getParameterNames();
        int count = 0;
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            if (parameterName.startsWith("jobs-title")) {
                Jobs vo = new Jobs();
                vo.setTitle(request.getParameter(parameterName));
                vo.setJid(Integer.valueOf(parameterName.substring("jobs-title".length())));
                vo.setNote(request.getParameter("jobs-note" + vo.getJid()));
                try {
                    if (ServiceBackFactory.getJobsServiceBackImpl().update(vo)) {
                        count += 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        title = "职位批量";
        if (count == allJobs.size()) {
            setMsgAndBack("update.success.msg");
        } else {
            setMsgAndBack("update.failure.msg");
        }

        return forwardPath;
    }


    @Override
    public String getUploadDirectory() {
        return null;
    }


}

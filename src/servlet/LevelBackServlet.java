package servlet;

import factory.ServiceBackFactory;
import util.servlet.DispatcherServlet;
import vo.Level;

import javax.servlet.annotation.WebServlet;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "LevelBackServlet", urlPatterns = "/pages/back/admin/level/LevelBackServlet/*")
public class LevelBackServlet extends DispatcherServlet {
    private Level level = new Level();

    public Level getLevel() {
        return level;
    }

    String insert() {
        try {
            title = "工资等级";
            if (ServiceBackFactory.getLevelServiceBackImpl().insert(level)) {
                setMsgAndUrl("insert.success.msg", "back.level.list.servlet");
            } else {
                setMsgAndUrl("insert.failure.msg", "back.level.list.servlet");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forwardPath;
    }

    String delete() {
        String[] ids = request.getParameterValues("checkItem");
        title = "工资等级批量";
        if (ids != null) {
            Set<Integer> set = new HashSet<>();
            for (String x : ids) {
                set.add(Integer.valueOf(x));
            }
            try {
                if (ServiceBackFactory.getLevelServiceBackImpl().delete(set)) {
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

    String list() {
        try {
            List<Level> allLevel = ServiceBackFactory.getLevelServiceBackImpl().list();
            StringBuffer validateItem = new StringBuffer();
            for (Level x : allLevel) {
                validateItem.append(x.getLevid()).append(",");
            }
            validateItem.delete(validateItem.length() - 1, validateItem.length());
            request.setAttribute("validateItem", validateItem);

            request.getSession().setAttribute("allLevel", allLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "back.level.list.page";
    }


    String update() {
        List<Level> allLevel = (List<Level>) request.getSession().getAttribute("allLevel");
        Enumeration<String> enumeration = request.getParameterNames();
        int count = 0;
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            if (parameterName.startsWith("level-title")) {
                Level vo = new Level();
                vo.setTitle(request.getParameter(parameterName));
                vo.setLevid(Integer.valueOf(parameterName.substring("level-title".length())));
                vo.setLosal(Double.valueOf(request.getParameter("level-losal" + vo.getLevid())));
                vo.setHisal(Double.valueOf(request.getParameter("level-hisal" + vo.getLevid())));
                try {
                    if (ServiceBackFactory.getLevelServiceBackImpl().update(vo)) {
                        count += 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        title = "工资等级信息批量";
        if (count == allLevel.size()) {
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

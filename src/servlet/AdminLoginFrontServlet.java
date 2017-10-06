package servlet;

import factory.ServiceFrontFactory;
import util.MD5Code;
import util.servlet.DispatcherServlet;
import vo.Admin;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

@WebServlet(urlPatterns = "/AdminLoginFrontServlet/*")
public class AdminLoginFrontServlet extends DispatcherServlet {
    private final String loginValidate = "admin.aid|admin.password";
    Admin admin = new Admin();
    int admintype;

    public Admin getAdmin() {
        return admin;
    }

    String login() {
        if (checkCode()) {
            try {
                admin.setPassword(new MD5Code().getMD5ofStr(admin.getPassword()));
                admin.setIp(request.getRemoteAddr());
                Map<String, Object> map = ServiceFrontFactory.getAdminServiceFrontImpl().findLogin(admin);
                boolean flag = (boolean) map.get("flag");
                Admin admin = (Admin) map.get("admin");

                if (flag) {
                    admin.setIp(request.getRemoteAddr());
                    admintype = admin.getType();
                    if (admintype == 1) {
                        request.getSession().setAttribute("badmin", admin);
                        setMsgAndUrl("admin.login.success", "back.admin.index");
                    } else if (admintype == 0) {
                        setMsgAndUrl("admin.login.success", "front.admin.index");
                        request.getSession().setAttribute("fadmin", admin);
                    }
                } else {
                    setMsgAndUrl("admin.login.failure", "login.page");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setMsgAndUrl("admin.code.error", "login.page");
        }


        return forwardPath;
    }

    String logout() {
        request.getSession().invalidate();
        setMsgAndUrl("invalidate.success", "login.page");
        String url = (String) request.getAttribute("url");
        String msg = (String) request.getAttribute("msg");
        return forwardPath;
    }

    String updatePassword() {
        //先把密码加密处理
        String oldPassword = new MD5Code().getMD5ofStr(request.getParameter("oldPassword"));
        String newPassword = request.getParameter("newPassword");
        if (admin.getPassword().equals(newPassword)) {//两次密码输入一致
            Admin admin = (Admin) request.getSession().getAttribute(admintype == 1 ? "badmin" : "fadmin");
            if (oldPassword.equals(admin.getPassword())) {//原密码输入正确
                //设置新密码，同样需要加密
                admin.setPassword(new MD5Code().getMD5ofStr(this.admin.getPassword()));
                try {
                    String adminType = admin.getType() == 1 ? "back.admin.index" : "front.admin.index";
                    if (ServiceFrontFactory.getAdminServiceFrontImpl().doUpdatePassword(admin)) {
                        title = "密码";
                        setMsgAndUrl("update.success.msg", adminType);
                    } else {
                        setMsgAndUrl("update.failure.msg", adminType);
                    }
                    title = "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                setMsgAndBack("updatePassword.failure.oldPasswordError");
            }
        } else {
            setMsgAndBack("updatePassword.failure.notsame");
        }


        return forwardPath;
    }


    @Override
    public String getUploadDirectory() {
        return null;
    }



}

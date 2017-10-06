package service.front.impl;

import dbc.DatabaseConnection;
import factory.DAOFactory;
import service.front.IAdminServiceFront;
import vo.Admin;
import vo.Adminlogs;
import vo.Groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class AdminServiceFrontImpl implements IAdminServiceFront {
    private Connection conn = new DatabaseConnection().getConnection();

    @Override
    public Map<String, Object> findLogin(Admin vo) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            //1.进行用户的登录验证
            Admin admin = DAOFactory.getAdminDAOImpl(conn).findLogin(vo);
            if (admin != null) {
                Date currentDate = new Date();
                Adminlogs adminlogs = new Adminlogs();
                adminlogs.setAid(admin);
                adminlogs.setIp(admin.getIp());
                adminlogs.setLogindate(currentDate);
                if (DAOFactory.getAdminlogsDAOImpl(conn).doCreate(adminlogs)) {//增加登陆日志成功
                    if (DAOFactory.getAdminDAOImpl(conn).doUpdateLastDate(admin.getAid(), currentDate)) {//修改管理员最后登陆日期成功
                        //判断是哪种管理员
                        if (admin.getType() == 1) {//是后台管理员，就查询出所有权限组，和权限,设置权限组
                            List<Groups> groupsList = DAOFactory.getGroupsDAOImpl(conn).findAllByRoleId(admin.getRid().getRid());
                            //查询出权限，和设置权限
                            Iterator<Groups> iterator = groupsList.iterator();
                            while (iterator.hasNext()) {
                                Groups groups = iterator.next();
                                //每个权限组设置其权限
                                groups.setActions(DAOFactory.getActionDAOImpl(conn).findAllByGroups(groups.getGid()));
                            }
                            //把已经设置了权限的权限组设置到角色中;
                            admin.getRid().setGroups(groupsList);
                        }

                    }
                }
            }
            map.put("admin", admin);
            map.put("flag", admin != null);
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return null;
    }

    @Override
    public boolean doUpdatePassword(Admin vo) throws Exception {
        try {
            return DAOFactory.getAdminDAOImpl(conn).doUpdate(vo);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

}

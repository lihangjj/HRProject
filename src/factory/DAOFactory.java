package factory;

import dao.impl.*;

import java.sql.Connection;

public class DAOFactory {
    public static AdminDAOImpl getAdminDAOImpl(Connection conn) throws Exception {
        return new AdminDAOImpl(conn);
    }

    public static ActionDAOImpl getActionDAOImpl(Connection conn) throws Exception {
        return new ActionDAOImpl(conn);
    }

    public static AdminlogsDAOImpl getAdminlogsDAOImpl(Connection conn) throws Exception {
        return new AdminlogsDAOImpl(conn);
    }

    public static GroupsDAOImpl getGroupsDAOImpl(Connection conn) throws Exception {
        return new GroupsDAOImpl(conn);
    }

    public static DeptDAOImpl getDeptDAOImpl(Connection conn) throws Exception {
        return new DeptDAOImpl(conn);
    }

    public static LevelDAOImpl getLevelDAOImpl(Connection conn) throws Exception {
        return new LevelDAOImpl(conn);
    }

    public static JobsDAOImpl getJobsDAOImpl(Connection conn) throws Exception {
        return new JobsDAOImpl(conn);
    }

    public static EmployeeDAOImpl getEmployeeDAOImpl(Connection conn) throws Exception {
        return new EmployeeDAOImpl(conn);
    }
}

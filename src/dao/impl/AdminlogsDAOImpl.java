package dao.impl;

import dao.AbstractDAOImpl;
import dao.IAdminlogsDAO;
import vo.Adminlogs;

import java.sql.Connection;

public class AdminlogsDAOImpl extends AbstractDAOImpl<Integer, Adminlogs> implements IAdminlogsDAO {
    public AdminlogsDAOImpl(Connection conn) throws Exception {
        super(conn, new Adminlogs());
    }

}

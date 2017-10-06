package dao.impl;

import dao.AbstractDAOImpl;
import dao.IAdminDAO;
import vo.Admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class AdminDAOImpl extends AbstractDAOImpl<String, Admin> implements IAdminDAO {
    public AdminDAOImpl(Connection conn) throws Exception {
        super(conn, new Admin());
    }

    @Override
    public Admin findLogin(Admin vo) throws Exception {
        return findById(vo.getAid());
    }

    @Override
    public boolean doUpdateLastDate(String aid, Date date) throws SQLException {
        sql = "update admin set lastdate=? where aid=?";
        pre = conn.prepareStatement(sql);
        pre.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
        pre.setString(2, aid);
        return pre.executeUpdate() > 0;
    }

}

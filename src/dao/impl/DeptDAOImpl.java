package dao.impl;

import dao.AbstractDAOImpl;
import dao.IDeptDAO;
import vo.Dept;

import java.sql.Connection;

public class DeptDAOImpl extends AbstractDAOImpl<Integer, Dept> implements IDeptDAO {
    public DeptDAOImpl(Connection conn) throws Exception {
        super(conn, new Dept());
    }

    @Override
    public Dept findByName(String dname) throws Exception {
        bufSelectAll();
        buf.append(" where dname=?");
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, dname);
        res = pre.executeQuery();
        Dept dept = null;
        while (res.next()) {
            dept = new Dept();
            voSet(dept);
        }
        return dept;
    }

    @Override
    public Dept findByNameExceptID(String dname, Integer id) throws Exception {
        bufSelectAll();
        buf.append(" where dname=? and did!=" + id);
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, dname);
        res = pre.executeQuery();
        Dept dept = null;
        while (res.next()) {
            dept = new Dept();
            voSet(dept);
        }
        return dept;
    }
}

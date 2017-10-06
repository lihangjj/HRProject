package dao.impl;

import dao.AbstractDAOImpl;
import dao.IEmployeeDAO;
import vo.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class EmployeeDAOImpl extends AbstractDAOImpl<Integer, Employee> implements IEmployeeDAO {
    public EmployeeDAOImpl(Connection conn) throws Exception {
        super(conn, new Employee());
    }

    @Override
    public boolean doUpdateStatus(Integer status, List<Integer> ids, Date date) throws SQLException {
        buf.setLength(0);
        buf.append("update set " + tableName + " status=" + status + "," + (status == 1 ? "indate=?" : "outdate=?") + " where eid in(");
        for (Integer x : ids) {
            buf.append(x).append(",");
        }
        buf.delete(buf.length() - 1, buf.length()).append(")");
        pre = conn.prepareStatement(buf.toString());
        pre.setTimestamp(1, new Timestamp(date.getTime()));
        return pre.executeUpdate() > 0;
    }
}

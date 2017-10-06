package dao.impl;

import dao.AbstractDAOImpl;
import dao.IActionDAO;
import vo.Action;
import vo.Groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActionDAOImpl extends AbstractDAOImpl<Integer, Action> implements IActionDAO {
    public ActionDAOImpl(Connection conn) throws Exception {
        super(conn, new Action());
    }

    @Override
    public List<Action> findAllByGroups(Integer gid) throws SQLException {
        sql = "select actid,gid,title,url from action where gid =" + gid;
        pre = conn.prepareStatement(sql);
        res = pre.executeQuery();
        List<Action> list = new ArrayList<>();
        while (res.next()) {
            Action a = new Action();
            a.setActid(res.getInt(1));
            Groups g = new Groups();
            g.setGid(res.getInt(2));
            a.setGid(g);
            a.setTitle(res.getString(3));
            a.setUrl(res.getString(4));
            list.add(a);
        }
        return list;
    }

}

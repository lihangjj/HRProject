package dao.impl;

import dao.AbstractDAOImpl;
import dao.IGroupsDAO;
import vo.Groups;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GroupsDAOImpl extends AbstractDAOImpl<Integer, Groups> implements IGroupsDAO {
    public GroupsDAOImpl(Connection conn) throws Exception {
        super(conn, new Groups());
    }

    @Override
    public List<Groups> findAllByRoleId(Integer rid) throws Exception {
        sql = "select gid,title,note from groups where gid in(select gid from role_groups where rid=" + rid + ")";
        pre = conn.prepareStatement(sql);
        res = pre.executeQuery();
        List<Groups> list = new ArrayList<>();
        while (res.next()) {
            Groups groups = new Groups();
            groups.setGid(res.getInt(1));
            groups.setTitle(res.getString(2));
            groups.setNote(res.getString(3));
            list.add(groups);
        }
        return list;
    }


}

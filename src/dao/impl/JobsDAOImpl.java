package dao.impl;

import dao.AbstractDAOImpl;
import dao.IJobsDAO;
import vo.Jobs;

import java.sql.Connection;

public class JobsDAOImpl extends AbstractDAOImpl<Integer, Jobs> implements IJobsDAO {
    public JobsDAOImpl(Connection conn) throws Exception {
        super(conn, new Jobs());
    }


    @Override
    public Jobs findByTitle(String title) throws Exception {
        bufSelectAll();
        buf.append(" where title=?");
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, title);
        res = pre.executeQuery();
        Jobs jobs = null;
        while (res.next()) {
            jobs = new Jobs();
            voSet(jobs);
        }
        return jobs;
    }

    @Override
    public Jobs findByTitleAndGid(String title, Integer jid) throws Exception {
        bufSelectAll();
        buf.append(" where title=? and jid=" + jid);
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, title);
        res = pre.executeQuery();
        Jobs jobs = null;
        while (res.next()) {
            jobs = new Jobs();
            voSet(jobs);
        }
        return jobs;
    }
}

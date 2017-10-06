package service.back.impl;

import dbc.DatabaseConnection;
import factory.DAOFactory;
import service.back.IJobsServiceBack;
import vo.Jobs;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public class JobsServiceBackImpl implements IJobsServiceBack {
    private Connection conn = new DatabaseConnection().getConnection();

    @Override
    public boolean insert(Jobs vo) throws Exception {
        try {
            if (DAOFactory.getJobsDAOImpl(conn).findByTitle(vo.getTitle()) == null && vo.getTitle() != null) {
                return DAOFactory.getJobsDAOImpl(conn).doCreate(vo);
            }
            return false;
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public List<Jobs> list() throws Exception {
        try {
            return DAOFactory.getJobsDAOImpl(conn).findAll();
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean update(Jobs vo) throws Exception {
        try {
            return DAOFactory.getJobsDAOImpl(conn).doUpdate(vo);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean delete(Set<Integer> ids) throws Exception {
        try {
            return DAOFactory.getJobsDAOImpl(conn).doRemoveBatch(ids);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }
}

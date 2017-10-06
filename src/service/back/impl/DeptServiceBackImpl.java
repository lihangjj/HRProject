package service.back.impl;

import dbc.DatabaseConnection;
import factory.DAOFactory;
import service.back.IDeptServiceBack;
import vo.Dept;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public class DeptServiceBackImpl implements IDeptServiceBack {
    private Connection conn = new DatabaseConnection().getConnection();

    @Override
    public boolean insert(Dept vo) throws Exception {
        try {
            if (DAOFactory.getDeptDAOImpl(conn).findByName(vo.getDname()) == null && vo.getDname() != null) {
                return DAOFactory.getDeptDAOImpl(conn).doCreate(vo);
            }
            return false;

        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public List<Dept> list() throws Exception {
        try {
            return DAOFactory.getDeptDAOImpl(conn).findAll();
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean update(Dept vo) throws Exception {
        try {
            return DAOFactory.getDeptDAOImpl(conn).doUpdate(vo);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean delete(Set<Integer> ids) throws Exception {
        try {
            return DAOFactory.getDeptDAOImpl(conn).doRemoveBatch(ids);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }
}

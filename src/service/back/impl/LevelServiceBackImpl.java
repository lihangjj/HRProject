package service.back.impl;

import dbc.DatabaseConnection;
import factory.DAOFactory;
import service.back.ILevelServiceBack;
import vo.Level;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public class LevelServiceBackImpl implements ILevelServiceBack {
    private Connection conn = new DatabaseConnection().getConnection();

    @Override
    public boolean insert(Level vo) throws Exception {
        try {
            if (DAOFactory.getLevelDAOImpl(conn).findByTitle(vo.getTitle()) == null && vo.getTitle() != null) {
                return DAOFactory.getLevelDAOImpl(conn).doCreate(vo);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
        return false;
    }

    @Override
    public List<Level> list() throws Exception {
        try {
            return DAOFactory.getLevelDAOImpl(conn).findAll();
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean update(Level vo) throws Exception {
        try {
            return DAOFactory.getLevelDAOImpl(conn).doUpdate(vo);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean delete(Set<Integer> ids) throws Exception {
        try {
            return DAOFactory.getLevelDAOImpl(conn).doRemoveBatch(ids);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }
}

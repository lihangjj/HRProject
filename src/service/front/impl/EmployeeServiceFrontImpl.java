package service.front.impl;

import dbc.DatabaseConnection;
import factory.DAOFactory;
import service.front.IEmployeeServiceFront;
import vo.Dept;
import vo.Employee;
import vo.Jobs;
import vo.Level;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeServiceFrontImpl implements IEmployeeServiceFront {
    Connection conn = new DatabaseConnection().getConnection();
    List<Dept> allDept = DAOFactory.getDeptDAOImpl(conn).findAll();
    List<Level> allLevel = DAOFactory.getLevelDAOImpl(conn).findAll();
    List<Jobs> allJobs = DAOFactory.getJobsDAOImpl(conn).findAll();

    public EmployeeServiceFrontImpl() throws Exception {
    }

    @Override
    public Map<String, Object> insertPre() throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("allLevel", allLevel);
            map.put("allDept", allDept);
            map.put("allJobs", allJobs);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
        return map;
    }

    @Override
    public boolean insert(Employee vo) throws Exception {
        try {
            //判断是否有相同的雇员编号
            if (DAOFactory.getEmployeeDAOImpl(conn).findById(vo.getEid()) != null) {
                return false;
            } else {

                //判断工资等级是否符合标准
                Double losal = Double.valueOf(vo.getLevid().getTitle().split("\\-")[0]);
                System.out.println(losal);
                Level level = DAOFactory.getLevelDAOImpl(conn).findByLosal(losal);
                if (level.getLosal() <= vo.getSal() && vo.getSal() <= level.getHisal()) {
                    vo.setLevid(level);
                    vo.setDid(DAOFactory.getDeptDAOImpl(conn).findByName(vo.getDname()));
                    vo.setJid(DAOFactory.getJobsDAOImpl(conn).findByTitle(vo.getJob()));
                } else {
                    return false;
                }
                if (DAOFactory.getEmployeeDAOImpl(conn).doCreate(vo)) {
                    Dept dept = DAOFactory.getDeptDAOImpl(conn).findByName(vo.getDname());
                    dept.setCurrent(dept.getCurrent() + 1);
                    return DAOFactory.getDeptDAOImpl(conn).doUpdate(dept);
                }
                return false;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public Map<String, Object> updatePre(int eid) throws Exception {
        return null;
    }

    @Override
    public boolean updatePre(Employee vo) throws Exception {
        return false;
    }

    @Override
    public boolean updateOut() throws Exception {
        return false;
    }

    @Override
    public boolean update(Employee vo) throws Exception {
        try {
            //判断工资等级是否符合标准
            System.out.println(vo.getLevid().getTitle() + "nihao你好");
            Level level = DAOFactory.getLevelDAOImpl(conn).findByTitle(vo.getLevid().getTitle());
            if (level.getLosal() <= vo.getSal() && vo.getSal() <= level.getHisal()) {
                vo.setLevid(level);
                vo.setDid(DAOFactory.getDeptDAOImpl(conn).findByName(vo.getDname()));
                vo.setJid(DAOFactory.getJobsDAOImpl(conn).findByTitle(vo.getJob()));
            } else {
                return false;
            }
            return DAOFactory.getEmployeeDAOImpl(conn).doUpdate(vo);
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            map.put("allLevel", allLevel);
            map.put("allDept", allDept);
            map.put("allJobs", allJobs);
            List<Employee> list = DAOFactory.getEmployeeDAOImpl(conn).findAllSplit(currentPage, lineSize, column, keyWord);
            for (Employee x : list) {
                x.setLevid(DAOFactory.getLevelDAOImpl(conn).findById(x.getLevid().getLevid()));
            }
            map.put("allEmployee", list);
            map.put("allCount", DAOFactory.getEmployeeDAOImpl(conn).getAllCount(column, keyWord));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return map;
    }

    @Override
    public Map<String, Object> listByStatus(int status, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            map.put("allLevel", allLevel);
            map.put("allDept", allDept);
            map.put("allJobs", allJobs);
            map.put("allEmployee", DAOFactory.getEmployeeDAOImpl(conn).findAllSplitByStatus(status, currentPage, lineSize, column, keyWord));
            map.put("allCount", DAOFactory.getEmployeeDAOImpl(conn).getAllCountByStatus(status, column, keyWord));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return map;
    }
}

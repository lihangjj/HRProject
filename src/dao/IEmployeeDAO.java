package dao;

import util.dao.IDAO;
import vo.Employee;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IEmployeeDAO extends IDAO<Integer, Employee> {
    /**
     * @param status  雇员的离职或入职
     * @param ids     要更新的雇员ids
     * @param outDate 雇员离职或入职的日期
     * @return
     * @throws SQLException
     */
    boolean doUpdateStatus(Integer status, List<Integer> ids, Date outDate) throws SQLException;


}

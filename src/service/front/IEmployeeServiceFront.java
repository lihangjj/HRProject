package service.front;

import vo.Employee;

import java.util.Map;

public interface IEmployeeServiceFront {
    Map<String, Object> insertPre() throws Exception;

    boolean insert(Employee vo) throws Exception;

    /**
     * 根据雇员编号查询出修改前的部门，职位，工资级别，雇员的信息
     *
     * @param eid
     * @return
     * @throws Exception
     */
    Map<String, Object> updatePre(int eid) throws Exception;

    /**
     * 要根据雇员的编号查询出部门的名称，职位名称，工资范围是否合理，入职离职的时间，和顾源数据的更新。
     *
     * @param vo
     * @return
     * @throws Exception
     */
    boolean updatePre(Employee vo) throws Exception;

    boolean updateOut() throws Exception;
    boolean update(Employee vo) throws Exception;

    Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception;

    Map<String, Object> listByStatus(int status, int currentPage, int lineSize, String column, String keyWord) throws Exception;


}

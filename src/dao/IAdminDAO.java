package dao;

import util.dao.IDAO;
import vo.Admin;

import java.sql.SQLException;
import java.util.Date;

public interface IAdminDAO extends IDAO<String, Admin> {
    Admin findLogin(Admin vo) throws Exception;
    boolean doUpdateLastDate(String aid, Date date) throws SQLException;
}

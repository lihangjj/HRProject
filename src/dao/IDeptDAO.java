package dao;

import util.dao.IDAO;
import vo.Dept;

public interface IDeptDAO extends IDAO<Integer, Dept> {
    Dept findByName(String dname) throws Exception;
    Dept findByNameExceptID(String dname,Integer id) throws Exception;
}

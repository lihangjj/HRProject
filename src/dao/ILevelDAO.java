package dao;

import util.dao.IDAO;
import vo.Level;

public interface ILevelDAO extends IDAO<Integer, Level> {
    Level findByTitle(String title) throws Exception;
    Level findByLosal(Double losal) throws Exception;

    Level findByTitleAndlevid(String title, Integer levid) throws Exception;
}

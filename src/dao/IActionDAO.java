package dao;

import util.dao.IDAO;
import vo.Action;

import java.sql.SQLException;
import java.util.List;

public interface IActionDAO extends IDAO<Integer, Action> {
    List<Action> findAllByGroups(Integer gids)throws SQLException;
}

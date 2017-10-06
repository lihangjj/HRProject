package dao;

import util.dao.IDAO;
import vo.Groups;

import java.util.List;

public interface IGroupsDAO extends IDAO<Integer, Groups> {
    List<Groups> findAllByRoleId(Integer rid) throws Exception;

}

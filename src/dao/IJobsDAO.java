package dao;

import util.dao.IDAO;
import vo.Jobs;

public interface IJobsDAO extends IDAO<Integer, Jobs> {
    Jobs findByTitle(String title) throws Exception;

    Jobs findByTitleAndGid(String title, Integer jid) throws Exception;
}

package service.front;

import vo.Admin;

import java.util.Map;

public interface IAdminServiceFront {
    Map<String, Object> findLogin(Admin vo) throws Exception;
    boolean doUpdatePassword(Admin vo)throws Exception;
}

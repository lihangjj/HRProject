package factory;

import service.front.impl.AdminServiceFrontImpl;
import service.front.impl.EmployeeServiceFrontImpl;

public class ServiceFrontFactory {
    public static AdminServiceFrontImpl getAdminServiceFrontImpl() {
        return new AdminServiceFrontImpl();
    }
    public static EmployeeServiceFrontImpl getEmployeeServiceFrontImpl() throws Exception {
        return new EmployeeServiceFrontImpl();
    }
}

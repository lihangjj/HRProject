package factory;

import service.back.impl.DeptServiceBackImpl;
import service.back.impl.JobsServiceBackImpl;
import service.back.impl.LevelServiceBackImpl;

public class ServiceBackFactory {
    public static DeptServiceBackImpl getDeptServiceBackImpl() {
        return new DeptServiceBackImpl();
    }

    public static LevelServiceBackImpl getLevelServiceBackImpl() {
        return new LevelServiceBackImpl();
    }

    public static JobsServiceBackImpl getJobsServiceBackImpl() {
        return new JobsServiceBackImpl();
    }
}

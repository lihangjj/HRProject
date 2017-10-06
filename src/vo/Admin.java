package vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Admin implements Serializable {
    private String aid, password, ip;
    private Integer type, flag;
    private Date lastdate;
    private Role rid;
    private List<Adminlogs> logs;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public Role getRid() {
        return rid;
    }

    public void setRid(Role rid) {
        this.rid = rid;
    }

    public List<Adminlogs> getLogs() {
        return logs;
    }

    public void setLogs(List<Adminlogs> logs) {
        this.logs = logs;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }
}

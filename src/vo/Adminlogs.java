package vo;

import java.io.Serializable;
import java.util.Date;

public class Adminlogs implements Serializable {
    private Integer alid;
    private Admin aid;
    private Date logindate;
    private String ip;

    public Integer getAlid() {
        return alid;
    }

    public void setAlid(Integer alid) {
        this.alid = alid;
    }

    public Admin getAid() {
        return aid;
    }

    public void setAid(Admin aid) {
        this.aid = aid;
    }

    public Date getLogindate() {
        return logindate;
    }

    public void setLogindate(Date logindate) {
        this.logindate = logindate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

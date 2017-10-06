package vo;

import java.util.Date;

public class Salary {
    private Integer sid, oldlevid, newlevid, eid;
    private String aid, reason, note;
    private Date cdate;
    private Double oldsal, newsal;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getOldlevid() {
        return oldlevid;
    }

    public void setOldlevid(Integer oldlevid) {
        this.oldlevid = oldlevid;
    }

    public Integer getNewlevid() {
        return newlevid;
    }

    public void setNewlevid(Integer newlevid) {
        this.newlevid = newlevid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Double getOldsal() {
        return oldsal;
    }

    public void setOldsal(Double oldsal) {
        this.oldsal = oldsal;
    }

    public Double getNewsal() {
        return newsal;
    }

    public void setNewsal(Double newsal) {
        this.newsal = newsal;
    }
}

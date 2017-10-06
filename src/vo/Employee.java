package vo;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    private String ename, sex, idcard, dname, job, school, profession, photo, note, edu;
    private Admin aid = new Admin();
    private Dept did = new Dept();
    private Level levid = new Level();
    private Jobs jid = new Jobs();
    private Date birthday, grad, indate, outdate;
    private Integer eid, status;
    private Double sal;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public Admin getAid() {
        return aid;
    }

    public void setAid(Admin aid) {
        this.aid = aid;
    }

    public Dept getDid() {
        return did;
    }

    public void setDid(Dept did) {
        this.did = did;
    }

    public Level getLevid() {
        return levid;
    }

    public void setLevid(Level levid) {
        this.levid = levid;
    }

    public Jobs getJid() {
        return jid;
    }

    public void setJid(Jobs jid) {
        this.jid = jid;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getGrad() {
        return grad;
    }

    public void setGrad(Date grad) {
        this.grad = grad;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public Date getOutdate() {
        return outdate;
    }

    public void setOutdate(Date outdate) {
        this.outdate = outdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ename='" + ename + '\'' +
                ", sex='" + sex + '\'' +
                ", idcard='" + idcard + '\'' +
                ", dname='" + dname + '\'' +
                ", job='" + job + '\'' +
                ", school='" + school + '\'' +
                ", profession='" + profession + '\'' +
                ", photo='" + photo + '\'' +
                ", note='" + note + '\'' +
                ", edu='" + edu + '\'' +
                ", aid=" + aid +
                ", did=" + did +
                ", levid=" + levid +
                ", jid=" + jid +
                ", birthday=" + birthday +
                ", grad=" + grad +
                ", indate=" + indate +
                ", outdate=" + outdate +
                ", eid=" + eid +
                ", status=" + status +
                ", sal=" + sal +
                '}';
    }
}

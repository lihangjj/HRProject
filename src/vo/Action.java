package vo;

import java.io.Serializable;

public class Action implements Serializable {
    private Integer actid;
    private String title,url;
    private Groups gid;

    public Integer getActid() {
        return actid;
    }

    public void setActid(Integer actid) {
        this.actid = actid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Groups getGid() {
        return gid;
    }

    public void setGid(Groups gid) {
        this.gid = gid;
    }
}

package com.mutant.gmon.goldhand1;

import java.io.Serializable;

public class Upload implements Serializable {
    public String nam;
    public String downUrl;

    public Upload(String nam, String downUrl) {
        this.nam = nam;
        this.downUrl = downUrl;
    }

    public Upload(){

    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
}

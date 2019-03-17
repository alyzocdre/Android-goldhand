package com.mutant.gmon.goldhand1;

import java.io.Serializable;

public class UID implements Serializable {
    private String username;
    private String password1;
    private String mobile;
    private String birth;
    public UID() {};
    public UID(String username,String password1,String mobile,String birth) {
        this.username=username;
        this.password1=password1;
        this.mobile=mobile;
        this.birth=birth;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMobile(){
        return mobile;
    }
    public String getPassword1(){return password1;}
    public String getBirth(){return birth;}

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}

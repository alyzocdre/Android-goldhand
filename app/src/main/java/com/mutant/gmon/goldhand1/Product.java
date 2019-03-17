package com.mutant.gmon.goldhand1;

import java.io.Serializable;

public class Product implements Serializable {
    private String imgUrl;
    private String name;
    private String phone;
    public Product(){
        imgUrl = null;
        name = null;
        phone = null;
    }
    public Product(String imgUrl,String name,String phone){
        this.imgUrl = imgUrl;
        this.name = name;
        this.phone = phone;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {

        return imgUrl;
    }

}

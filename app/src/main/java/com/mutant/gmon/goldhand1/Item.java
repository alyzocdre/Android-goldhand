package com.mutant.gmon.goldhand1;

public class Item {

    public String text;
    public int resource_id;
    public Item(){;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getResource_id() {
        return resource_id;
    }

    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }

    public Item(String text, int resource_id){
        this.text = text;
        this.resource_id = resource_id;
    }
}


package com.soft.zkrn.weilin_application.Class;

import android.media.Image;

public class Gift {

    private int point;
    private String name;
    private String description;
    private int num;
    private Image image;

    public int getPoint() {
        return point;
    }
    public Image getImage(){
        return image;
    }
    public String getName(){
        return name;
    }
    public int getNum(){
        return num;
    }
    public String getDescription(){
        return description;
    }



    public Gift(int point, int num, String name, String description, Image image){
        this.point = point;
        this.num = num;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Gift(int point, int num, String name, Image image){
        this.point = point;
        this.num = num;
        this.name = name;
        this.description = "";
        this.image = image;
    }

    public Gift(int point, int num, String name){
        this.point = point;
        this.num = num;
        this.name = name;
        this.description = "";
        this.image = null;
    }

    public Gift(int point, int num, String name, String description){
        this.point = point;
        this.num = num;
        this.name = name;
        this.description = description;
        this.image = null;
    }
}

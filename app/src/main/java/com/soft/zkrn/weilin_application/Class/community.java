package com.soft.zkrn.weilin_application.Class;

public class community {

    private byte[] head_portrait;//图片类
    private String title;
    private String type;
    private String description;
    private int num;
    private int id;
    private String location;

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public byte[] getHead_portrait() {
        return head_portrait;
    }

    public int getNum() {
        return num;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
    //    public community()

    public community(int id,
                     String title,
                     String type,
                     int num,
                     String description,
                     String location,
                     byte[]image){
        this.head_portrait = image;
        this.id = id;
        this.num = num;
        this.location = location;
        this.title = title;
        this.type = type;
        this.description = description;
    }
}

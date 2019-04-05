package com.soft.zkrn.weilin_application;

public class community {

    private int head_portrait;//图片类
    /*
     * name为社区名称
     * type为社区类别
     * num为居民人数
     * location为社区位置
     */

    private String type;
    private String name;
    private int num;
    private long location;

//    public void get_data(){
//
//    }
    public String get_type(){
        return this.type;
    }

    public String get_name(){
        return this.name;
    }

    public int get_image(){
        return this.head_portrait;
    }

    public int get_num(){
        return this.num;
    }

    public long get_location(){
        return this.location;
    }

//    public community()

    public community(int image, int num, long location, String name, String type){
        this.head_portrait = image;
        this.num = num;
        this.location = location;
        this.name = name;
        this.type = type;

    }
}

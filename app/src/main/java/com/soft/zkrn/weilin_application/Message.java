package com.soft.zkrn.weilin_application;

public class Message {

    private long time;
    private String content;

    public long gettime() {
        return time;
    }

    public String getcontent() {
        return content;
    }

    Message(long time, String content){
        this.time = time;
        this.content = content;
    }
}

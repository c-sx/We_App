package com.soft.zkrn.weilin_application.Class;

public class Task {

    private int callId;//	int	任务id
    private int subId;//	int	发布人id
    private long subTime;//	date	发布时间
    private long endTime;//	date	截止时间
    private String callTitle;//	string	标题
    private String callDesp;//	string	描述
    private int callMoney;//	int	金额
    private String callNow;//	string	状态
    private int recId;//	int	接收人id
    private String subName;//	string	发布人昵称
    private String recName;//	string	接收人昵称
    private String callAddress;//	string	发布人地址

    public int getCallId() {
        return callId;
    }

    public int getCallMoney() {
        return callMoney;
    }

    public int getRecId() {
        return recId;
    }

    public int getSubId() {
        return subId;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getSubTime() {
        return subTime;
    }

    public String getCallAddress() {
        return callAddress;
    }

    public String getCallDesp() {
        return callDesp;
    }

    public String getCallNow() {
        return callNow;
    }

    public String getCallTitle() {
        return callTitle;
    }

    public String getRecName() {
        return recName;
    }

    public String getSubName() {
        return subName;
    }

    public void initTask(int callId,int subId,long subTime,long endTime,String callTitle,String callDesp,int callMoney,String callNow,int recId,String subName,String recName,String callAddress){
        this.callId = callId;
        this.subId = subId;
        this.subTime = subTime;
        this.endTime = endTime;
        this.callTitle = callTitle;
        this.callDesp = callDesp;
        this.callMoney = callMoney;
        this.callNow = callNow;
        this.recId = recId;
        this.subName = subName;
        this.recName = recName;
        this.callAddress = callAddress;
    }

    public void setCallId(int callId) {
        this.callId = callId;
    }

    public Task(){

    }

    public Task(int callId,int subId,long subTime,long endTime,String callTitle,String callDesp,int callMoney,String callNow,int recId,String subName,String recName,String callAddress){
        this.callId = callId;
        this.subId = subId;
        this.subTime = subTime;
        this.endTime = endTime;
        this.callTitle = callTitle;
        this.callDesp = callDesp;
        this.callMoney = callMoney;
        this.callNow = callNow;
        this.recId = recId;
        this.subName = subName;
        this.recName = recName;
        this.callAddress = callAddress;
    }
}

package com.soft.zkrn.weilin_application.GsonClass;

public class PublicData_UserBasic {
    private int uid;//	int	用户id
    private String userName;//	string	昵称
    private String userPassword;//	string	密码
    private String userPhonenumber;//	string	电话号码
    private String userDept;//	string	权限
    private String userSex;//	string	性别
    private String userDesp;//	string	个人描述
    private String userNamecheck;//	string	实名情况
    private int userCreditlevel;//	int	信用级别
    private String userMessagelevel;//	string	消息提示等级
    private int userPoint;//	int	积分

    public String getUserSex() {
        return userSex;
    }

    public String getUserPhonenumber() {
        return userPhonenumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserDesp() {
        return userDesp;
    }

    public String getUserNamecheck() {
        return userNamecheck;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserDept() {
        return userDept;
    }

    public String getUserMessagelevel() {
        return userMessagelevel;
    }

    public int getUserCreditlevel() {
        return userCreditlevel;
    }

    public int getUid() {
        return uid;
    }

    public int getUserPoint() {
        return userPoint;
    }
}

package com.soft.zkrn.weilin_application.GsonClass;

public class PublicData_CommunityBasic {

    private int comId;//	int	社区id
    private String comTitle;//	string	社区标题
    private String comCategory;//	string	社区种类
    private int comNumber;//	int	社区人数
    private String comDesp;//	string	社区描述
    private String comAddress;//	string	社区地址
    private byte[] comPicture;//	byte[]	社区头像

    public String getComTitle() {
        return comTitle;
    }

    public String getComDesp() {
        return comDesp;
    }

    public String getComCategory() {
        return comCategory;
    }

    public String getComAddress() {
        return comAddress;
    }

    public int getComNumber() {
        return comNumber;
    }

    public int getComId() {
        return comId;
    }

    public byte[] getComPicture() {
        return comPicture;
    }

}

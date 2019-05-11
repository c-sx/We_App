package com.soft.zkrn.weilin_application.GsonClass;

import java.util.List;

public class CommunityData_Search {
    private int code;//状态码
    private String msg;//提示信息
    private Extend extend;

    public class Extend{

        private Community community;

        public class Community{

            private int comId;//	int	社区id
            private String comTitle;//	string	社区标题
            private String comCategory;//	string	社区种类
            private int comNumber;//	int	社区人数
            private String comDesp;//	string	社区描述
            private String comAddress;//	string	社区地址
            private byte[] comPicture;//	byte[]	社区头像

            public byte[] getComPicture() {
                return comPicture;
            }

            public int getComId() {
                return comId;
            }

            public int getComNumber() {
                return comNumber;
            }

            public String getComAddress() {
                return comAddress;
            }

            public String getComCategory() {
                return comCategory;
            }

            public String getComDesp() {
                return comDesp;
            }

            public String getComTitle() {
                return comTitle;
            }


        }

        public Community getCommunity() {
            return community;
        }
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public Extend getExtend() {
        return extend;
    }
}

package com.soft.zkrn.weilin_application.GsonClass;

public class UserInformationData {
    private int code;//	int	状态码
    private String msg;//	string	提示信息

    private Extend extend;

    public class Extend{
        private User user;

        public class User{
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

            public int getUid() {
                return uid;
            }

            public String getUserDept() {
                return userDept;
            }

            public String getUserDesp() {
                return userDesp;
            }

            public int getUserCreditlevel() {
                return userCreditlevel;
            }

            public String getUserName() {
                return userName;
            }

            public String getUserPassword() {
                return userPassword;
            }

            public int getUserPoint() {
                return userPoint;
            }

            public String getUserNamecheck() {
                return userNamecheck;
            }

            public String getUserPhonenumber() {
                return userPhonenumber;
            }

            public String getUserMessagelevel() {
                return userMessagelevel;
            }

            public String getUserSex() {
                return userSex;
            }
        }

        public User getUser() {
            return user;
        }
    }

    public Extend getExtend() {
        return extend;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

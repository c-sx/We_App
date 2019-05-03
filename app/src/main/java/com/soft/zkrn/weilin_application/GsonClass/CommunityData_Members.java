package com.soft.zkrn.weilin_application.GsonClass;

import java.util.List;

public class CommunityData_Members {

    private int code;
    private String msg;
    private Extend extend;

    public class Extend{
        private List<User> users;

        private class User{
            private int uncId;//	int	社区与用户关系ID
            private int uId;//	int	用户ID
            private int cId;//	int	社区ID
            private PublicData_UserBasic userBasic;
            private PublicData_CommunityBasic communityBasic;

            public PublicData_UserBasic getUserBasic() {
                return userBasic;
            }

            public int getUncId() {
                return uncId;
            }

            public int getuId() {
                return uId;
            }

            public int getcId() {
                return cId;
            }

            public PublicData_CommunityBasic getCommunityBasic() {
                return communityBasic;
            }
        }

        public List<User> getUsers() {
            return users;
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

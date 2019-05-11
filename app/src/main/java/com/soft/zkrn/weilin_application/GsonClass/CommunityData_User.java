package com.soft.zkrn.weilin_application.GsonClass;

import java.util.List;

public class CommunityData_User {
    private int code;//状态码
    private String msg;//提示信息
    private Extend extend;

    public class Extend{

        private List<Communities> communities;

        public class Communities{
            private int uncId;//	int	社区与用户关系ID
            private int uId;//	int	用户ID
            private int cId;//	int	社区ID
            private PublicData_UserBasic userBasic;
            private PublicData_CommunityBasic communityBasic;

            public int getcId() {
                return cId;
            }

            public int getuId() {
                return uId;
            }

            public int getUncId() {
                return uncId;
            }

            public PublicData_CommunityBasic getCommunityBasic() {
                return communityBasic;
            }

            public PublicData_UserBasic getUserBasic() {
                return userBasic;
            }
        }

        public List<Communities> getCommunities() {
            return communities;
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

package com.soft.zkrn.weilin_application.GsonClass;

import java.util.List;

public class UserData_JoinedCommunities {

    private int code;//状态码
    private String msg;//提示信息
    private Extend extend;

    public class Extend{

        private List<Communities> communities;

        public class Communities{

            private int uncId;// null,
            private int uId;// null,
            private int cId;// 1,
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

            public PublicData_UserBasic getUserBasic() {
                return userBasic;
            }

            public PublicData_CommunityBasic getCommunityBasic() {
                return communityBasic;
            }
        }

        public List<Communities> getCommunities() {
            return communities;
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

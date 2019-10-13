package com.soft.zkrn.weilin_application.GsonClass;

import java.util.List;

public class TaskData_PublishedOrReceived {
    private int code;//	int	状态码
    private String msg;//	string	提示信息
    private Extend extend;

    public class Extend{
        private List<Calls> calls;

        public class Calls{
            private int callId;//	int	任务id
            private int subId;//	int	发布人id
            private long subTime;//	date	发布时间
            private long endTime;//	date	截止时间
            private String callTitle;//	string	标题
            private String callDesp;//	string	描述
            private int callMoney;//	int	金额
            private String callNow;//	string	状态，1.未接取，2.未完成，3，未评价，4.已评价
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

        }

        public List<Calls> getCalls() {
            return calls;
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

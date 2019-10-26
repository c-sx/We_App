package com.soft.zkrn.weilin_application.GsonClass;

public class DeviceData_Map {
    private int code;//	int	状态码
    private String msg;//	string	提示信息
    private Extend extend;

    public class Extend{
        private Request request;

        public class Request{
            private int rid;//	int	消息id
            private String notifytype;//	int	电信云推送的消息类型
            private String requestid;//	date	不知道干嘛用
            private String deviceid;//	date	发送数据的设备在电信云上id
            private String gatewayid;//	    string	不知道干嘛用
            private String serviceid;//	    string	设备上报的服务类型id
            private String servicetype;//	string	设备上报的服务类型名称
            private String devicenumber;//	int	上报数据的设备的设备号
            private String dimension;//	    string	维度
            private String nsflag;//	int	南北纬标志
            private String longitude;//	    string	经度
            private String weflag;//	    string	东西经标志
            private String eventtime;//	    string	时间

            public int getRid() {
                return rid;
            }
            public String getGatewayid() {
                return gatewayid;
            }
            public String getServiceid() {
                return serviceid;
            }
            public String getDimension() {
                return dimension;
            }
            public String getServicetype() {
                return servicetype;
            }
            public String getEventtime() {
                return eventtime;
            }
            public String getLongitude() {
                return longitude;
            }
            public String getWeflag() {
                return weflag;
            }
            public String getDeviceid() {
                return deviceid;
            }
            public String getNotifytype() {
                return notifytype;
            }
            public String getRequestid() {
                return requestid;
            }
            public String getDevicenumber() {
                return devicenumber;
            }
            public String getNsflag() {
                return nsflag;
            }
        }

        public Request getRequest() {
            return request;
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

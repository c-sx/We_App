package com.soft.zkrn.weilin_application.GsonClass;

import java.util.ArrayList;
import java.util.List;

public class TaskData {

    private int code;//状态码
    private String msg;//提示信息
    private Extend extend;

    public class Extend {

        private Pageinfo pageInfo;

        public class Pageinfo {

            private int pageNum;//当前页
            private int pageSize;//每页的数量
            private int size;//当前页的数量
            private int  startRow;//当前页面第一个元素在数据库中的行号
            private int  endRow;//当前页面最后一个元素在数据库中的行号
            private long total; //总记录数
            private int pages; //总页数
            private List<TaskList> list;//具体范围
            private int prePage;// 前一页
            private int nextPage;// 后一页
            private boolean isFirstPage;// 是否为第一页
            private boolean isLastPage;// 是否为最后一页
            private boolean hasPreviousPage;// 是否有前一页
            private boolean hasNextPage;// 是否有后一页
            private int navigatePages;// 导航页码数
            private int[] navigatepageNums;// 所有导航页号
            private int navigateFirstPage;// 导航第一页
            private int navigateLastPage;// 导航最后一页
            private int firstPage;// 第一页
            private int lastPage;// 最后一页

            public class TaskList{

                private int callId;// 任务id
                private int subId;// 发布人id
                private long subTime ;// 发布时间
                private long endTime;// 截止时间
                private String callTitle;//  标题
                private String callDesp;//  描述
                private int callMoney;// 金额
                private String callNow;//状态
                private int recId;// 接收人id
                private String subName;// 发布人昵称
                private String recName;// 接收人昵称
                private String callAddress;//地址

                public int getcallId(){return callId; }// 任务id
                public int getsubId(){return subId;}// 发布人id
                public long getsubTime(){return subTime;} // 发布时间
                public long getendTime(){return endTime;}// 截止时间
                public String getcallTitle(){return callTitle;}//  标题
                public String getcallDesp(){return callDesp;}//  描述
                public int getcallMoney(){return callMoney;}// 金额
                public String getcallNow(){return callNow;}//状态
                public int getrecId(){return recId;}// 接收人id
                public String getsubName(){return subName;}// 发布人昵称
                public String getrecName(){return recName;}// 接收人昵称
                public String getCallAddress(){return callAddress;}
            }

            public int getpageNum(){return pageNum;}//当前页
            public int getpageSize(){return pageSize;}//每页的数量
            public int getsize(){return size;}//当前页的数量
            public int getstartRow(){return startRow;}//当前页面第一个元素在数据库中的行号
            public int getendRow(){return endRow;}//当前页面最后一个元素在数据库中的行号
            public long gettotal(){return total;} //总记录数
            public int getpages(){return pages;} //总页数
            public int getprePage(){return prePage;}// 前一页
            public int getnextPage(){return nextPage;}// 后一页
            public boolean getisFirstPage(){return isFirstPage;}// 是否为第一页
            public boolean getisLastPage(){return isLastPage;}// 是否为最后一页
            public boolean gethasPreviousPage(){return hasPreviousPage;}// 是否有前一页
            public boolean gethasNextPage(){return hasNextPage;}// 是否有后一页
            public int getnavigatePages(){return navigatePages;}// 导航页码数
            public int getnavigateFirstPage(){return navigateFirstPage;}// 导航第一页
            public int getnavigateLastPage(){return navigateLastPage;}// 导航最后一页
            public int getfirstPage(){return firstPage;}// 第一页
            public int getlastPage(){return lastPage;}// 最后一页
            public int[] getnavigatepageNums(){return this.navigatepageNums;}
            public List<TaskList> getlist(){return this.list;}

            Pageinfo(){
                this.list = new ArrayList<TaskList>();
            }
        }

        public Pageinfo getpageinfo(){return this.pageInfo;}

        Extend(){
            this.pageInfo = new Pageinfo();
        }

    }

    public int getcode() {return code;}
    public String getmsg(){return msg;}//提示信
    public Extend getextend(){return extend;}

    TaskData(){
        this.extend = new Extend();
    }
}

package com.soft.zkrn.weilin_application;

public class Calls {
//    /**
//     * code : 100
//     * msg : 处理成功！
//     * extend : {"pageInfo":{"pageNum":1,"pageSize":5,"size":4,"startRow":1,"endRow":4,"total":4,"pages":1,"list":[{"callId":3,"subId":2,"subTime":1545549302000,"endTime":1545549302000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":2,"subName":"white","recName":"asdf"},{"callId":4,"subId":2,"subTime":1545571900000,"endTime":1545571900000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":2,"subName":"white","recName":"asdf"},{"callId":6,"subId":2,"subTime":1545575839000,"endTime":1545575856000,"callTitle":"123","callDesp":"qianga","callMoney":24,"callNow":"y","recId":5,"subName":"white","recName":"whte"},{"callId":7,"subId":2,"subTime":1545513712000,"endTime":1545513712000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":5,"subName":"white","recName":"whte"}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":5,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
//     */
//
//    private int code;
//    private String msg;
//    private ExtendBean extend;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    @Override
//    public String toString() {
//        return "Calls{" +
//                "code=" + code +
//                ", msg='" + msg + '\'' +
//                ", extend=" + extend +
//                '}';
//    }
//
//    public ExtendBean getExtend() {
//        return extend;
//    }
//
//    public void setExtend(ExtendBean extend) {
//        this.extend = extend;
//    }
//
//    public static class ExtendBean {
//        /**
//         * pageInfo : {"pageNum":1,"pageSize":5,"size":4,"startRow":1,"endRow":4,"total":4,"pages":1,"list":[{"callId":3,"subId":2,"subTime":1545549302000,"endTime":1545549302000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":2,"subName":"white","recName":"asdf"},{"callId":4,"subId":2,"subTime":1545571900000,"endTime":1545571900000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":2,"subName":"white","recName":"asdf"},{"callId":6,"subId":2,"subTime":1545575839000,"endTime":1545575856000,"callTitle":"123","callDesp":"qianga","callMoney":24,"callNow":"y","recId":5,"subName":"white","recName":"whte"},{"callId":7,"subId":2,"subTime":1545513712000,"endTime":1545513712000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":5,"subName":"white","recName":"whte"}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":5,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}
//         */
//
//        private PageInfoBean pageInfo;
//
//        public PageInfoBean getPageInfo() {
//            return pageInfo;
//        }
//
//        public void setPageInfo(PageInfoBean pageInfo) {
//            this.pageInfo = pageInfo;
//        }
//
//        @Override
//        public String toString() {
//            return "ExtendBean{" +
//                    "pageInfo=" + pageInfo +
//                    '}';
//        }
//
//        public static class PageInfoBean {
//            @Override
//            public String toString() {
//                return "PageInfoBean{" +
//                        "pageNum=" + pageNum +
//                        ", pageSize=" + pageSize +
//                        ", size=" + size +
//                        ", startRow=" + startRow +
//                        ", endRow=" + endRow +
//                        ", total=" + total +
//                        ", pages=" + pages +
//                        ", prePage=" + prePage +
//                        ", nextPage=" + nextPage +
//                        ", isFirstPage=" + isFirstPage +
//                        ", isLastPage=" + isLastPage +
//                        ", hasPreviousPage=" + hasPreviousPage +
//                        ", hasNextPage=" + hasNextPage +
//                        ", navigatePages=" + navigatePages +
//                        ", navigateFirstPage=" + navigateFirstPage +
//                        ", navigateLastPage=" + navigateLastPage +
//                        ", lastPage=" + lastPage +
//                        ", firstPage=" + firstPage +
//                        ", list=" + list +
//                        ", navigatepageNums=" + navigatepageNums +
//                        '}';
//            }
//
//            /**
//             * pageNum : 1
//             * pageSize : 5
//             * size : 4
//             * startRow : 1
//             * endRow : 4
//             * total : 4
//             * pages : 1
//             * list : [{"callId":3,"subId":2,"subTime":1545549302000,"endTime":1545549302000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":2,"subName":"white","recName":"asdf"},{"callId":4,"subId":2,"subTime":1545571900000,"endTime":1545571900000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":2,"subName":"white","recName":"asdf"},{"callId":6,"subId":2,"subTime":1545575839000,"endTime":1545575856000,"callTitle":"123","callDesp":"qianga","callMoney":24,"callNow":"y","recId":5,"subName":"white","recName":"whte"},{"callId":7,"subId":2,"subTime":1545513712000,"endTime":1545513712000,"callTitle":"123","callDesp":"asd","callMoney":12,"callNow":"y","recId":5,"subName":"white","recName":"whte"}]
//             * prePage : 0
//             * nextPage : 0
//             * isFirstPage : true
//             * isLastPage : true
//             * hasPreviousPage : false
//             * hasNextPage : false
//             * navigatePages : 5
//             * navigatepageNums : [1]
//             * navigateFirstPage : 1
//             * navigateLastPage : 1
//             * lastPage : 1
//             * firstPage : 1
//             */
//
//            private int pageNum;
//            private int pageSize;
//            private int size;
//            private int startRow;
//            private int endRow;
//            private int total;
//            private int pages;
//            private int prePage;
//            private int nextPage;
//            private boolean isFirstPage;
//            private boolean isLastPage;
//            private boolean hasPreviousPage;
//            private boolean hasNextPage;
//            private int navigatePages;
//            private int navigateFirstPage;
//            private int navigateLastPage;
//            private int lastPage;
//            private int firstPage;
//            private List<ListBean> list;
//            private List<Integer> navigatepageNums;
//
//            public int getPageNum() {
//                return pageNum;
//            }
//
//            public void setPageNum(int pageNum) {
//                this.pageNum = pageNum;
//            }
//
//            public int getPageSize() {
//                return pageSize;
//            }
//
//            public void setPageSize(int pageSize) {
//                this.pageSize = pageSize;
//            }
//
//            public int getSize() {
//                return size;
//            }
//
//            public void setSize(int size) {
//                this.size = size;
//            }
//
//            public int getStartRow() {
//                return startRow;
//            }
//
//            public void setStartRow(int startRow) {
//                this.startRow = startRow;
//            }
//
//            public int getEndRow() {
//                return endRow;
//            }
//
//            public void setEndRow(int endRow) {
//                this.endRow = endRow;
//            }
//
//            public int getTotal() {
//                return total;
//            }
//
//            public void setTotal(int total) {
//                this.total = total;
//            }
//
//            public int getPages() {
//                return pages;
//            }
//
//            public void setPages(int pages) {
//                this.pages = pages;
//            }
//
//            public int getPrePage() {
//                return prePage;
//            }
//
//            public void setPrePage(int prePage) {
//                this.prePage = prePage;
//            }
//
//            public int getNextPage() {
//                return nextPage;
//            }
//
//            public void setNextPage(int nextPage) {
//                this.nextPage = nextPage;
//            }
//
//            public boolean isIsFirstPage() {
//                return isFirstPage;
//            }
//
//            public void setIsFirstPage(boolean isFirstPage) {
//                this.isFirstPage = isFirstPage;
//            }
//
//            public boolean isIsLastPage() {
//                return isLastPage;
//            }
//
//            public void setIsLastPage(boolean isLastPage) {
//                this.isLastPage = isLastPage;
//            }
//
//            public boolean isHasPreviousPage() {
//                return hasPreviousPage;
//            }
//
//            public void setHasPreviousPage(boolean hasPreviousPage) {
//                this.hasPreviousPage = hasPreviousPage;
//            }
//
//            public boolean isHasNextPage() {
//                return hasNextPage;
//            }
//
//            public void setHasNextPage(boolean hasNextPage) {
//                this.hasNextPage = hasNextPage;
//            }
//
//            public int getNavigatePages() {
//                return navigatePages;
//            }
//
//            public void setNavigatePages(int navigatePages) {
//                this.navigatePages = navigatePages;
//            }
//
//            public int getNavigateFirstPage() {
//                return navigateFirstPage;
//            }
//
//            public void setNavigateFirstPage(int navigateFirstPage) {
//                this.navigateFirstPage = navigateFirstPage;
//            }
//
//            public int getNavigateLastPage() {
//                return navigateLastPage;
//            }
//
//            public void setNavigateLastPage(int navigateLastPage) {
//                this.navigateLastPage = navigateLastPage;
//            }
//
//            public int getLastPage() {
//                return lastPage;
//            }
//
//            public void setLastPage(int lastPage) {
//                this.lastPage = lastPage;
//            }
//
//            public int getFirstPage() {
//                return firstPage;
//            }
//
//            public void setFirstPage(int firstPage) {
//                this.firstPage = firstPage;
//            }
//
//            public List<ListBean> getList() {
//                return list;
//            }
//
//            public void setList(List<ListBean> list) {
//                this.list = list;
//            }
//
//            public List<Integer> getNavigatepageNums() {
//                return navigatepageNums;
//            }
//
//            public void setNavigatepageNums(List<Integer> navigatepageNums) {
//                this.navigatepageNums = navigatepageNums;
//            }
//
//            public static class ListBean {
//                /**
//                 * callId : 3
//                 * subId : 2
//                 * subTime : 1545549302000
//                 * endTime : 1545549302000
//                 * callTitle : 123
//                 * callDesp : asd
//                 * callMoney : 12
//                 * callNow : y
//                 * recId : 2
//                 * subName : white
//                 * recName : asdf
//                 */
//
//                private int callId;
//                private int subId;
//                private long subTime;
//                private long endTime;
//                private String callTitle;
//                private String callDesp;
//                private int callMoney;
//                private String callNow;
//                private int recId;
//                private String subName;
//                private String recName;
//
//                public int getCallId() {
//                    return callId;
//                }
//
//                public void setCallId(int callId) {
//                    this.callId = callId;
//                }
//
//                public int getSubId() {
//                    return subId;
//                }
//
//                public void setSubId(int subId) {
//                    this.subId = subId;
//                }
//
//                public long getSubTime() {
//                    return subTime;
//                }
//
//                public void setSubTime(long subTime) {
//                    this.subTime = subTime;
//                }
//
//                public long getEndTime() {
//                    return endTime;
//                }
//
//                public void setEndTime(long endTime) {
//                    this.endTime = endTime;
//                }
//
//                public String getCallTitle() {
//                    return callTitle;
//                }
//
//                public void setCallTitle(String callTitle) {
//                    this.callTitle = callTitle;
//                }
//
//                public String getCallDesp() {
//                    return callDesp;
//                }
//
//                public void setCallDesp(String callDesp) {
//                    this.callDesp = callDesp;
//                }
//
//                public int getCallMoney() {
//                    return callMoney;
//                }
//
//                public void setCallMoney(int callMoney) {
//                    this.callMoney = callMoney;
//                }
//
//                public String getCallNow() {
//                    return callNow;
//                }
//
//                public void setCallNow(String callNow) {
//                    this.callNow = callNow;
//                }
//
//                public int getRecId() {
//                    return recId;
//                }
//
//                public void setRecId(int recId) {
//                    this.recId = recId;
//                }
//
//                public String getSubName() {
//                    return subName;
//                }
//
//                public void setSubName(String subName) {
//                    this.subName = subName;
//                }
//
//                public String getRecName() {
//                    return recName;
//                }
//
//                public void setRecName(String recName) {
//                    this.recName = recName;
//                }
//
//                @Override
//                public String toString() {
//                    return "ListBean{" +
//                            "callId=" + callId +
//                            ", subId=" + subId +
//                            ", subTime=" + subTime +
//                            ", endTime=" + endTime +
//                            ", callTitle='" + callTitle + '\'' +
//                            ", callDesp='" + callDesp + '\'' +
//                            ", callMoney=" + callMoney +
//                            ", callNow='" + callNow + '\'' +
//                            ", recId=" + recId +
//                            ", subName='" + subName + '\'' +
//                            ", recName='" + recName + '\'' +
//                            '}';
//                }
//            }
//        }
//    }
}

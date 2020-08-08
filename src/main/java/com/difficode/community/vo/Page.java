package com.difficode.community.vo;


public class Page<T> {
    private int pageNum;
    private int pageSize;
    private int totalCount;
    private int totalPage;

    public int getPageNum() {
        if (pageNum<1){
            pageNum=1;
        }else if (pageNum>this.getTotalPage()){
            pageNum=this.getTotalPage();
        }
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getFrom(){
        int from = pageNum - 2;
        return from>0?from:1;
    }
    public int getTo(){
        int to = pageNum + 2;
        return to<totalPage?to:totalPage;
    }

}

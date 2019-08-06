package com.weston.study.core.common.contract.query;

/**
 * 分页接口
 */
public class Pageable {

    private static final int DEFAULT_CUR_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 50;

    protected int curPage = DEFAULT_CUR_PAGE;// 查询第几页，注意：页数是从第1页开始的
    protected int pageSize = DEFAULT_PAGE_SIZE;// 每页记录数

    public void setCurPage(int curPage) {
        this.curPage = curPage <= 0 ? DEFAULT_CUR_PAGE : curPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public int getCurPage() {
        return curPage <= 0 ? DEFAULT_CUR_PAGE : curPage;
    }

    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public int getIndex() {
        int startIndex = (curPage - 1) * pageSize;
        return (startIndex < 0) ? 0 : startIndex;
    }
}
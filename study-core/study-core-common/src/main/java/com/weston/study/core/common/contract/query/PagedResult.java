package com.weston.study.core.common.contract.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResult<T> {

    private Integer curPage;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalNum;
    private List<T> data;

    public PagedResult(Integer curPage, Integer pageSize, Integer totalNum, List<T> data) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = totalNum / pageSize + (totalNum % pageSize > 0 ? 1 : 0);
        this.data = data;
    }
}

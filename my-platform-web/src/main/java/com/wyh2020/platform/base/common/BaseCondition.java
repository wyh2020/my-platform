package com.wyh2020.platform.base.common;

import lombok.Getter;
import lombok.Setter;



/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午2:30
 */
public class BaseCondition {

    @Getter
    private int pageSize = Integer.MAX_VALUE;

    @Getter
    private int pageNo;

    @Setter
    @Getter
    private int skipResults = 0;

    @Setter
    @Getter
    private String orderBy;

    @Setter
    @Getter
    private String beforOrAfter;


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        skipResults = pageSize * pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        skipResults = pageSize * pageNo;
    }

}

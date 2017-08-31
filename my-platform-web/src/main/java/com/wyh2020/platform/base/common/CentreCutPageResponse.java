package com.wyh2020.platform.base.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午1:48
 */
public class CentreCutPageResponse<T> extends CentreListResponse<T>  {
    /**
     * 序列化ID.
     */
    private static final long serialVersionUID = 5888709607809204814L;

    /**
     * 记录总数；
     */
    @Setter
    @Getter
    private int totalCount;

    /**
     * 当前页；
     */
    @Setter
    @Getter
    private int pageNum;

    /**
     * 每页条数；
     */
    @Setter
    @Getter
    private int pageSize;

}

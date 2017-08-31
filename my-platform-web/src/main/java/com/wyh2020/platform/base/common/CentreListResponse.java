package com.wyh2020.platform.base.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午1:50
 * @param <T> 返回值结果所带出来的列表数据对象
 */
public class CentreListResponse<T> extends CentreSerializable{
    /**
     * 序列化ID.
     */
    private static final long serialVersionUID = -7628952830016632166L;

    /**
     * 记录列表;
     */
    @Setter
    @Getter
    private List<T> dataList;

}

package com.wyh2020.platform.base.common;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午1:51
 * @version 1.0
 * @since 1.0
 * 中心序列化数据对象基类；<br>
 * 实现了序列化接口<br>
 * 覆盖了toString方法，采用Json格式描述Bean对象；
 */
public class CentreSerializable implements Serializable {

    /**
     * 序列化ID.
     */
    private static final long serialVersionUID = -9006756275701089041L;

    /**
     * @return Bean的字符串描述；
     */
    @Override
    public final String toString() {
        return JSON.toJSONString(this);
    }

}

package com.wyh2020.platform.base.common;

import lombok.Data;

/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午2:30
 */

@Data
public class SortInfo {
    public String field;

    public String sort;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SortInfo{");
        sb.append("field='").append(field).append('\'');
        sb.append(", sort='").append(sort).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

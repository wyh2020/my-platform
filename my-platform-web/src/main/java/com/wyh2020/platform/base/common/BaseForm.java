package com.wyh2020.platform.base.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午2:30
 */
@Data
public class BaseForm {

    @ApiModelProperty("分页每页条数")
    public Integer pageSize = 20;

    @ApiModelProperty("分页页码")
    public Integer pageNum = 0;

    @ApiModelProperty(value = "排序字段")
    public List<SortInfo> sortInfos;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseForm{");
        sb.append("pageSize=").append(pageSize);
        sb.append(", pageNum=").append(pageNum);
        sb.append('}');
        return sb.toString();
    }
}

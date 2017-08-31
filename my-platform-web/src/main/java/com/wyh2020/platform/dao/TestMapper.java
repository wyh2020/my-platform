package com.wyh2020.platform.dao;


import com.wyh2020.platform.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with wyh.
 * Date: 2017/7/2
 * Time: 上午12:50
 */
@Mapper
public interface TestMapper {

    List<Test> getTestList();

    /**
     * 获取sum值
     */
    int selectSum(int id);

    /**
     * 更新sum值
     */
    void updateSum1(Test test);


    /**
     * 更新sum值
     */
    void updateSum2(Test test);
}

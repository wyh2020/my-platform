package com.wyh2020.platform.service;


import com.wyh2020.platform.domain.Test;

import java.util.List;

/**
 * Created with wyh.
 * Date: 2017/7/2
 * Time: 上午12:52
 */
public interface TestService {

    List<Test> getTestList();

    int querySum(int id);

    void updateSum1(Test test);

    void updateSum2(Test test);
}

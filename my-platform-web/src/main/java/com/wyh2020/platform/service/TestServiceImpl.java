package com.wyh2020.platform.service;


import com.wyh2020.platform.dao.TestMapper;
import com.wyh2020.platform.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with wyh.
 * Date: 2017/7/2
 * Time: 上午1:03
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> getTestList() {

        List<Test> testList = this.testMapper.getTestList();

        return testList;
    }

    @Override
    public int querySum(int id) {
        return this.testMapper.selectSum(id);
    }

    @Override
    public void updateSum1(Test test) {
        this.testMapper.updateSum1(test);
    }

    @Override
    public void updateSum2(Test test) {
        this.testMapper.updateSum2(test);
    }


}

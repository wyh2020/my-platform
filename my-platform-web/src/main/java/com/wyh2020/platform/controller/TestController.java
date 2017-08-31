package com.wyh2020.platform.controller;


import com.wyh2020.platform.domain.Test;
import com.wyh2020.platform.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with wyh.
 * Date: 2017/7/2
 * Time: 上午12:47
 */
@RestController
@RequestMapping("/test")
@Api(value = "TestController", description = "测试接口")
public class TestController {

    @Autowired
    private TestService testService;


    Logger logger = Logger.getLogger(TestController.class);

    Lock lock = new ReentrantLock();


    @ApiOperation(value = "查询Test列表", notes = "查询Test列表", httpMethod = "GET")
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTestList() {
        logger.info("进入接口啦啦啦啦啦啦");
        List<Test> testList = testService.getTestList();

        return testList.toString();
    }


    @ApiOperation(value = "测试sum数值", notes = "测试sum值是否减对了", httpMethod = "GET")
    @RequestMapping(value = "/testSum", method = {RequestMethod.GET, RequestMethod.POST})
    public String getLatestSum() {
        int id = 1;
        int sum = testService.querySum(id);
        System.out.println("sum======" + sum);
        Test test = new Test();
        test.setId(id);
        test.setSum(sum - 1);
        testService.updateSum1(test);
        return "减库前的库存为====" + sum;
    }


    @ApiOperation(value = "使用锁来测试减库存并发", notes = "重入锁--ReentrantLock", httpMethod = "GET")
    @RequestMapping(value = "/testSum1", method = {RequestMethod.GET, RequestMethod.POST})
    public String getLatestSum1() {

        lock.lock();
        int id = 1;
        int sum = testService.querySum(id);
        System.out.println("sum======" + sum);
        Test test = new Test();
        test.setId(id);
        test.setSum(sum - 1);
        testService.updateSum1(test);
        lock.unlock();

        return "The Latest Sum1 ===" + sum;
    }

    @ApiOperation(value = "使用synchronized来测试减库存并发", notes = "synchronized是Java中的关键字，是一种同步锁", httpMethod = "GET")
    @RequestMapping(value = "/testSum2", method = {RequestMethod.GET, RequestMethod.POST})
    public String getLatestSum2() {

        synchronized (this) {
            int id = 1;
            int sum = testService.querySum(id);
            System.out.println("sum======" + sum);
            Test test = new Test();
            test.setId(id);
            test.setSum(sum - 1);
            testService.updateSum1(test);
        }

        return "The is testing keyword 'synchronized'";
    }


    @ApiOperation(value = "在sql语句加for update来测试减库存并发", notes = "在sql语句加for update来测试减库存并发", httpMethod = "GET")
    @RequestMapping(value = "/testSum3", method = {RequestMethod.GET, RequestMethod.POST})
    public String getLatestSum3() {

        /**
         * 这样测试会有问题，
         * 猜测问题点：testService.querySum() testService.updateSum 两个动作为2个单独的事务
         * 可能解决方案：在test.updateSum()中直接进行sum-1
         */

        /*int id = 1;
        int sum = testService.querySum(id);
        System.out.println("sum======" + sum);
        Test test = new Test();
        test.setId(id);
        test.setSum(sum - 1);
        testService.updateSum(test);
        return "在sql语句加for update来测试减库存并发";*/


        /**
         * 正确的测试方案
         */
        Test test = new Test();
        test.setId(1);
        testService.updateSum2(test);
        return "88888888888888";

    }


    @ApiOperation(value = "使用多线程来测试减库存并发", notes = "使用多线程来测试减库存并发", httpMethod = "GET")
    @RequestMapping(value = "/testSum4", method = {RequestMethod.GET, RequestMethod.POST})
    public String getLatestSum4() {

        /**
         * 这里还有问题 还需改进
         */
        Thread thread1 = new Thread(new MyThread());
        thread1.start();
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread2 = new Thread(new MyThread());
        thread2.start();
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "使用多线程来测试减库存并发ssss";
    }


    class MyThread implements Runnable{
        public void run(){
            System.out.println( "1111111使用多线程来测试减库存并发");
            int id = 1;
            int sum = testService.querySum(id);
            System.out.println("sum======" + sum);
            Test test = new Test();
            test.setId(id);
            test.setSum(sum - 1);
            testService.updateSum1(test);
        }
    }

}

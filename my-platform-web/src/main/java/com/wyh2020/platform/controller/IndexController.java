package com.wyh2020.platform.controller;

import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with wyh.
 * Date: 2017/7/5
 * Time: 上午10:35
 */
@Controller
@Api(value = "index", description = "前端路由")
public class IndexController {


    Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping(value = {"/", "/index", "login"}, method = {RequestMethod.GET})
    public String index(){

        ModelAndView index = new ModelAndView("index");

        return "/index";
    }

}

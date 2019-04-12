package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.model.DemoModel;
import com.ritonelion.bootopen.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HelloSpringBootController
{
    private final DemoService demoService;

    @Autowired
    public HelloSpringBootController(DemoService _demoService)
    {
        demoService = _demoService;
    }

    @RequestMapping("/hello")
    public String hello()
    {
        return "hello";
    }

    @RequestMapping("/test")
    public String test()
    {
        return "test";
    }

    @RequestMapping("/dbtest")
    @ResponseBody
    public List<DemoModel> allDemo()
    {
        return demoService.selectAll();
    }

}

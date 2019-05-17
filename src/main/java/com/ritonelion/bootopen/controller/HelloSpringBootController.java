package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.model.DemoModel;
import com.ritonelion.bootopen.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/post")
    public String post()
    {
        return "post";
    }

    @PostMapping("/post")
    public String show()
    {
        return "result";
    }

}

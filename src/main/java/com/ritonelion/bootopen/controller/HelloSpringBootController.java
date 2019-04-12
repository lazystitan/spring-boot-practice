package com.ritonelion.bootopen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloSpringBootController
{
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
}

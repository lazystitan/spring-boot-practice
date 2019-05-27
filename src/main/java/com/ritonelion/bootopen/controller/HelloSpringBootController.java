package com.ritonelion.bootopen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloSpringBootController
{
    @RequestMapping("/hello")
    public String hello()
    {
        return "hello";
    }
}

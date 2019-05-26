package com.ritonelion.bootopen.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloSpringBootController
{
    @RequestMapping("/hello")
    public String hello()
    {
        return "hello";
    }

    @PostMapping("/result")
    public ModelAndView result(@Param("data") String data)
    {
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("data",data);

        return modelAndView;
    }
}

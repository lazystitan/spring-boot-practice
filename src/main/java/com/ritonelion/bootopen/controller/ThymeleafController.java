package com.ritonelion.bootopen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThymeleafController
{
    @RequestMapping("/thymeleaf/partHello")
    public ModelAndView partHello()
    {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/partHello");
        modelAndView.addObject("flag",true);
        return modelAndView;
    }

    @RequestMapping("/thymeleaf/part1")
    public ModelAndView part1()
    {
        return new ModelAndView("thymeleaf/part1");
    }
}

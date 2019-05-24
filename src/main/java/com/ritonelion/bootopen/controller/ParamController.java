package com.ritonelion.bootopen.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ParamController
{
    @GetMapping("/param")
    public ModelAndView param(@RequestParam("test") String test) {

        ModelAndView modelAndView = new ModelAndView("param");

        modelAndView.addObject("test",test);

        return modelAndView;
    }
}

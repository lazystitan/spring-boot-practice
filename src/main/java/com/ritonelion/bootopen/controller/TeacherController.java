package com.ritonelion.bootopen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TeacherController
{
    @GetMapping("/studentlist")
    public ModelAndView studentList()
    {
        ModelAndView modelAndView = new ModelAndView("teacher/studentlist");
        return modelAndView;
    }
}

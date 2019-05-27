package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.dao.StudentDao;
import com.ritonelion.bootopen.dao.TeacherDao;
import com.ritonelion.bootopen.model.Student;
import com.ritonelion.bootopen.model.Teacher;
import com.ritonelion.bootopen.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Controller
public class IndexController
{
    @Autowired
    StudentDao studentDao;

    @Autowired
    TeacherDao teacherDao;

    @GetMapping("/index")
    public ModelAndView index(
        @SessionAttribute(WebSecurityConfig.SESSION_ROLE) String role,
        @SessionAttribute(WebSecurityConfig.SESSION_ID) String id
    )
    {
        ModelAndView modelAndView = new ModelAndView("index");

        String name = "";

        switch (role)
        {
            case "student":
                modelAndView.addObject("student",true);
                Student student = studentDao.getStudentById(id);
                name = student.getName();
                break;
            case "teacher":
                modelAndView.addObject("teacher",true);
                Teacher teacher = teacherDao.getTeacherById(id);
                name = teacher.getName();
                break;
            case "admin":
                modelAndView.addObject("admin",true);
                name = "管理员";
                break;
        }

        modelAndView.addObject("name",name);

        String term = "";

        try
        {
            FileReader fileReader = new FileReader("term.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            term = bufferedReader.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        modelAndView.addObject("term", term);

        return modelAndView;
    }
}

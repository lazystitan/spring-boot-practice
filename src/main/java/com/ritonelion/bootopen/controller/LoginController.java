package com.ritonelion.bootopen.controller;


import com.ritonelion.bootopen.dao.StudentDao;
import com.ritonelion.bootopen.dao.TeacherDao;
import com.ritonelion.bootopen.model.Student;
import com.ritonelion.bootopen.model.Teacher;
import com.ritonelion.bootopen.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController
{
    @GetMapping("/logintest")
    public ModelAndView logintest(@SessionAttribute(WebSecurityConfig.SESSION_ID) String id, @SessionAttribute(WebSecurityConfig.SESSION_role) String role)
    {
        System.out.println("test");
        ModelAndView modelAndView = new ModelAndView("logintest");
        modelAndView.addObject("id",id);
        modelAndView.addObject("role",role);
        return modelAndView;
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @PostMapping("/loginpost")
    public @ResponseBody
    Map<String,Object> loginPost(@RequestParam("role") String role, @RequestParam("id") String id,@RequestParam("password") String password, HttpSession session)
    {
        Map<String,Object> map = new HashMap<>();

        switch (role)
        {
            case "teacher":
                Teacher teacher = teacherDao.getTeacherById(id);

                if (teacher == null) return setMap(map, false, "found no account correspond");

                if (!teacher.getPassword().equals(password)) return setMap(map, false, "wrong password");

                setSessionAttribute(session,id,role);

                return setMap(map, true, "success");

            case "student":
                Student student = studentDao.getStudentById(id);

                if (student == null) return setMap(map, false, "found no account correspond");

                if (!student.getPassword().equals(password)) return setMap(map, false, "wrong password");

                setSessionAttribute(session,id,role);

                return setMap(map, true, "success");

            case "admin":
                if (!"admin".equals(password)) return setMap(map, false, "wrong password");

                setSessionAttribute(session,id,role);

                return setMap(map, true, "success");

            default:
                return setMap(map, false, "unknown error");
        }
    }

    private Map<String,Object> setMap(Map<String,Object> map, boolean success, String message)
    {
        map.put("success", success);
        map.put("message", message);
        return map;
    }

    private void setSessionAttribute(HttpSession session, String id, String role)
    {
        session.setAttribute(WebSecurityConfig.SESSION_ID, id);
        session.setAttribute(WebSecurityConfig.SESSION_role,role);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute(WebSecurityConfig.SESSION_ID);
        return "redirect:/login";
    }

    public static void main(String[] args)
    {
        String test = "test1";
        System.out.println("test".equals(test));
        System.out.println("test"==test);
    }
}

package com.ritonelion.bootopen.controller;


import com.ritonelion.bootopen.dao.DepartmentDao;
import com.ritonelion.bootopen.dao.StudentDao;
import com.ritonelion.bootopen.dao.TeacherDao;
import com.ritonelion.bootopen.model.Department;
import com.ritonelion.bootopen.model.Student;
import com.ritonelion.bootopen.model.Teacher;
import com.ritonelion.bootopen.security.WebSecurityConfig;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController
{
    @GetMapping("/logintest")
    public ModelAndView logintest(@SessionAttribute(WebSecurityConfig.SESSION_ID) String id, @SessionAttribute(WebSecurityConfig.SESSION_ROLE) String role)
    {
        ModelAndView modelAndView = new ModelAndView("logintest");
        modelAndView.addObject("id",id);
        modelAndView.addObject("role",role);
        return modelAndView;
    }

    @GetMapping("/login")
    public String login()
    {
        return "auth/login";
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
        session.setAttribute(WebSecurityConfig.SESSION_ROLE,role);
    }

    @Autowired
    DepartmentDao departmentDao;

    @GetMapping
    public ModelAndView register()
    {
        ModelAndView modelAndView = new ModelAndView("auth/register");

        List<Department> departments = new ArrayList<>();

        departments = departmentDao.getDepartments();

        modelAndView.addObject("departments",departments);

        return modelAndView;
    }

    @PostMapping("/registertest")
    @ResponseBody
    Map<String,Object> registerTest(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("sex") String sex,
            @RequestParam("birthday") String birthday,
            @RequestParam("password") String password,
            @RequestParam("department") String department
    )
    {
        Map<String,Object> map = new HashMap<>();

        map.put("id", id);
        map.put("name", name);
        map.put("sex", sex);
        map.put("birthday", birthday);
        map.put("password",password);
        map.put("department",department);

        return map;
    }

    @PostMapping("/registerpost")
    public ModelAndView registerPost(
            @RequestParam("role") String role,
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("sex") String sex,
            @RequestParam("birthday") String birthday,
            @RequestParam("password") String password,
            @RequestParam("department") String department
    )
    {
        ModelAndView modelAndView = new ModelAndView("auth/registerpost");

        try
        {
            if (role.equals("teacher"))
                teacherDao.insertTeacher(id, name, sex, birthday, password, department);
            else if (role.equals("student"))
                studentDao.insertStudent(id, name, sex, birthday, password, department);
            else
                throw new Exception("no such role");
        }
        catch (Exception e)
        {
//            e.printStackTrace();
            modelAndView.addObject("success",false);
            return modelAndView;
        }
        modelAndView.addObject("success",true);

        return modelAndView;
    }
    

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute(WebSecurityConfig.SESSION_ID);
        session.removeAttribute(WebSecurityConfig.SESSION_ROLE);
        return "redirect:auth/login";
    }

    public static void main(String[] args)
    {
        String test = "test1";
        System.out.println("test".equals(test));
        System.out.println("test"==test);
    }
}

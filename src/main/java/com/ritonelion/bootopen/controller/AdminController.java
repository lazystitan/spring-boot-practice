package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.dao.CourseDao;
import com.ritonelion.bootopen.dao.DepartmentDao;
import com.ritonelion.bootopen.model.Course;
import com.ritonelion.bootopen.model.Department;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController
{
    @Autowired
    CourseDao courseDao;
    @Autowired
    DepartmentDao departmentDao;

    @GetMapping("/editcourse")
    public ModelAndView editCourseSearch(@RequestParam(value = "course_id",required = false) String id)
    {
        ModelAndView modelAndView = new ModelAndView("admin/editcourse");

        List<Department> departments = departmentDao.getDepartments();

        modelAndView.addObject("departments",departments);

        if (id != null)
        {
            Course course = courseDao.getCourseById(id);

            if (course == null)
            {
                modelAndView.addObject("notFound", true);
                return modelAndView;
            }

            Department department= departmentDao.getDepartmentById(course.getDepartmentId());

            modelAndView.addObject("notFound", false);
            modelAndView.addObject("id",course.getId());
            modelAndView.addObject("name",course.getName());
            modelAndView.addObject("credit",course.getCredit());
            modelAndView.addObject("max",course.getMax());
            modelAndView.addObject("department",department.getName());

            return modelAndView;

        }

        modelAndView.addObject("notFound",true);
        return modelAndView;
    }

    @GetMapping("/setterm")
    public String setTerm(@RequestParam("term") String term)
    {
        try
        {
            FileWriter fileWriter = new FileWriter("term.txt");
            fileWriter.write(term);
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return "redirect:/index";
    }

    @GetMapping("/updatecourse")
    public ModelAndView updateCourse(
            @RequestParam("id") String id,
            @RequestParam("name_input") String name,
            @RequestParam("credit_input") int credit,
            @RequestParam("max_input") int max,
            @RequestParam("department_input") String departmentId
            )
    {
        ModelAndView modelAndView = new ModelAndView("admin/updatecoursestatus");

        try
        {
            courseDao.updateCourse(id,name,credit,max,departmentId);

            modelAndView.addObject("success", true);
            return modelAndView;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            modelAndView.addObject("success", false);
            return modelAndView;
        }
    }

    @PostMapping("/addcourse")
    public ModelAndView addCourse(
            @RequestParam("id_add") String id,
            @RequestParam("name_add") String name,
            @RequestParam("credit_add") int credit,
            @RequestParam("max_add") int max,
            @RequestParam("department_add") String departmentId
    )
    {
        ModelAndView modelAndView = new ModelAndView("admin/updatecoursestatus");

        try
        {
            courseDao.insertCourse(id,name,credit,max,departmentId);

            modelAndView.addObject("success", true);
            return modelAndView;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            modelAndView.addObject("success", false);
            return modelAndView;
        }
    }

    @GetMapping("/delcourse")
    public ModelAndView delCourse(@RequestParam("id") String id)
    {
        ModelAndView modelAndView = new ModelAndView("admin/updatecoursestatus");

        try
        {
            courseDao.delCourse(id);

            modelAndView.addObject("success", true);
            return modelAndView;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            modelAndView.addObject("success", false);
            return modelAndView;
        }
    }

}

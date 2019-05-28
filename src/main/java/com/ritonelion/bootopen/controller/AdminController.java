package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.dao.*;
import com.ritonelion.bootopen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController
{
    @Autowired
    CourseDao courseDao;
    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    OpenedDao openedDao;
    @Autowired
    TeacherDao teacherDao;

    @GetMapping("/admin/editdepartment")
    public ModelAndView editDepartment()
    {
        ModelAndView modelAndView = new ModelAndView("admin/editdepartment");

        List<Department> departments = departmentDao.getDepartments();

        modelAndView.addObject("departments",departments);

        addRole(modelAndView);

        return modelAndView;
    }

    private void addRole(ModelAndView modelAndView)
    {
        modelAndView.addObject("admin",true);
        modelAndView.addObject("name","管理员");
        addTerm(modelAndView);
    }

    static void addTerm(ModelAndView modelAndView)
    {
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
    }

    @GetMapping("/adddepartment")
    public ModelAndView addDepartment(@RequestParam("id_add") String id, @RequestParam("name_add") String name)
    {
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");
        modelAndView.addObject("editdepartment",true);
        try
        {
            departmentDao.insertDepartment(id, name);

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

    @GetMapping("/updatedepartment")
    public ModelAndView updateDepartment(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");
        modelAndView.addObject("editdepartment",true);
        try
        {
            String id = request.getParameter("id");
            String name = request.getParameter(id+"_name_input");
            departmentDao.updateDepartment(id, name);

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

    @GetMapping("/deldepartment")
    public ModelAndView deleteDepartment(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");
        modelAndView.addObject("editdepartment",true);
        try
        {
            String id = request.getParameter("id");
            departmentDao.deleteDepartment(id);

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

    @GetMapping("/admin/editcourse")
    public ModelAndView editCourseSearch()
    {
        ModelAndView modelAndView = new ModelAndView("admin/editcourse");

        List<Department> departments = departmentDao.getDepartments();

        modelAndView.addObject("departments",departments);

        List<Course> courses = courseDao.getCourses();
        List<CourseComplete> courseCompletes = new ArrayList<>();
        for (Course course:courses)
        {
            CourseComplete temp = new CourseComplete();
            temp.setId(course.getId());
            temp.setName(course.getName());
            temp.setCredit(course.getCredit());
            temp.setMax(course.getMax());
            temp.setDepartment(departmentDao.getDepartmentById(course.getDepartmentId()).getName());
            courseCompletes.add(temp);
        }

        modelAndView.addObject("courses",courseCompletes);

        addRole(modelAndView);

        return modelAndView;
    }

    private class CourseComplete
    {
        private String id;
        private String name;
        private int credit;
        private int max;
        private String department;

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getCredit()
        {
            return credit;
        }

        public void setCredit(int credit)
        {
            this.credit = credit;
        }

        public int getMax()
        {
            return max;
        }

        public void setMax(int max)
        {
            this.max = max;
        }

        public String getDepartment()
        {
            return department;
        }

        public void setDepartment(String department)
        {
            this.department = department;
        }
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
    public ModelAndView updateCourse(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");

        modelAndView.addObject("editcourse",true);

        try
        {
            String id = request.getParameter("id");
            String name = request.getParameter(id+"_name_input");
            int credit = Integer.parseInt(request.getParameter(id+"_credit_input"));
            int max = Integer.parseInt(request.getParameter(id+"_max_input"));
            String departmentId = request.getParameter(id+"_department_input");
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

    @GetMapping("/addcourse")
    public ModelAndView addCourse(
            @RequestParam("id_add") String id,
            @RequestParam("name_add") String name,
            @RequestParam("credit_add") int credit,
            @RequestParam("max_add") int max,
            @RequestParam("department_add") String departmentId
    )
    {
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");
        modelAndView.addObject("editcourse",true);

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
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");
        modelAndView.addObject("editcourse",true);

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

    @GetMapping("/admin/editopen")
    public ModelAndView editOpen()
    {
        ModelAndView modelAndView = new ModelAndView("admin/editopen");
        List<OpenedComplete> openedCompletes = getOpenedCompletes();
        modelAndView.addObject("openeds",openedCompletes);
        List<Teacher> teachers = teacherDao.getTeachers();
        List<Course> courses = courseDao.getCourses();
        modelAndView.addObject("teachers",teachers);
        modelAndView.addObject("courses",courses);
        addRole(modelAndView);
        return modelAndView;
    }

    private List<OpenedComplete> getOpenedCompletes()
    {
        List<OpenedComplete> result = new ArrayList<>();
        List<Opened> list =openedDao.getOpeneds();
        for (Opened item:list)
        {
            OpenedComplete temp = new OpenedComplete();
            temp.setId(item.getId());
            temp.setCourseId(item.getCourseId());
            temp.setTeacherId(item.getTeacherId());
            temp.setTerm(item.getTerm());
            temp.setTime(item.getTime());

            temp.setCourseName(courseDao.getCourseById(temp.getCourseId()).getName());
            temp.setTeacherName(teacherDao.getTeacherById(temp.getTeacherId()).getName());
            result.add(temp);
        }
        return result;
    }

    @GetMapping("/addopened")
    public ModelAndView addOpened(
            @RequestParam("term_add") String term,
            @RequestParam("courseId_add") String courseId,
            @RequestParam("teacherId_add") String teacherId,
            @RequestParam("time_add") String time
    )
    {
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");
        modelAndView.addObject("editopen",true);
        try
        {
            openedDao.insertOpened(term, courseId, teacherId, time);

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

    @GetMapping("/delopened")
    public ModelAndView delOpened( @RequestParam("id") int id)
    {
        ModelAndView modelAndView = new ModelAndView("admin/changestatus");
        modelAndView.addObject("editopen",true);
        try
        {
            openedDao.deleteOpened(id);

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

package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.dao.*;
import com.ritonelion.bootopen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController
{
    @Autowired
    CourseDao courseDao;
    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    OpenedDao openedDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    SelectedDao selectedDao;

    @GetMapping("/student/selectcourse")
    public ModelAndView selectCourse(@RequestParam(value = "courseid",required = false) String courseid, HttpSession session)
    {
        ModelAndView modelAndView = new ModelAndView("student/selectcourse");

        setNameAndGetId(modelAndView, session);
        addRole(modelAndView);

        if (courseid != null && courseid.equals("*"))
        {
            modelAndView.addObject("flag",true);
            List<OpenedComplete> openedCompletes = getOpenedCompletes();
            modelAndView.addObject("openeds", openedCompletes);
        }
        else if (courseid != null)
        {
            modelAndView.addObject("flag",true);
            List<OpenedComplete> openedCompletes = getOpenedCompletes();
            List<OpenedComplete> openeds = new ArrayList<>();
            for (OpenedComplete openedComplete:openedCompletes)
            {
                if (openedComplete.getCourseId().equals(courseid))
                    openeds.add(openedComplete);
            }
            modelAndView.addObject("openeds", openeds);
        }

        return modelAndView;
    }

    private String setNameAndGetId(ModelAndView modelAndView,HttpSession session)
    {
        String id = (String) session.getAttribute("id");
        String name = studentDao.getStudentById(id).getName();
        modelAndView.addObject("name", name);
        return id;
    }

    private void addRole(ModelAndView modelAndView)
    {
        modelAndView.addObject("student",true);
        AdminController.addTerm(modelAndView);
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

    @GetMapping("/addselect")
    public ModelAndView addSelect(HttpSession session,@RequestParam("id") int openedId)
    {
        ModelAndView modelAndView = new ModelAndView("/student/changestatus");
        String id = setNameAndGetId(modelAndView,session);
        modelAndView.addObject("addselect",true);

        try
        {
            selectedDao.insertSelected(id, openedId);

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

    @GetMapping("/student/deleteselected")
    public ModelAndView deleteSelected(HttpSession session)
    {
        ModelAndView modelAndView = new ModelAndView("student/deleteselected");

        String id = setNameAndGetId(modelAndView, session);
        addRole(modelAndView);

        List<Selected> selecteds =  selectedDao.getSelectedByStudent(id);
        SelectedComplete temp;
        List<SelectedComplete> selectedCompletes = new ArrayList<>();

        for (Selected selected : selecteds)
        {
            temp = new SelectedComplete();
            temp.setStudentId(selected.getStudentId());
            temp.setOpenedId(selected.getOpenedId());
            temp.setPscj(selected.getPscj());
            temp.setKscj(selected.getKscj());
            temp.setZpcj(selected.getZpcj());

            List<OpenedComplete> openedCompletes = getOpenedCompletes();
            for (OpenedComplete openedComplete: openedCompletes)
            {
                if (openedComplete.getId() == selected.getOpenedId())
                {
                    temp.setTerm(openedComplete.getTerm());
                    temp.setCourseId(openedComplete.getCourseId());
                    temp.setCourseName(openedComplete.getCourseName());
                    temp.setTeacherId(openedComplete.getTeacherId());
                    temp.setTeacherName(openedComplete.getTeacherName());
                    temp.setTime(openedComplete.getTime());
                    break;
                }
            }
            selectedCompletes.add(temp);
        }

        modelAndView.addObject("selecteds",selectedCompletes);

        return modelAndView;
    }

    private class SelectedComplete
    {
        private String studentId;
        private int openedId;
        private String term;
        private String courseId;
        private String courseName;
        private String teacherId;
        private String teacherName;
        private String time;
        private int pscj;
        private int kscj;
        private int zpcj;

        public String getStudentId()
        {
            return studentId;
        }

        public void setStudentId(String studentId)
        {
            this.studentId = studentId;
        }

        public int getOpenedId()
        {
            return openedId;
        }

        public void setOpenedId(int openedId)
        {
            this.openedId = openedId;
        }

        public int getPscj()
        {
            return pscj;
        }

        public void setPscj(int pscj)
        {
            this.pscj = pscj;
        }

        public int getKscj()
        {
            return kscj;
        }

        public void setKscj(int kscj)
        {
            this.kscj = kscj;
        }

        public int getZpcj()
        {
            return zpcj;
        }

        public void setZpcj(int zpcj)
        {
            this.zpcj = zpcj;
        }

        public String getTerm()
        {
            return term;
        }

        public void setTerm(String term)
        {
            this.term = term;
        }

        public String getCourseId()
        {
            return courseId;
        }

        public void setCourseId(String courseId)
        {
            this.courseId = courseId;
        }

        public String getCourseName()
        {
            return courseName;
        }

        public void setCourseName(String courseName)
        {
            this.courseName = courseName;
        }

        public String getTeacherId()
        {
            return teacherId;
        }

        public void setTeacherId(String teacherId)
        {
            this.teacherId = teacherId;
        }

        public String getTeacherName()
        {
            return teacherName;
        }

        public void setTeacherName(String teacherName)
        {
            this.teacherName = teacherName;
        }

        public String getTime()
        {
            return time;
        }

        public void setTime(String time)
        {
            this.time = time;
        }
    }

    @GetMapping("/delselected")
    public ModelAndView delSelected(HttpSession session,@RequestParam("openedid") int openedId)
    {
        ModelAndView modelAndView = new ModelAndView("/student/changestatus");
        String id = setNameAndGetId(modelAndView,session);
        modelAndView.addObject("delselect",true);

        try
        {
            selectedDao.deleteSelected(id, openedId);

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

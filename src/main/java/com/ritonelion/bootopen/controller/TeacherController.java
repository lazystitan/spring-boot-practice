package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.dao.*;
import com.ritonelion.bootopen.model.Opened;
import com.ritonelion.bootopen.model.OpenedComplete;
import com.ritonelion.bootopen.model.Selected;
import com.ritonelion.bootopen.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController
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

    @GetMapping("/teacher/studentlist")
    public ModelAndView studentList(HttpSession session)
    {
        ModelAndView modelAndView = new ModelAndView("teacher/studentlist");
        String id = setNameAndGetId(modelAndView,session);
        addRole(modelAndView);

        List<OpenedComplete> openedCompletes = getOpenedCompletes();
        List<OpenedComplete> openeds = new ArrayList<>();

        for (OpenedComplete openedcomplete : openedCompletes)
        {
            if (openedcomplete.getTeacherId().equals(id) )
                openeds.add(openedcomplete);
        }

        List<List<Selected>> selecteds = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for (OpenedComplete openedComplete:openeds)
        {
            selecteds.add(selectedDao.getSelectedByOpenedId(openedComplete.getId()));
            ids.add(openedComplete.getId());
        }

        List<SelectedList> tables = new ArrayList<>();

        for (int i = 0; i < selecteds.size(); i++)
        {
            List<Selected> selected = selecteds.get(i);
            List<SelectedInfo> selectedInfos = new ArrayList<>();
            for (Selected item : selected)
            {
                SelectedInfo temp = new SelectedInfo();
                temp.setStudentId(item.getStudentId());
                temp.setOpenedId(item.getOpenedId());
                temp.setPscj(item.getPscj());
                temp.setKscj(item.getKscj());
                temp.setZpcj(item.getZpcj());
                Student student = studentDao.getStudentById(item.getStudentId());
                temp.setStudentName(student.getName());
                temp.setSex(student.getSex());

                selectedInfos.add(temp);
            }

            SelectedList selectedList = new SelectedList();
            selectedList.openedId = ids.get(i);
            selectedList.list = selectedInfos;
            Map<String,Object> map = new HashMap<>();
            map.put("openedId",ids.get(i));
            selectedDao.averageGrade(map);
            selectedList.avgGrade = map.get("avgGrade") != null ? (double) map.get("avgGrade"):0.0 ;
            tables.add(selectedList);
        }

        modelAndView.addObject("tables",tables);


        modelAndView.addObject("openeds",openeds);

        return modelAndView;
    }

    @GetMapping("/changegrade")
    public ModelAndView changeGrade(HttpServletRequest request, @RequestParam("openedId") int openedId)
    {
        ModelAndView modelAndView = new ModelAndView("teacher/changestatus");

        try
        {
            List<Selected> selecteds =  selectedDao.getSelectedByOpenedId(openedId);

            for (Selected selected: selecteds)
            {
                String studentId = selected.getStudentId();
                selectedDao.updateGrade(studentId, openedId,
                        Integer.parseInt(request.getParameter(studentId+"_pscj_input")),
                        Integer.parseInt(request.getParameter(studentId+"_kscj_input")));
            }

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

    private class SelectedList
    {
        private int openedId;
        private List<SelectedInfo> list;
        private double avgGrade;

        public int getOpenedId()
        {
            return openedId;
        }

        public void setOpenedId(int openedId)
        {
            this.openedId = openedId;
        }

        public List<SelectedInfo> getList()
        {
            return list;
        }

        public void setList(List<SelectedInfo> list)
        {
            this.list = list;
        }

        public double getAvgGrade()
        {
            return avgGrade;
        }

        public void setAvgGrade(double avgGrade)
        {
            this.avgGrade = avgGrade;
        }
    }


    private String setNameAndGetId(ModelAndView modelAndView, HttpSession session)
    {
        String id = (String) session.getAttribute("id");
        String name = teacherDao.getTeacherById(id).getName();
        modelAndView.addObject("name", name);
        return id;
    }

    private void addRole(ModelAndView modelAndView)
    {
        modelAndView.addObject("teacher",true);
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

    private class SelectedInfo
    {
        private String studentId;
        private String studentName;
        private String sex;
        private int openedId;
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

        public String getStudentName()
        {
            return studentName;
        }

        public void setStudentName(String studentName)
        {
            this.studentName = studentName;
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

        public String getSex()
        {
            return sex;
        }

        public void setSex(String sex)
        {
            this.sex = sex;
        }
    }
}

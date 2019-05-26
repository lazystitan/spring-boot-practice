package com.ritonelion.bootopen.controller;

import com.ritonelion.bootopen.dao.DepartmentDao;
import com.ritonelion.bootopen.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MysqlController
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/mysql",method = RequestMethod.GET)
    public String Mysql() {
        String sql = "select name from department where id = ?";

        String departments = (String)jdbcTemplate.queryForObject(
                sql,new Object[] {"01"},String.class
        );

        return departments;
    }

    @Autowired
    DepartmentDao departmentDao;

    @RequestMapping(value = "/mybatis", method = RequestMethod.GET)
    public String mybatis(@RequestParam("id") String id){
        Department department = departmentDao.getDepartmentById(id);
        return department.toString();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert(@RequestParam("id") String id, @RequestParam("name") String name){
        departmentDao.insertDepartment(id,name);
        return "success";
    }
}

package com.ritonelion.bootopen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
}

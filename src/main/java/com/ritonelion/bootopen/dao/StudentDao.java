package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao
{
    Student getStudentById(String id);
    void insertStudent(String id, String name, String sex, String birthday, String password, String department_id);
}

package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherDao
{
    Teacher getTeacherById(String id);
//    void insertTeacher(String id, String name, String sex, String birthday, String password, String department_id);
}

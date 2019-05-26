package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao
{
    Student getStudentById(String id);
}

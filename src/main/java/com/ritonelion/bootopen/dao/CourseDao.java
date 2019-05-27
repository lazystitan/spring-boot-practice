package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseDao
{
    Course getCourseById(String id);
    void updateCourse(String id, String name, int credit, int max, String departmentId );
    void insertCourse(String id, String name, int credit, int max, String departmentId );
    void delCourse(String id);
}

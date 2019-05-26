package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentDao
{
    Department getDepartmentById(String id);
    void insertDepartment(String id,String name);
    List<Department> getDepartments();
}

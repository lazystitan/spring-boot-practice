package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentDao
{
    Department getDepartmentById(String id);
    void insertDepartment(String id,String name);
}

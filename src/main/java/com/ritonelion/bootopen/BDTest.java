package com.ritonelion.bootopen;

import com.ritonelion.bootopen.dao.DepartmentDao;
import com.ritonelion.bootopen.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BDTest
{
    @Autowired
    DepartmentDao departmentDao;

    @Test
    public void testDB(){
        Department department = departmentDao.getDepartmentById("01");
        System.out.println(department.toString());
    }
}

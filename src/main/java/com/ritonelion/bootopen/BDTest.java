package com.ritonelion.bootopen;

import com.ritonelion.bootopen.dao.DepartmentDao;
import com.ritonelion.bootopen.dao.OpenedDao;
import com.ritonelion.bootopen.dao.SelectedDao;
import com.ritonelion.bootopen.model.Department;
import com.ritonelion.bootopen.model.Opened;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BDTest
{
    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    OpenedDao openedDao;

    @Test
    public void testDB(){
        Department department = departmentDao.getDepartmentById("01");
        System.out.println(department.toString());
    }

    @Test
    public void testOpenedAll()
    {
        List<Opened> list =openedDao.getOpeneds();
        for (Opened item:list)
        {
            System.out.println(item.toString());
        }
    }

    @Autowired
    SelectedDao selectedDao;

    @Test
    public void testAvgGrade()
    {
        double num = 0.0;
        Map<String,Object> map = new HashMap<>();
        map.put("openedId",17);
        selectedDao.averageGrade(map);
        num = map.get("avgGrade") != null ? (double) map.get("avgGrade"):0.0 ;
        System.out.println(num);
    }
}

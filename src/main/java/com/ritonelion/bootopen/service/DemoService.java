package com.ritonelion.bootopen.service;

import com.ritonelion.bootopen.mapper.DemoMapper;
import com.ritonelion.bootopen.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService
{
    private final DemoMapper dao;

    @Autowired
    public DemoService(DemoMapper _dao)
    {
        dao = _dao;
    }

    public boolean insert(DemoModel demoModel)
    {
        return dao.insert(demoModel) > 0;
    }

    public DemoModel select(int id)
    {
        return dao.select(id);
    }

    public List<DemoModel> selectAll()
    {
        return dao.selectAll();
    }

    public boolean updateGrade(DemoModel demoModel)
    {
        return dao.updateGrade(demoModel) > 0;
    }

    public boolean delete(Integer id)
    {
        return dao.delete(id) > 0;
    }

}

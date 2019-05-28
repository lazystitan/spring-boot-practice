package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Opened;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpenedDao
{
    List<Opened> getOpeneds();
    void updateOpened(int id, String term, String courseId, String teacherId, String time);
    void insertOpened(String term, String courseId, String teacherId, String time);
    Opened getOpenedById(int id);
    void deleteOpened(int id);

}

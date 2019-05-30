package com.ritonelion.bootopen.dao;

import com.ritonelion.bootopen.model.Selected;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SelectedDao
{
    List<Selected> getSelectedByStudent(String studentId);
    void insertSelected(String studentId, int openedId);
    void deleteSelected(String studentId, int openedId);
    List<Selected> getSelectedByOpenedId(int openedId);
    void updateSelected(String studentId, int openedId, Integer pscj, Integer kscj, Integer zpcj);
    void updateGrade(String studentId, int openedId, Integer pscj, Integer kscj);
}

package com.ritonelion.bootopen.model;

public class Course
{
    private String id;
    private String name;
    private int credit;
    private int max;
    private String departmentId;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCredit()
    {
        return credit;
    }

    public void setCredit(int credit)
    {
        this.credit = credit;
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public String getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(String department_id)
    {
        this.departmentId = department_id;
    }
}

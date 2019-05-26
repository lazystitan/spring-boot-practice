package com.ritonelion.bootopen.model;

public class Student
{
    private String id;
    private String name;
    private String sex;
    private String birthday;
    private String password;
    private String department_id;

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

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDepartment_id()
    {
        return department_id;
    }

    public void setDepartment_id(String department_id)
    {
        this.department_id = department_id;
    }
}

package com.ritonelion.bootopen.mapper;

import com.ritonelion.bootopen.model.DemoModel;
import org.apache.ibatis.annotations.*;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DemoMapper
{
    @Insert("insert into first(name,grade) values(#{name},#{grade})")
    @SelectKey(statement ="select seq id from sqlite_sequence where (name = 'first')",
            before = false, keyProperty = "id", resultType = int.class)
    int insert(DemoModel demoModel);

    @Select("select * from first where id = #{id}")
    DemoModel select(int id);

    @Select("select * from first")
    List<DemoModel> selectAll();

    @Update("update first set grade=#{grade} where id = #{id}")
    int updateGrade(DemoModel demoModel);

    @Delete("delete from first where id = #{id}")
    int delete(Integer id);

}

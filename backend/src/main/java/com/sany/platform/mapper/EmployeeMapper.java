package com.sany.platform.mapper;

import com.sany.platform.model.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    Employee selectById(@Param("id") Long id);

    List<Employee> selectList(@Param("employeeName") String employeeName,
                              @Param("employeeNo") String employeeNo,
                              @Param("position") String position,
                              @Param("status") Integer status);

    long countList(@Param("employeeName") String employeeName,
                   @Param("employeeNo") String employeeNo,
                   @Param("position") String position,
                   @Param("status") Integer status);

    int insert(Employee employee);

    int updateById(Employee employee);

    int deleteById(@Param("id") Long id);
}

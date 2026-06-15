package com.sany.platform.service;

import com.sany.platform.model.entity.Employee;
import com.sany.platform.model.vo.PageResult;

public interface EmployeeService {
    Employee getById(Long id);

    PageResult<Employee> pageList(String employeeName, String employeeNo, String position, Integer status, Integer pageNum, Integer pageSize);

    void create(Employee employee);

    void update(Employee employee);

    void delete(Long id);
}

package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.Employee;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Resource
    private EmployeeService employeeService;

    @GetMapping("/page")
    public Result<PageResult<Employee>> pageList(@RequestParam(required = false) String employeeName,
                                                  @RequestParam(required = false) String employeeNo,
                                                  @RequestParam(required = false) String position,
                                                  @RequestParam(required = false) Integer status,
                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(employeeService.pageList(employeeName, employeeNo, position, status, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        return Result.success(employeeService.getById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Employee employee) {
        employeeService.create(employee);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return Result.success();
    }
}

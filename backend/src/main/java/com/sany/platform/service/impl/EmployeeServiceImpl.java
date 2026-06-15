package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.EmployeeMapper;
import com.sany.platform.model.entity.Employee;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Employee getById(Long id) {
        if (id == null) throw new BusinessException(400, "员工ID不能为空");
        Employee entity = employeeMapper.selectById(id);
        if (entity == null) throw new BusinessException(404, "员工不存在或已被删除");
        return entity;
    }

    @Override
    public PageResult<Employee> pageList(String employeeName, String employeeNo, String position, Integer status, Integer pageNum, Integer pageSize) {
        long total = employeeMapper.countList(employeeName, employeeNo, position, status);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<Employee> all = employeeMapper.selectList(employeeName, employeeNo, position, status);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Employee employee) {
        log.info("创建员工, 姓名: {}", employee.getEmployeeName());
        int rows = employeeMapper.insert(employee);
        if (rows != 1) throw new BusinessException("员工创建失败");
        log.info("员工创建成功, ID: {}", employee.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Employee employee) {
        if (employee.getId() == null) throw new BusinessException(400, "员工ID不能为空");
        Employee exist = employeeMapper.selectById(employee.getId());
        if (exist == null) throw new BusinessException(404, "员工不存在或已被删除");
        int rows = employeeMapper.updateById(employee);
        if (rows != 1) throw new BusinessException("员工更新失败");
        log.info("员工更新成功, ID: {}", employee.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除员工, ID: {}", id);
        if (id == null) throw new BusinessException(400, "员工ID不能为空");
        Employee exist = employeeMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "员工不存在或已被删除");
        int rows = employeeMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("员工删除成功, ID: {}", id);
    }
}

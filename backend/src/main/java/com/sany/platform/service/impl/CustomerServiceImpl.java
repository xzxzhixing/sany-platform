package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.CustomerMapper;
import com.sany.platform.model.entity.Customer;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public PageResult<Customer> pageList(String customerName, String city, Integer serviceLevel, Integer pageNum, Integer pageSize) {
        long total = customerMapper.countList(customerName, city, serviceLevel);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<Customer> all = customerMapper.selectList(customerName, city, serviceLevel);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    public Customer getById(Long id) {
        if (id == null) throw new BusinessException(400, "客户ID不能为空");
        Customer customer = customerMapper.selectById(id);
        if (customer == null) throw new BusinessException(404, "客户不存在或已被删除");
        return customer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer create(Customer customer) {
        log.info("创建客户, 名称: {}", customer.getCustomerName());
        if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
            throw new BusinessException(400, "客户名称不能为空");
        }
        customer.setStatus(1);
        int rows = customerMapper.insert(customer);
        if (rows != 1) throw new BusinessException("客户创建失败");
        log.info("客户创建成功, ID: {}", customer.getId());
        return customer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Customer customer) {
        log.info("更新客户, ID: {}", customer.getId());
        if (customer.getId() == null) throw new BusinessException(400, "客户ID不能为空");
        Customer exist = customerMapper.selectById(customer.getId());
        if (exist == null) throw new BusinessException(404, "客户不存在");
        int rows = customerMapper.updateById(customer);
        if (rows != 1) throw new BusinessException("客户更新失败");
        log.info("客户更新成功, ID: {}", customer.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除客户, ID: {}", id);
        if (id == null) throw new BusinessException(400, "客户ID不能为空");
        Customer exist = customerMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "客户不存在");
        int rows = customerMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("客户删除失败");
        log.info("客户删除成功, ID: {}", id);
    }
}

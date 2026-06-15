package com.sany.platform.service;

import com.sany.platform.model.entity.Customer;
import com.sany.platform.model.vo.PageResult;

public interface CustomerService {

    PageResult<Customer> pageList(String customerName, String city, Integer serviceLevel, Integer pageNum, Integer pageSize);

    Customer getById(Long id);

    Customer create(Customer customer);

    void update(Customer customer);

    void delete(Long id);
}

package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.Customer;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/page")
    public Result<PageResult<Customer>> pageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(required = false) String customerName,
                                                  @RequestParam(required = false) String city,
                                                  @RequestParam(required = false) Integer serviceLevel) {
        return Result.success(customerService.pageList(customerName, city, serviceLevel, pageNum, pageSize));
    }

    @GetMapping("/list")
    public Result<PageResult<Customer>> list(@RequestParam(required = false) String customerName,
                                              @RequestParam(required = false) String city,
                                              @RequestParam(required = false) Integer serviceLevel) {
        return Result.success(customerService.pageList(customerName, city, serviceLevel, 1, 999));
    }

    @GetMapping("/{id}")
    public Result<Customer> getById(@PathVariable Long id) {
        return Result.success(customerService.getById(id));
    }

    @PostMapping
    public Result<Customer> create(@RequestBody Customer customer) {
        return Result.success(customerService.create(customer));
    }

    @PutMapping
    public Result<Void> update(@RequestBody Customer customer) {
        customerService.update(customer);
        return Result.success();
    }
}

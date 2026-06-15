package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.Contract;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/contract")
public class ContractController {

    private static final Logger log = LoggerFactory.getLogger(ContractController.class);

    @Resource
    private ContractService contractService;

    @GetMapping("/page")
    public Result<PageResult<Contract>> pageList(
            @RequestParam(required = false) Integer contractType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String customerName,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "15") Integer pageSize) {
        return Result.success(contractService.pageList(contractType, status, customerName, pageNum, pageSize));
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats() {
        return Result.success(contractService.getStats());
    }

    @GetMapping("/{id}")
    public Result<Contract> getById(@PathVariable Long id) {
        return Result.success(contractService.getById(id));
    }

    @PostMapping
    public Result<Contract> create(@RequestBody Contract contract) {
        return Result.success(contractService.create(contract));
    }

    @PutMapping
    public Result<Void> update(@RequestBody Contract contract) {
        contractService.update(contract);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        contractService.delete(id);
        return Result.success();
    }
}

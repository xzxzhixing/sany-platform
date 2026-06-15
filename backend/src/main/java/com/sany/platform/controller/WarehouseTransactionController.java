package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.WarehouseTransaction;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.WarehouseTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/warehouse/transactions")
public class WarehouseTransactionController {

    private static final Logger log = LoggerFactory.getLogger(WarehouseTransactionController.class);

    @Resource
    private WarehouseTransactionService warehouseTransactionService;

    @GetMapping("/page")
    public Result<PageResult<WarehouseTransaction>> pageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                                              @RequestParam(required = false) Integer type,
                                                              @RequestParam(required = false) String itemName) {
        return Result.success(warehouseTransactionService.pageList(pageNum, pageSize, type, itemName));
    }

    @PostMapping
    public Result<WarehouseTransaction> create(@RequestBody WarehouseTransaction transaction) {
        return Result.success(warehouseTransactionService.create(transaction));
    }
}

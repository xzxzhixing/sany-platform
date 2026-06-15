package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.WarehouseItem;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.WarehouseItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/warehouse/items")
public class WarehouseItemController {

    private static final Logger log = LoggerFactory.getLogger(WarehouseItemController.class);

    @Resource
    private WarehouseItemService warehouseItemService;

    @GetMapping("/page")
    public Result<PageResult<WarehouseItem>> pageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       @RequestParam(required = false) String category,
                                                       @RequestParam(required = false) String itemName,
                                                       @RequestParam(required = false) Integer status) {
        return Result.success(warehouseItemService.pageList(pageNum, pageSize, category, itemName, status));
    }

    @GetMapping("/{id}")
    public Result<WarehouseItem> getById(@PathVariable Long id) {
        return Result.success(warehouseItemService.getById(id));
    }

    @PostMapping
    public Result<WarehouseItem> create(@RequestBody WarehouseItem item) {
        return Result.success(warehouseItemService.create(item));
    }

    @PutMapping
    public Result<Void> update(@RequestBody WarehouseItem item) {
        warehouseItemService.update(item);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        warehouseItemService.delete(id);
        return Result.success();
    }
}

package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.Equipment;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/equipment")
public class EquipmentController {

    @Resource
    private EquipmentService equipmentService;

    @GetMapping("/list")
    public Result<PageResult<Equipment>> pageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) String equipmentName,
                                                   @RequestParam(required = false) String equipmentCode,
                                                   @RequestParam(required = false) Integer status) {
        return Result.success(equipmentService.pageList(equipmentName, equipmentCode, status, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<Equipment> getById(@PathVariable Long id) {
        return Result.success(equipmentService.getById(id));
    }

    @PostMapping
    public Result<Equipment> create(@RequestBody Equipment equipment) {
        return Result.success(equipmentService.create(equipment));
    }
}

package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.MaintenancePlan;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.MaintenancePlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/maintenance-plan")
public class MaintenancePlanController {

    private static final Logger log = LoggerFactory.getLogger(MaintenancePlanController.class);

    @Resource
    private MaintenancePlanService maintenancePlanService;

    @GetMapping("/page")
    public Result<PageResult<MaintenancePlan>> pageList(@RequestParam(required = false) Integer planType,
                                                         @RequestParam(required = false) Integer status,
                                                         @RequestParam(required = false) String equipmentName,
                                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(maintenancePlanService.pageList(planType, status, equipmentName, pageNum, pageSize));
    }

    @PostMapping
    public Result<Void> create(@RequestBody MaintenancePlan maintenancePlan) {
        maintenancePlanService.create(maintenancePlan);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody MaintenancePlan maintenancePlan) {
        maintenancePlanService.update(maintenancePlan);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        maintenancePlanService.delete(id);
        return Result.success();
    }
}

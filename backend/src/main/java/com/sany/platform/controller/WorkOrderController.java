package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.dto.WorkOrderCreateDTO;
import com.sany.platform.model.dto.WorkOrderQueryDTO;
import com.sany.platform.model.entity.WorkOrder;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.model.vo.WorkOrderVO;
import com.sany.platform.service.WorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/work-order")
public class WorkOrderController {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderController.class);

    @Resource
    private WorkOrderService workOrderService;

    @GetMapping("/{id}")
    public Result<WorkOrderVO> getById(@PathVariable Long id) {
        return Result.success(workOrderService.getById(id));
    }

    @GetMapping("/page")
    public Result<PageResult<WorkOrderVO>> pageList(WorkOrderQueryDTO queryDTO) {
        return Result.success(workOrderService.pageList(queryDTO));
    }

    @PostMapping
    public Result<WorkOrder> create(@Valid @RequestBody WorkOrderCreateDTO createDTO) {
        return Result.success(workOrderService.create(createDTO));
    }

    @PutMapping
    public Result<Void> update(@RequestBody WorkOrder workOrder) {
        workOrderService.update(workOrder);
        return Result.success();
    }

    @PutMapping("/{id}/dispatch")
    public Result<Void> dispatch(@PathVariable Long id, @RequestParam Long assigneeId) {
        workOrderService.dispatch(id, assigneeId);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @RequestParam Integer newStatus,
                                     @RequestParam(required = false) Integer oldStatus) {
        workOrderService.updateStatus(id, newStatus, oldStatus);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        workOrderService.delete(id);
        return Result.success();
    }
}

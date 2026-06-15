package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.InspectionTask;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.InspectionTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inspection-tasks")
public class InspectionTaskController {

    private static final Logger log = LoggerFactory.getLogger(InspectionTaskController.class);

    @Resource
    private InspectionTaskService inspectionTaskService;

    @GetMapping("/page")
    public Result<PageResult<InspectionTask>> pageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(required = false) Integer taskType,
                                                        @RequestParam(required = false) Integer status,
                                                        @RequestParam(required = false) String equipmentName) {
        return Result.success(inspectionTaskService.pageList(pageNum, pageSize, taskType, status, equipmentName));
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats() {
        return Result.success(inspectionTaskService.getStats());
    }

    @PostMapping
    public Result<InspectionTask> create(@RequestBody InspectionTask task) {
        return Result.success(inspectionTaskService.create(task));
    }

    @PutMapping("/{id}/start")
    public Result<Void> start(@PathVariable Long id) {
        inspectionTaskService.start(id);
        return Result.success();
    }

    @PutMapping("/{id}/complete")
    public Result<Void> complete(@PathVariable Long id) {
        inspectionTaskService.complete(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        inspectionTaskService.delete(id);
        return Result.success();
    }
}

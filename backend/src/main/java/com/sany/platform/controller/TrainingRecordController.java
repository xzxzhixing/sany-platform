package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.TrainingRecord;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.TrainingRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/training")
public class TrainingRecordController {

    private static final Logger log = LoggerFactory.getLogger(TrainingRecordController.class);

    @Resource
    private TrainingRecordService trainingRecordService;

    @GetMapping("/page")
    public Result<PageResult<TrainingRecord>> pageList(
            @RequestParam(required = false) Integer courseType,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "15") Integer pageSize) {
        return Result.success(trainingRecordService.pageList(courseType, status, pageNum, pageSize));
    }

    @PostMapping
    public Result<TrainingRecord> create(@RequestBody TrainingRecord record) {
        return Result.success(trainingRecordService.create(record));
    }

    @PutMapping
    public Result<Void> update(@RequestBody TrainingRecord record) {
        trainingRecordService.update(record);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        trainingRecordService.delete(id);
        return Result.success();
    }
}

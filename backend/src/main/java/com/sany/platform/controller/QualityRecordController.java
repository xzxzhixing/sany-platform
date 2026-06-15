package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.QualityRecord;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.QualityRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/quality")
public class QualityRecordController {

    private static final Logger log = LoggerFactory.getLogger(QualityRecordController.class);

    @Resource
    private QualityRecordService qualityRecordService;

    @GetMapping("/page")
    public Result<PageResult<QualityRecord>> pageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       @RequestParam(required = false) Integer inspectionType,
                                                       @RequestParam(required = false) Integer result,
                                                       @RequestParam(required = false) String equipmentName) {
        return Result.success(qualityRecordService.pageList(pageNum, pageSize, inspectionType, result, equipmentName));
    }

    @GetMapping("/{id}")
    public Result<QualityRecord> getById(@PathVariable Long id) {
        return Result.success(qualityRecordService.getById(id));
    }

    @PostMapping
    public Result<QualityRecord> create(@RequestBody QualityRecord record) {
        return Result.success(qualityRecordService.create(record));
    }

    @PutMapping
    public Result<Void> update(@RequestBody QualityRecord record) {
        qualityRecordService.update(record);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        qualityRecordService.delete(id);
        return Result.success();
    }
}

package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.ServiceReport;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    @Resource
    private ReportService reportService;

    @GetMapping("/page")
    public Result<PageResult<ServiceReport>> pageList(@RequestParam(required = false) String reportNo,
                                                       @RequestParam(required = false) String orderNo,
                                                       @RequestParam(required = false) Integer reportType,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(reportService.pageList(reportNo, orderNo, reportType, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<ServiceReport> getById(@PathVariable Long id) {
        return Result.success(reportService.getById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody ServiceReport serviceReport) {
        reportService.create(serviceReport);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        reportService.delete(id);
        return Result.success();
    }
}

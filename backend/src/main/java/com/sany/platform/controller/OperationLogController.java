package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.OperationLog;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/operation-log")
public class OperationLogController {

    private static final Logger log = LoggerFactory.getLogger(OperationLogController.class);

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageResult<OperationLog>> pageList(@RequestParam(required = false) String action,
                                                      @RequestParam(required = false) String operator,
                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(operationLogService.pageList(action, operator, pageNum, pageSize));
    }
}

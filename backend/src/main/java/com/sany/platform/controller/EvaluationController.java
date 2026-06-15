package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.Evaluation;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.EvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {

    private static final Logger log = LoggerFactory.getLogger(EvaluationController.class);

    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/page")
    public Result<PageResult<Evaluation>> pageList(@RequestParam(required = false) String orderNo,
                                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(evaluationService.pageList(orderNo, pageNum, pageSize));
    }

    @GetMapping("/{orderId}")
    public Result<Evaluation> findByOrderId(@PathVariable Long orderId) {
        return Result.success(evaluationService.findByOrderId(orderId));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Evaluation evaluation) {
        evaluationService.create(evaluation);
        return Result.success();
    }
}

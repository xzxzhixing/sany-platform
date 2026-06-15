package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.Settlement;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.SettlementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/settlement")
public class SettlementController {

    private static final Logger log = LoggerFactory.getLogger(SettlementController.class);

    @Resource
    private SettlementService settlementService;

    @GetMapping("/page")
    public Result<PageResult<Settlement>> pageList(
            @RequestParam(required = false) Integer serviceType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String customerName,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "15") Integer pageSize) {
        return Result.success(settlementService.pageList(serviceType, status, customerName, pageNum, pageSize));
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats() {
        return Result.success(settlementService.getStats());
    }

    @GetMapping("/{id}")
    public Result<Settlement> getById(@PathVariable Long id) {
        return Result.success(settlementService.getById(id));
    }

    @PostMapping
    public Result<Settlement> create(@RequestBody Settlement settlement) {
        return Result.success(settlementService.create(settlement));
    }

    @PutMapping
    public Result<Void> update(@RequestBody Settlement settlement) {
        settlementService.update(settlement);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<Void> updateStatus(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        Integer status = Integer.valueOf(body.get("status").toString());
        settlementService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        settlementService.delete(id);
        return Result.success();
    }
}

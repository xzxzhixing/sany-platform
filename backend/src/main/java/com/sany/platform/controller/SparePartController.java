package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.SparePart;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.SparePartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/spare-part")
public class SparePartController {

    private static final Logger log = LoggerFactory.getLogger(SparePartController.class);

    @Resource
    private SparePartService sparePartService;

    @GetMapping("/page")
    public Result<PageResult<SparePart>> pageList(@RequestParam(required = false) String partName,
                                                   @RequestParam(required = false) String partCode,
                                                   @RequestParam(required = false) Integer stockStatus,
                                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(sparePartService.pageList(partName, partCode, stockStatus, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<SparePart> getById(@PathVariable Long id) {
        return Result.success(sparePartService.getById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody SparePart sparePart) {
        sparePartService.create(sparePart);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody SparePart sparePart) {
        sparePartService.update(sparePart);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        sparePartService.delete(id);
        return Result.success();
    }
}

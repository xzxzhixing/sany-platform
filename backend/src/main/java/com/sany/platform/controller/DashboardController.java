package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Resource
    private DashboardService dashboardService;

    @GetMapping("/overview")
    public Result<?> overview() {
        return Result.success(dashboardService.getOverview());
    }

    @GetMapping("/stats")
    public Result<?> stats() {
        return Result.success(dashboardService.getStats());
    }

    @GetMapping("/device-dist")
    public Result<?> deviceDist() {
        return Result.success(dashboardService.getDeviceDist());
    }

    @GetMapping("/timeline")
    public Result<?> timeline() {
        return Result.success(dashboardService.getTimeline());
    }

    @GetMapping("/alarms")
    public Result<?> alarms() {
        return Result.success(dashboardService.getAlarms());
    }

    @GetMapping("/device-health")
    public Result<?> deviceHealth() {
        return Result.success(dashboardService.getDeviceHealth());
    }
}

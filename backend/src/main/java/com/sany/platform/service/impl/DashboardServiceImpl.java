package com.sany.platform.service.impl;

import com.sany.platform.mapper.DashboardMapper;
import com.sany.platform.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Resource
    private DashboardMapper dashboardMapper;

    @Override
    public Map<String, Object> getOverview() {
        Map<String, Object> stats = dashboardMapper.selectOverviewStats();
        if (stats == null) stats = new HashMap<>();

        List<Map<String, Object>> trendRaw = dashboardMapper.selectTrendLast7Days();
        List<Map<String, Object>> trendData = fillTrendData(trendRaw);

        List<Map<String, Object>> statusDist = dashboardMapper.selectStatusDistribution();
        long totalOrders = statusDist.stream().mapToLong(s -> ((Number) s.getOrDefault("count", 0)).longValue()).sum();
        for (Map<String, Object> s : statusDist) {
            long c = ((Number) s.getOrDefault("count", 0)).longValue();
            s.put("percent", totalOrders > 0 ? (int) Math.round(c * 100.0 / totalOrders) : 0);
        }

        List<Map<String, Object>> todos = dashboardMapper.selectRecentTodos(5);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("pendingOrders", stats.getOrDefault("pendingOrders", 0));
        result.put("monthOrders", stats.getOrDefault("monthOrders", 0));
        result.put("completionRate", stats.getOrDefault("completionRate", 0));
        result.put("alarmCount", stats.getOrDefault("alarmCount", 0));
        result.put("trendData", trendData);
        result.put("statusDist", statusDist);
        result.put("todos", todos);
        return result;
    }

    @Override
    public Map<String, Object> getStats() {
        long totalEq = dashboardMapper.selectTotalEquipment();
        long onlineEq = dashboardMapper.selectOnlineEquipment();

        Map<String, Object> overview = dashboardMapper.selectOverviewStats();
        long todayOrders = dashboardMapper.selectTodayOrderCount();
        long onlineEngineers = dashboardMapper.selectOnlineEngineers();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("onlineDevices", onlineEq);
        result.put("runRate", totalEq > 0 ? (int) Math.round(onlineEq * 100.0 / totalEq) : 0);
        result.put("todayAlarms", overview != null ? overview.getOrDefault("alarmCount", 0) : 0);
        result.put("todayOrders", todayOrders);
        result.put("onlineEngineers", onlineEngineers);
        return result;
    }

    @Override
    public Map<String, Object> getDeviceDist() {
        List<Map<String, Object>> dist = dashboardMapper.selectEquipmentDistribution();
        long total = dist.stream().mapToLong(d -> ((Number) d.getOrDefault("count", 0)).longValue()).sum();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("dist", dist);
        result.put("total", total);
        return result;
    }

    @Override
    public List<Map<String, Object>> getTimeline() {
        return dashboardMapper.selectTodayTimeline();
    }

    @Override
    public List<Map<String, Object>> getAlarms() {
        return dashboardMapper.selectAlarms(10);
    }

    @Override
    public List<Map<String, Object>> getDeviceHealth() {
        return dashboardMapper.selectDeviceHealth();
    }

    private List<Map<String, Object>> fillTrendData(List<Map<String, Object>> raw) {
        Map<String, Map<String, Object>> map = Optional.ofNullable(raw)
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.toMap(
                        m -> (String) m.get("day"),
                        m -> m,
                        (a, b) -> a
                ));

        List<Map<String, Object>> result = new ArrayList<>();
        java.time.LocalDate today = java.time.LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            String day = today.minusDays(i).format(java.time.format.DateTimeFormatter.ofPattern("MM-dd"));
            Map<String, Object> item = map.getOrDefault(day, new LinkedHashMap<>());
            item.putIfAbsent("day", day);
            item.putIfAbsent("count", 0);
            result.add(item);
        }
        return result;
    }
}

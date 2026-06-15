package com.sany.platform.service;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    Map<String, Object> getOverview();

    Map<String, Object> getStats();

    Map<String, Object> getDeviceDist();

    List<Map<String, Object>> getTimeline();

    List<Map<String, Object>> getAlarms();

    List<Map<String, Object>> getDeviceHealth();
}

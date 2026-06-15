package com.sany.platform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {

    Map<String, Object> selectOverviewStats();

    List<Map<String, Object>> selectTrendLast7Days();

    List<Map<String, Object>> selectStatusDistribution();

    List<Map<String, Object>> selectRecentTodos(@Param("limit") int limit);

    long selectTotalEquipment();

    long selectOnlineEquipment();

    List<Map<String, Object>> selectEquipmentDistribution();

    List<Map<String, Object>> selectTodayTimeline();

    List<Map<String, Object>> selectAlarms(@Param("limit") int limit);

    List<Map<String, Object>> selectDeviceHealth();

    long selectTodayOrderCount();

    long selectOnlineEngineers();

    List<String> selectDistinctMonths();
}

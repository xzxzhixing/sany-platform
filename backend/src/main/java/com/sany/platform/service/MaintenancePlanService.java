package com.sany.platform.service;

import com.sany.platform.model.entity.MaintenancePlan;
import com.sany.platform.model.vo.PageResult;

public interface MaintenancePlanService {
    PageResult<MaintenancePlan> pageList(Integer planType, Integer status, String equipmentName, Integer pageNum, Integer pageSize);

    void create(MaintenancePlan maintenancePlan);

    void update(MaintenancePlan maintenancePlan);

    void delete(Long id);
}

package com.sany.platform.service;

import com.sany.platform.model.entity.InspectionTask;
import com.sany.platform.model.vo.PageResult;

import java.util.Map;

public interface InspectionTaskService {

    InspectionTask getById(Long id);

    PageResult<InspectionTask> pageList(Integer pageNum, Integer pageSize, Integer taskType, Integer status, String equipmentName);

    InspectionTask create(InspectionTask task);

    void start(Long id);

    void complete(Long id);

    void delete(Long id);

    Map<String, Object> getStats();
}

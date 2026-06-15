package com.sany.platform.service;

import com.sany.platform.model.entity.ServiceReport;
import com.sany.platform.model.vo.PageResult;

public interface ReportService {
    ServiceReport getById(Long id);

    PageResult<ServiceReport> pageList(String reportNo, String orderNo, Integer reportType, Integer pageNum, Integer pageSize);

    void create(ServiceReport serviceReport);

    void delete(Long id);
}

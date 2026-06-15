package com.sany.platform.service;

import com.sany.platform.model.entity.OperationLog;
import com.sany.platform.model.vo.PageResult;

public interface OperationLogService {
    PageResult<OperationLog> pageList(String action, String operator, Integer pageNum, Integer pageSize);
}

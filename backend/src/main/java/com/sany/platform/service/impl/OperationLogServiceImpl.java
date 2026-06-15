package com.sany.platform.service.impl;

import com.sany.platform.mapper.OperationLogMapper;
import com.sany.platform.model.entity.OperationLog;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    private static final Logger log = LoggerFactory.getLogger(OperationLogServiceImpl.class);

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public PageResult<OperationLog> pageList(String action, String operator, Integer pageNum, Integer pageSize) {
        long total = operationLogMapper.countList(action, operator);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<OperationLog> all = operationLogMapper.selectList(action, operator);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }
}

package com.sany.platform.service;

import com.sany.platform.model.entity.Settlement;
import com.sany.platform.model.vo.PageResult;

import java.util.Map;

public interface SettlementService {

    Settlement getById(Long id);

    PageResult<Settlement> pageList(Integer serviceType, Integer status, String customerName,
                                    Integer pageNum, Integer pageSize);

    Map<String, Object> getStats();

    Settlement create(Settlement settlement);

    void update(Settlement settlement);

    void updateStatus(Long id, Integer status);

    void delete(Long id);
}

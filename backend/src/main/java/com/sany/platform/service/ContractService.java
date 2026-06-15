package com.sany.platform.service;

import com.sany.platform.model.entity.Contract;
import com.sany.platform.model.vo.PageResult;

import java.util.Map;

public interface ContractService {

    Contract getById(Long id);

    PageResult<Contract> pageList(Integer contractType, Integer status, String customerName,
                                  Integer pageNum, Integer pageSize);

    Map<String, Object> getStats();

    Contract create(Contract contract);

    void update(Contract contract);

    void delete(Long id);
}

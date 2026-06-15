package com.sany.platform.service;

import com.sany.platform.model.entity.WarehouseTransaction;
import com.sany.platform.model.vo.PageResult;

public interface WarehouseTransactionService {

    PageResult<WarehouseTransaction> pageList(Integer pageNum, Integer pageSize, Integer type, String itemName);

    WarehouseTransaction create(WarehouseTransaction transaction);
}

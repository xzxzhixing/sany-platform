package com.sany.platform.service;

import com.sany.platform.model.entity.WarehouseItem;
import com.sany.platform.model.vo.PageResult;

public interface WarehouseItemService {

    WarehouseItem getById(Long id);

    PageResult<WarehouseItem> pageList(Integer pageNum, Integer pageSize, String category, String itemName, Integer status);

    WarehouseItem create(WarehouseItem item);

    void update(WarehouseItem item);

    void delete(Long id);
}

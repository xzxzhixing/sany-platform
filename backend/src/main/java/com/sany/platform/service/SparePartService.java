package com.sany.platform.service;

import com.sany.platform.model.entity.SparePart;
import com.sany.platform.model.vo.PageResult;

public interface SparePartService {
    SparePart getById(Long id);

    PageResult<SparePart> pageList(String partName, String partCode, Integer stockStatus, Integer pageNum, Integer pageSize);

    void create(SparePart sparePart);

    void update(SparePart sparePart);

    void delete(Long id);
}

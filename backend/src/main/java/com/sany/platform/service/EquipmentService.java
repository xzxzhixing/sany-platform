package com.sany.platform.service;

import com.sany.platform.model.entity.Equipment;
import com.sany.platform.model.vo.PageResult;

public interface EquipmentService {

    PageResult<Equipment> pageList(String equipmentName, String equipmentCode, Integer status, Integer pageNum, Integer pageSize);

    Equipment getById(Long id);

    Equipment create(Equipment equipment);
}

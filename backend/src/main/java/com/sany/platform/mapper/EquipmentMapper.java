package com.sany.platform.mapper;

import com.sany.platform.model.entity.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentMapper {

    Equipment selectById(@Param("id") Long id);

    Equipment selectByCode(@Param("equipmentCode") String equipmentCode);
}

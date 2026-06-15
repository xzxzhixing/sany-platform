package com.sany.platform.mapper;

import com.sany.platform.model.entity.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EquipmentMapper {

    Equipment selectById(@Param("id") Long id);

    Equipment selectByCode(@Param("equipmentCode") String equipmentCode);

    List<Equipment> selectList(@Param("equipmentName") String equipmentName, @Param("equipmentCode") String equipmentCode, @Param("status") Integer status);

    long countList(@Param("equipmentName") String equipmentName, @Param("equipmentCode") String equipmentCode, @Param("status") Integer status);

    int insert(Equipment equipment);
}

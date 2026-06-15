package com.sany.platform.mapper;

import com.sany.platform.model.entity.MaintenancePlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MaintenancePlanMapper {
    MaintenancePlan selectById(@Param("id") Long id);

    List<MaintenancePlan> selectList(@Param("planType") Integer planType,
                                     @Param("status") Integer status,
                                     @Param("equipmentName") String equipmentName);

    long countList(@Param("planType") Integer planType,
                   @Param("status") Integer status,
                   @Param("equipmentName") String equipmentName);

    int insert(MaintenancePlan maintenancePlan);

    int updateById(MaintenancePlan maintenancePlan);

    int deleteById(@Param("id") Long id);
}

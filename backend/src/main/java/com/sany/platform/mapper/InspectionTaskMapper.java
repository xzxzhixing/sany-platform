package com.sany.platform.mapper;

import com.sany.platform.model.entity.InspectionTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InspectionTaskMapper {

    InspectionTask selectById(@Param("id") Long id);

    List<InspectionTask> selectList(@Param("taskType") Integer taskType,
                                    @Param("status") Integer status,
                                    @Param("equipmentName") String equipmentName);

    long countList(@Param("taskType") Integer taskType,
                   @Param("status") Integer status,
                   @Param("equipmentName") String equipmentName);

    long countByStatus(@Param("status") Integer status);

    long countDelayed();

    long countAll();

    int insert(InspectionTask task);

    int updateById(InspectionTask task);

    int updateStatus(@Param("id") Long id,
                     @Param("newStatus") Integer newStatus);

    int updateStart(@Param("id") Long id,
                    @Param("actualDate") String actualDate);

    int updateComplete(@Param("id") Long id,
                       @Param("status") Integer status,
                       @Param("result") Integer result);

    int deleteById(@Param("id") Long id);

    String selectMaxTaskNoByDate(@Param("datePrefix") String datePrefix);
}

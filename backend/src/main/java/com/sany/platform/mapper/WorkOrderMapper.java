package com.sany.platform.mapper;

import com.sany.platform.model.entity.WorkOrder;
import com.sany.platform.model.vo.WorkOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkOrderMapper {

    WorkOrder selectById(@Param("id") Long id);

    WorkOrderVO selectVOById(@Param("id") Long id);

    List<WorkOrderVO> selectVOList(@Param("orderNo") String orderNo,
                                   @Param("equipmentCode") String equipmentCode,
                                   @Param("orderStatus") Integer orderStatus,
                                   @Param("customerId") Long customerId,
                                   @Param("assigneeId") Long assigneeId,
                                   @Param("orderType") Integer orderType,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime);

    long countList(@Param("orderNo") String orderNo,
                   @Param("equipmentCode") String equipmentCode,
                   @Param("orderStatus") Integer orderStatus,
                   @Param("customerId") Long customerId,
                   @Param("assigneeId") Long assigneeId,
                   @Param("orderType") Integer orderType,
                   @Param("startTime") String startTime,
                   @Param("endTime") String endTime);

    int insert(WorkOrder workOrder);

    int updateById(WorkOrder workOrder);

    int updateStatus(@Param("id") Long id,
                     @Param("newStatus") Integer newStatus,
                     @Param("oldStatus") Integer oldStatus);

    int deleteById(@Param("id") Long id);

    String selectMaxOrderNoByDate(@Param("datePrefix") String datePrefix);
}

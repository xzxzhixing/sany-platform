package com.sany.platform.service;

import com.sany.platform.model.dto.WorkOrderCreateDTO;
import com.sany.platform.model.dto.WorkOrderQueryDTO;
import com.sany.platform.model.entity.WorkOrder;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.model.vo.WorkOrderVO;

public interface WorkOrderService {

    WorkOrderVO getById(Long id);

    PageResult<WorkOrderVO> pageList(WorkOrderQueryDTO queryDTO);

    WorkOrder create(WorkOrderCreateDTO createDTO);

    void update(WorkOrder workOrder);

    void dispatch(Long id, Long assigneeId);

    void updateStatus(Long id, Integer newStatus, Integer oldStatus);

    void delete(Long id);
}

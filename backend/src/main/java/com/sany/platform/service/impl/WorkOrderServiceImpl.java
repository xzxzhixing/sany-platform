package com.sany.platform.service.impl;

import com.sany.platform.common.enums.WorkOrderStatusEnum;
import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.EquipmentMapper;
import com.sany.platform.mapper.WorkOrderMapper;
import com.sany.platform.model.dto.WorkOrderCreateDTO;
import com.sany.platform.model.dto.WorkOrderQueryDTO;
import com.sany.platform.model.entity.Equipment;
import com.sany.platform.model.entity.WorkOrder;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.model.vo.WorkOrderVO;
import com.sany.platform.service.WorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderServiceImpl.class);

    @Resource
    private WorkOrderMapper workOrderMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Override
    public WorkOrderVO getById(Long id) {
        if (id == null) throw new BusinessException(400, "工单ID不能为空");
        WorkOrderVO vo = workOrderMapper.selectVOById(id);
        if (vo == null) throw new BusinessException(404, "工单不存在或已被删除");
        return vo;
    }

    @Override
    public PageResult<WorkOrderVO> pageList(WorkOrderQueryDTO dto) {
        long total = workOrderMapper.countList(
                dto.getOrderNo(), dto.getEquipmentCode(),
                dto.getOrderStatus(), dto.getCustomerId(),
                dto.getAssigneeId(), dto.getOrderType(),
                dto.getStartTime(), dto.getEndTime());

        if (total == 0) {
            return new PageResult<>(0, dto.getPageNum(), dto.getPageSize(), Collections.emptyList());
        }

        List<WorkOrderVO> all = workOrderMapper.selectVOList(
                dto.getOrderNo(), dto.getEquipmentCode(),
                dto.getOrderStatus(), dto.getCustomerId(),
                dto.getAssigneeId(), dto.getOrderType(),
                dto.getStartTime(), dto.getEndTime());

        int from = (dto.getPageNum() - 1) * dto.getPageSize();
        int to = Math.min(from + dto.getPageSize(), all.size());
        return new PageResult<>(total, dto.getPageNum(), dto.getPageSize(), all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WorkOrder create(WorkOrderCreateDTO dto) {
        log.info("创建工单, 设备编号: {}, 报修人: {}", dto.getEquipmentCode(), dto.getReporterName());

        Equipment eq = equipmentMapper.selectByCode(dto.getEquipmentCode());
        if (eq == null) throw new BusinessException(400, "设备编号不存在");

        WorkOrder wo = new WorkOrder();
        wo.setOrderNo(generateOrderNo());
        wo.setOrderType(dto.getOrderType() != null ? dto.getOrderType() : 1);
        wo.setOrderSource(1);
        wo.setCustomerId(dto.getCustomerId());
        wo.setEquipmentId(eq.getId());
        wo.setReporterName(dto.getReporterName());
        wo.setReporterPhone(dto.getReporterPhone());
        wo.setFaultDescription(dto.getFaultDescription());
        wo.setFaultLevel(dto.getFaultLevel() != null ? dto.getFaultLevel() : 3);
        wo.setFaultCategory(dto.getFaultCategory());
        wo.setOrderStatus(WorkOrderStatusEnum.PENDING_DISPATCH.getCode());

        int rows = workOrderMapper.insert(wo);
        if (rows != 1) throw new BusinessException("工单创建失败");

        log.info("工单创建成功, 编号: {}, ID: {}", wo.getOrderNo(), wo.getId());
        return wo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WorkOrder workOrder) {
        if (workOrder.getId() == null) throw new BusinessException(400, "工单ID不能为空");
        WorkOrder exist = workOrderMapper.selectById(workOrder.getId());
        if (exist == null) throw new BusinessException(404, "工单不存在");
        int rows = workOrderMapper.updateById(workOrder);
        if (rows != 1) throw new BusinessException("工单更新失败");
        log.info("工单更新成功, ID: {}", workOrder.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dispatch(Long id, Long assigneeId) {
        log.info("派单, 工单ID: {}, 运维人员ID: {}", id, assigneeId);
        if (id == null || assigneeId == null) throw new BusinessException(400, "参数不完整");

        WorkOrder exist = workOrderMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "工单不存在");
        if (exist.getOrderStatus() != WorkOrderStatusEnum.PENDING_DISPATCH.getCode()) {
            throw new BusinessException(400, "当前状态不允许派单");
        }

        WorkOrder update = new WorkOrder();
        update.setId(id);
        update.setAssigneeId(assigneeId);
        update.setAssignTime(LocalDateTime.now());
        update.setOrderStatus(WorkOrderStatusEnum.PENDING_ACCEPT.getCode());

        int rows = workOrderMapper.updateById(update);
        if (rows != 1) throw new BusinessException("派单失败");
        log.info("派单成功, 工单ID: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer newStatus, Integer oldStatus) {
        log.info("更新状态, ID: {}, 新状态: {}", id, newStatus);
        if (id == null || newStatus == null) throw new BusinessException(400, "参数不完整");

        WorkOrder exist = workOrderMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "工单不存在");
        WorkOrderStatusEnum.of(newStatus); // 校验状态值合法性

        if (oldStatus != null && !exist.getOrderStatus().equals(oldStatus)) {
            throw new BusinessException(400, "工单状态已变更，请刷新后重试");
        }

        int rows = workOrderMapper.updateStatus(id, newStatus, oldStatus);
        if (rows != 1) throw new BusinessException("状态更新失败");
        log.info("状态更新成功, ID: {}, 当前: {}", id, WorkOrderStatusEnum.of(newStatus).getDescription());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除工单, ID: {}", id);
        if (id == null) throw new BusinessException(400, "工单ID不能为空");
        WorkOrder exist = workOrderMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "工单不存在");
        int rows = workOrderMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("工单删除成功, ID: {}", id);
    }

    private String generateOrderNo() {
        String prefix = "WO" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String max = workOrderMapper.selectMaxOrderNoByDate(prefix);
        if (max == null) return prefix + "0001";
        int seq = Integer.parseInt(max.substring(max.length() - 4)) + 1;
        return prefix + String.format("%04d", seq);
    }
}

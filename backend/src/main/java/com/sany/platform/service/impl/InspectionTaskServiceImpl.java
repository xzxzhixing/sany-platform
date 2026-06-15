package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.InspectionTaskMapper;
import com.sany.platform.model.entity.InspectionTask;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.InspectionTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InspectionTaskServiceImpl implements InspectionTaskService {

    private static final Logger log = LoggerFactory.getLogger(InspectionTaskServiceImpl.class);

    @Resource
    private InspectionTaskMapper inspectionTaskMapper;

    @Override
    public InspectionTask getById(Long id) {
        if (id == null) throw new BusinessException(400, "任务ID不能为空");
        InspectionTask task = inspectionTaskMapper.selectById(id);
        if (task == null) throw new BusinessException(404, "巡检任务不存在或已被删除");
        return task;
    }

    @Override
    public PageResult<InspectionTask> pageList(Integer pageNum, Integer pageSize, Integer taskType, Integer status, String equipmentName) {
        long total = inspectionTaskMapper.countList(taskType, status, equipmentName);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<InspectionTask> all = inspectionTaskMapper.selectList(taskType, status, equipmentName);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InspectionTask create(InspectionTask task) {
        log.info("创建巡检任务, 设备: {}, 客户: {}", task.getEquipmentName(), task.getCustomerName());
        task.setTaskNo(generateTaskNo());
        task.setStatus(0);
        task.setDeleted(0);
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        int rows = inspectionTaskMapper.insert(task);
        if (rows != 1) throw new BusinessException("巡检任务创建失败");
        log.info("巡检任务创建成功, ID: {}, 编号: {}", task.getId(), task.getTaskNo());
        return task;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void start(Long id) {
        log.info("开始执行巡检任务, ID: {}", id);
        if (id == null) throw new BusinessException(400, "任务ID不能为空");
        InspectionTask exist = inspectionTaskMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "巡检任务不存在");
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int rows = inspectionTaskMapper.updateStart(id, today);
        if (rows != 1) throw new BusinessException("开始任务失败");
        log.info("巡检任务开始执行成功, ID: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void complete(Long id) {
        log.info("完成巡检任务, ID: {}", id);
        if (id == null) throw new BusinessException(400, "任务ID不能为空");
        InspectionTask exist = inspectionTaskMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "巡检任务不存在");
        int rows = inspectionTaskMapper.updateComplete(id, 2, 1);
        if (rows != 1) throw new BusinessException("完成任务失败");
        log.info("巡检任务完成成功, ID: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除巡检任务, ID: {}", id);
        if (id == null) throw new BusinessException(400, "任务ID不能为空");
        InspectionTask exist = inspectionTaskMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "巡检任务不存在");
        int rows = inspectionTaskMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("巡检任务删除成功, ID: {}", id);
    }

    @Override
    public Map<String, Object> getStats() {
        long pendingCount = inspectionTaskMapper.countByStatus(0);
        long inProgressCount = inspectionTaskMapper.countByStatus(1);
        long completedCount = inspectionTaskMapper.countByStatus(2);
        long delayedCount = inspectionTaskMapper.countDelayed();
        long total = inspectionTaskMapper.countAll();
        Map<String, Object> stats = new HashMap<>();
        stats.put("pendingCount", pendingCount);
        stats.put("inProgressCount", inProgressCount);
        stats.put("completedCount", completedCount);
        stats.put("delayedCount", delayedCount);
        stats.put("total", total);
        return stats;
    }

    private String generateTaskNo() {
        String prefix = "IT-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String max = inspectionTaskMapper.selectMaxTaskNoByDate(prefix);
        if (max == null) return prefix + "0001";
        int seq = Integer.parseInt(max.substring(max.length() - 4)) + 1;
        return prefix + String.format("%04d", seq);
    }
}

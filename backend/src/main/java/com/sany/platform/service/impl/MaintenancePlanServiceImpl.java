package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.MaintenancePlanMapper;
import com.sany.platform.model.entity.MaintenancePlan;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.MaintenancePlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class MaintenancePlanServiceImpl implements MaintenancePlanService {

    private static final Logger log = LoggerFactory.getLogger(MaintenancePlanServiceImpl.class);

    @Resource
    private MaintenancePlanMapper maintenancePlanMapper;

    @Override
    public PageResult<MaintenancePlan> pageList(Integer planType, Integer status, String equipmentName, Integer pageNum, Integer pageSize) {
        long total = maintenancePlanMapper.countList(planType, status, equipmentName);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<MaintenancePlan> all = maintenancePlanMapper.selectList(planType, status, equipmentName);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MaintenancePlan maintenancePlan) {
        log.info("创建保养计划");
        int rows = maintenancePlanMapper.insert(maintenancePlan);
        if (rows != 1) throw new BusinessException("保养计划创建失败");
        log.info("保养计划创建成功, ID: {}", maintenancePlan.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaintenancePlan maintenancePlan) {
        if (maintenancePlan.getId() == null) throw new BusinessException(400, "保养计划ID不能为空");
        MaintenancePlan exist = maintenancePlanMapper.selectById(maintenancePlan.getId());
        if (exist == null) throw new BusinessException(404, "保养计划不存在或已被删除");
        int rows = maintenancePlanMapper.updateById(maintenancePlan);
        if (rows != 1) throw new BusinessException("保养计划更新失败");
        log.info("保养计划更新成功, ID: {}", maintenancePlan.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除保养计划, ID: {}", id);
        if (id == null) throw new BusinessException(400, "保养计划ID不能为空");
        MaintenancePlan exist = maintenancePlanMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "保养计划不存在或已被删除");
        int rows = maintenancePlanMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("保养计划删除成功, ID: {}", id);
    }
}

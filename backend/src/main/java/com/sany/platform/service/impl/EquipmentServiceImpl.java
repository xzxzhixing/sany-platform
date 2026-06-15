package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.EquipmentMapper;
import com.sany.platform.model.entity.Equipment;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Resource
    private EquipmentMapper equipmentMapper;

    @Override
    public PageResult<Equipment> pageList(String equipmentName, String equipmentCode, Integer status, Integer pageNum, Integer pageSize) {
        long total = equipmentMapper.countList(equipmentName, equipmentCode, status);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<Equipment> all = equipmentMapper.selectList(equipmentName, equipmentCode, status);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    public Equipment getById(Long id) {
        if (id == null) throw new BusinessException(400, "设备ID不能为空");
        Equipment equipment = equipmentMapper.selectById(id);
        if (equipment == null) throw new BusinessException(404, "设备不存在或已被删除");
        return equipment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Equipment create(Equipment equipment) {
        log.info("创建设备, 名称: {}, 编码: {}", equipment.getEquipmentName(), equipment.getEquipmentCode());
        if (equipment.getEquipmentName() == null || equipment.getEquipmentName().isEmpty()) {
            throw new BusinessException(400, "设备名称不能为空");
        }
        if (equipment.getEquipmentCode() == null || equipment.getEquipmentCode().isEmpty()) {
            throw new BusinessException(400, "设备编码不能为空");
        }
        Equipment exist = equipmentMapper.selectByCode(equipment.getEquipmentCode());
        if (exist != null) throw new BusinessException(400, "设备编码已存在");
        equipment.setStatus(1);
        int rows = equipmentMapper.insert(equipment);
        if (rows != 1) throw new BusinessException("设备创建失败");
        log.info("设备创建成功, ID: {}", equipment.getId());
        return equipment;
    }
}

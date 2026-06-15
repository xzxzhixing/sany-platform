package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.QualityRecordMapper;
import com.sany.platform.model.entity.QualityRecord;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.QualityRecordService;
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
public class QualityRecordServiceImpl implements QualityRecordService {

    private static final Logger log = LoggerFactory.getLogger(QualityRecordServiceImpl.class);

    @Resource
    private QualityRecordMapper qualityRecordMapper;

    @Override
    public QualityRecord getById(Long id) {
        if (id == null) throw new BusinessException(400, "记录ID不能为空");
        QualityRecord record = qualityRecordMapper.selectById(id);
        if (record == null) throw new BusinessException(404, "质量记录不存在或已被删除");
        return record;
    }

    @Override
    public PageResult<QualityRecord> pageList(Integer pageNum, Integer pageSize, Integer inspectionType, Integer result, String equipmentName) {
        long total = qualityRecordMapper.countList(inspectionType, result, equipmentName);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<QualityRecord> all = qualityRecordMapper.selectList(inspectionType, result, equipmentName);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QualityRecord create(QualityRecord record) {
        log.info("创建质量记录, 设备: {}, 检验人: {}", record.getEquipmentName(), record.getInspector());
        record.setRecordNo(generateRecordNo());
        record.setDeleted(0);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        int rows = qualityRecordMapper.insert(record);
        if (rows != 1) throw new BusinessException("质量记录创建失败");
        log.info("质量记录创建成功, ID: {}, 编号: {}", record.getId(), record.getRecordNo());
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QualityRecord record) {
        if (record.getId() == null) throw new BusinessException(400, "记录ID不能为空");
        QualityRecord exist = qualityRecordMapper.selectById(record.getId());
        if (exist == null) throw new BusinessException(404, "质量记录不存在");
        record.setUpdateTime(LocalDateTime.now());
        int rows = qualityRecordMapper.updateById(record);
        if (rows != 1) throw new BusinessException("质量记录更新失败");
        log.info("质量记录更新成功, ID: {}", record.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除质量记录, ID: {}", id);
        if (id == null) throw new BusinessException(400, "记录ID不能为空");
        QualityRecord exist = qualityRecordMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "质量记录不存在");
        int rows = qualityRecordMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("质量记录删除成功, ID: {}", id);
    }

    private String generateRecordNo() {
        String prefix = "QC-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String max = qualityRecordMapper.selectMaxRecordNoByDate(prefix);
        if (max == null) return prefix + "0001";
        int seq = Integer.parseInt(max.substring(max.length() - 4)) + 1;
        return prefix + String.format("%04d", seq);
    }
}

package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.TrainingRecordMapper;
import com.sany.platform.model.entity.TrainingRecord;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.TrainingRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class TrainingRecordServiceImpl implements TrainingRecordService {

    private static final Logger log = LoggerFactory.getLogger(TrainingRecordServiceImpl.class);

    @Resource
    private TrainingRecordMapper trainingRecordMapper;

    @Override
    public PageResult<TrainingRecord> pageList(Integer courseType, Integer status,
                                               Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        if (pageSize == null || pageSize < 1) pageSize = 15;

        long total = trainingRecordMapper.countList(courseType, status);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }

        List<TrainingRecord> all = trainingRecordMapper.selectList(courseType, status);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TrainingRecord create(TrainingRecord record) {
        log.info("创建培训记录, 课程: {}", record.getCourseName());
        record.setRecordNo(generateRecordNo());
        record.setDeleted(0);
        int rows = trainingRecordMapper.insert(record);
        if (rows != 1) throw new BusinessException("培训记录创建失败");
        log.info("培训记录创建成功, ID: {}, 编号: {}", record.getId(), record.getRecordNo());
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TrainingRecord record) {
        if (record.getId() == null) throw new BusinessException(400, "培训记录ID不能为空");
        TrainingRecord exist = trainingRecordMapper.selectById(record.getId());
        if (exist == null) throw new BusinessException(404, "培训记录不存在");
        int rows = trainingRecordMapper.updateById(record);
        if (rows != 1) throw new BusinessException("培训记录更新失败");
        log.info("培训记录更新成功, ID: {}", record.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除培训记录, ID: {}", id);
        if (id == null) throw new BusinessException(400, "培训记录ID不能为空");
        TrainingRecord exist = trainingRecordMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "培训记录不存在");
        int rows = trainingRecordMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("培训记录删除成功, ID: {}", id);
    }

    private String generateRecordNo() {
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "TR-" + datePrefix;
        String max = trainingRecordMapper.selectMaxRecordNoByDate(datePrefix);
        if (max == null) return prefix + "0001";
        int seq = Integer.parseInt(max.substring(max.length() - 4)) + 1;
        return prefix + String.format("%04d", seq);
    }
}

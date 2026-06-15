package com.sany.platform.service;

import com.sany.platform.model.entity.QualityRecord;
import com.sany.platform.model.vo.PageResult;

public interface QualityRecordService {

    QualityRecord getById(Long id);

    PageResult<QualityRecord> pageList(Integer pageNum, Integer pageSize, Integer inspectionType, Integer result, String equipmentName);

    QualityRecord create(QualityRecord record);

    void update(QualityRecord record);

    void delete(Long id);
}

package com.sany.platform.service;

import com.sany.platform.model.entity.TrainingRecord;
import com.sany.platform.model.vo.PageResult;

public interface TrainingRecordService {

    PageResult<TrainingRecord> pageList(Integer courseType, Integer status,
                                        Integer pageNum, Integer pageSize);

    TrainingRecord create(TrainingRecord record);

    void update(TrainingRecord record);

    void delete(Long id);
}

package com.sany.platform.service;

import com.sany.platform.model.entity.Evaluation;
import com.sany.platform.model.vo.PageResult;

public interface EvaluationService {
    Evaluation findByOrderId(Long orderId);

    PageResult<Evaluation> pageList(String orderNo, Integer pageNum, Integer pageSize);

    void create(Evaluation evaluation);
}

package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.EvaluationMapper;
import com.sany.platform.model.entity.Evaluation;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.EvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private static final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    @Resource
    private EvaluationMapper evaluationMapper;

    @Override
    public Evaluation findByOrderId(Long orderId) {
        if (orderId == null) throw new BusinessException(400, "工单ID不能为空");
        return evaluationMapper.findByOrderId(orderId);
    }

    @Override
    public PageResult<Evaluation> pageList(String orderNo, Integer pageNum, Integer pageSize) {
        long total = evaluationMapper.countList(orderNo);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<Evaluation> all = evaluationMapper.selectList(orderNo);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Evaluation evaluation) {
        log.info("创建评价");
        evaluation.setEvaluateTime(LocalDateTime.now());
        int rows = evaluationMapper.insert(evaluation);
        if (rows != 1) throw new BusinessException("评价创建失败");
        log.info("评价创建成功, ID: {}", evaluation.getId());
    }
}

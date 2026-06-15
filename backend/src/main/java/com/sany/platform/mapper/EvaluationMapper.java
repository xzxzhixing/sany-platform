package com.sany.platform.mapper;

import com.sany.platform.model.entity.Evaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EvaluationMapper {
    Evaluation findByOrderId(@Param("orderId") Long orderId);

    List<Evaluation> selectList(@Param("orderNo") String orderNo);

    long countList(@Param("orderNo") String orderNo);

    int insert(Evaluation evaluation);
}

package com.sany.platform.mapper;

import com.sany.platform.model.entity.TrainingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainingRecordMapper {

    TrainingRecord selectById(@Param("id") Long id);

    List<TrainingRecord> selectList(@Param("courseType") Integer courseType,
                                    @Param("status") Integer status);

    long countList(@Param("courseType") Integer courseType,
                   @Param("status") Integer status);

    int insert(TrainingRecord record);

    int updateById(TrainingRecord record);

    int deleteById(@Param("id") Long id);

    String selectMaxRecordNoByDate(@Param("datePrefix") String datePrefix);
}

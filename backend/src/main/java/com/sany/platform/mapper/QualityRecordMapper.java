package com.sany.platform.mapper;

import com.sany.platform.model.entity.QualityRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QualityRecordMapper {

    QualityRecord selectById(@Param("id") Long id);

    List<QualityRecord> selectList(@Param("inspectionType") Integer inspectionType,
                                   @Param("result") Integer result,
                                   @Param("equipmentName") String equipmentName);

    long countList(@Param("inspectionType") Integer inspectionType,
                   @Param("result") Integer result,
                   @Param("equipmentName") String equipmentName);

    int insert(QualityRecord record);

    int updateById(QualityRecord record);

    int deleteById(@Param("id") Long id);

    String selectMaxRecordNoByDate(@Param("datePrefix") String datePrefix);
}

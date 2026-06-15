package com.sany.platform.mapper;

import com.sany.platform.model.entity.ServiceReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ServiceReportMapper {
    ServiceReport selectById(@Param("id") Long id);

    List<ServiceReport> selectList(@Param("reportNo") String reportNo,
                                   @Param("orderNo") String orderNo,
                                   @Param("reportType") Integer reportType);

    long countList(@Param("reportNo") String reportNo,
                   @Param("orderNo") String orderNo,
                   @Param("reportType") Integer reportType);

    int insert(ServiceReport serviceReport);

    int deleteById(@Param("id") Long id);

    String selectMaxReportNoByDate(@Param("datePrefix") String datePrefix);
}

package com.sany.platform.mapper;

import com.sany.platform.model.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OperationLogMapper {
    List<OperationLog> selectList(@Param("action") String action,
                                  @Param("operator") String operator);

    long countList(@Param("action") String action,
                   @Param("operator") String operator);
}

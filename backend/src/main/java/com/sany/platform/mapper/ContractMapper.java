package com.sany.platform.mapper;

import com.sany.platform.model.entity.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {

    Contract selectById(@Param("id") Long id);

    List<Contract> selectList(@Param("contractType") Integer contractType,
                              @Param("status") Integer status,
                              @Param("customerName") String customerName);

    long countList(@Param("contractType") Integer contractType,
                   @Param("status") Integer status,
                   @Param("customerName") String customerName);

    long selectTotalCount();

    long selectActiveCount();

    long selectExpiringThisMonthCount(@Param("monthStart") String monthStart,
                                      @Param("monthEnd") String monthEnd);

    java.math.BigDecimal selectTotalAmount();

    int insert(Contract contract);

    int updateById(Contract contract);

    int deleteById(@Param("id") Long id);
}

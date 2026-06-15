package com.sany.platform.mapper;

import com.sany.platform.model.entity.WarehouseTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseTransactionMapper {

    List<WarehouseTransaction> selectList(@Param("type") Integer type,
                                           @Param("itemName") String itemName);

    long countList(@Param("type") Integer type,
                   @Param("itemName") String itemName);

    int insert(WarehouseTransaction transaction);

    String selectMaxTransactionNoByDate(@Param("datePrefix") String datePrefix);
}

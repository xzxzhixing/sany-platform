package com.sany.platform.mapper;

import com.sany.platform.model.entity.WarehouseItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseItemMapper {

    WarehouseItem selectById(@Param("id") Long id);

    List<WarehouseItem> selectList(@Param("category") String category,
                                   @Param("itemName") String itemName,
                                   @Param("status") Integer status);

    long countList(@Param("category") String category,
                   @Param("itemName") String itemName,
                   @Param("status") Integer status);

    int insert(WarehouseItem item);

    int updateById(WarehouseItem item);

    int deleteById(@Param("id") Long id);
}

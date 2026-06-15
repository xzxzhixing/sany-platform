package com.sany.platform.mapper;

import com.sany.platform.model.entity.SparePart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SparePartMapper {
    SparePart selectById(@Param("id") Long id);

    List<SparePart> selectList(@Param("partName") String partName,
                               @Param("partCode") String partCode,
                               @Param("stockStatus") Integer stockStatus);

    long countList(@Param("partName") String partName,
                   @Param("partCode") String partCode,
                   @Param("stockStatus") Integer stockStatus);

    int insert(SparePart sparePart);

    int updateById(SparePart sparePart);

    int deleteById(@Param("id") Long id);
}

package com.sany.platform.mapper;

import com.sany.platform.model.entity.Settlement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SettlementMapper {

    Settlement selectById(@Param("id") Long id);

    List<Settlement> selectList(@Param("serviceType") Integer serviceType,
                                @Param("status") Integer status,
                                @Param("customerName") String customerName);

    long countList(@Param("serviceType") Integer serviceType,
                   @Param("status") Integer status,
                   @Param("customerName") String customerName);

    int insert(Settlement settlement);

    int updateById(Settlement settlement);

    int deleteById(@Param("id") Long id);

    void updateStatusWithBillingDate(@Param("id") Long id,
                                     @Param("status") Integer status,
                                     @Param("billingDate") String billingDate);

    BigDecimal selectPendingInvoice();

    BigDecimal selectCollectedThisMonth(@Param("monthStart") String monthStart,
                                        @Param("monthEnd") String monthEnd);

    BigDecimal selectQuarterTotal();

    BigDecimal selectUncollected();
}

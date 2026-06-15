package com.sany.platform.mapper;

import com.sany.platform.model.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {
    Customer selectById(@Param("id") Long id);
    List<Customer> selectList(@Param("customerName") String customerName, @Param("city") String city, @Param("serviceLevel") Integer serviceLevel);
    long countList(@Param("customerName") String customerName, @Param("city") String city, @Param("serviceLevel") Integer serviceLevel);
    int insert(Customer customer);
    int updateById(Customer customer);
    int deleteById(@Param("id") Long id);
}

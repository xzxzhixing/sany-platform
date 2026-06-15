package com.sany.platform.mapper;

import com.sany.platform.model.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    List<Notification> selectList(@Param("type") Integer type);

    long countList(@Param("type") Integer type);

    Notification selectById(@Param("id") Long id);

    int insert(Notification notification);

    int updateById(Notification notification);

    int markAllRead();

    int deleteById(@Param("id") Long id);
}

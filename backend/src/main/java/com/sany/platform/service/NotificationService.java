package com.sany.platform.service;

import com.sany.platform.model.entity.Notification;
import com.sany.platform.model.vo.PageResult;

public interface NotificationService {

    PageResult<Notification> pageList(Integer type, Integer pageNum, Integer pageSize);

    void markRead(Long id);

    void markAllRead();

    void delete(Long id);
}

package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.NotificationMapper;
import com.sany.platform.model.entity.Notification;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public PageResult<Notification> pageList(Integer type, Integer pageNum, Integer pageSize) {
        long total = notificationMapper.countList(type);

        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }

        List<Notification> all = notificationMapper.selectList(type);

        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        List<Notification> page = from >= all.size() ? Collections.emptyList() : all.subList(from, to);

        return new PageResult<>(total, pageNum, pageSize, page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markRead(Long id) {
        if (id == null) throw new BusinessException(400, "通知ID不能为空");
        Notification exist = notificationMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "通知不存在");
        Notification update = new Notification();
        update.setId(id);
        update.setIsRead(1);
        notificationMapper.updateById(update);
        log.info("通知已标记已读, ID: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markAllRead() {
        int rows = notificationMapper.markAllRead();
        log.info("全部通知已标记已读, 影响行数: {}", rows);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (id == null) throw new BusinessException(400, "通知ID不能为空");
        Notification exist = notificationMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "通知不存在");
        notificationMapper.deleteById(id);
        log.info("通知已删除, ID: {}", id);
    }
}

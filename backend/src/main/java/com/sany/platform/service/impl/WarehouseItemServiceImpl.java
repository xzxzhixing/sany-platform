package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.WarehouseItemMapper;
import com.sany.platform.model.entity.WarehouseItem;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.WarehouseItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class WarehouseItemServiceImpl implements WarehouseItemService {

    private static final Logger log = LoggerFactory.getLogger(WarehouseItemServiceImpl.class);

    @Resource
    private WarehouseItemMapper warehouseItemMapper;

    @Override
    public WarehouseItem getById(Long id) {
        if (id == null) throw new BusinessException(400, "物料ID不能为空");
        WarehouseItem item = warehouseItemMapper.selectById(id);
        if (item == null) throw new BusinessException(404, "物料不存在或已被删除");
        return item;
    }

    @Override
    public PageResult<WarehouseItem> pageList(Integer pageNum, Integer pageSize, String category, String itemName, Integer status) {
        long total = warehouseItemMapper.countList(category, itemName, status);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<WarehouseItem> all = warehouseItemMapper.selectList(category, itemName, status);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WarehouseItem create(WarehouseItem item) {
        log.info("创建物料, 名称: {}, 分类: {}", item.getItemName(), item.getCategory());
        item.setDeleted(0);
        item.setCreateTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        int rows = warehouseItemMapper.insert(item);
        if (rows != 1) throw new BusinessException("物料创建失败");
        log.info("物料创建成功, ID: {}, 编码: {}", item.getId(), item.getItemCode());
        return item;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WarehouseItem item) {
        if (item.getId() == null) throw new BusinessException(400, "物料ID不能为空");
        WarehouseItem exist = warehouseItemMapper.selectById(item.getId());
        if (exist == null) throw new BusinessException(404, "物料不存在");
        item.setUpdateTime(LocalDateTime.now());
        int rows = warehouseItemMapper.updateById(item);
        if (rows != 1) throw new BusinessException("物料更新失败");
        log.info("物料更新成功, ID: {}", item.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除物料, ID: {}", id);
        if (id == null) throw new BusinessException(400, "物料ID不能为空");
        WarehouseItem exist = warehouseItemMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "物料不存在");
        int rows = warehouseItemMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("物料删除成功, ID: {}", id);
    }
}

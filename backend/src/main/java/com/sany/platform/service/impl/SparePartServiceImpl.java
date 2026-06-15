package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.SparePartMapper;
import com.sany.platform.model.entity.SparePart;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.SparePartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SparePartServiceImpl implements SparePartService {

    private static final Logger log = LoggerFactory.getLogger(SparePartServiceImpl.class);

    @Resource
    private SparePartMapper sparePartMapper;

    @Override
    public SparePart getById(Long id) {
        if (id == null) throw new BusinessException(400, "备件ID不能为空");
        SparePart entity = sparePartMapper.selectById(id);
        if (entity == null) throw new BusinessException(404, "备件不存在或已被删除");
        return entity;
    }

    @Override
    public PageResult<SparePart> pageList(String partName, String partCode, Integer stockStatus, Integer pageNum, Integer pageSize) {
        long total = sparePartMapper.countList(partName, partCode, stockStatus);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<SparePart> all = sparePartMapper.selectList(partName, partCode, stockStatus);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SparePart sparePart) {
        log.info("创建备件, 名称: {}", sparePart.getPartName());
        int rows = sparePartMapper.insert(sparePart);
        if (rows != 1) throw new BusinessException("备件创建失败");
        log.info("备件创建成功, ID: {}", sparePart.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SparePart sparePart) {
        if (sparePart.getId() == null) throw new BusinessException(400, "备件ID不能为空");
        SparePart exist = sparePartMapper.selectById(sparePart.getId());
        if (exist == null) throw new BusinessException(404, "备件不存在或已被删除");
        int rows = sparePartMapper.updateById(sparePart);
        if (rows != 1) throw new BusinessException("备件更新失败");
        log.info("备件更新成功, ID: {}", sparePart.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除备件, ID: {}", id);
        if (id == null) throw new BusinessException(400, "备件ID不能为空");
        SparePart exist = sparePartMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "备件不存在或已被删除");
        int rows = sparePartMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("备件删除成功, ID: {}", id);
    }
}

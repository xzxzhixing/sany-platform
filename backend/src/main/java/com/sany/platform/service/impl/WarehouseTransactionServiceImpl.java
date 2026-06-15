package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.WarehouseTransactionMapper;
import com.sany.platform.model.entity.WarehouseTransaction;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.WarehouseTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class WarehouseTransactionServiceImpl implements WarehouseTransactionService {

    private static final Logger log = LoggerFactory.getLogger(WarehouseTransactionServiceImpl.class);

    @Resource
    private WarehouseTransactionMapper warehouseTransactionMapper;

    @Override
    public PageResult<WarehouseTransaction> pageList(Integer pageNum, Integer pageSize, Integer type, String itemName) {
        long total = warehouseTransactionMapper.countList(type, itemName);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<WarehouseTransaction> all = warehouseTransactionMapper.selectList(type, itemName);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WarehouseTransaction create(WarehouseTransaction transaction) {
        log.info("创建仓库交易记录, 物料: {}, 类型: {}", transaction.getItemName(), transaction.getType());
        transaction.setTransactionNo(generateTransactionNo(transaction.getType()));
        transaction.setTransactionDate(LocalDateTime.now());
        int rows = warehouseTransactionMapper.insert(transaction);
        if (rows != 1) throw new BusinessException("仓库交易记录创建失败");
        log.info("仓库交易记录创建成功, ID: {}, 编号: {}", transaction.getId(), transaction.getTransactionNo());
        return transaction;
    }

    private String generateTransactionNo(Integer type) {
        String prefix;
        if (type != null && type == 1) {
            prefix = "IN-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        } else {
            prefix = "OUT-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        String max = warehouseTransactionMapper.selectMaxTransactionNoByDate(prefix);
        if (max == null) return prefix + "0001";
        int seq = Integer.parseInt(max.substring(max.length() - 4)) + 1;
        return prefix + String.format("%04d", seq);
    }
}

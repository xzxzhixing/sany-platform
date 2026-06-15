package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.ContractMapper;
import com.sany.platform.model.entity.Contract;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {

    private static final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Resource
    private ContractMapper contractMapper;

    @Override
    public Contract getById(Long id) {
        if (id == null) throw new BusinessException(400, "合同ID不能为空");
        Contract contract = contractMapper.selectById(id);
        if (contract == null) throw new BusinessException(404, "合同不存在或已被删除");
        return contract;
    }

    @Override
    public PageResult<Contract> pageList(Integer contractType, Integer status, String customerName,
                                         Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        if (pageSize == null || pageSize < 1) pageSize = 15;

        long total = contractMapper.countList(contractType, status, customerName);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }

        List<Contract> all = contractMapper.selectList(contractType, status, customerName);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    public Map<String, Object> getStats() {
        LocalDate now = LocalDate.now();
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd = now.withDayOfMonth(now.lengthOfMonth());

        long total = contractMapper.selectTotalCount();
        long activeCount = contractMapper.selectActiveCount();
        long expiringThisMonth = contractMapper.selectExpiringThisMonthCount(
                monthStart.toString(), monthEnd.toString());
        BigDecimal totalAmount = contractMapper.selectTotalAmount();

        Map<String, Object> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("activeCount", activeCount);
        stats.put("expiringThisMonth", expiringThisMonth);
        stats.put("totalAmount", totalAmount);
        return stats;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Contract create(Contract contract) {
        log.info("创建合同, 名称: {}", contract.getContractName());
        contract.setDeleted(0);
        int rows = contractMapper.insert(contract);
        if (rows != 1) throw new BusinessException("合同创建失败");
        log.info("合同创建成功, ID: {}", contract.getId());
        return contract;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Contract contract) {
        if (contract.getId() == null) throw new BusinessException(400, "合同ID不能为空");
        Contract exist = contractMapper.selectById(contract.getId());
        if (exist == null) throw new BusinessException(404, "合同不存在");
        int rows = contractMapper.updateById(contract);
        if (rows != 1) throw new BusinessException("合同更新失败");
        log.info("合同更新成功, ID: {}", contract.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除合同, ID: {}", id);
        if (id == null) throw new BusinessException(400, "合同ID不能为空");
        Contract exist = contractMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "合同不存在");
        int rows = contractMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("合同删除成功, ID: {}", id);
    }
}

package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.SettlementMapper;
import com.sany.platform.model.entity.Settlement;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.SettlementService;
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
public class SettlementServiceImpl implements SettlementService {

    private static final Logger log = LoggerFactory.getLogger(SettlementServiceImpl.class);

    @Resource
    private SettlementMapper settlementMapper;

    @Override
    public Settlement getById(Long id) {
        if (id == null) throw new BusinessException(400, "结算ID不能为空");
        Settlement settlement = settlementMapper.selectById(id);
        if (settlement == null) throw new BusinessException(404, "结算记录不存在或已被删除");
        return settlement;
    }

    @Override
    public PageResult<Settlement> pageList(Integer serviceType, Integer status, String customerName,
                                           Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        if (pageSize == null || pageSize < 1) pageSize = 15;

        long total = settlementMapper.countList(serviceType, status, customerName);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }

        List<Settlement> all = settlementMapper.selectList(serviceType, status, customerName);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    public Map<String, Object> getStats() {
        LocalDate now = LocalDate.now();
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd = now.withDayOfMonth(now.lengthOfMonth());

        BigDecimal pendingInvoice = settlementMapper.selectPendingInvoice();
        BigDecimal collectedThisMonth = settlementMapper.selectCollectedThisMonth(
                monthStart.toString(), monthEnd.toString());
        BigDecimal quarterTotal = settlementMapper.selectQuarterTotal();
        BigDecimal uncollected = settlementMapper.selectUncollected();

        Map<String, Object> stats = new HashMap<>();
        stats.put("pendingInvoice", pendingInvoice);
        stats.put("collectedThisMonth", collectedThisMonth);
        stats.put("quarterTotal", quarterTotal);
        stats.put("uncollected", uncollected);
        return stats;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Settlement create(Settlement settlement) {
        log.info("创建结算记录, 客户: {}", settlement.getCustomerName());
        settlement.setDeleted(0);
        int rows = settlementMapper.insert(settlement);
        if (rows != 1) throw new BusinessException("结算记录创建失败");
        log.info("结算记录创建成功, ID: {}", settlement.getId());
        return settlement;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Settlement settlement) {
        if (settlement.getId() == null) throw new BusinessException(400, "结算ID不能为空");
        Settlement exist = settlementMapper.selectById(settlement.getId());
        if (exist == null) throw new BusinessException(404, "结算记录不存在");
        int rows = settlementMapper.updateById(settlement);
        if (rows != 1) throw new BusinessException("结算记录更新失败");
        log.info("结算记录更新成功, ID: {}", settlement.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        log.info("更新结算状态, ID: {}, 状态: {}", id, status);
        if (id == null || status == null) throw new BusinessException(400, "参数不完整");
        Settlement exist = settlementMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "结算记录不存在");

        String billingDate = null;
        if (status == 1) {
            billingDate = LocalDate.now().toString();
        }
        settlementMapper.updateStatusWithBillingDate(id, status, billingDate);
        log.info("结算状态更新成功, ID: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除结算记录, ID: {}", id);
        if (id == null) throw new BusinessException(400, "结算ID不能为空");
        Settlement exist = settlementMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "结算记录不存在");
        int rows = settlementMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("结算记录删除成功, ID: {}", id);
    }
}

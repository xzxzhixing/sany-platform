package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.ServiceReportMapper;
import com.sany.platform.model.entity.ServiceReport;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Resource
    private ServiceReportMapper serviceReportMapper;

    @Override
    public ServiceReport getById(Long id) {
        if (id == null) throw new BusinessException(400, "报告ID不能为空");
        ServiceReport entity = serviceReportMapper.selectById(id);
        if (entity == null) throw new BusinessException(404, "报告不存在");
        return entity;
    }

    @Override
    public PageResult<ServiceReport> pageList(String reportNo, String orderNo, Integer reportType, Integer pageNum, Integer pageSize) {
        long total = serviceReportMapper.countList(reportNo, orderNo, reportType);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }
        List<ServiceReport> all = serviceReportMapper.selectList(reportNo, orderNo, reportType);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ServiceReport serviceReport) {
        log.info("创建服务报告");
        serviceReport.setReportNo(generateReportNo());
        int rows = serviceReportMapper.insert(serviceReport);
        if (rows != 1) throw new BusinessException("服务报告创建失败");
        log.info("服务报告创建成功, ID: {}, 编号: {}", serviceReport.getId(), serviceReport.getReportNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除服务报告, ID: {}", id);
        if (id == null) throw new BusinessException(400, "报告ID不能为空");
        ServiceReport exist = serviceReportMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "报告不存在");
        int rows = serviceReportMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("服务报告删除成功, ID: {}", id);
    }

    private String generateReportNo() {
        String prefix = "SR-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String max = serviceReportMapper.selectMaxReportNoByDate(prefix);
        if (max == null) return prefix + "001";
        int seq = Integer.parseInt(max.substring(max.length() - 3)) + 1;
        return prefix + String.format("%03d", seq);
    }
}

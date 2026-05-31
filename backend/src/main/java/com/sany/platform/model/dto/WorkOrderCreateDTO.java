package com.sany.platform.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WorkOrderCreateDTO {

    @NotBlank(message = "设备编号不能为空")
    private String equipmentCode;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    private Integer orderType;

    @NotBlank(message = "报修人姓名不能为空")
    private String reporterName;

    @NotBlank(message = "报修人电话不能为空")
    private String reporterPhone;

    @NotBlank(message = "故障描述不能为空")
    private String faultDescription;

    private Integer faultLevel;
    private String faultCategory;

    public String getEquipmentCode() { return equipmentCode; }
    public void setEquipmentCode(String equipmentCode) { this.equipmentCode = equipmentCode; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Integer getOrderType() { return orderType; }
    public void setOrderType(Integer orderType) { this.orderType = orderType; }
    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }
    public String getReporterPhone() { return reporterPhone; }
    public void setReporterPhone(String reporterPhone) { this.reporterPhone = reporterPhone; }
    public String getFaultDescription() { return faultDescription; }
    public void setFaultDescription(String faultDescription) { this.faultDescription = faultDescription; }
    public Integer getFaultLevel() { return faultLevel; }
    public void setFaultLevel(Integer faultLevel) { this.faultLevel = faultLevel; }
    public String getFaultCategory() { return faultCategory; }
    public void setFaultCategory(String faultCategory) { this.faultCategory = faultCategory; }
}

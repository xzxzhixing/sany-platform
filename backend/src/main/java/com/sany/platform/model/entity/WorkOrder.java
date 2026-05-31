package com.sany.platform.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WorkOrder {
    private Long id;
    private String orderNo;
    private Integer orderType;
    private Integer orderSource;
    private Long customerId;
    private Long equipmentId;
    private String reporterName;
    private String reporterPhone;
    private String faultDescription;
    private Integer faultLevel;
    private String faultCategory;
    private Long assigneeId;
    private LocalDateTime assignTime;
    private LocalDateTime acceptTime;
    private LocalDateTime arriveTime;
    private LocalDateTime finishTime;
    private LocalDateTime confirmTime;
    private Integer orderStatus;
    private String handlingDetail;
    private Integer handlingResult;
    private BigDecimal chargeAmount;
    private Integer isChargeable;
    private String customerSign;
    private Integer rating;
    private String ratingRemark;
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public Integer getOrderType() { return orderType; }
    public void setOrderType(Integer orderType) { this.orderType = orderType; }
    public Integer getOrderSource() { return orderSource; }
    public void setOrderSource(Integer orderSource) { this.orderSource = orderSource; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getEquipmentId() { return equipmentId; }
    public void setEquipmentId(Long equipmentId) { this.equipmentId = equipmentId; }
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
    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
    public LocalDateTime getAssignTime() { return assignTime; }
    public void setAssignTime(LocalDateTime assignTime) { this.assignTime = assignTime; }
    public LocalDateTime getAcceptTime() { return acceptTime; }
    public void setAcceptTime(LocalDateTime acceptTime) { this.acceptTime = acceptTime; }
    public LocalDateTime getArriveTime() { return arriveTime; }
    public void setArriveTime(LocalDateTime arriveTime) { this.arriveTime = arriveTime; }
    public LocalDateTime getFinishTime() { return finishTime; }
    public void setFinishTime(LocalDateTime finishTime) { this.finishTime = finishTime; }
    public LocalDateTime getConfirmTime() { return confirmTime; }
    public void setConfirmTime(LocalDateTime confirmTime) { this.confirmTime = confirmTime; }
    public Integer getOrderStatus() { return orderStatus; }
    public void setOrderStatus(Integer orderStatus) { this.orderStatus = orderStatus; }
    public String getHandlingDetail() { return handlingDetail; }
    public void setHandlingDetail(String handlingDetail) { this.handlingDetail = handlingDetail; }
    public Integer getHandlingResult() { return handlingResult; }
    public void setHandlingResult(Integer handlingResult) { this.handlingResult = handlingResult; }
    public BigDecimal getChargeAmount() { return chargeAmount; }
    public void setChargeAmount(BigDecimal chargeAmount) { this.chargeAmount = chargeAmount; }
    public Integer getIsChargeable() { return isChargeable; }
    public void setIsChargeable(Integer isChargeable) { this.isChargeable = isChargeable; }
    public String getCustomerSign() { return customerSign; }
    public void setCustomerSign(String customerSign) { this.customerSign = customerSign; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getRatingRemark() { return ratingRemark; }
    public void setRatingRemark(String ratingRemark) { this.ratingRemark = ratingRemark; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}

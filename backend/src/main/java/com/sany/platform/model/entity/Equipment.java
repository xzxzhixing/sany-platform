package com.sany.platform.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Equipment {
    private Long id;
    private String equipmentCode;
    private String equipmentName;
    private String equipmentModel;
    private String serialNumber;
    private String category;
    private Long customerId;
    private String installAddress;
    private LocalDate purchaseDate;
    private LocalDate warrantyStart;
    private LocalDate warrantyEnd;
    private Integer maintenanceCycle;
    private Integer maintenanceHour;
    private Integer status;
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEquipmentCode() { return equipmentCode; }
    public void setEquipmentCode(String equipmentCode) { this.equipmentCode = equipmentCode; }
    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public String getEquipmentModel() { return equipmentModel; }
    public void setEquipmentModel(String equipmentModel) { this.equipmentModel = equipmentModel; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getInstallAddress() { return installAddress; }
    public void setInstallAddress(String installAddress) { this.installAddress = installAddress; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public LocalDate getWarrantyStart() { return warrantyStart; }
    public void setWarrantyStart(LocalDate warrantyStart) { this.warrantyStart = warrantyStart; }
    public LocalDate getWarrantyEnd() { return warrantyEnd; }
    public void setWarrantyEnd(LocalDate warrantyEnd) { this.warrantyEnd = warrantyEnd; }
    public Integer getMaintenanceCycle() { return maintenanceCycle; }
    public void setMaintenanceCycle(Integer maintenanceCycle) { this.maintenanceCycle = maintenanceCycle; }
    public Integer getMaintenanceHour() { return maintenanceHour; }
    public void setMaintenanceHour(Integer maintenanceHour) { this.maintenanceHour = maintenanceHour; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}

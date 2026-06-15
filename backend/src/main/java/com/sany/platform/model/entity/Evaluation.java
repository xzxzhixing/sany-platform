package com.sany.platform.model.entity;

import java.time.LocalDateTime;

public class Evaluation {
    private Long id;
    private Long orderId;
    private String orderNo;
    private String customerName;
    private String equipmentName;
    private Integer rating;
    private String ratingRemark;
    private String evaluator;
    private LocalDateTime evaluateTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getRatingRemark() { return ratingRemark; }
    public void setRatingRemark(String ratingRemark) { this.ratingRemark = ratingRemark; }
    public String getEvaluator() { return evaluator; }
    public void setEvaluator(String evaluator) { this.evaluator = evaluator; }
    public LocalDateTime getEvaluateTime() { return evaluateTime; }
    public void setEvaluateTime(LocalDateTime evaluateTime) { this.evaluateTime = evaluateTime; }
}

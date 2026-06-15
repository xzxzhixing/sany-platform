package com.sany.platform.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrainingRecord {

    private Long id;
    private String recordNo;
    private String courseName;
    private Integer courseType;
    private String trainer;
    private Integer traineeCount;
    private String traineeNames;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal duration;
    private Integer status;
    private BigDecimal examPassRate;
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRecordNo() { return recordNo; }
    public void setRecordNo(String recordNo) { this.recordNo = recordNo; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public Integer getCourseType() { return courseType; }
    public void setCourseType(Integer courseType) { this.courseType = courseType; }
    public String getTrainer() { return trainer; }
    public void setTrainer(String trainer) { this.trainer = trainer; }
    public Integer getTraineeCount() { return traineeCount; }
    public void setTraineeCount(Integer traineeCount) { this.traineeCount = traineeCount; }
    public String getTraineeNames() { return traineeNames; }
    public void setTraineeNames(String traineeNames) { this.traineeNames = traineeNames; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public BigDecimal getDuration() { return duration; }
    public void setDuration(BigDecimal duration) { this.duration = duration; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public BigDecimal getExamPassRate() { return examPassRate; }
    public void setExamPassRate(BigDecimal examPassRate) { this.examPassRate = examPassRate; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}

package com.sany.platform.common.enums;

public enum WorkOrderStatusEnum {

    PENDING_DISPATCH(0, "待派单"),
    PENDING_ACCEPT(1, "待接单"),
    IN_PROGRESS(2, "处理中"),
    PENDING_CONFIRM(3, "待验收"),
    COMPLETED(4, "已完成"),
    CLOSED(5, "已关闭"),
    CANCELLED(6, "已取消");

    private final int code;
    private final String description;

    WorkOrderStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() { return code; }
    public String getDescription() { return description; }

    public static WorkOrderStatusEnum of(int code) {
        for (WorkOrderStatusEnum s : values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("无效的状态码: " + code);
    }
}

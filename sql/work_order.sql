-- 三一工业智能服务平台 - 建表脚本（MySQL 8.0）
-- 使用前先创建数据库:
-- CREATE DATABASE IF NOT EXISTS sany_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tbl_sys_user (
    id              BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    username        VARCHAR(64)     NOT NULL                 COMMENT '登录账号',
    password        VARCHAR(256)    NOT NULL                 COMMENT '加密密码',
    real_name       VARCHAR(64)                              COMMENT '真实姓名',
    phone           VARCHAR(20)                              COMMENT '手机号',
    email           VARCHAR(128)                             COMMENT '邮箱',
    user_type       TINYINT         NOT NULL DEFAULT 0       COMMENT '0-管理员 1-运维人员 2-客户',
    status          TINYINT         NOT NULL DEFAULT 1       COMMENT '0-禁用 1-启用',
    customer_id     BIGINT                                   COMMENT '所属客户ID',
    deleted         TINYINT         NOT NULL DEFAULT 0,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

CREATE TABLE IF NOT EXISTS tbl_customer (
    id              BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    customer_name   VARCHAR(128)    NOT NULL                 COMMENT '客户名称',
    contact_person  VARCHAR(64)                              COMMENT '联系人',
    contact_phone   VARCHAR(20)                              COMMENT '联系电话',
    province        VARCHAR(64)                              COMMENT '省份',
    city            VARCHAR(64)                              COMMENT '城市',
    address         VARCHAR(256)                             COMMENT '详细地址',
    service_level   TINYINT         DEFAULT 3                COMMENT '1-VIP 2-重要 3-普通',
    status          TINYINT         NOT NULL DEFAULT 1,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_customer_name (customer_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息表';

CREATE TABLE IF NOT EXISTS tbl_equipment (
    id              BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    equipment_code  VARCHAR(64)     NOT NULL                 COMMENT '设备编号',
    equipment_name  VARCHAR(128)    NOT NULL                 COMMENT '设备名称',
    equipment_model VARCHAR(128)                             COMMENT '设备型号',
    serial_number   VARCHAR(128)                             COMMENT '出厂序列号',
    category        VARCHAR(64)                              COMMENT '设备分类',
    customer_id     BIGINT          NOT NULL                 COMMENT '所属客户ID',
    install_address VARCHAR(256)                             COMMENT '安装地址',
    purchase_date   DATE                                     COMMENT '购买日期',
    warranty_start  DATE                                     COMMENT '保修开始',
    warranty_end    DATE                                     COMMENT '保修截止',
    status          TINYINT        NOT NULL DEFAULT 1        COMMENT '0-停用 1-运行中 2-维修中 3-报废',
    deleted         TINYINT        NOT NULL DEFAULT 0,
    create_time     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_equipment_code (equipment_code),
    KEY idx_customer_id (customer_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备信息表';

CREATE TABLE IF NOT EXISTS tbl_work_order (
    id                  BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    order_no            VARCHAR(64)     NOT NULL                 COMMENT '工单编号 WO+yyyyMMdd+0001',
    order_type          TINYINT         NOT NULL DEFAULT 1       COMMENT '1-报修 2-保养 3-安装调试 4-巡检',
    order_source        TINYINT         NOT NULL DEFAULT 1       COMMENT '1-客户报修 2-系统生成 3-管理员创建',
    customer_id         BIGINT          NOT NULL                 COMMENT '客户ID',
    equipment_id        BIGINT          NOT NULL                 COMMENT '设备ID',
    reporter_name       VARCHAR(64)                              COMMENT '报修人',
    reporter_phone      VARCHAR(20)                              COMMENT '报修人电话',
    fault_description   TEXT                                     COMMENT '故障描述',
    fault_level         TINYINT         DEFAULT 3                COMMENT '1-紧急 2-高 3-中 4-低',
    fault_category      VARCHAR(64)                              COMMENT '故障分类',
    assignee_id         BIGINT                                   COMMENT '运维人员ID',
    assign_time         DATETIME                                 COMMENT '派单时间',
    accept_time         DATETIME                                 COMMENT '接单时间',
    arrive_time         DATETIME                                 COMMENT '到达时间',
    finish_time         DATETIME                                 COMMENT '完工时间',
    confirm_time        DATETIME                                 COMMENT '客户确认时间',
    order_status        TINYINT         NOT NULL DEFAULT 0       COMMENT '0-待派单 1-待接单 2-处理中 3-待验收 4-已完成 5-已关闭 6-已取消',
    handling_detail     TEXT                                     COMMENT '处理详情',
    handling_result     TINYINT         DEFAULT 0                COMMENT '0-未处理 1-已修复 2-无法修复 3-需更换设备',
    charge_amount       DECIMAL(12,2)   DEFAULT 0.00             COMMENT '收费金额',
    is_chargeable       TINYINT         DEFAULT 0                COMMENT '0-免费 1-收费',
    customer_sign       VARCHAR(512)                             COMMENT '签名图片URL',
    rating              TINYINT                                  COMMENT '1-5星',
    rating_remark       VARCHAR(512)                             COMMENT '评价备注',
    deleted             TINYINT         NOT NULL DEFAULT 0,
    create_time         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_customer_id (customer_id),
    KEY idx_equipment_id (equipment_id),
    KEY idx_assignee_id (assignee_id),
    KEY idx_order_status (order_status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

CREATE TABLE IF NOT EXISTS tbl_service_record (
    id              BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    order_id        BIGINT          NOT NULL                 COMMENT '工单ID',
    technician_id   BIGINT          NOT NULL                 COMMENT '服务人员ID',
    service_type    TINYINT         NOT NULL DEFAULT 1       COMMENT '1-维修 2-保养 3-巡检 4-培训',
    fault_phenomenon TEXT                                    COMMENT '故障现象',
    fault_cause     TEXT                                     COMMENT '故障原因',
    solution        TEXT                                     COMMENT '解决方案',
    parts_replaced  VARCHAR(512)                             COMMENT '更换备件',
    labor_hours     DECIMAL(5,1)    DEFAULT 0.0              COMMENT '工时',
    service_cost    DECIMAL(12,2)   DEFAULT 0.00             COMMENT '服务费用',
    service_status  TINYINT         DEFAULT 0                COMMENT '0-待审核 1-已审核 2-已驳回',
    deleted         TINYINT         NOT NULL DEFAULT 0,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_order_id (order_id),
    KEY idx_technician_id (technician_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务记录表';

-- 测试数据
INSERT INTO tbl_sys_user (username, password, real_name, phone, user_type, status) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '系统管理员', '13800000000', 0, 1),
('engineer01', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '张工', '13800138001', 1, 1),
('engineer02', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '李工', '13800138002', 1, 1);

INSERT INTO tbl_customer (customer_name, contact_person, contact_phone, city, address) VALUES
('三一重工长沙工厂', '王经理', '13900139001', '长沙市', '长沙经济开发区'),
('三一重工昆山工厂', '赵经理', '13900139002', '昆山市', '昆山开发区');

INSERT INTO tbl_equipment (equipment_code, equipment_name, equipment_model, serial_number, category, customer_id, status) VALUES
('EQ-2024-0001', 'SY485H挖掘机', 'SY485H-10', 'SN-2024-001', '挖掘机', 1, 1),
('EQ-2024-0002', 'SYM5350THB泵车', 'SYM5350THB-10', 'SN-2024-002', '泵车', 2, 1);

INSERT INTO tbl_work_order (order_no, order_type, customer_id, equipment_id, reporter_name, reporter_phone, fault_description, fault_level, fault_category, assignee_id, order_status, create_time) VALUES
('WO202605250001', 1, 1, 1, '王经理', '13900139001', 'SY485H挖掘机液压系统异响，无法正常作业，请求尽快维修', 1, '液压故障', 2, 0, '2026-05-25 08:30:00'),
('WO202605250002', 1, 2, 2, '赵经理', '13900139002', '泵车臂架无法展开，控制面板报E032故障码，疑似电气系统故障', 2, '电气故障', 1, 2, '2026-05-25 09:00:00'),
('WO202605250003', 2, 1, 1, '王经理', '13900139001', 'SY485H挖掘机500小时保养到期，需更换机油滤芯及液压油', 3, '保养', 1, 4, '2026-05-24 10:00:00'),
('WO202605250004', 1, 1, 1, '刘工', '13800138005', '发动机故障灯常亮，动力明显不足，排气管冒黑烟', 2, '发动机故障', 2, 1, '2026-05-24 14:00:00'),
('WO202605250005', 4, 2, 2, '赵经理', '13900139002', '季度例行巡检，检查泵车各系统运行状态', 3, '巡检', NULL, 5, '2026-05-23 09:00:00'),
('WO202605250006', 1, 1, 1, '孙工', '13800138006', '行走履带异响严重，右侧履带跑偏，需现场检查', 3, '行走机构故障', NULL, 3, '2026-05-22 15:30:00');

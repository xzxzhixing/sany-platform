/*
 Navicat Premium Dump SQL

 Source Server         : githubTest
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : sany_platform

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 14/06/2026 12:45:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_contract
-- ----------------------------
DROP TABLE IF EXISTS `tbl_contract`;
CREATE TABLE `tbl_contract`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contract_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `contract_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contract_type` tinyint NOT NULL DEFAULT 1,
  `start_date` date NULL DEFAULT NULL,
  `end_date` date NULL DEFAULT NULL,
  `contract_amount` decimal(14, 2) NULL DEFAULT 0.00,
  `status` tinyint NOT NULL DEFAULT 0,
  `sign_date` date NULL DEFAULT NULL,
  `responsible_person` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_contract_no`(`contract_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '合同表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_contract
-- ----------------------------
INSERT INTO `tbl_contract` VALUES (1, 'CT-2026001', '湖南中建机械年度维保合同', '湖南中建机械', 1, '2026-01-01', '2026-12-31', 580000.00, 1, '2025-12-15', '赵刚强', '覆盖全部设备', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_contract` VALUES (2, 'CT-2026002', '湖北路桥集团设备维修合同', '湖北路桥集团', 2, '2026-03-01', '2026-08-31', 320000.00, 1, '2026-02-28', '王建国', '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_contract` VALUES (3, 'CT-2026003', '江西赣南矿业设备租赁合同', '江西赣南矿业', 3, '2026-04-01', '2026-09-30', 450000.00, 1, '2026-03-25', '赵刚强', '含操作手', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_contract` VALUES (4, 'CT-2026004', '广东交通建设技术服务合同', '广东交通建设', 4, '2026-02-01', '2026-07-31', 280000.00, 1, '2026-01-20', '李明辉', '技术支持+培训', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_contract` VALUES (5, 'CT-2026005', '四川能源开发设备维保合同', '四川能源开发', 1, '2025-07-01', '2026-06-30', 360000.00, 2, '2025-06-15', '赵刚强', '待续签', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_contract` VALUES (6, 'CT-2026006', '河南振兴路桥新机安装合同', '河南振兴路桥', 2, '2026-05-01', '2026-06-15', 185000.00, 0, NULL, '王建国', '新签合同待审批', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_contract` VALUES (7, 'CT-2026007', '陕西建工集团年度技术服务', '陕西建工集团', 4, '2026-04-15', '2027-04-14', 420000.00, 1, '2026-04-10', '周志远', '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_contract` VALUES (8, 'CT-2026008', '云南水利工程设备租赁合同', '云南水利工程', 3, '2026-05-15', '2026-08-15', 220000.00, 0, NULL, '赵刚强', '合同条款确认中', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_customer
-- ----------------------------
DROP TABLE IF EXISTS `tbl_customer`;
CREATE TABLE `tbl_customer`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户名称',
  `contact_person` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `district` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区县',
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `industry` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属行业',
  `contract_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '合同编号',
  `service_level` tinyint NULL DEFAULT 3 COMMENT '服务等级 1-VIP 2-重要 3-普通',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_customer_name`(`customer_name` ASC) USING BTREE,
  INDEX `idx_contact_phone`(`contact_phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_customer
-- ----------------------------
INSERT INTO `tbl_customer` VALUES (1, '三一重工长沙工厂', '王经理', '13900139001', NULL, '长沙市', NULL, '湖南省长沙市经济开发区', NULL, NULL, 1, 1, 0, '2026-05-25 20:03:56', '2026-05-25 20:03:56');
INSERT INTO `tbl_customer` VALUES (2, '三一重工昆山工厂', '赵经理', '13900139002', NULL, '昆山市', NULL, '江苏省昆山市开发区', NULL, NULL, 2, 1, 0, '2026-05-25 20:03:56', '2026-05-25 20:03:56');

-- ----------------------------
-- Table structure for tbl_employee
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `employee_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `employee_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` tinyint NULL DEFAULT 1,
  `position` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `department` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT 1,
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_employee_no`(`employee_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_employee
-- ----------------------------
INSERT INTO `tbl_employee` VALUES (1, 'EMP001', '王建国', 1, '维修工程师', '18812340001', 'wangjg@sany.com.cn', '售后服务部', 1, '高级工程师，擅长液压系统维修', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (2, 'EMP002', '李明辉', 1, '维修工程师', '18812340002', 'limh@sany.com.cn', '售后服务部', 1, '中级工程师，擅长电气系统', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (3, 'EMP003', '刘晓燕', 0, '客服专员', '18812340003', 'liuxy@sany.com.cn', '客服部', 1, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (4, 'EMP004', '赵刚强', 1, '服务经理', '18812340004', 'zhaogq@sany.com.cn', '售后服务部', 1, '负责华中区域', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (5, 'EMP005', '孙丽华', 0, '技术支持', '18812340005', 'sunlh@sany.com.cn', '技术部', 2, '休假中', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (6, 'EMP006', '周志远', 1, '维修工程师', '18812340006', 'zhouzy@sany.com.cn', '售后服务部', 1, '中级工程师', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (7, 'EMP007', '吴敏', 0, '客服专员', '18812340007', 'wumin@sany.com.cn', '客服部', 1, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (8, 'EMP008', '郑海涛', 1, '管理员', '18812340008', 'zhenght@sany.com.cn', '综合管理部', 1, '系统管理员', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (9, 'EMP009', '陈思远', 1, '维修工程师', '18812340009', 'chensy@sany.com.cn', '售后服务部', 1, '初级工程师', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_employee` VALUES (10, 'EMP010', '林小红', 0, '客服专员', '18812340010', 'linxh@sany.com.cn', '客服部', 1, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_equipment
-- ----------------------------
DROP TABLE IF EXISTS `tbl_equipment`;
CREATE TABLE `tbl_equipment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `equipment_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备编号',
  `equipment_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备名称',
  `equipment_model` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备型号',
  `serial_number` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出厂序列号',
  `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备分类',
  `customer_id` bigint NOT NULL COMMENT '所属客户ID',
  `install_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '安装地址',
  `purchase_date` date NULL DEFAULT NULL COMMENT '购买日期',
  `warranty_start` date NULL DEFAULT NULL COMMENT '保修开始日期',
  `warranty_end` date NULL DEFAULT NULL COMMENT '保修截止日期',
  `maintenance_cycle` int NULL DEFAULT 0 COMMENT '保养周期（天）',
  `maintenance_hour` int NULL DEFAULT 0 COMMENT '保养周期（工作小时）',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-运行中 2-维修中 3-报废',
  `latitude` decimal(10, 6) NULL DEFAULT NULL COMMENT 'GPS纬度',
  `longitude` decimal(10, 6) NULL DEFAULT NULL COMMENT 'GPS经度',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_equipment_code`(`equipment_code` ASC) USING BTREE,
  INDEX `idx_customer_id`(`customer_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_serial_number`(`serial_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '设备信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_equipment
-- ----------------------------
INSERT INTO `tbl_equipment` VALUES (1, 'EQ-2024-0001', 'SY485H挖掘机', 'SY485H-10', 'SN-2024-001', '挖掘机', 1, NULL, NULL, NULL, NULL, 0, 0, 1, NULL, NULL, 0, '2026-05-25 20:03:56', '2026-05-25 20:03:56');
INSERT INTO `tbl_equipment` VALUES (2, 'EQ-2024-0002', 'SYM5350THB泵车', 'SYM5350THB-10', 'SN-2024-002', '泵车', 2, NULL, NULL, NULL, NULL, 0, 0, 1, NULL, NULL, 0, '2026-05-25 20:03:56', '2026-05-25 20:03:56');

-- ----------------------------
-- Table structure for tbl_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_evaluation`;
CREATE TABLE `tbl_evaluation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `equipment_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `rating` tinyint NULL DEFAULT NULL,
  `rating_remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `evaluator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `evaluate_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_evaluation
-- ----------------------------
INSERT INTO `tbl_evaluation` VALUES (1, 1, 'WO20260520001', '湖南中建机械', 'SY485H 液压挖掘机', 5, '维修非常及时，技术专业，非常满意', '张伟', '2026-05-22 10:30:00');
INSERT INTO `tbl_evaluation` VALUES (2, 5, 'WO20260524005', '湖南中建机械', 'SY215 液压挖掘机', 5, '巡检认真负责，发现潜在问题并给出建议', '张伟', '2026-05-24 14:00:00');
INSERT INTO `tbl_evaluation` VALUES (3, 7, 'WO20260525007', '广东交通建设', 'SYM5440 混凝土泵车', 4, '维修质量好，但响应速度可以更快一些', '陈明', '2026-05-26 11:20:00');

-- ----------------------------
-- Table structure for tbl_inspection_task
-- ----------------------------
DROP TABLE IF EXISTS `tbl_inspection_task`;
CREATE TABLE `tbl_inspection_task`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `equipment_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `equipment_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `task_type` tinyint NOT NULL DEFAULT 1,
  `assignee` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `plan_date` date NULL DEFAULT NULL,
  `actual_date` date NULL DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT 0,
  `result` tinyint NULL DEFAULT NULL,
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_task_no`(`task_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '巡检任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_inspection_task
-- ----------------------------
INSERT INTO `tbl_inspection_task` VALUES (1, 'IT-202605001', 'SY485H 液压挖掘机', 'SY485H-2024001', '湖南中建机械', 1, '王建国', '2026-05-26', '2026-05-26', 2, 1, '设备运行正常，各项参数达标', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (2, 'IT-202605002', 'STC250 汽车起重机', 'STC250-2024003', '江西赣南矿业', 1, '李明辉', '2026-05-26', NULL, 1, NULL, '正在巡检中', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (3, 'IT-202605003', 'SYM5230 混凝土泵车', 'SYM5230-2024004', '广东交通建设', 2, '周志远', '2026-05-27', NULL, 0, NULL, '液压系统专项检查', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (4, 'IT-202605004', 'SR360 旋挖钻机', 'SR360-2024005', '四川能源开发', 3, '赵刚强', '2026-05-25', '2026-05-26', 2, 0, '发现主泵压力异常，已开具维修工单', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (5, 'IT-202605005', 'SY215 液压挖掘机', 'SY215-2024007', '湖南中建机械', 1, '王建国', '2026-05-25', '2026-05-25', 2, 1, '季度巡检完成', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (6, 'IT-202605006', 'SCC2000 履带起重机', 'SCC2000-2024006', '河南振兴路桥', 2, '陈思远', '2026-05-28', NULL, 0, NULL, '安装调试后专项检查', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (7, 'IT-202605007', 'SYM5440 混凝土泵车', 'SYM5440-2024008', '广东交通建设', 4, '李明辉', '2026-05-24', NULL, 3, NULL, '维修后质量复查', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (8, 'IT-202605008', 'SY485H 液压挖掘机', 'SY485H-2024002', '湖北路桥集团', 3, '周志远', '2026-05-29', NULL, 0, NULL, '季度安全排查', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (9, 'IT-202605009', 'STC800 汽车起重机', 'STC800-2024009', '陕西建工集团', 1, '陈思远', '2026-05-26', '2026-05-26', 1, NULL, '日常运行巡检', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_inspection_task` VALUES (10, 'IT-202605010', 'SY485H 液压挖掘机', 'SY485H-2024001', '湖南中建机械', 4, '王建国', '2026-05-22', '2026-05-22', 2, 1, '维修完工质量检查', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_knowledge_article
-- ----------------------------
DROP TABLE IF EXISTS `tbl_knowledge_article`;
CREATE TABLE `tbl_knowledge_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `summary` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tags` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `publish_date` date NULL DEFAULT NULL,
  `view_count` int NULL DEFAULT 0,
  `status` tinyint NOT NULL DEFAULT 1,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_article_no`(`article_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '知识库文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_knowledge_article
-- ----------------------------
INSERT INTO `tbl_knowledge_article` VALUES (1, 'KB-001', 'SY485H液压挖掘机发动机异响故障诊断与排除', '维修指南', '详细讲解SY485H液压挖掘机发动机异响的常见原因、诊断步骤和维修方法', 'SY485H,发动机,异响', '王建国', '2026-05-20', 156, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (2, 'KB-002', 'STC250汽车起重机操作安全规范', '安全规范', 'STC250汽车起重机操作过程中的安全注意事项和规范操作流程', 'STC250,安全,操作', '赵刚强', '2026-05-18', 289, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (3, 'KB-003', '混凝土泵车液压系统常见故障及处理方法', '故障案例', '收集整理SYM系列混凝土泵车液压系统典型故障案例及处理方案', '泵车,液压,故障', '周志远', '2026-05-15', 432, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (4, 'KB-004', 'SR360旋挖钻机操作手册', '操作手册', 'SR360旋挖钻机完整操作流程、参数设置和维护保养指南', 'SR360,操作,手册', '陈思远', '2026-05-12', 98, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (5, 'KB-005', '液压系统维修基础培训教材', '培训资料', '液压系统原理、常见故障分析及维修实操培训教材', '液压,培训,维修', '王建国', '2026-05-10', 367, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (6, 'KB-006', 'SCC2000履带起重机年度保养规范', '维修指南', 'SCC2000履带起重机年度保养项目、标准和周期说明', 'SCC2000,保养,规范', '李明辉', '2026-05-08', 124, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (7, 'KB-007', '电气系统故障快速诊断指南', '培训资料', '工程机械电气系统常见故障的快速诊断方法和工具使用指南', '电气,诊断,培训', '李明辉', '2026-05-05', 203, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (8, 'KB-008', '施工现场安全巡查标准', '安全规范', '施工现场安全巡查的项目、频次和记录标准', '安全,巡查,标准', '赵刚强', '2026-05-03', 178, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (9, 'KB-009', 'SYM5440泵车臂架系统故障案例汇编', '故障案例', 'SYM5440混凝土泵车臂架系统典型故障案例及维修经验总结', 'SYM5440,臂架,故障', '周志远', '2026-04-28', 87, 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_knowledge_article` VALUES (10, 'KB-010', '履带起重机拆装运输操作规程', '操作手册', 'SCC系列履带起重机的拆卸、运输和组装操作规程', '起重机,拆装,运输', '陈思远', '2026-04-25', 145, 0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_maintenance_plan
-- ----------------------------
DROP TABLE IF EXISTS `tbl_maintenance_plan`;
CREATE TABLE `tbl_maintenance_plan`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `equipment_id` bigint NULL DEFAULT NULL,
  `equipment_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `equipment_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `plan_type` tinyint NOT NULL DEFAULT 1,
  `plan_date` date NULL DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT 0,
  `assignee_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_plan_no`(`plan_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '保养计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_maintenance_plan
-- ----------------------------
INSERT INTO `tbl_maintenance_plan` VALUES (1, 'MP-202605001', 1, 'SY485H 液压挖掘机', 'SY485H-2024001', '湖南中建机械', 1, '2026-06-01', 0, '王建国', '更换机油、滤芯', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_maintenance_plan` VALUES (2, 'MP-202605002', 3, 'STC250 汽车起重机', 'STC250-2024003', '江西赣南矿业', 2, '2026-06-15', 0, '李明辉', '年度安全检查', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_maintenance_plan` VALUES (3, 'MP-202605003', 5, 'SR360 旋挖钻机', 'SR360-2024005', '四川能源开发', 1, '2026-05-30', 1, '周志远', '更换液压油、滤芯', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_maintenance_plan` VALUES (4, 'MP-202605004', 7, 'SY215 液压挖掘机', 'SY215-2024007', '湖南中建机械', 3, '2026-05-28', 1, '王建国', '季度巡检', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_maintenance_plan` VALUES (5, 'MP-202605005', 2, 'SY485H 液压挖掘机', 'SY485H-2024002', '湖北路桥集团', 1, '2026-06-10', 0, '', '更换三滤、机油', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_maintenance_plan` VALUES (6, 'MP-202605006', 8, 'SYM5440 混凝土泵车', 'SYM5440-2024008', '广东交通建设', 2, '2026-07-01', 0, '', '年度全面检查', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_maintenance_plan` VALUES (7, 'MP-202605007', 4, 'SYM5230 混凝土泵车', 'SYM5230-2024004', '广东交通建设', 1, '2026-05-25', 2, '陈思远', '更换机油、液压油', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_notification
-- ----------------------------
DROP TABLE IF EXISTS `tbl_notification`;
CREATE TABLE `tbl_notification`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '通知内容',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '1-警告 2-消息 3-成功 4-订单',
  `is_read` tinyint NOT NULL DEFAULT 0 COMMENT '0-未读 1-已读',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_is_read`(`is_read` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_notification
-- ----------------------------
INSERT INTO `tbl_notification` VALUES (1, '紧急工单待处理', '工单 WO20260526008（SY485H液压系统漏油）已被标记为紧急，请尽快派单处理。', 1, 0, 0, '2026-05-26 07:50:00', '2026-06-14 12:14:01');
INSERT INTO `tbl_notification` VALUES (2, '工单已完成待验收', '工单 WO20260525007（SYM5440泵送系统维修）已完工，请客户及时验收。', 2, 0, 0, '2026-05-26 10:05:00', '2026-06-14 12:14:01');
INSERT INTO `tbl_notification` VALUES (3, '备件库存预警', '备件 \"发动机机油(18L)\" 库存仅剩2桶，低于最低库存线(10桶)，请及时采购。', 1, 0, 0, '2026-05-26 08:30:00', '2026-06-14 12:14:01');
INSERT INTO `tbl_notification` VALUES (4, '保养到期提醒', '设备 SY485H-2024001（湖南中建机械）保养周期已到，请安排保养服务。', 3, 0, 0, '2026-05-25 09:00:00', '2026-06-14 12:14:01');
INSERT INTO `tbl_notification` VALUES (5, '新工单已创建', '工单 WO20260525006（SCC2000履带起重机安装调试）已创建并派单给孙工。', 2, 1, 0, '2026-05-25 08:00:00', '2026-06-14 12:14:01');
INSERT INTO `tbl_notification` VALUES (6, '备件采购到货通知', '备件 \"液压油过滤芯\" 已到货入库，数量50个。', 3, 1, 0, '2026-05-24 14:00:00', '2026-06-14 12:14:01');
INSERT INTO `tbl_notification` VALUES (7, '系统升级通知', '系统将于2026年5月28日凌晨2:00-4:00进行版本升级，届时服务将暂时中断。', 4, 1, 0, '2026-05-24 10:00:00', '2026-06-14 12:14:01');

-- ----------------------------
-- Table structure for tbl_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `tbl_operation_log`;
CREATE TABLE `tbl_operation_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `operator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `target` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `detail` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_operator`(`operator` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_operation_log
-- ----------------------------
INSERT INTO `tbl_operation_log` VALUES (1, '管理员', '登录系统', '', '管理员登录系统', '192.168.1.100', '2026-05-26 08:30:00');
INSERT INTO `tbl_operation_log` VALUES (2, '管理员', '创建工单', 'WO20260526008', '创建紧急维修工单', '192.168.1.100', '2026-05-26 08:45:00');
INSERT INTO `tbl_operation_log` VALUES (3, '管理员', '派单', 'WO20260526008', '派单给王工', '192.168.1.100', '2026-05-26 09:00:00');
INSERT INTO `tbl_operation_log` VALUES (4, '管理员', '新增备件', 'SP-011', '新增斗齿备件', '192.168.1.100', '2026-05-26 09:30:00');
INSERT INTO `tbl_operation_log` VALUES (5, '管理员', '编辑员工', 'EMP001', '更新员工信息', '192.168.1.100', '2026-05-26 10:00:00');
INSERT INTO `tbl_operation_log` VALUES (6, '王建国', '接单', 'WO20260524004', '维修工程师接单', '192.168.1.101', '2026-05-25 14:20:00');
INSERT INTO `tbl_operation_log` VALUES (7, '王建国', '完成工单', 'WO20260520001', '维修完成，待验收', '192.168.1.101', '2026-05-25 16:00:00');
INSERT INTO `tbl_operation_log` VALUES (8, '管理员', '创建保养计划', 'MP-202605001', '创建定期保养计划', '192.168.1.100', '2026-05-24 11:00:00');
INSERT INTO `tbl_operation_log` VALUES (9, '管理员', '客户评价', 'WO20260520001', '客户提交5星评价', '192.168.1.100', '2026-05-24 09:15:00');
INSERT INTO `tbl_operation_log` VALUES (10, '管理员', '删除工单', 'WO20260519009', '删除已取消工单', '192.168.1.100', '2026-05-23 17:30:00');
INSERT INTO `tbl_operation_log` VALUES (11, '管理员', '修改系统设置', '', '修改通知开关设置', '192.168.1.100', '2026-05-23 15:00:00');
INSERT INTO `tbl_operation_log` VALUES (12, '李明辉', '接单', 'WO20260522002', '维修工程师接单', '192.168.1.102', '2026-05-22 14:30:00');

-- ----------------------------
-- Table structure for tbl_quality_record
-- ----------------------------
DROP TABLE IF EXISTS `tbl_quality_record`;
CREATE TABLE `tbl_quality_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `record_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `equipment_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `equipment_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `inspector` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `inspection_date` date NULL DEFAULT NULL,
  `inspection_type` tinyint NOT NULL DEFAULT 1,
  `result` tinyint NULL DEFAULT 0,
  `defect_count` int NULL DEFAULT 0,
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_record_no`(`record_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '质量记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_quality_record
-- ----------------------------
INSERT INTO `tbl_quality_record` VALUES (1, 'QC-202605001', 'SY485H 液压挖掘机', 'SY485H-2024001', '湖南中建机械', '王建国', '2026-05-26', 3, 1, 0, '维修后检验合格', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_quality_record` VALUES (2, 'QC-202605002', 'STC250 汽车起重机', 'STC250-2024003', '江西赣南矿业', '李明辉', '2026-05-25', 3, 2, 2, '伸缩油缸有轻微渗油，需复检', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_quality_record` VALUES (3, 'QC-202605003', 'SYM5230 混凝土泵车', 'SYM5230-2024004', '广东交通建设', '周志远', '2026-05-24', 1, 1, 0, '新到备件检验合格', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_quality_record` VALUES (4, 'QC-202605004', 'SR360 旋挖钻机', 'SR360-2024005', '四川能源开发', '陈思远', '2026-05-23', 2, 0, 3, '主泵压力未达标准，需调整', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_quality_record` VALUES (5, 'QC-202605005', 'SY215 液压挖掘机', 'SY215-2024007', '湖南中建机械', '王建国', '2026-05-22', 4, 1, 0, '巡检完成，设备状态良好', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_quality_record` VALUES (6, 'QC-202605006', 'SCC2000 履带起重机', 'SCC2000-2024006', '河南振兴路桥', '周志远', '2026-05-21', 2, 1, 1, '电气接线需重新固定，已整改', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_quality_record` VALUES (7, 'QC-202605007', 'SYM5440 混凝土泵车', 'SYM5440-2024008', '广东交通建设', '李明辉', '2026-05-20', 3, 1, 0, '泵送系统维修后检验合格', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_quality_record` VALUES (8, 'QC-202605008', 'SY485H 液压挖掘机', 'SY485H-2024002', '湖北路桥集团', '王建国', '2026-05-19', 1, 0, 2, '部分密封件尺寸不符', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_service_record
-- ----------------------------
DROP TABLE IF EXISTS `tbl_service_record`;
CREATE TABLE `tbl_service_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NOT NULL COMMENT '关联工单ID',
  `technician_id` bigint NOT NULL COMMENT '服务人员ID',
  `service_type` tinyint NOT NULL DEFAULT 1 COMMENT '服务类型 1-维修 2-保养 3-巡检 4-培训',
  `fault_phenomenon` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '故障现象',
  `fault_cause` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '故障原因分析',
  `solution` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '解决方案',
  `parts_replaced` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更换备件列表',
  `labor_hours` decimal(5, 1) NULL DEFAULT 0.0 COMMENT '工时（小时）',
  `service_cost` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '服务费用',
  `is_remote` tinyint NULL DEFAULT 0 COMMENT '是否远程 0-现场 1-远程',
  `service_status` tinyint NULL DEFAULT 0 COMMENT '状态 0-待审核 1-已审核 2-已驳回',
  `audit_remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核备注',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_technician_id`(`technician_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '服务记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_service_record
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_service_report
-- ----------------------------
DROP TABLE IF EXISTS `tbl_service_report`;
CREATE TABLE `tbl_service_report`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `report_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `report_type` tinyint NOT NULL DEFAULT 1,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `equipment_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `equipment_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_by_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_report_no`(`report_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '服务报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_service_report
-- ----------------------------
INSERT INTO `tbl_service_report` VALUES (1, 'SR-202605001', 'WO20260520001', 1, 'SY485H挖掘机发动机异响维修报告', 'SY485H 液压挖掘机', 'SY485H-2024001', '湖南中建机械', '王工', '故障现象：发动机异响严重，冒黑烟。\n处理过程：\n1. 现场检测发动机各缸压力，发现第四缸压力不足。\n2. 进一步检查发现涡轮增压器叶轮磨损严重，导致进气量不足。\n3. 更换涡轮增压器总成（零件编号：SP-001）。\n4. 清洗燃油管路和喷油嘴。\n5. 调试运行2小时，各项参数正常。\n结论：维修完成，建议客户每500小时检查增压器运行状态。', '2026-05-21 17:00:00');
INSERT INTO `tbl_service_report` VALUES (2, 'SR-202605002', 'WO20260524005', 3, 'SY215挖掘机季度巡检报告', 'SY215 液压挖掘机', 'SY215-2024007', '湖南中建机械', '刘工', '巡检项目：\n1. 发动机系统：运行正常，机油液位正常。\n2. 液压系统：各管路无泄漏，液压油清洁度合格。\n3. 电气系统：各传感器工作正常。\n4. 行走系统：履带张紧度合适。\n5. 工作装置：各销轴润滑良好。\n建议：下次保养时（约200小时后）更换液压油及滤芯。', '2026-05-24 11:30:00');

-- ----------------------------
-- Table structure for tbl_settlement
-- ----------------------------
DROP TABLE IF EXISTS `tbl_settlement`;
CREATE TABLE `tbl_settlement`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `settlement_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `equipment_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `service_type` tinyint NOT NULL DEFAULT 1,
  `service_fee` decimal(12, 2) NULL DEFAULT 0.00,
  `parts_fee` decimal(12, 2) NULL DEFAULT 0.00,
  `total_amount` decimal(12, 2) NULL DEFAULT 0.00,
  `billing_date` date NULL DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT 0,
  `payment_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_settlement_no`(`settlement_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '结算表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_settlement
-- ----------------------------
INSERT INTO `tbl_settlement` VALUES (1, 'SET-202605001', 'WO20260520001', '湖南中建机械', 'SY485H 液压挖掘机', 1, 3500.00, 6800.00, 10300.00, '2026-05-22', 2, '转账', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (2, 'SET-202605002', 'WO20260524005', '湖南中建机械', 'SY215 液压挖掘机', 4, 1500.00, 0.00, 1500.00, '2026-05-24', 3, '转账', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (3, 'SET-202605003', 'WO20260525007', '广东交通建设', 'SYM5440 混凝土泵车', 1, 4500.00, 3500.00, 8000.00, NULL, 0, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (4, 'SET-202605004', 'WO20260522002', '江西赣南矿业', 'STC250 汽车起重机', 1, 2800.00, 1200.00, 4000.00, NULL, 0, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (5, 'SET-202605005', 'WO20260525006', '河南振兴路桥', 'SCC2000 履带起重机', 3, 8000.00, 0.00, 8000.00, '2026-05-26', 1, '支票', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (6, 'SET-202605006', 'WO20260524004', '四川能源开发', 'SR360 旋挖钻机', 1, 3200.00, 4500.00, 7700.00, NULL, 0, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (7, 'SET-202605007', 'WO20260523003', '广东交通建设', 'SYM5230 混凝土泵车', 2, 1800.00, 2500.00, 4300.00, '2026-05-25', 2, '现金', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (8, 'SET-202605008', 'WO20260526008', '湖北路桥集团', 'SY485H 液压挖掘机', 1, 4000.00, 5500.00, 9500.00, NULL, 0, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (9, 'SET-202605009', 'WO20260520001', '湖南中建机械', 'SY485H 液压挖掘机', 2, 1200.00, 1800.00, 3000.00, '2026-05-20', 3, '转账', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_settlement` VALUES (10, 'SET-202605010', 'WO20260524005', '湖南中建机械', 'SY215 液压挖掘机', 2, 800.00, 1600.00, 2400.00, '2026-05-23', 2, '转账', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_spare_part
-- ----------------------------
DROP TABLE IF EXISTS `tbl_spare_part`;
CREATE TABLE `tbl_spare_part`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `part_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `part_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `specification` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stock_quantity` int NOT NULL DEFAULT 0,
  `min_stock` int NOT NULL DEFAULT 0,
  `price` decimal(12, 2) NULL DEFAULT 0.00,
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_part_code`(`part_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '备件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_spare_part
-- ----------------------------
INSERT INTO `tbl_spare_part` VALUES (1, 'SP-001', '涡轮增压器总成', 'HX55W', '个', 5, 3, 6800.00, '适用于SY485H', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (2, 'SP-002', '液压油过滤芯', 'P851718', '个', 50, 20, 180.00, '通用型', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (3, 'SP-003', '柴油滤清器', 'FS1280', '个', 30, 15, 95.00, '适用于SY系列', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (4, 'SP-004', '伸缩油缸密封圈套件', 'STC250-01', '套', 8, 5, 1200.00, '适用于STC250', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (5, 'SP-005', '主油泵维修包', 'K3V63', '套', 3, 5, 3500.00, '库存偏少，需补充', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (6, 'SP-006', '发动机机油(18L)', 'CI-4 15W-40', '桶', 2, 10, 420.00, '库存不足', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (7, 'SP-007', '液压油(208L)', 'L-HM 46', '桶', 10, 5, 2800.00, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (8, 'SP-008', '旋转马达总成', 'SY485H-RM', '台', 1, 2, 45000.00, '贵重备件', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (9, 'SP-009', '空气滤清器滤芯', 'K2023', '个', 60, 20, 65.00, '', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_spare_part` VALUES (10, 'SP-010', '斗齿(适用于SY485H)', 'SY485H-DC', '个', 0, 30, 85.00, '缺货，需采购', 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_user`;
CREATE TABLE `tbl_sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密密码',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型 0-管理员 1-运维人员 2-客户',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '所属客户ID',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  INDEX `idx_phone`(`phone` ASC) USING BTREE,
  INDEX `idx_customer_id`(`customer_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_sys_user
-- ----------------------------
INSERT INTO `tbl_sys_user` VALUES (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '系统管理员', NULL, NULL, NULL, 0, 1, NULL, 0, '2026-05-25 20:03:56', '2026-05-25 20:03:56');
INSERT INTO `tbl_sys_user` VALUES (2, 'engineer01', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '张工', '13800138001', NULL, NULL, 1, 1, NULL, 0, '2026-05-25 20:03:56', '2026-05-25 20:03:56');
INSERT INTO `tbl_sys_user` VALUES (3, 'engineer02', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '李工', '13800138002', NULL, NULL, 1, 1, NULL, 0, '2026-05-25 20:03:56', '2026-05-25 20:03:56');

-- ----------------------------
-- Table structure for tbl_training_record
-- ----------------------------
DROP TABLE IF EXISTS `tbl_training_record`;
CREATE TABLE `tbl_training_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `record_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `course_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `course_type` tinyint NOT NULL DEFAULT 1,
  `trainer` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trainee_count` int NULL DEFAULT 0,
  `trainee_names` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_date` date NULL DEFAULT NULL,
  `end_date` date NULL DEFAULT NULL,
  `duration` decimal(5, 1) NULL DEFAULT 0.0,
  `status` tinyint NOT NULL DEFAULT 0,
  `exam_pass_rate` decimal(5, 1) NULL DEFAULT 0.0,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_record_no`(`record_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '培训记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_training_record
-- ----------------------------
INSERT INTO `tbl_training_record` VALUES (1, 'TR-2026001', 'SY485H液压系统维修技术培训', 1, '王建国', 12, '李明辉,周志远,陈思远...', '2026-05-20', '2026-05-22', 24.0, 2, 92.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_training_record` VALUES (2, 'TR-2026002', '施工现场安全操作规范培训', 2, '赵刚强', 25, '售后服务部全员...', '2026-05-25', '2026-05-26', 8.0, 2, 88.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_training_record` VALUES (3, 'TR-2026003', '服务团队管理能力提升', 3, '外部讲师', 8, '赵刚强,王建国...', '2026-06-01', '2026-06-03', 16.0, 0, 0.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_training_record` VALUES (4, 'TR-2026004', 'STC系列起重机新产品培训', 4, '研发部讲师', 15, '维修工程师团队...', '2026-05-28', '2026-05-29', 12.0, 1, 0.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_training_record` VALUES (5, 'TR-2026005', '电气系统故障诊断进阶培训', 1, '李明辉', 6, '周志远,陈思远...', '2026-05-15', '2026-05-16', 12.0, 2, 95.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_training_record` VALUES (6, 'TR-2026006', '高空作业安全培训', 2, '安全专员', 20, '全体外勤人员...', '2026-06-05', '2026-06-05', 4.0, 0, 0.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_training_record` VALUES (7, 'TR-2026007', '混凝土泵车操作技能培训', 4, '周志远', 10, '新入职工程师...', '2026-05-10', '2026-05-11', 8.0, 2, 85.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_training_record` VALUES (8, 'TR-2026008', '质量管理体系内部审核培训', 3, '外部讲师', 5, '管理团队...', '2026-06-10', '2026-06-12', 20.0, 0, 0.0, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_warehouse_item
-- ----------------------------
DROP TABLE IF EXISTS `tbl_warehouse_item`;
CREATE TABLE `tbl_warehouse_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `item_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `specification` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stock_quantity` int NOT NULL DEFAULT 0,
  `min_stock` int NOT NULL DEFAULT 0,
  `max_stock` int NOT NULL DEFAULT 0,
  `location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `last_inbound` date NULL DEFAULT NULL,
  `last_outbound` date NULL DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT 1,
  `deleted` tinyint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_item_code`(`item_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '仓储物料表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_warehouse_item
-- ----------------------------
INSERT INTO `tbl_warehouse_item` VALUES (1, 'WH-001', 'Q345B钢板(16mm)', '原材料', '16mm*1500*6000', '张', 120, 30, 300, 'A-01-01', '2026-05-20', '2026-05-25', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (2, 'WH-002', 'Q235钢管(108mm)', '原材料', '108*4mm', '根', 200, 50, 500, 'A-01-02', '2026-05-18', '2026-05-24', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (3, 'WH-003', '标准螺栓M20', '辅料', 'M20*80', '套', 5000, 1000, 10000, 'B-02-01', '2026-05-15', '2026-05-26', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (4, 'WH-004', '液压管路总成', '半成品', 'SY485H-01', '套', 15, 10, 50, 'C-01-01', '2026-05-22', '2026-05-26', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (5, 'WH-005', '电气控制柜', '半成品', 'STC250-DQ', '台', 5, 5, 20, 'C-01-02', '2026-05-10', '2026-05-23', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (6, 'WH-006', '挖掘机斗齿', '成品', 'SY485H-DC', '个', 80, 30, 200, 'D-01-01', '2026-05-19', '2026-05-25', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (7, 'WH-007', '液压油(208L)', '辅料', 'L-HM 46', '桶', 8, 10, 40, 'B-02-02', '2026-05-08', '2026-05-24', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (8, 'WH-008', '发动机机油(18L)', '辅料', 'CI-4 15W-40', '桶', 3, 10, 50, 'B-02-03', '2026-05-05', '2026-05-22', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (9, 'WH-009', '焊接机器人焊丝', '辅料', 'ER50-6 1.2mm', '卷', 45, 15, 100, 'B-01-01', '2026-05-21', '2026-05-26', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (10, 'WH-010', '回转支承总成', '半成品', 'SY485H-HZ', '套', 3, 5, 15, 'C-02-01', '2026-05-12', '2026-05-20', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (11, 'WH-011', '驾驶室总成', '成品', 'SY485H-JS', '台', 2, 3, 10, 'D-01-02', '2026-05-06', '2026-05-18', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (12, 'WH-012', '履带板总成', '半成品', 'SY485H-LD', '对', 10, 5, 25, 'C-02-02', '2026-05-16', '2026-05-24', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (13, 'WH-013', 'Q460C钢板(20mm)', '原材料', '20mm*1500*6000', '张', 60, 20, 200, 'A-01-03', '2026-05-23', '2026-05-26', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (14, 'WH-014', '传动轴总成', '成品', 'STC250-CD', '根', 0, 5, 15, 'D-02-01', '2026-04-28', '2026-05-15', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');
INSERT INTO `tbl_warehouse_item` VALUES (15, 'WH-015', '耐磨衬板', '原材料', 'NM400 12mm', '张', 35, 10, 80, 'A-02-01', '2026-05-24', '2026-05-26', 1, 0, '2026-06-14 12:44:57', '2026-06-14 12:44:57');

-- ----------------------------
-- Table structure for tbl_warehouse_transaction
-- ----------------------------
DROP TABLE IF EXISTS `tbl_warehouse_transaction`;
CREATE TABLE `tbl_warehouse_transaction`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` tinyint NOT NULL DEFAULT 1,
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `item_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT 0,
  `operator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `transaction_date` datetime NULL DEFAULT NULL,
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_transaction_no`(`transaction_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '仓储流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_warehouse_transaction
-- ----------------------------
INSERT INTO `tbl_warehouse_transaction` VALUES (1, 'IN-202605001', 1, 'WH-001', 'Q345B钢板(16mm)', 50, '管理员', '2026-05-26 09:30:00', '采购入库');
INSERT INTO `tbl_warehouse_transaction` VALUES (2, 'OUT-202605001', 2, 'WH-003', '标准螺栓M20', 200, '王建国', '2026-05-26 08:15:00', '维修领用');
INSERT INTO `tbl_warehouse_transaction` VALUES (3, 'IN-202605002', 1, 'WH-013', 'Q460C钢板(20mm)', 30, '管理员', '2026-05-25 14:00:00', '采购入库');
INSERT INTO `tbl_warehouse_transaction` VALUES (4, 'OUT-202605002', 2, 'WH-004', '液压管路总成', 2, '周志远', '2026-05-25 10:30:00', '维修更换');
INSERT INTO `tbl_warehouse_transaction` VALUES (5, 'IN-202605003', 1, 'WH-009', '焊接机器人焊丝', 20, '管理员', '2026-05-24 16:00:00', '采购入库');
INSERT INTO `tbl_warehouse_transaction` VALUES (6, 'OUT-202605003', 2, 'WH-006', '挖掘机斗齿', 15, '王建国', '2026-05-24 09:00:00', '客户更换');
INSERT INTO `tbl_warehouse_transaction` VALUES (7, 'IN-202605004', 1, 'WH-015', '耐磨衬板', 20, '管理员', '2026-05-23 11:00:00', '采购入库');
INSERT INTO `tbl_warehouse_transaction` VALUES (8, 'OUT-202605004', 2, 'WH-002', 'Q235钢管(108mm)', 30, '陈思远', '2026-05-23 08:45:00', '生产领用');
INSERT INTO `tbl_warehouse_transaction` VALUES (9, 'IN-202605005', 1, 'WH-005', '电气控制柜', 3, '管理员', '2026-05-22 15:30:00', '生产完工入库');
INSERT INTO `tbl_warehouse_transaction` VALUES (10, 'OUT-202605005', 2, 'WH-010', '回转支承总成', 1, '周志远', '2026-05-22 10:00:00', '维修更换');
INSERT INTO `tbl_warehouse_transaction` VALUES (11, 'IN-202605006', 1, 'WH-012', '履带板总成', 5, '管理员', '2026-05-21 13:00:00', '采购入库');
INSERT INTO `tbl_warehouse_transaction` VALUES (12, 'OUT-202605006', 2, 'WH-007', '液压油(208L)', 2, '李明辉', '2026-05-21 09:15:00', '保养更换');
INSERT INTO `tbl_warehouse_transaction` VALUES (13, 'IN-202605007', 1, 'WH-011', '驾驶室总成', 1, '管理员', '2026-05-20 16:45:00', '生产完工入库');
INSERT INTO `tbl_warehouse_transaction` VALUES (14, 'OUT-202605007', 2, 'WH-008', '发动机机油(18L)', 5, '王建国', '2026-05-20 08:30:00', '定期保养');
INSERT INTO `tbl_warehouse_transaction` VALUES (15, 'IN-202605008', 1, 'WH-001', 'Q345B钢板(16mm)', 30, '管理员', '2026-05-19 14:20:00', '采购入库');

-- ----------------------------
-- Table structure for tbl_work_order
-- ----------------------------
DROP TABLE IF EXISTS `tbl_work_order`;
CREATE TABLE `tbl_work_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工单编号 WO+yyyyMMdd+0001',
  `order_type` tinyint NOT NULL DEFAULT 1 COMMENT '工单类型 1-报修 2-保养 3-安装调试 4-巡检',
  `order_source` tinyint NOT NULL DEFAULT 1 COMMENT '来源 1-客户报修 2-系统生成 3-管理员创建',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `equipment_id` bigint NOT NULL COMMENT '设备ID',
  `reporter_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '报修人姓名',
  `reporter_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '报修人电话',
  `fault_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '故障描述',
  `fault_level` tinyint NULL DEFAULT 3 COMMENT '紧急程度 1-紧急 2-高 3-中 4-低',
  `fault_category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '故障分类',
  `assignee_id` bigint NULL DEFAULT NULL COMMENT '指派的运维人员ID',
  `assign_time` datetime NULL DEFAULT NULL COMMENT '派单时间',
  `accept_time` datetime NULL DEFAULT NULL COMMENT '接单时间',
  `arrive_time` datetime NULL DEFAULT NULL COMMENT '到达现场时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完工时间',
  `confirm_time` datetime NULL DEFAULT NULL COMMENT '客户确认时间',
  `order_status` tinyint NOT NULL DEFAULT 0 COMMENT '工单状态 0-待派单 1-待接单 2-处理中 3-待验收 4-已完成 5-已关闭 6-已取消',
  `handling_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '处理详情',
  `handling_result` tinyint NULL DEFAULT 0 COMMENT '处理结果 0-未处理 1-已修复 2-无法修复 3-需更换设备',
  `charge_amount` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '收费金额',
  `is_chargeable` tinyint NULL DEFAULT 0 COMMENT '是否收费 0-免费 1-收费',
  `customer_sign` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户签名图片URL',
  `rating` tinyint NULL DEFAULT NULL COMMENT '客户评分 1-5星',
  `rating_remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价备注',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_customer_id`(`customer_id` ASC) USING BTREE,
  INDEX `idx_equipment_id`(`equipment_id` ASC) USING BTREE,
  INDEX `idx_assignee_id`(`assignee_id` ASC) USING BTREE,
  INDEX `idx_order_status`(`order_status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  CONSTRAINT `fk_wo_assignee` FOREIGN KEY (`assignee_id`) REFERENCES `tbl_sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_wo_customer` FOREIGN KEY (`customer_id`) REFERENCES `tbl_customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_wo_equipment` FOREIGN KEY (`equipment_id`) REFERENCES `tbl_equipment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_work_order
-- ----------------------------
INSERT INTO `tbl_work_order` VALUES (1, 'WO202605250001', 1, 1, 1, 1, '王经理', '13900139001', 'SY485H挖掘机液压系统异响，无法正常作业', 1, '液压故障', 2, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 0.00, 0, NULL, NULL, NULL, 0, '2026-05-25 08:30:00', '2026-05-25 21:06:33');
INSERT INTO `tbl_work_order` VALUES (2, 'WO202605250002', 1, 1, 2, 2, '赵经理', '13900139002', '泵车臂架无法展开，控制面板报E032故障码', 2, '电气故障', 1, NULL, NULL, NULL, NULL, NULL, 2, NULL, 0, 0.00, 0, NULL, NULL, NULL, 0, '2026-05-25 09:00:00', '2026-05-25 21:06:33');
INSERT INTO `tbl_work_order` VALUES (3, 'WO202605250003', 2, 1, 1, 1, '王经理', '13900139001', '500小时保养到期，需更换机油滤芯及液压油', 3, '保养', 1, NULL, NULL, NULL, NULL, NULL, 4, NULL, 0, 0.00, 0, NULL, NULL, NULL, 0, '2026-05-24 10:00:00', '2026-05-25 21:06:33');
INSERT INTO `tbl_work_order` VALUES (4, 'WO202605250004', 1, 1, 1, 1, '刘工', '13800138005', '发动机故障灯常亮，动力明显不足', 2, '发动机故障', 2, NULL, NULL, NULL, NULL, NULL, 1, NULL, 0, 0.00, 0, NULL, NULL, NULL, 0, '2026-05-24 14:00:00', '2026-05-25 21:06:33');
INSERT INTO `tbl_work_order` VALUES (5, 'WO202605250005', 4, 1, 2, 2, '赵经理', '13900139002', '季度例行巡检，检查泵车各系统运行状态', 3, '巡检', NULL, NULL, NULL, NULL, NULL, NULL, 5, NULL, 0, 0.00, 0, NULL, NULL, NULL, 0, '2026-05-23 09:00:00', '2026-05-25 21:06:33');
INSERT INTO `tbl_work_order` VALUES (6, 'WO202605250006', 1, 1, 1, 1, '孙工', '13800138006', '行走履带异响严重，右侧履带跑偏', 3, '行走机构故障', NULL, NULL, NULL, NULL, NULL, NULL, 3, NULL, 0, 0.00, 0, NULL, NULL, NULL, 0, '2026-05-22 15:30:00', '2026-05-25 21:06:33');

SET FOREIGN_KEY_CHECKS = 1;

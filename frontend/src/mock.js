const _ = {
  user: { id: 1, username: 'admin', realName: '管理员', role: 'admin', token: 'mock-token-123456' },
  customers: [
    { id: 1, customerName: '湖南中建机械', contactPerson: '张伟', contactPhone: '13800001111', province: '湖南省', city: '长沙', address: '长沙市雨花区韶山中路18号', serviceLevel: 1, status: 1, totalEquipment: 3, totalOrders: 24 },
    { id: 2, customerName: '湖北路桥集团', contactPerson: '李强', contactPhone: '13900002222', province: '湖北省', city: '武汉', address: '武汉市硚口区建设大道168号', serviceLevel: 1, status: 1, totalEquipment: 2, totalOrders: 18 },
    { id: 3, customerName: '江西赣南矿业', contactPerson: '王磊', contactPhone: '13700003333', province: '江西省', city: '赣州', address: '赣州市章贡区红旗大道88号', serviceLevel: 2, status: 1, totalEquipment: 1, totalOrders: 7 },
    { id: 4, customerName: '广东交通建设', contactPerson: '陈明', contactPhone: '13600004444', province: '广东省', city: '广州', address: '广州市天河区珠江新城66号', serviceLevel: 1, status: 1, totalEquipment: 2, totalOrders: 31 },
    { id: 5, customerName: '四川能源开发', contactPerson: '刘洋', contactPhone: '13500005555', province: '四川省', city: '成都', address: '成都市高新区天府大道999号', serviceLevel: 2, status: 1, totalEquipment: 1, totalOrders: 9 },
    { id: 6, customerName: '河南振兴路桥', contactPerson: '赵刚', contactPhone: '13400006666', province: '河南省', city: '郑州', address: '郑州市金水区农业路88号', serviceLevel: 3, status: 1, totalEquipment: 1, totalOrders: 5 },
    { id: 7, customerName: '陕西建工集团', contactPerson: '孙涛', contactPhone: '13300007777', province: '陕西省', city: '西安', address: '西安市雁塔区高新路88号', serviceLevel: 2, status: 1, totalEquipment: 2, totalOrders: 12 },
    { id: 8, customerName: '云南水利工程', contactPerson: '钱进', contactPhone: '13200008888', province: '云南省', city: '昆明', address: '昆明市官渡区民航路66号', serviceLevel: 3, status: 1, totalEquipment: 0, totalOrders: 3 }
  ],
  equipment: [
    { id: 1, equipmentCode: 'SY485H-2024001', equipmentName: 'SY485H 液压挖掘机', equipmentModel: 'SY485H', category: '挖掘机', customerName: '湖南中建机械', customerId: 1, serialNumber: 'SN-485H-2024-001', purchaseDate: '2024-01-15', warrantyEnd: '2027-01-15', installAddress: '长沙市雨花区韶山中路18号', status: 1 },
    { id: 2, equipmentCode: 'SY485H-2024002', equipmentName: 'SY485H 液压挖掘机', equipmentModel: 'SY485H', category: '挖掘机', customerName: '湖北路桥集团', customerId: 2, serialNumber: 'SN-485H-2024-002', purchaseDate: '2024-03-20', warrantyEnd: '2027-03-20', installAddress: '武汉市硚口区建设大道168号', status: 1 },
    { id: 3, equipmentCode: 'STC250-2024003', equipmentName: 'STC250 汽车起重机', equipmentModel: 'STC250', category: '起重机', customerName: '江西赣南矿业', customerId: 3, serialNumber: 'SN-STC250-2024-001', purchaseDate: '2024-06-01', warrantyEnd: '2027-06-01', installAddress: '赣州市章贡区红旗大道88号', status: 1 },
    { id: 4, equipmentCode: 'SYM5230-2024004', equipmentName: 'SYM5230 混凝土泵车', equipmentModel: 'SYM5230', category: '混凝土泵车', customerName: '广东交通建设', customerId: 4, serialNumber: 'SN-SYM5230-2024-001', purchaseDate: '2024-04-10', warrantyEnd: '2027-04-10', installAddress: '广州市天河区珠江新城66号', status: 2 },
    { id: 5, equipmentCode: 'SR360-2024005', equipmentName: 'SR360 旋挖钻机', equipmentModel: 'SR360', category: '旋挖钻机', customerName: '四川能源开发', customerId: 5, serialNumber: 'SN-SR360-2024-001', purchaseDate: '2024-08-15', warrantyEnd: '2027-08-15', installAddress: '成都市高新区天府大道999号', status: 1 },
    { id: 6, equipmentCode: 'SCC2000-2024006', equipmentName: 'SCC2000 履带起重机', equipmentModel: 'SCC2000', category: '起重机', customerName: '河南振兴路桥', customerId: 6, serialNumber: 'SN-SCC2000-2024-001', purchaseDate: '2023-11-20', warrantyEnd: '2026-11-20', installAddress: '郑州市金水区农业路88号', status: 0 },
    { id: 7, equipmentCode: 'SY215-2024007', equipmentName: 'SY215 液压挖掘机', equipmentModel: 'SY215', category: '挖掘机', customerName: '湖南中建机械', customerId: 1, serialNumber: 'SN-215H-2024-001', purchaseDate: '2024-05-08', warrantyEnd: '2027-05-08', installAddress: '长沙市雨花区韶山中路18号', status: 1 },
    { id: 8, equipmentCode: 'SYM5440-2024008', equipmentName: 'SYM5440 混凝土泵车', equipmentModel: 'SYM5440', category: '混凝土泵车', customerName: '广东交通建设', customerId: 4, serialNumber: 'SN-SYM5440-2024-001', purchaseDate: '2024-09-12', warrantyEnd: '2027-09-12', installAddress: '广州市天河区珠江新城66号', status: 1 },
    { id: 9, equipmentCode: 'STC800-2024009', equipmentName: 'STC800 汽车起重机', equipmentModel: 'STC800', category: '起重机', customerName: '陕西建工集团', customerId: 7, serialNumber: 'SN-STC800-2024-001', purchaseDate: '2024-07-22', warrantyEnd: '2027-07-22', installAddress: '西安市雁塔区高新路88号', status: 1 },
    { id: 10, equipmentCode: 'SY485H-2024010', equipmentName: 'SY485H 液压挖掘机', equipmentModel: 'SY485H', category: '挖掘机', customerName: '陕西建工集团', customerId: 7, serialNumber: 'SN-485H-2024-003', purchaseDate: '2024-10-01', warrantyEnd: '2027-10-01', installAddress: '西安市雁塔区高新路88号', status: 1 }
  ],
  workOrders: [
    { id: 1, orderNo: 'WO20260520001', orderTypeName: '维修', orderStatus: 4, orderStatusName: '已完成', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024001', customerName: '湖南中建机械', reporterName: '张伟', reporterPhone: '13800001111', faultLevel: 1, faultLevelName: '紧急', faultDescription: '发动机异响严重，冒黑烟，需要立即检修', assigneeName: '王工', assignTime: '2026-05-20 09:30:00', arriveTime: '2026-05-20 11:15:00', finishTime: '2026-05-21 16:00:00', handlingDetail: '更换涡轮增压器总成，清洗燃油管路，调试后运行正常', createTime: '2026-05-20 08:15:00' },
    { id: 2, orderNo: 'WO20260522002', orderTypeName: '维修', orderStatus: 2, orderStatusName: '处理中', equipmentName: 'STC250 汽车起重机', equipmentCode: 'STC250-2024003', customerName: '江西赣南矿业', reporterName: '王磊', reporterPhone: '13700003333', faultLevel: 2, faultLevelName: '高', faultDescription: '起重臂伸缩时有异响，动作不顺畅', assigneeName: '李工', assignTime: '2026-05-22 14:00:00', arriveTime: '2026-05-22 16:30:00', finishTime: null, handlingDetail: '检查发现伸缩油缸密封圈损坏，已更换密封圈，正在调试中', createTime: '2026-05-22 10:20:00' },
    { id: 3, orderNo: 'WO20260523003', orderTypeName: '保养', orderStatus: 0, orderStatusName: '待派单', equipmentName: 'SYM5230 混凝土泵车', equipmentCode: 'SYM5230-2024004', customerName: '广东交通建设', reporterName: '陈明', reporterPhone: '13600004444', faultLevel: 3, faultLevelName: '中', faultDescription: '定期保养到期，需要更换机油、滤芯，检查液压系统', assigneeName: null, assignTime: null, arriveTime: null, finishTime: null, handlingDetail: null, createTime: '2026-05-23 09:00:00' },
    { id: 4, orderNo: 'WO20260524004', orderTypeName: '维修', orderStatus: 1, orderStatusName: '待接单', equipmentName: 'SR360 旋挖钻机', equipmentCode: 'SR360-2024005', customerName: '四川能源开发', reporterName: '刘洋', reporterPhone: '13500005555', faultLevel: 1, faultLevelName: '紧急', faultDescription: '动力头无法旋转，主泵压力异常', assigneeName: '赵工', assignTime: '2026-05-24 11:00:00', arriveTime: null, finishTime: null, handlingDetail: null, createTime: '2026-05-24 10:30:00' },
    { id: 5, orderNo: 'WO20260524005', orderTypeName: '巡检', orderStatus: 4, orderStatusName: '已完成', equipmentName: 'SY215 液压挖掘机', equipmentCode: 'SY215-2024007', customerName: '湖南中建机械', reporterName: '张伟', reporterPhone: '13800001111', faultLevel: 4, faultLevelName: '低', faultDescription: '季度例行巡检，检查各系统运行状况', assigneeName: '刘工', assignTime: '2026-05-24 08:30:00', arriveTime: '2026-05-24 09:00:00', finishTime: '2026-05-24 11:30:00', handlingDetail: '各系统运行正常，建议下次保养时更换液压油', createTime: '2026-05-23 16:00:00' },
    { id: 6, orderNo: 'WO20260525006', orderTypeName: '安装', orderStatus: 2, orderStatusName: '处理中', equipmentName: 'SCC2000 履带起重机', equipmentCode: 'SCC2000-2024006', customerName: '河南振兴路桥', reporterName: '赵刚', reporterPhone: '13400006666', faultLevel: 3, faultLevelName: '中', faultDescription: '新设备到场安装调试，需要技术支持', assigneeName: '孙工', assignTime: '2026-05-25 08:00:00', arriveTime: '2026-05-25 10:00:00', finishTime: null, handlingDetail: '正在进行主机组装和电气系统接线', createTime: '2026-05-24 15:00:00' },
    { id: 7, orderNo: 'WO20260525007', orderTypeName: '维修', orderStatus: 3, orderStatusName: '待验收', equipmentName: 'SYM5440 混凝土泵车', equipmentCode: 'SYM5440-2024008', customerName: '广东交通建设', reporterName: '陈明', reporterPhone: '13600004444', faultLevel: 2, faultLevelName: '高', faultDescription: '泵送系统压力不足，输送量下降30%', assigneeName: '周工', assignTime: '2026-05-25 13:00:00', arriveTime: '2026-05-25 14:30:00', finishTime: '2026-05-26 10:00:00', handlingDetail: '更换主油泵维修包，清洗液压阀组，试机运行正常，待客户验收', createTime: '2026-05-25 11:00:00' },
    { id: 8, orderNo: 'WO20260526008', orderTypeName: '维修', orderStatus: 0, orderStatusName: '待派单', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024002', customerName: '湖北路桥集团', reporterName: '李强', reporterPhone: '13900002222', faultLevel: 1, faultLevelName: '紧急', faultDescription: '液压系统漏油严重，无法正常作业', assigneeName: null, assignTime: null, arriveTime: null, finishTime: null, handlingDetail: null, createTime: '2026-05-26 07:45:00' }
  ],
  spareParts: [
    { id: 1, partCode: 'SP-001', partName: '涡轮增压器总成', specification: 'HX55W', unit: '个', stockQuantity: 5, minStock: 3, price: 6800, remark: '适用于SY485H' },
    { id: 2, partCode: 'SP-002', partName: '液压油过滤芯', specification: 'P851718', unit: '个', stockQuantity: 50, minStock: 20, price: 180, remark: '通用型' },
    { id: 3, partCode: 'SP-003', partName: '柴油滤清器', specification: 'FS1280', unit: '个', stockQuantity: 30, minStock: 15, price: 95, remark: '适用于SY系列' },
    { id: 4, partCode: 'SP-004', partName: '伸缩油缸密封圈套件', specification: 'STC250-01', unit: '套', stockQuantity: 8, minStock: 5, price: 1200, remark: '适用于STC250' },
    { id: 5, partCode: 'SP-005', partName: '主油泵维修包', specification: 'K3V63', unit: '套', stockQuantity: 3, minStock: 5, price: 3500, remark: '库存偏少，需补充' },
    { id: 6, partCode: 'SP-006', partName: '发动机机油(18L)', specification: 'CI-4 15W-40', unit: '桶', stockQuantity: 2, minStock: 10, price: 420, remark: '库存不足' },
    { id: 7, partCode: 'SP-007', partName: '液压油(208L)', specification: 'L-HM 46', unit: '桶', stockQuantity: 10, minStock: 5, price: 2800, remark: '' },
    { id: 8, partCode: 'SP-008', partName: '旋转马达总成', specification: 'SY485H-RM', unit: '台', stockQuantity: 1, minStock: 2, price: 45000, remark: '贵重备件' },
    { id: 9, partCode: 'SP-009', partName: '空气滤清器滤芯', specification: 'K2023', unit: '个', stockQuantity: 60, minStock: 20, price: 65, remark: '' },
    { id: 10, partCode: 'SP-010', partName: '斗齿(适用于SY485H)', specification: 'SY485H-DC', unit: '个', stockQuantity: 0, minStock: 30, price: 85, remark: '缺货，需采购' }
  ],
  reports: [
    { id: 1, reportNo: 'SR-202605001', orderNo: 'WO20260520001', reportType: 1, title: 'SY485H挖掘机发动机异响维修报告', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024001', customerName: '湖南中建机械', createByName: '王工', createTime: '2026-05-21 17:00:00', content: '故障现象：发动机异响严重，冒黑烟。\n处理过程：\n1. 现场检测发动机各缸压力，发现第四缸压力不足。\n2. 进一步检查发现涡轮增压器叶轮磨损严重，导致进气量不足。\n3. 更换涡轮增压器总成（零件编号：SP-001）。\n4. 清洗燃油管路和喷油嘴。\n5. 调试运行2小时，各项参数正常。\n结论：维修完成，建议客户每500小时检查增压器运行状态。' },
    { id: 2, reportNo: 'SR-202605002', orderNo: 'WO20260524005', reportType: 3, title: 'SY215挖掘机季度巡检报告', equipmentName: 'SY215 液压挖掘机', equipmentCode: 'SY215-2024007', customerName: '湖南中建机械', createByName: '刘工', createTime: '2026-05-24 11:30:00', content: '巡检项目：\n1. 发动机系统：运行正常，机油液位正常。\n2. 液压系统：各管路无泄漏，液压油清洁度合格。\n3. 电气系统：各传感器工作正常。\n4. 行走系统：履带张紧度合适。\n5. 工作装置：各销轴润滑良好。\n建议：下次保养时（约200小时后）更换液压油及滤芯。' }
  ],
  notifications: [
    { id: 1, title: '紧急工单待处理', content: '工单 WO20260526008（SY485H液压系统漏油）已被标记为紧急，请尽快派单处理。', type: 1, isRead: false, createTime: '2026-05-26 07:50:00' },
    { id: 2, title: '工单已完成待验收', content: '工单 WO20260525007（SYM5440泵送系统维修）已完工，请客户及时验收。', type: 2, isRead: false, createTime: '2026-05-26 10:05:00' },
    { id: 3, title: '备件库存预警', content: '备件 "发动机机油(18L)" 库存仅剩2桶，低于最低库存线(10桶)，请及时采购。', type: 1, isRead: false, createTime: '2026-05-26 08:30:00' },
    { id: 4, title: '保养到期提醒', content: '设备 SY485H-2024001（湖南中建机械）保养周期已到，请安排保养服务。', type: 3, isRead: false, createTime: '2026-05-25 09:00:00' },
    { id: 5, title: '新工单已创建', content: '工单 WO20260525006（SCC2000履带起重机安装调试）已创建并派单给孙工。', type: 2, isRead: true, createTime: '2026-05-25 08:00:00' },
    { id: 6, title: '备件采购到货通知', content: '备件 "液压油过滤芯" 已到货入库，数量50个。', type: 3, isRead: true, createTime: '2026-05-24 14:00:00' },
    { id: 7, title: '系统升级通知', content: '系统将于2026年5月28日凌晨2:00-4:00进行版本升级，届时服务将暂时中断。', type: 4, isRead: true, createTime: '2026-05-24 10:00:00' }
  ],
  employees: [
    { id: 1, employeeNo: 'EMP001', employeeName: '王建国', gender: 1, position: '维修工程师', phone: '18812340001', email: 'wangjg@sany.com.cn', department: '售后服务部', status: 1, remark: '高级工程师，擅长液压系统维修' },
    { id: 2, employeeNo: 'EMP002', employeeName: '李明辉', gender: 1, position: '维修工程师', phone: '18812340002', email: 'limh@sany.com.cn', department: '售后服务部', status: 1, remark: '中级工程师，擅长电气系统' },
    { id: 3, employeeNo: 'EMP003', employeeName: '刘晓燕', gender: 0, position: '客服专员', phone: '18812340003', email: 'liuxy@sany.com.cn', department: '客服部', status: 1, remark: '' },
    { id: 4, employeeNo: 'EMP004', employeeName: '赵刚强', gender: 1, position: '服务经理', phone: '18812340004', email: 'zhaogq@sany.com.cn', department: '售后服务部', status: 1, remark: '负责华中区域' },
    { id: 5, employeeNo: 'EMP005', employeeName: '孙丽华', gender: 0, position: '技术支持', phone: '18812340005', email: 'sunlh@sany.com.cn', department: '技术部', status: 2, remark: '休假中' },
    { id: 6, employeeNo: 'EMP006', employeeName: '周志远', gender: 1, position: '维修工程师', phone: '18812340006', email: 'zhouzy@sany.com.cn', department: '售后服务部', status: 1, remark: '中级工程师' },
    { id: 7, employeeNo: 'EMP007', employeeName: '吴敏', gender: 0, position: '客服专员', phone: '18812340007', email: 'wumin@sany.com.cn', department: '客服部', status: 1, remark: '' },
    { id: 8, employeeNo: 'EMP008', employeeName: '郑海涛', gender: 1, position: '管理员', phone: '18812340008', email: 'zhenght@sany.com.cn', department: '综合管理部', status: 1, remark: '系统管理员' },
    { id: 9, employeeNo: 'EMP009', employeeName: '陈思远', gender: 1, position: '维修工程师', phone: '18812340009', email: 'chensy@sany.com.cn', department: '售后服务部', status: 1, remark: '初级工程师' },
    { id: 10, employeeNo: 'EMP010', employeeName: '林小红', gender: 0, position: '客服专员', phone: '18812340010', email: 'linxh@sany.com.cn', department: '客服部', status: 1, remark: '' }
  ],
  maintenancePlans: [
    { id: 1, planNo: 'MP-202605001', equipmentId: 1, equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024001', customerName: '湖南中建机械', planType: 1, planTypeName: '定期保养', planDate: '2026-06-01', status: 0, statusName: '待执行', assigneeName: '王建国', remark: '更换机油、滤芯' },
    { id: 2, planNo: 'MP-202605002', equipmentId: 3, equipmentName: 'STC250 汽车起重机', equipmentCode: 'STC250-2024003', customerName: '江西赣南矿业', planType: 2, planTypeName: '年检', planDate: '2026-06-15', status: 0, statusName: '待执行', assigneeName: '李明辉', remark: '年度安全检查' },
    { id: 3, planNo: 'MP-202605003', equipmentId: 5, equipmentName: 'SR360 旋挖钻机', equipmentCode: 'SR360-2024005', customerName: '四川能源开发', planType: 1, planTypeName: '定期保养', planDate: '2026-05-30', status: 1, statusName: '执行中', assigneeName: '周志远', remark: '更换液压油、滤芯' },
    { id: 4, planNo: 'MP-202605004', equipmentId: 7, equipmentName: 'SY215 液压挖掘机', equipmentCode: 'SY215-2024007', customerName: '湖南中建机械', planType: 3, planTypeName: '巡检', planDate: '2026-05-28', status: 1, statusName: '执行中', assigneeName: '王建国', remark: '季度巡检' },
    { id: 5, planNo: 'MP-202605005', equipmentId: 2, equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024002', customerName: '湖北路桥集团', planType: 1, planTypeName: '定期保养', planDate: '2026-06-10', status: 0, statusName: '待执行', assigneeName: '', remark: '更换三滤、机油' },
    { id: 6, planNo: 'MP-202605006', equipmentId: 8, equipmentName: 'SYM5440 混凝土泵车', equipmentCode: 'SYM5440-2024008', customerName: '广东交通建设', planType: 2, planTypeName: '年检', planDate: '2026-07-01', status: 0, statusName: '待执行', assigneeName: '', remark: '年度全面检查' },
    { id: 7, planNo: 'MP-202605007', equipmentId: 4, equipmentName: 'SYM5230 混凝土泵车', equipmentCode: 'SYM5230-2024004', customerName: '广东交通建设', planType: 1, planTypeName: '定期保养', planDate: '2026-05-25', status: 2, statusName: '已完成', assigneeName: '陈思远', remark: '更换机油、液压油' }
  ],
  operationLogs: [
    { id: 1, operator: '管理员', action: '登录系统', target: '', detail: '管理员登录系统', ip: '192.168.1.100', createTime: '2026-05-26 08:30:00' },
    { id: 2, operator: '管理员', action: '创建工单', target: 'WO20260526008', detail: '创建紧急维修工单', ip: '192.168.1.100', createTime: '2026-05-26 08:45:00' },
    { id: 3, operator: '管理员', action: '派单', target: 'WO20260526008', detail: '派单给王工', ip: '192.168.1.100', createTime: '2026-05-26 09:00:00' },
    { id: 4, operator: '管理员', action: '新增备件', target: 'SP-011', detail: '新增斗齿备件', ip: '192.168.1.100', createTime: '2026-05-26 09:30:00' },
    { id: 5, operator: '管理员', action: '编辑员工', target: 'EMP001', detail: '更新员工信息', ip: '192.168.1.100', createTime: '2026-05-26 10:00:00' },
    { id: 6, operator: '王建国', action: '接单', target: 'WO20260524004', detail: '维修工程师接单', ip: '192.168.1.101', createTime: '2026-05-25 14:20:00' },
    { id: 7, operator: '王建国', action: '完成工单', target: 'WO20260520001', detail: '维修完成，待验收', ip: '192.168.1.101', createTime: '2026-05-25 16:00:00' },
    { id: 8, operator: '管理员', action: '创建保养计划', target: 'MP-202605001', detail: '创建定期保养计划', ip: '192.168.1.100', createTime: '2026-05-24 11:00:00' },
    { id: 9, operator: '管理员', action: '客户评价', target: 'WO20260520001', detail: '客户提交5星评价', ip: '192.168.1.100', createTime: '2026-05-24 09:15:00' },
    { id: 10, operator: '管理员', action: '删除工单', target: 'WO20260519009', detail: '删除已取消工单', ip: '192.168.1.100', createTime: '2026-05-23 17:30:00' },
    { id: 11, operator: '管理员', action: '修改系统设置', target: '', detail: '修改通知开关设置', ip: '192.168.1.100', createTime: '2026-05-23 15:00:00' },
    { id: 12, operator: '李明辉', action: '接单', target: 'WO20260522002', detail: '维修工程师接单', ip: '192.168.1.102', createTime: '2026-05-22 14:30:00' }
  ],
  evaluations: [
    { id: 1, orderId: 1, orderNo: 'WO20260520001', customerName: '湖南中建机械', equipmentName: 'SY485H 液压挖掘机', rating: 5, ratingRemark: '维修非常及时，技术专业，非常满意', evaluator: '张伟', evaluateTime: '2026-05-22 10:30:00' },
    { id: 2, orderId: 5, orderNo: 'WO20260524005', customerName: '湖南中建机械', equipmentName: 'SY215 液压挖掘机', rating: 5, ratingRemark: '巡检认真负责，发现潜在问题并给出建议', evaluator: '张伟', evaluateTime: '2026-05-24 14:00:00' },
    { id: 3, orderId: 7, orderNo: 'WO20260525007', customerName: '广东交通建设', equipmentName: 'SYM5440 混凝土泵车', rating: 4, ratingRemark: '维修质量好，但响应速度可以更快一些', evaluator: '陈明', evaluateTime: '2026-05-26 11:20:00' }
  ],
  qualityRecords: [
    { id: 1, recordNo: 'QC-202605001', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024001', customerName: '湖南中建机械', inspector: '王建国', inspectionDate: '2026-05-26', inspectionType: 3, inspectionTypeName: '完工检验', result: 1, resultName: '合格', defectCount: 0, remark: '维修后检验合格' },
    { id: 2, recordNo: 'QC-202605002', equipmentName: 'STC250 汽车起重机', equipmentCode: 'STC250-2024003', customerName: '江西赣南矿业', inspector: '李明辉', inspectionDate: '2026-05-25', inspectionType: 3, inspectionTypeName: '完工检验', result: 2, resultName: '待复检', defectCount: 2, remark: '伸缩油缸有轻微渗油，需复检' },
    { id: 3, recordNo: 'QC-202605003', equipmentName: 'SYM5230 混凝土泵车', equipmentCode: 'SYM5230-2024004', customerName: '广东交通建设', inspector: '周志远', inspectionDate: '2026-05-24', inspectionType: 1, inspectionTypeName: '来料检验', result: 1, resultName: '合格', defectCount: 0, remark: '新到备件检验合格' },
    { id: 4, recordNo: 'QC-202605004', equipmentName: 'SR360 旋挖钻机', equipmentCode: 'SR360-2024005', customerName: '四川能源开发', inspector: '陈思远', inspectionDate: '2026-05-23', inspectionType: 2, inspectionTypeName: '过程检验', result: 0, resultName: '不合格', defectCount: 3, remark: '主泵压力未达标准，需调整' },
    { id: 5, recordNo: 'QC-202605005', equipmentName: 'SY215 液压挖掘机', equipmentCode: 'SY215-2024007', customerName: '湖南中建机械', inspector: '王建国', inspectionDate: '2026-05-22', inspectionType: 4, inspectionTypeName: '出货检验', result: 1, resultName: '合格', defectCount: 0, remark: '巡检完成，设备状态良好' },
    { id: 6, recordNo: 'QC-202605006', equipmentName: 'SCC2000 履带起重机', equipmentCode: 'SCC2000-2024006', customerName: '河南振兴路桥', inspector: '周志远', inspectionDate: '2026-05-21', inspectionType: 2, inspectionTypeName: '过程检验', result: 1, resultName: '合格', defectCount: 1, remark: '电气接线需重新固定，已整改' },
    { id: 7, recordNo: 'QC-202605007', equipmentName: 'SYM5440 混凝土泵车', equipmentCode: 'SYM5440-2024008', customerName: '广东交通建设', inspector: '李明辉', inspectionDate: '2026-05-20', inspectionType: 3, inspectionTypeName: '完工检验', result: 1, resultName: '合格', defectCount: 0, remark: '泵送系统维修后检验合格' },
    { id: 8, recordNo: 'QC-202605008', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024002', customerName: '湖北路桥集团', inspector: '王建国', inspectionDate: '2026-05-19', inspectionType: 1, inspectionTypeName: '来料检验', result: 0, resultName: '不合格', defectCount: 2, remark: '部分密封件尺寸不符' }
  ],
  warehouseItems: [
    { id: 1, itemCode: 'WH-001', itemName: 'Q345B钢板(16mm)', category: '原材料', specification: '16mm*1500*6000', unit: '张', stockQuantity: 120, minStock: 30, maxStock: 300, location: 'A-01-01', lastInbound: '2026-05-20', lastOutbound: '2026-05-25', status: 1 },
    { id: 2, itemCode: 'WH-002', itemName: 'Q235钢管(108mm)', category: '原材料', specification: '108*4mm', unit: '根', stockQuantity: 200, minStock: 50, maxStock: 500, location: 'A-01-02', lastInbound: '2026-05-18', lastOutbound: '2026-05-24', status: 1 },
    { id: 3, itemCode: 'WH-003', itemName: '标准螺栓M20', category: '辅料', specification: 'M20*80', unit: '套', stockQuantity: 5000, minStock: 1000, maxStock: 10000, location: 'B-02-01', lastInbound: '2026-05-15', lastOutbound: '2026-05-26', status: 1 },
    { id: 4, itemCode: 'WH-004', itemName: '液压管路总成', category: '半成品', specification: 'SY485H-01', unit: '套', stockQuantity: 15, minStock: 10, maxStock: 50, location: 'C-01-01', lastInbound: '2026-05-22', lastOutbound: '2026-05-26', status: 1 },
    { id: 5, itemCode: 'WH-005', itemName: '电气控制柜', category: '半成品', specification: 'STC250-DQ', unit: '台', stockQuantity: 5, minStock: 5, maxStock: 20, location: 'C-01-02', lastInbound: '2026-05-10', lastOutbound: '2026-05-23', status: 1 },
    { id: 6, itemCode: 'WH-006', itemName: '挖掘机斗齿', category: '成品', specification: 'SY485H-DC', unit: '个', stockQuantity: 80, minStock: 30, maxStock: 200, location: 'D-01-01', lastInbound: '2026-05-19', lastOutbound: '2026-05-25', status: 1 },
    { id: 7, itemCode: 'WH-007', itemName: '液压油(208L)', category: '辅料', specification: 'L-HM 46', unit: '桶', stockQuantity: 8, minStock: 10, maxStock: 40, location: 'B-02-02', lastInbound: '2026-05-08', lastOutbound: '2026-05-24', status: 1 },
    { id: 8, itemCode: 'WH-008', itemName: '发动机机油(18L)', category: '辅料', specification: 'CI-4 15W-40', unit: '桶', stockQuantity: 3, minStock: 10, maxStock: 50, location: 'B-02-03', lastInbound: '2026-05-05', lastOutbound: '2026-05-22', status: 1 },
    { id: 9, itemCode: 'WH-009', itemName: '焊接机器人焊丝', category: '辅料', specification: 'ER50-6 1.2mm', unit: '卷', stockQuantity: 45, minStock: 15, maxStock: 100, location: 'B-01-01', lastInbound: '2026-05-21', lastOutbound: '2026-05-26', status: 1 },
    { id: 10, itemCode: 'WH-010', itemName: '回转支承总成', category: '半成品', specification: 'SY485H-HZ', unit: '套', stockQuantity: 3, minStock: 5, maxStock: 15, location: 'C-02-01', lastInbound: '2026-05-12', lastOutbound: '2026-05-20', status: 1 },
    { id: 11, itemCode: 'WH-011', itemName: '驾驶室总成', category: '成品', specification: 'SY485H-JS', unit: '台', stockQuantity: 2, minStock: 3, maxStock: 10, location: 'D-01-02', lastInbound: '2026-05-06', lastOutbound: '2026-05-18', status: 1 },
    { id: 12, itemCode: 'WH-012', itemName: '履带板总成', category: '半成品', specification: 'SY485H-LD', unit: '对', stockQuantity: 10, minStock: 5, maxStock: 25, location: 'C-02-02', lastInbound: '2026-05-16', lastOutbound: '2026-05-24', status: 1 },
    { id: 13, itemCode: 'WH-013', itemName: 'Q460C钢板(20mm)', category: '原材料', specification: '20mm*1500*6000', unit: '张', stockQuantity: 60, minStock: 20, maxStock: 200, location: 'A-01-03', lastInbound: '2026-05-23', lastOutbound: '2026-05-26', status: 1 },
    { id: 14, itemCode: 'WH-014', itemName: '传动轴总成', category: '成品', specification: 'STC250-CD', unit: '根', stockQuantity: 0, minStock: 5, maxStock: 15, location: 'D-02-01', lastInbound: '2026-04-28', lastOutbound: '2026-05-15', status: 1 },
    { id: 15, itemCode: 'WH-015', itemName: '耐磨衬板', category: '原材料', specification: 'NM400 12mm', unit: '张', stockQuantity: 35, minStock: 10, maxStock: 80, location: 'A-02-01', lastInbound: '2026-05-24', lastOutbound: '2026-05-26', status: 1 }
  ],
  warehouseTransactions: [
    { id: 1, transactionNo: 'IN-202605001', type: 1, typeName: '入库', itemCode: 'WH-001', itemName: 'Q345B钢板(16mm)', quantity: 50, operator: '管理员', transactionDate: '2026-05-26 09:30:00', remark: '采购入库' },
    { id: 2, transactionNo: 'OUT-202605001', type: 2, typeName: '出库', itemCode: 'WH-003', itemName: '标准螺栓M20', quantity: 200, operator: '王建国', transactionDate: '2026-05-26 08:15:00', remark: '维修领用' },
    { id: 3, transactionNo: 'IN-202605002', type: 1, typeName: '入库', itemCode: 'WH-013', itemName: 'Q460C钢板(20mm)', quantity: 30, operator: '管理员', transactionDate: '2026-05-25 14:00:00', remark: '采购入库' },
    { id: 4, transactionNo: 'OUT-202605002', type: 2, typeName: '出库', itemCode: 'WH-004', itemName: '液压管路总成', quantity: 2, operator: '周志远', transactionDate: '2026-05-25 10:30:00', remark: '维修更换' },
    { id: 5, transactionNo: 'IN-202605003', type: 1, typeName: '入库', itemCode: 'WH-009', itemName: '焊接机器人焊丝', quantity: 20, operator: '管理员', transactionDate: '2026-05-24 16:00:00', remark: '采购入库' },
    { id: 6, transactionNo: 'OUT-202605003', type: 2, typeName: '出库', itemCode: 'WH-006', itemName: '挖掘机斗齿', quantity: 15, operator: '王建国', transactionDate: '2026-05-24 09:00:00', remark: '客户更换' },
    { id: 7, transactionNo: 'IN-202605004', type: 1, typeName: '入库', itemCode: 'WH-015', itemName: '耐磨衬板', quantity: 20, operator: '管理员', transactionDate: '2026-05-23 11:00:00', remark: '采购入库' },
    { id: 8, transactionNo: 'OUT-202605004', type: 2, typeName: '出库', itemCode: 'WH-002', itemName: 'Q235钢管(108mm)', quantity: 30, operator: '陈思远', transactionDate: '2026-05-23 08:45:00', remark: '生产领用' },
    { id: 9, transactionNo: 'IN-202605005', type: 1, typeName: '入库', itemCode: 'WH-005', itemName: '电气控制柜', quantity: 3, operator: '管理员', transactionDate: '2026-05-22 15:30:00', remark: '生产完工入库' },
    { id: 10, transactionNo: 'OUT-202605005', type: 2, typeName: '出库', itemCode: 'WH-010', itemName: '回转支承总成', quantity: 1, operator: '周志远', transactionDate: '2026-05-22 10:00:00', remark: '维修更换' },
    { id: 11, transactionNo: 'IN-202605006', type: 1, typeName: '入库', itemCode: 'WH-012', itemName: '履带板总成', quantity: 5, operator: '管理员', transactionDate: '2026-05-21 13:00:00', remark: '采购入库' },
    { id: 12, transactionNo: 'OUT-202605006', type: 2, typeName: '出库', itemCode: 'WH-007', itemName: '液压油(208L)', quantity: 2, operator: '李明辉', transactionDate: '2026-05-21 09:15:00', remark: '保养更换' },
    { id: 13, transactionNo: 'IN-202605007', type: 1, typeName: '入库', itemCode: 'WH-011', itemName: '驾驶室总成', quantity: 1, operator: '管理员', transactionDate: '2026-05-20 16:45:00', remark: '生产完工入库' },
    { id: 14, transactionNo: 'OUT-202605007', type: 2, typeName: '出库', itemCode: 'WH-008', itemName: '发动机机油(18L)', quantity: 5, operator: '王建国', transactionDate: '2026-05-20 08:30:00', remark: '定期保养' },
    { id: 15, transactionNo: 'IN-202605008', type: 1, typeName: '入库', itemCode: 'WH-001', itemName: 'Q345B钢板(16mm)', quantity: 30, operator: '管理员', transactionDate: '2026-05-19 14:20:00', remark: '采购入库' }
  ],
  knowledgeArticles: [
    { id: 1, articleNo: 'KB-001', title: 'SY485H液压挖掘机发动机异响故障诊断与排除', category: '维修指南', summary: '详细讲解SY485H液压挖掘机发动机异响的常见原因、诊断步骤和维修方法', tags: 'SY485H,发动机,异响', author: '王建国', publishDate: '2026-05-20', viewCount: 156, status: 1 },
    { id: 2, articleNo: 'KB-002', title: 'STC250汽车起重机操作安全规范', category: '安全规范', summary: 'STC250汽车起重机操作过程中的安全注意事项和规范操作流程', tags: 'STC250,安全,操作', author: '赵刚强', publishDate: '2026-05-18', viewCount: 289, status: 1 },
    { id: 3, articleNo: 'KB-003', title: '混凝土泵车液压系统常见故障及处理方法', category: '故障案例', summary: '收集整理SYM系列混凝土泵车液压系统典型故障案例及处理方案', tags: '泵车,液压,故障', author: '周志远', publishDate: '2026-05-15', viewCount: 432, status: 1 },
    { id: 4, articleNo: 'KB-004', title: 'SR360旋挖钻机操作手册', category: '操作手册', summary: 'SR360旋挖钻机完整操作流程、参数设置和维护保养指南', tags: 'SR360,操作,手册', author: '陈思远', publishDate: '2026-05-12', viewCount: 98, status: 1 },
    { id: 5, articleNo: 'KB-005', title: '液压系统维修基础培训教材', category: '培训资料', summary: '液压系统原理、常见故障分析及维修实操培训教材', tags: '液压,培训,维修', author: '王建国', publishDate: '2026-05-10', viewCount: 367, status: 1 },
    { id: 6, articleNo: 'KB-006', title: 'SCC2000履带起重机年度保养规范', category: '维修指南', summary: 'SCC2000履带起重机年度保养项目、标准和周期说明', tags: 'SCC2000,保养,规范', author: '李明辉', publishDate: '2026-05-08', viewCount: 124, status: 1 },
    { id: 7, articleNo: 'KB-007', title: '电气系统故障快速诊断指南', category: '培训资料', summary: '工程机械电气系统常见故障的快速诊断方法和工具使用指南', tags: '电气,诊断,培训', author: '李明辉', publishDate: '2026-05-05', viewCount: 203, status: 1 },
    { id: 8, articleNo: 'KB-008', title: '施工现场安全巡查标准', category: '安全规范', summary: '施工现场安全巡查的项目、频次和记录标准', tags: '安全,巡查,标准', author: '赵刚强', publishDate: '2026-05-03', viewCount: 178, status: 1 },
    { id: 9, articleNo: 'KB-009', title: 'SYM5440泵车臂架系统故障案例汇编', category: '故障案例', summary: 'SYM5440混凝土泵车臂架系统典型故障案例及维修经验总结', tags: 'SYM5440,臂架,故障', author: '周志远', publishDate: '2026-04-28', viewCount: 87, status: 1 },
    { id: 10, articleNo: 'KB-010', title: '履带起重机拆装运输操作规程', category: '操作手册', summary: 'SCC系列履带起重机的拆卸、运输和组装操作规程', tags: '起重机,拆装,运输', author: '陈思远', publishDate: '2026-04-25', viewCount: 145, status: 0 }
  ],
  contracts: [
    { id: 1, contractNo: 'CT-2026001', contractName: '湖南中建机械年度维保合同', customerName: '湖南中建机械', contractType: 1, contractTypeName: '维保合同', startDate: '2026-01-01', endDate: '2026-12-31', contractAmount: 580000, status: 1, statusName: '执行中', signDate: '2025-12-15', responsiblePerson: '赵刚强', remark: '覆盖全部设备' },
    { id: 2, contractNo: 'CT-2026002', contractName: '湖北路桥集团设备维修合同', customerName: '湖北路桥集团', contractType: 2, contractTypeName: '维修合同', startDate: '2026-03-01', endDate: '2026-08-31', contractAmount: 320000, status: 1, statusName: '执行中', signDate: '2026-02-28', responsiblePerson: '王建国', remark: '' },
    { id: 3, contractNo: 'CT-2026003', contractName: '江西赣南矿业设备租赁合同', customerName: '江西赣南矿业', contractType: 3, contractTypeName: '租赁合同', startDate: '2026-04-01', endDate: '2026-09-30', contractAmount: 450000, status: 1, statusName: '执行中', signDate: '2026-03-25', responsiblePerson: '赵刚强', remark: '含操作手' },
    { id: 4, contractNo: 'CT-2026004', contractName: '广东交通建设技术服务合同', customerName: '广东交通建设', contractType: 4, contractTypeName: '技术服务', startDate: '2026-02-01', endDate: '2026-07-31', contractAmount: 280000, status: 1, statusName: '执行中', signDate: '2026-01-20', responsiblePerson: '李明辉', remark: '技术支持+培训' },
    { id: 5, contractNo: 'CT-2026005', contractName: '四川能源开发设备维保合同', customerName: '四川能源开发', contractType: 1, contractTypeName: '维保合同', startDate: '2025-07-01', endDate: '2026-06-30', contractAmount: 360000, status: 2, statusName: '已到期', signDate: '2025-06-15', responsiblePerson: '赵刚强', remark: '待续签' },
    { id: 6, contractNo: 'CT-2026006', contractName: '河南振兴路桥新机安装合同', customerName: '河南振兴路桥', contractType: 2, contractTypeName: '维修合同', startDate: '2026-05-01', endDate: '2026-06-15', contractAmount: 185000, status: 0, statusName: '待签署', signDate: null, responsiblePerson: '王建国', remark: '新签合同待审批' },
    { id: 7, contractNo: 'CT-2026007', contractName: '陕西建工集团年度技术服务', customerName: '陕西建工集团', contractType: 4, contractTypeName: '技术服务', startDate: '2026-04-15', endDate: '2027-04-14', contractAmount: 420000, status: 1, statusName: '执行中', signDate: '2026-04-10', responsiblePerson: '周志远', remark: '' },
    { id: 8, contractNo: 'CT-2026008', contractName: '云南水利工程设备租赁合同', customerName: '云南水利工程', contractType: 3, contractTypeName: '租赁合同', startDate: '2026-05-15', endDate: '2026-08-15', contractAmount: 220000, status: 0, statusName: '待签署', signDate: null, responsiblePerson: '赵刚强', remark: '合同条款确认中' }
  ],
  inspectionTasks: [
    { id: 1, taskNo: 'IT-202605001', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024001', customerName: '湖南中建机械', taskType: 1, taskTypeName: '日常巡检', assignee: '王建国', planDate: '2026-05-26', actualDate: '2026-05-26', status: 2, statusName: '已完成', result: 1, resultName: '正常', description: '设备运行正常，各项参数达标' },
    { id: 2, taskNo: 'IT-202605002', equipmentName: 'STC250 汽车起重机', equipmentCode: 'STC250-2024003', customerName: '江西赣南矿业', taskType: 1, taskTypeName: '日常巡检', assignee: '李明辉', planDate: '2026-05-26', actualDate: null, status: 1, statusName: '执行中', result: null, resultName: '', description: '正在巡检中' },
    { id: 3, taskNo: 'IT-202605003', equipmentName: 'SYM5230 混凝土泵车', equipmentCode: 'SYM5230-2024004', customerName: '广东交通建设', taskType: 2, taskTypeName: '专项检查', assignee: '周志远', planDate: '2026-05-27', actualDate: null, status: 0, statusName: '待执行', result: null, resultName: '', description: '液压系统专项检查' },
    { id: 4, taskNo: 'IT-202605004', equipmentName: 'SR360 旋挖钻机', equipmentCode: 'SR360-2024005', customerName: '四川能源开发', taskType: 3, taskTypeName: '安全排查', assignee: '赵刚强', planDate: '2026-05-25', actualDate: '2026-05-26', status: 2, statusName: '已完成', result: 0, resultName: '异常', description: '发现主泵压力异常，已开具维修工单' },
    { id: 5, taskNo: 'IT-202605005', equipmentName: 'SY215 液压挖掘机', equipmentCode: 'SY215-2024007', customerName: '湖南中建机械', taskType: 1, taskTypeName: '日常巡检', assignee: '王建国', planDate: '2026-05-25', actualDate: '2026-05-25', status: 2, statusName: '已完成', result: 1, resultName: '正常', description: '季度巡检完成' },
    { id: 6, taskNo: 'IT-202605006', equipmentName: 'SCC2000 履带起重机', equipmentCode: 'SCC2000-2024006', customerName: '河南振兴路桥', taskType: 2, taskTypeName: '专项检查', assignee: '陈思远', planDate: '2026-05-28', actualDate: null, status: 0, statusName: '待执行', result: null, resultName: '', description: '安装调试后专项检查' },
    { id: 7, taskNo: 'IT-202605007', equipmentName: 'SYM5440 混凝土泵车', equipmentCode: 'SYM5440-2024008', customerName: '广东交通建设', taskType: 4, taskTypeName: '质量检查', assignee: '李明辉', planDate: '2026-05-24', actualDate: null, status: 3, statusName: '已延期', result: null, resultName: '', description: '维修后质量复查' },
    { id: 8, taskNo: 'IT-202605008', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024002', customerName: '湖北路桥集团', taskType: 3, taskTypeName: '安全排查', assignee: '周志远', planDate: '2026-05-29', actualDate: null, status: 0, statusName: '待执行', result: null, resultName: '', description: '季度安全排查' },
    { id: 9, taskNo: 'IT-202605009', equipmentName: 'STC800 汽车起重机', equipmentCode: 'STC800-2024009', customerName: '陕西建工集团', taskType: 1, taskTypeName: '日常巡检', assignee: '陈思远', planDate: '2026-05-26', actualDate: '2026-05-26', status: 1, statusName: '执行中', result: null, resultName: '', description: '日常运行巡检' },
    { id: 10, taskNo: 'IT-202605010', equipmentName: 'SY485H 液压挖掘机', equipmentCode: 'SY485H-2024001', customerName: '湖南中建机械', taskType: 4, taskTypeName: '质量检查', assignee: '王建国', planDate: '2026-05-22', actualDate: '2026-05-22', status: 2, statusName: '已完成', result: 1, resultName: '正常', description: '维修完工质量检查' }
  ],
  trainingRecords: [
    { id: 1, recordNo: 'TR-2026001', courseName: 'SY485H液压系统维修技术培训', courseType: 1, courseTypeName: '技术培训', trainer: '王建国', traineeCount: 12, traineeNames: '李明辉,周志远,陈思远...', startDate: '2026-05-20', endDate: '2026-05-22', duration: 24, status: 2, statusName: '已完成', examPassRate: 92 },
    { id: 2, recordNo: 'TR-2026002', courseName: '施工现场安全操作规范培训', courseType: 2, courseTypeName: '安全培训', trainer: '赵刚强', traineeCount: 25, traineeNames: '售后服务部全员...', startDate: '2026-05-25', endDate: '2026-05-26', duration: 8, status: 2, statusName: '已完成', examPassRate: 88 },
    { id: 3, recordNo: 'TR-2026003', courseName: '服务团队管理能力提升', courseType: 3, courseTypeName: '管理培训', trainer: '外部讲师', traineeCount: 8, traineeNames: '赵刚强,王建国...', startDate: '2026-06-01', endDate: '2026-06-03', duration: 16, status: 0, statusName: '计划中', examPassRate: 0 },
    { id: 4, recordNo: 'TR-2026004', courseName: 'STC系列起重机新产品培训', courseType: 4, courseTypeName: '产品培训', trainer: '研发部讲师', traineeCount: 15, traineeNames: '维修工程师团队...', startDate: '2026-05-28', endDate: '2026-05-29', duration: 12, status: 1, statusName: '进行中', examPassRate: 0 },
    { id: 5, recordNo: 'TR-2026005', courseName: '电气系统故障诊断进阶培训', courseType: 1, courseTypeName: '技术培训', trainer: '李明辉', traineeCount: 6, traineeNames: '周志远,陈思远...', startDate: '2026-05-15', endDate: '2026-05-16', duration: 12, status: 2, statusName: '已完成', examPassRate: 95 },
    { id: 6, recordNo: 'TR-2026006', courseName: '高空作业安全培训', courseType: 2, courseTypeName: '安全培训', trainer: '安全专员', traineeCount: 20, traineeNames: '全体外勤人员...', startDate: '2026-06-05', endDate: '2026-06-05', duration: 4, status: 0, statusName: '计划中', examPassRate: 0 },
    { id: 7, recordNo: 'TR-2026007', courseName: '混凝土泵车操作技能培训', courseType: 4, courseTypeName: '产品培训', trainer: '周志远', traineeCount: 10, traineeNames: '新入职工程师...', startDate: '2026-05-10', endDate: '2026-05-11', duration: 8, status: 2, statusName: '已完成', examPassRate: 85 },
    { id: 8, recordNo: 'TR-2026008', courseName: '质量管理体系内部审核培训', courseType: 3, courseTypeName: '管理培训', trainer: '外部讲师', traineeCount: 5, traineeNames: '管理团队...', startDate: '2026-06-10', endDate: '2026-06-12', duration: 20, status: 0, statusName: '计划中', examPassRate: 0 }
  ],
  financialRecords: [
    { id: 1, settlementNo: 'SET-202605001', orderNo: 'WO20260520001', customerName: '湖南中建机械', equipmentName: 'SY485H 液压挖掘机', serviceType: 1, serviceTypeName: '维修', serviceFee: 3500, partsFee: 6800, totalAmount: 10300, billingDate: '2026-05-22', status: 2, statusName: '已收款', paymentMethod: '转账' },
    { id: 2, settlementNo: 'SET-202605002', orderNo: 'WO20260524005', customerName: '湖南中建机械', equipmentName: 'SY215 液压挖掘机', serviceType: 4, serviceTypeName: '巡检', serviceFee: 1500, partsFee: 0, totalAmount: 1500, billingDate: '2026-05-24', status: 3, statusName: '已核销', paymentMethod: '转账' },
    { id: 3, settlementNo: 'SET-202605003', orderNo: 'WO20260525007', customerName: '广东交通建设', equipmentName: 'SYM5440 混凝土泵车', serviceType: 1, serviceTypeName: '维修', serviceFee: 4500, partsFee: 3500, totalAmount: 8000, billingDate: null, status: 0, statusName: '待开票', paymentMethod: '' },
    { id: 4, settlementNo: 'SET-202605004', orderNo: 'WO20260522002', customerName: '江西赣南矿业', equipmentName: 'STC250 汽车起重机', serviceType: 1, serviceTypeName: '维修', serviceFee: 2800, partsFee: 1200, totalAmount: 4000, billingDate: null, status: 0, statusName: '待开票', paymentMethod: '' },
    { id: 5, settlementNo: 'SET-202605005', orderNo: 'WO20260525006', customerName: '河南振兴路桥', equipmentName: 'SCC2000 履带起重机', serviceType: 3, serviceTypeName: '安装', serviceFee: 8000, partsFee: 0, totalAmount: 8000, billingDate: '2026-05-26', status: 1, statusName: '已开票', paymentMethod: '支票' },
    { id: 6, settlementNo: 'SET-202605006', orderNo: 'WO20260524004', customerName: '四川能源开发', equipmentName: 'SR360 旋挖钻机', serviceType: 1, serviceTypeName: '维修', serviceFee: 3200, partsFee: 4500, totalAmount: 7700, billingDate: null, status: 0, statusName: '待开票', paymentMethod: '' },
    { id: 7, settlementNo: 'SET-202605007', orderNo: 'WO20260523003', customerName: '广东交通建设', equipmentName: 'SYM5230 混凝土泵车', serviceType: 2, serviceTypeName: '保养', serviceFee: 1800, partsFee: 2500, totalAmount: 4300, billingDate: '2026-05-25', status: 2, statusName: '已收款', paymentMethod: '现金' },
    { id: 8, settlementNo: 'SET-202605008', orderNo: 'WO20260526008', customerName: '湖北路桥集团', equipmentName: 'SY485H 液压挖掘机', serviceType: 1, serviceTypeName: '维修', serviceFee: 4000, partsFee: 5500, totalAmount: 9500, billingDate: null, status: 0, statusName: '待开票', paymentMethod: '' },
    { id: 9, settlementNo: 'SET-202605009', orderNo: 'WO20260520001', customerName: '湖南中建机械', equipmentName: 'SY485H 液压挖掘机', serviceType: 2, serviceTypeName: '保养', serviceFee: 1200, partsFee: 1800, totalAmount: 3000, billingDate: '2026-05-20', status: 3, statusName: '已核销', paymentMethod: '转账' },
    { id: 10, settlementNo: 'SET-202605010', orderNo: 'WO20260524005', customerName: '湖南中建机械', equipmentName: 'SY215 液压挖掘机', serviceType: 2, serviceTypeName: '保养', serviceFee: 800, partsFee: 1600, totalAmount: 2400, billingDate: '2026-05-23', status: 2, statusName: '已收款', paymentMethod: '转账' }
  ],
  nextId: 200
}

function page(list, params) {
  const pn = Math.max(1, parseInt(params?.pageNum) || 1)
  const ps = Math.max(1, parseInt(params?.pageSize) || 10)
  const start = (pn - 1) * ps
  return { list: list.slice(start, start + ps), total: list.length, pageNum: pn, pageSize: ps }
}

function mock(config) {
  const u = config.url, m = config.method?.toLowerCase(), p = config.params || {}
  const data = config.data ? (typeof config.data === 'string' ? JSON.parse(config.data) : config.data) : {}
  let result

  // auth login
  if (u === '/api/v1/auth/login' && m === 'post') {
    const { username, password } = data
    if (username === 'admin' && password === 'admin123') {
      result = { ..._.user }
    } else if (username === 'engineer' && password === '123456') {
      result = { ..._.user, id: 2, username: 'engineer', realName: '工程师', role: 'engineer', token: 'mock-token-789012' }
    } else {
      return { data: { code: 401, message: '用户名或密码错误', data: null }, status: 200, statusText: 'OK', headers: {}, config }
    }
  }
  // auth info
  else if (u === '/api/v1/auth/info' && m === 'get') {
    result = { ..._.user }
  }
  // dashboard stats
  else if (u === '/api/v1/dashboard/stats' && m === 'get') {
    result = {
      pendingOrders: 3, monthlyOrders: 86, completionRate: '94%', alerts: 3,
      pendingOrdersTrend: 12, monthlyTrend: 8, completionTrend: 2, alertTrend: -1,
      todayOrders: 5, todayCompleted: 3, onlineUsers: 3, systemUptime: '15天'
    }
  }
  // dashboard trend
  else if (u === '/api/v1/dashboard/trend' && m === 'get') {
    result = [
      { day: '05-20', count: 8 }, { day: '05-21', count: 12 }, { day: '05-22', count: 6 },
      { day: '05-23', count: 15 }, { day: '05-24', count: 10 }, { day: '05-25', count: 14 },
      { day: '05-26', count: 9 }
    ]
  }
  // dashboard todo
  else if (u === '/api/v1/dashboard/todo' && m === 'get') {
    result = [
      { text: '工单 WO20260526008 待派单', tag: '紧急', type: 'danger' },
      { text: 'SY485H挖掘机保养到期', tag: '保养', type: 'warning' },
      { text: '长沙工厂本月服务报告待审核', tag: '审核', type: 'info' }
    ]
  }
  // user profile update
  else if (u === '/api/v1/user/profile' && m === 'put') {
    Object.assign(_.user, data); result = _.user
  }
  // equipment detail
  else if (/^\/api\/v1\/equipment\/\d+$/.test(u) && m === 'get') {
    result = _.equipment.find(e => e.id === parseInt(u.split('/').pop())) || null
  }
  // customer detail
  else if (/^\/api\/v1\/customer\/\d+$/.test(u) && m === 'get') {
    result = _.customers.find(c => c.id === parseInt(u.split('/').pop())) || null
  }
  // maintenance plan page
  else if (u === '/api/v1/maintenance-plan/page' && m === 'get') {
    let list = [..._.maintenancePlans]
    if (p.planType !== '' && p.planType != null) list = list.filter(x => x.planType === Number(p.planType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    if (p.equipmentName) list = list.filter(x => x.equipmentName.includes(p.equipmentName))
    result = page(list, p)
  }
  else if (u === '/api/v1/maintenance-plan' && m === 'post') { const item = { id: ++_.nextId, planNo: 'MP-' + Date.now(), ...data }; _.maintenancePlans.unshift(item); result = item }
  else if (u === '/api/v1/maintenance-plan' && m === 'put') { const idx = _.maintenancePlans.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.maintenancePlans[idx], data); result = null }
  else if (/^\/api\/v1\/maintenance-plan\/\d+$/.test(u) && m === 'delete') { _.maintenancePlans = _.maintenancePlans.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // operation log page
  else if (u === '/api/v1/operation-log/page' && m === 'get') {
    let list = [..._.operationLogs]
    if (p.action) list = list.filter(x => x.action.includes(p.action))
    if (p.operator) list = list.filter(x => x.operator.includes(p.operator))
    result = page(list, p)
  }
  // evaluation page
  else if (u === '/api/v1/evaluation/page' && m === 'get') {
    let list = [..._.evaluations]
    if (p.orderNo) list = list.filter(x => x.orderNo.includes(p.orderNo))
    result = page(list, p)
  }
  else if (u === '/api/v1/evaluation' && m === 'post') {
    const item = { id: ++_.nextId, evaluator: _.user.realName, evaluateTime: new Date().toLocaleString('zh-CN', { hour12: false }), ...data }
    _.evaluations.unshift(item); result = item
  }
  else if (/^\/api\/v1\/evaluation\/\d+$/.test(u) && m === 'get') {
    const oid = parseInt(u.split('/').pop());
    result = _.evaluations.find(e => e.orderId === oid) || null
  }

  // quality-record page
  else if (u === '/api/v1/quality-record/page' && m === 'get') {
    let list = [..._.qualityRecords]
    if (p.inspectionType !== '' && p.inspectionType != null) list = list.filter(x => x.inspectionType === Number(p.inspectionType))
    if (p.result !== '' && p.result != null) list = list.filter(x => x.result === Number(p.result))
    if (p.equipmentName) list = list.filter(x => x.equipmentName.includes(p.equipmentName))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/quality-record\/\d+$/.test(u) && m === 'get') { result = _.qualityRecords.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/quality-record' && m === 'post') { const item = { id: ++_.nextId, recordNo: 'QC-' + Date.now(), ...data }; _.qualityRecords.unshift(item); result = item }
  else if (u === '/api/v1/quality-record' && m === 'put') { const idx = _.qualityRecords.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.qualityRecords[idx], data); result = null }
  // warehouse-item page
  else if (u === '/api/v1/warehouse-item/page' && m === 'get') {
    let list = [..._.warehouseItems]
    if (p.category) list = list.filter(x => x.category.includes(p.category))
    if (p.itemName) list = list.filter(x => x.itemName.includes(p.itemName))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/warehouse-item\/\d+$/.test(u) && m === 'get') { result = _.warehouseItems.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/warehouse-item' && m === 'post') { const item = { id: ++_.nextId, ...data }; _.warehouseItems.push(item); result = item }
  else if (u === '/api/v1/warehouse-item' && m === 'put') { const idx = _.warehouseItems.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.warehouseItems[idx], data); result = null }
  else if (/^\/api\/v1\/warehouse-item\/\d+$/.test(u) && m === 'delete') { _.warehouseItems = _.warehouseItems.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // warehouse-transaction page
  else if (u === '/api/v1/warehouse-transaction/page' && m === 'get') {
    let list = [..._.warehouseTransactions]
    if (p.type !== '' && p.type != null) list = list.filter(x => x.type === Number(p.type))
    if (p.itemName) list = list.filter(x => x.itemName.includes(p.itemName))
    result = page(list, p)
  }
  else if (u === '/api/v1/warehouse-transaction' && m === 'post') { const item = { id: ++_.nextId, transactionNo: (data.type === 1 ? 'IN-' : 'OUT-') + Date.now(), ...data }; _.warehouseTransactions.unshift(item); result = item }
  // knowledge-article page
  else if (u === '/api/v1/knowledge-article/page' && m === 'get') {
    let list = [..._.knowledgeArticles]
    if (p.category) list = list.filter(x => x.category === p.category)
    if (p.title) list = list.filter(x => x.title.includes(p.title))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/knowledge-article\/\d+$/.test(u) && m === 'get') { result = _.knowledgeArticles.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/knowledge-article' && m === 'post') { const item = { id: ++_.nextId, articleNo: 'KB-' + String(_.nextId).padStart(3,'0'), ...data }; _.knowledgeArticles.unshift(item); result = item }
  else if (u === '/api/v1/knowledge-article' && m === 'put') { const idx = _.knowledgeArticles.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.knowledgeArticles[idx], data); result = null }
  else if (/^\/api\/v1\/knowledge-article\/\d+$/.test(u) && m === 'delete') { _.knowledgeArticles = _.knowledgeArticles.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // contract page
  else if (u === '/api/v1/contract/page' && m === 'get') {
    let list = [..._.contracts]
    if (p.contractType !== '' && p.contractType != null) list = list.filter(x => x.contractType === Number(p.contractType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    if (p.customerName) list = list.filter(x => x.customerName.includes(p.customerName))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/contract\/\d+$/.test(u) && m === 'get') { result = _.contracts.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/contract' && m === 'post') { const item = { id: ++_.nextId, contractNo: 'CT-' + Date.now(), ...data }; _.contracts.unshift(item); result = item }
  else if (u === '/api/v1/contract' && m === 'put') { const idx = _.contracts.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.contracts[idx], data); result = null }
  else if (/^\/api\/v1\/contract\/\d+$/.test(u) && m === 'delete') { _.contracts = _.contracts.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // inspection-task page
  else if (u === '/api/v1/inspection-task/page' && m === 'get') {
    let list = [..._.inspectionTasks]
    if (p.taskType !== '' && p.taskType != null) list = list.filter(x => x.taskType === Number(p.taskType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    if (p.equipmentName) list = list.filter(x => x.equipmentName.includes(p.equipmentName))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/inspection-task\/\d+$/.test(u) && m === 'get') { result = _.inspectionTasks.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/inspection-task' && m === 'post') { const item = { id: ++_.nextId, taskNo: 'IT-' + Date.now(), ...data }; _.inspectionTasks.unshift(item); result = item }
  else if (u === '/api/v1/inspection-task' && m === 'put') { const idx = _.inspectionTasks.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.inspectionTasks[idx], data); result = null }
  else if (/^\/api\/v1\/inspection-task\/\d+$/.test(u) && m === 'delete') { _.inspectionTasks = _.inspectionTasks.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // training-record page
  else if (u === '/api/v1/training-record/page' && m === 'get') {
    let list = [..._.trainingRecords]
    if (p.courseType !== '' && p.courseType != null) list = list.filter(x => x.courseType === Number(p.courseType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    result = page(list, p)
  }
  else if (u === '/api/v1/training-record' && m === 'post') { const item = { id: ++_.nextId, recordNo: 'TR-' + Date.now(), ...data }; _.trainingRecords.unshift(item); result = item }
  else if (u === '/api/v1/training-record' && m === 'put') { const idx = _.trainingRecords.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.trainingRecords[idx], data); result = null }
  // financial-record page
  else if (u === '/api/v1/financial-record/page' && m === 'get') {
    let list = [..._.financialRecords]
    if (p.serviceType !== '' && p.serviceType != null) list = list.filter(x => x.serviceType === Number(p.serviceType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    if (p.customerName) list = list.filter(x => x.customerName.includes(p.customerName))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/financial-record\/\d+$/.test(u) && m === 'get') { result = _.financialRecords.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/financial-record' && m === 'put') { const idx = _.financialRecords.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.financialRecords[idx], data); result = null }

  // work-order page
  else if (u === '/api/v1/work-order/page' && m === 'get') {
    let list = [..._.workOrders]
    if (p.orderStatus !== '' && p.orderStatus != null) list = list.filter(w => w.orderStatus === Number(p.orderStatus))
    if (p.equipmentCode) list = list.filter(w => w.equipmentCode.includes(p.equipmentCode))
    result = page(list, p)
  }
  // work-order detail
  else if (/^\/api\/v1\/work-order\/\d+$/.test(u) && m === 'get') {
    const id = parseInt(u.split('/').pop())
    result = _.workOrders.find(w => w.id === id) || null
  }
  // work-order create
  else if (u === '/api/v1/work-order' && m === 'post') {
    const id = ++_.nextId
    const item = { id, orderNo: 'WO' + Date.now() + String(id).padStart(2, '0'), orderTypeName: '维修', orderStatus: 0, orderStatusName: '待派单', ...data, assigneeName: null, assignTime: null, arriveTime: null, finishTime: null, handlingDetail: null, createTime: new Date().toLocaleString('zh-CN', { hour12: false }) }
    _.workOrders.unshift(item); result = item
  }
  // work-order update
  else if (u === '/api/v1/work-order' && m === 'put') {
    const idx = _.workOrders.findIndex(w => w.id === data.id); if (idx >= 0) Object.assign(_.workOrders[idx], data); result = null
  }
  // work-order status
  else if (/^\/api\/v1\/work-order\/\d+\/status$/.test(u) && m === 'put') {
    const id = parseInt(u.split('/')[4]); const item = _.workOrders.find(w => w.id === id)
    if (item) { item.orderStatus = Number(p.newStatus); item.orderStatusName = { 0: '待派单', 1: '待接单', 2: '处理中', 3: '待验收', 4: '已完成', 5: '已关闭' }[item.orderStatus] || '未知'; if (item.orderStatus === 4) item.finishTime = new Date().toLocaleString('zh-CN', { hour12: false }) }
    result = null
  }
  // work-order dispatch
  else if (/^\/api\/v1\/work-order\/\d+\/dispatch$/.test(u) && m === 'put') {
    const id = parseInt(u.split('/')[4]); const item = _.workOrders.find(w => w.id === id)
    if (item) { item.orderStatus = 1; item.orderStatusName = '待接单'; item.assigneeName = '王工'; item.assignTime = new Date().toLocaleString('zh-CN', { hour12: false }) }
    result = null
  }
  // work-order delete
  else if (/^\/api\/v1\/work-order\/\d+$/.test(u) && m === 'delete') {
    const id = parseInt(u.split('/').pop()); _.workOrders = _.workOrders.filter(w => w.id !== id); result = null
  }
  // equipment list
  else if (u === '/api/v1/equipment/list' && m === 'get') {
    let list = [..._.equipment]
    if (p.equipmentName) list = list.filter(e => e.equipmentName.includes(p.equipmentName))
    if (p.equipmentCode) list = list.filter(e => e.equipmentCode.includes(p.equipmentCode))
    if (p.status !== '' && p.status != null) list = list.filter(e => e.status === Number(p.status))
    result = page(list, p)
  }
  else if (u === '/api/v1/equipment' && m === 'post') { const item = { id: ++_.nextId, ...data }; _.equipment.push(item); result = item }
  // customer list
  else if (u === '/api/v1/customer/list' && m === 'get') {
    let list = [..._.customers]
    if (p.customerName) list = list.filter(c => c.customerName.includes(p.customerName))
    if (p.serviceLevel !== '' && p.serviceLevel != null) list = list.filter(c => c.serviceLevel === Number(p.serviceLevel))
    if (p.city) list = list.filter(c => c.city.includes(p.city))
    result = page(list, p)
  }
  else if (u === '/api/v1/customer' && m === 'post') { const item = { id: ++_.nextId, ...data }; _.customers.push(item); result = item }
  // spare-part page
  else if (u === '/api/v1/spare-part/page' && m === 'get') {
    let list = [..._.spareParts]
    if (p.partName) list = list.filter(x => x.partName.includes(p.partName))
    if (p.partCode) list = list.filter(x => x.partCode.includes(p.partCode))
    if (p.stockStatus !== '' && p.stockStatus != null) { const s = Number(p.stockStatus); list = list.filter(x => s === 1 ? x.stockQuantity > x.minStock : s === 2 ? x.stockQuantity > 0 && x.stockQuantity <= x.minStock : x.stockQuantity <= 0) }
    result = page(list, p)
  }
  else if (/^\/api\/v1\/spare-part\/\d+$/.test(u) && m === 'get') { result = _.spareParts.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/spare-part' && m === 'post') { const item = { id: ++_.nextId, ...data }; _.spareParts.push(item); result = item }
  else if (u === '/api/v1/spare-part' && m === 'put') { const idx = _.spareParts.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.spareParts[idx], data); result = null }
  else if (/^\/api\/v1\/spare-part\/\d+$/.test(u) && m === 'delete') { _.spareParts = _.spareParts.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // report page
  else if (u === '/api/v1/report/page' && m === 'get') {
    let list = [..._.reports]
    if (p.reportNo) list = list.filter(r => r.reportNo.includes(p.reportNo))
    if (p.orderNo) list = list.filter(r => r.orderNo.includes(p.orderNo))
    if (p.reportType !== '' && p.reportType != null) list = list.filter(r => r.reportType === Number(p.reportType))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/report\/\d+$/.test(u) && m === 'get') { result = _.reports.find(r => r.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/report' && m === 'post') { const id = ++_.nextId; const item = { id, reportNo: 'SR-' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + String(id).padStart(2, '0'), createByName: '管理员', createTime: new Date().toLocaleString('zh-CN', { hour12: false }), ...data }; _.reports.unshift(item); result = item }
  else if (/^\/api\/v1\/report\/\d+$/.test(u) && m === 'delete') { _.reports = _.reports.filter(r => r.id !== parseInt(u.split('/').pop())); result = null }
  // notification page
  else if (u === '/api/v1/notification/page' && m === 'get') { result = page(_.notifications, p) }
  else if (/^\/api\/v1\/notification\/\d+\/read$/.test(u) && m === 'put') { const item = _.notifications.find(n => n.id === parseInt(u.split('/')[4])); if (item) item.isRead = true; result = null }
  else if (u === '/api/v1/notification/read-all' && m === 'put') { _.notifications.forEach(n => { n.isRead = true }); result = null }
  else if (/^\/api\/v1\/notification\/\d+$/.test(u) && m === 'delete') { _.notifications = _.notifications.filter(n => n.id !== parseInt(u.split('/').pop())); result = null }
  // employee page
  else if (u === '/api/v1/employee/page' && m === 'get') {
    let list = [..._.employees]
    if (p.employeeName) list = list.filter(e => e.employeeName.includes(p.employeeName))
    if (p.employeeNo) list = list.filter(e => e.employeeNo.includes(p.employeeNo))
    if (p.position) list = list.filter(e => e.position === p.position)
    if (p.status !== '' && p.status != null) list = list.filter(e => e.status === Number(p.status))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/employee\/\d+$/.test(u) && m === 'get') { result = _.employees.find(e => e.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/employee' && m === 'post') { const item = { id: ++_.nextId, ...data }; _.employees.push(item); result = item }
  else if (u === '/api/v1/employee' && m === 'put') { const idx = _.employees.findIndex(e => e.id === data.id); if (idx >= 0) Object.assign(_.employees[idx], data); result = null }
  else if (/^\/api\/v1\/employee\/\d+$/.test(u) && m === 'delete') { _.employees = _.employees.filter(e => e.id !== parseInt(u.split('/').pop())); result = null }

  // === ALIASES for page components that use different URL paths ===
  // knowledge aliases
  else if (u === '/api/v1/knowledge/page' && m === 'get') {
    let list = [..._.knowledgeArticles]
    if (p.category) list = list.filter(x => x.category === p.category)
    if (p.title) list = list.filter(x => x.title.includes(p.title))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/knowledge\/\d+$/.test(u) && m === 'get') { result = _.knowledgeArticles.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/knowledge' && m === 'post') { const item = { id: ++_.nextId, articleNo: 'KB-' + String(_.nextId).padStart(3,'0'), ...data }; _.knowledgeArticles.unshift(item); result = item }
  else if (u === '/api/v1/knowledge' && m === 'put') { const idx = _.knowledgeArticles.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.knowledgeArticles[idx], data); result = null }
  else if (/^\/api\/v1\/knowledge\/\d+$/.test(u) && m === 'delete') { _.knowledgeArticles = _.knowledgeArticles.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // quality aliases
  else if (u === '/api/v1/quality/page' && m === 'get') {
    let list = [..._.qualityRecords]
    if (p.inspectionType !== '' && p.inspectionType != null) list = list.filter(x => x.inspectionType === Number(p.inspectionType))
    if (p.result !== '' && p.result != null) list = list.filter(x => x.result === Number(p.result))
    if (p.equipmentName) list = list.filter(x => x.equipmentName.includes(p.equipmentName))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/quality\/\d+$/.test(u) && m === 'get') { result = _.qualityRecords.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/quality' && m === 'post') { const item = { id: ++_.nextId, recordNo: 'QC-' + Date.now(), ...data }; _.qualityRecords.unshift(item); result = item }
  else if (u === '/api/v1/quality' && m === 'put') { const idx = _.qualityRecords.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.qualityRecords[idx], data); result = null }
  else if (/^\/api\/v1\/quality\/\d+$/.test(u) && m === 'delete') { _.qualityRecords = _.qualityRecords.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // warehouse items aliases
  else if (u === '/api/v1/warehouse/items/page' && m === 'get') {
    let list = [..._.warehouseItems]
    if (p.category) list = list.filter(x => x.category.includes(p.category))
    if (p.itemName) list = list.filter(x => x.itemName.includes(p.itemName))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    result = page(list, p)
  }
  else if (/^\/api\/v1\/warehouse\/items\/\d+$/.test(u) && m === 'get') { result = _.warehouseItems.find(x => x.id === parseInt(u.split('/').pop())) || null }
  else if (u === '/api/v1/warehouse/items' && m === 'post') { const item = { id: ++_.nextId, ...data }; _.warehouseItems.push(item); result = item }
  else if (u === '/api/v1/warehouse/items' && m === 'put') { const idx = _.warehouseItems.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.warehouseItems[idx], data); result = null }
  else if (/^\/api\/v1\/warehouse\/items\/\d+$/.test(u) && m === 'delete') { _.warehouseItems = _.warehouseItems.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // warehouse transactions aliases
  else if (u === '/api/v1/warehouse/transactions/page' && m === 'get') {
    let list = [..._.warehouseTransactions]
    if (p.type !== '' && p.type != null) list = list.filter(x => x.type === Number(p.type))
    if (p.itemName) list = list.filter(x => x.itemName.includes(p.itemName))
    result = page(list, p)
  }
  // settlement aliases
  else if (u === '/api/v1/settlement/page' && m === 'get') {
    let list = [..._.financialRecords]
    if (p.serviceType !== '' && p.serviceType != null) list = list.filter(x => x.serviceType === Number(p.serviceType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    if (p.customerName) list = list.filter(x => x.customerName.includes(p.customerName))
    result = page(list, p)
  }
  else if (u === '/api/v1/settlement/status' && m === 'put') {
    const idx = _.financialRecords.findIndex(x => x.id === data.id)
    if (idx >= 0) {
      Object.assign(_.financialRecords[idx], data)
      const statusMap = { 0: '待开票', 1: '已开票', 2: '已收款', 3: '已核销' }
      _.financialRecords[idx].statusName = statusMap[data.status] || '未知'
      if (data.status === 1) _.financialRecords[idx].billingDate = new Date().toISOString().slice(0, 10)
    }
    result = null
  }
  else if (/^\/api\/v1\/settlement\/\d+$/.test(u) && m === 'get') { result = _.financialRecords.find(x => x.id === parseInt(u.split('/').pop())) || null }
  // training aliases
  else if (u === '/api/v1/training/page' && m === 'get') {
    let list = [..._.trainingRecords]
    if (p.courseType !== '' && p.courseType != null) list = list.filter(x => x.courseType === Number(p.courseType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    result = page(list, p)
  }
  else if (u === '/api/v1/training' && m === 'post') { const item = { id: ++_.nextId, recordNo: 'TR-' + Date.now(), ...data }; _.trainingRecords.unshift(item); result = item }
  else if (u === '/api/v1/training' && m === 'put') { const idx = _.trainingRecords.findIndex(x => x.id === data.id); if (idx >= 0) Object.assign(_.trainingRecords[idx], data); result = null }
  else if (/^\/api\/v1\/training\/\d+$/.test(u) && m === 'delete') { _.trainingRecords = _.trainingRecords.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // inspection-tasks aliases
  else if (u === '/api/v1/inspection-tasks/page' && m === 'get') {
    let list = [..._.inspectionTasks]
    if (p.taskType !== '' && p.taskType != null) list = list.filter(x => x.taskType === Number(p.taskType))
    if (p.status !== '' && p.status != null) list = list.filter(x => x.status === Number(p.status))
    if (p.equipmentName) list = list.filter(x => x.equipmentName.includes(p.equipmentName))
    result = page(list, p)
  }
  else if (u === '/api/v1/inspection-tasks/stats' && m === 'get') {
    const all = _.inspectionTasks
    result = {
      pendingCount: all.filter(x => x.status === 0).length,
      inProgressCount: all.filter(x => x.status === 1).length,
      completedCount: all.filter(x => x.status === 2).length,
      delayedCount: all.filter(x => x.status === 3).length,
      total: all.length
    }
  }
  else if (/^\/api\/v1\/inspection-tasks\/\d+\/start$/.test(u) && m === 'put') {
    const id = parseInt(u.split('/')[4]); const item = _.inspectionTasks.find(x => x.id === id)
    if (item) { item.status = 1; item.statusName = '执行中'; item.actualDate = new Date().toISOString().slice(0, 10) }
    result = null
  }
  else if (/^\/api\/v1\/inspection-tasks\/\d+\/complete$/.test(u) && m === 'put') {
    const id = parseInt(u.split('/')[4]); const item = _.inspectionTasks.find(x => x.id === id)
    if (item) { item.status = 2; item.statusName = '已完成'; item.result = 1; item.resultName = '正常'; item.actualDate = new Date().toISOString().slice(0, 10) }
    result = null
  }
  else if (u === '/api/v1/inspection-tasks' && m === 'post') { const item = { id: ++_.nextId, taskNo: 'IT-' + Date.now(), ...data }; _.inspectionTasks.unshift(item); result = item }
  else if (/^\/api\/v1\/inspection-tasks\/\d+$/.test(u) && m === 'delete') { _.inspectionTasks = _.inspectionTasks.filter(x => x.id !== parseInt(u.split('/').pop())); result = null }
  // contract stats
  else if (u === '/api/v1/contract/stats' && m === 'get') {
    const all = _.contracts
    result = {
      total: all.length,
      activeCount: all.filter(x => x.status === 1).length,
      expiringThisMonth: all.filter(x => x.status === 1 && x.endDate === '2026-06-30').length,
      totalAmount: all.reduce((s, x) => s + x.contractAmount, 0)
    }
  }
  // settlement stats
  else if (u === '/api/v1/settlement/stats' && m === 'get') {
    const all = _.financialRecords
    result = {
      pendingInvoice: all.filter(x => x.status === 0).reduce((s, x) => s + x.totalAmount, 0),
      collectedThisMonth: all.filter(x => x.status >= 2).reduce((s, x) => s + x.totalAmount, 0),
      quarterTotal: all.reduce((s, x) => s + x.totalAmount, 0),
      uncollected: all.filter(x => x.status < 2).reduce((s, x) => s + x.totalAmount, 0)
    }
  }
  // dashboard extra endpoints
  else if (u === '/api/v1/dashboard/device-dist' && m === 'get') {
    result = [
      { name: '挖掘机', value: 45, color: '#1890ff' },
      { name: '起重机', value: 25, color: '#52c41a' },
      { name: '混凝土泵车', value: 18, color: '#faad14' },
      { name: '旋挖钻机', value: 8, color: '#722ed1' },
      { name: '其他', value: 4, color: '#909399' }
    ]
  }
  else if (u === '/api/v1/dashboard/timeline' && m === 'get') {
    result = [
      { time: '08:15', content: '湖北路桥 SY485H 紧急报修，已创建工单', type: 'warning' },
      { time: '09:00', content: '工单 WO20260526008 已派单给王工', type: 'primary' },
      { time: '09:30', content: '王工已接单，正在赶往现场', type: 'info' },
      { time: '10:05', content: '广东交通建设 SYM5440 维修完成待验收', type: 'success' },
      { time: '10:30', content: '四川能源开发 SR360 异常排查完成', type: 'success' },
      { time: '11:00', content: '备件库存预警：发动机机油库存不足', type: 'danger' }
    ]
  }
  else if (u === '/api/v1/dashboard/alarms' && m === 'get') {
    result = [
      { level: 'critical', levelName: '紧急', content: 'SY485H-2024002 液压系统漏油严重', time: '08:15', equipmentName: 'SY485H 液压挖掘机' },
      { level: 'warning', levelName: '严重', content: '发动机机油库存仅剩2桶', time: '08:30', equipmentName: '仓库-辅料' },
      { level: 'info', levelName: '提醒', content: 'SYM5230 保养周期已到', time: '09:00', equipmentName: 'SYM5230 混凝土泵车' }
    ]
  }
  else if (u === '/api/v1/dashboard/device-health' && m === 'get') {
    result = [
      { name: 'SY485H-2024001', health: 95, status: '优秀' },
      { name: 'SY485H-2024002', health: 45, status: '故障' },
      { name: 'STC250-2024003', health: 78, status: '良好' },
      { name: 'SYM5230-2024004', health: 82, status: '良好' },
      { name: 'SR360-2024005', health: 70, status: '一般' },
      { name: 'SCC2000-2024006', health: 88, status: '良好' }
    ]
  }

  if (result !== undefined) {
    return { data: { code: 200, message: 'success', data: result }, status: 200, statusText: 'OK', headers: { 'content-type': 'application/json' }, config }
  }
  return null
}

export default function installMock(axiosInstance) {
  const origAdapter = axiosInstance.defaults.adapter
  axiosInstance.defaults.adapter = function (config) {
    const resp = mock(config)
    if (resp) {
      const delay = 80 + Math.random() * 150
      return new Promise(resolve => setTimeout(() => resolve(resp), delay))
    }
    return origAdapter ? origAdapter(config) : Promise.reject(new Error('No adapter'))
  }
}

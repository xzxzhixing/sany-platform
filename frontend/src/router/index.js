import Vue from 'vue'
import Router from 'vue-router'
import { Message } from 'element-ui'
import Layout from '../layout/Layout.vue'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import ProductionMonitor from '../views/ProductionMonitor.vue'
import WorkOrderList from '../views/WorkOrderList.vue'
import WorkOrderCreate from '../views/WorkOrderCreate.vue'
import WorkOrderDetail from '../views/WorkOrderDetail.vue'
import WorkOrderKanban from '../views/WorkOrderKanban.vue'
import InspectionTask from '../views/InspectionTask.vue'
import QualityManagement from '../views/QualityManagement.vue'
import EquipmentList from '../views/EquipmentList.vue'
import EquipmentDetail from '../views/EquipmentDetail.vue'
import WarehouseManagement from '../views/WarehouseManagement.vue'
import CustomerList from '../views/CustomerList.vue'
import CustomerDetail from '../views/CustomerDetail.vue'
import SpareParts from '../views/SpareParts.vue'
import ContractManagement from '../views/ContractManagement.vue'
import ServiceReport from '../views/ServiceReport.vue'
import MaintenancePlan from '../views/MaintenancePlan.vue'
import Notifications from '../views/Notifications.vue'
import Statistics from '../views/Statistics.vue'
import EmployeeList from '../views/EmployeeList.vue'
import FinancialSettlement from '../views/FinancialSettlement.vue'
import TrainingManagement from '../views/TrainingManagement.vue'
import KnowledgeBase from '../views/KnowledgeBase.vue'
import OperationLog from '../views/OperationLog.vue'
import EvaluationCenter from '../views/EvaluationCenter.vue'
import UserCenter from '../views/UserCenter.vue'
import Settings from '../views/Settings.vue'

Vue.use(Router)

// suppress NavigationDuplicated error when clicking same-route links
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const router = new Router({
  base: '/monitor/',
  routes: [
    { path: '/login', name: 'Login', component: Login, meta: { noAuth: true } },
    { path: '/', redirect: '/dashboard' },
    {
      path: '/',
      component: Layout,
      children: [
        { path: 'dashboard', name: 'Dashboard', component: Dashboard, meta: { title: '首页看板', roles: ['admin', 'engineer'] } },
        { path: 'prod-monitor', name: 'ProductionMonitor', component: ProductionMonitor, meta: { title: '生产监控', roles: ['admin', 'engineer'] } },
        { path: 'work-order', name: 'WorkOrderList', component: WorkOrderList, meta: { title: '工单管理', roles: ['admin', 'engineer'] } },
        { path: 'work-order/create', name: 'WorkOrderCreate', component: WorkOrderCreate, meta: { title: '创建工单', roles: ['admin', 'engineer'] } },
        { path: 'work-order/detail/:id', name: 'WorkOrderDetail', component: WorkOrderDetail, meta: { title: '工单详情', roles: ['admin', 'engineer'] } },
        { path: 'work-order/kanban', name: 'WorkOrderKanban', component: WorkOrderKanban, meta: { title: '工单看板', roles: ['admin', 'engineer'] } },
        { path: 'inspection-task', name: 'InspectionTask', component: InspectionTask, meta: { title: '巡检任务', roles: ['admin', 'engineer'] } },
        { path: 'quality', name: 'QualityManagement', component: QualityManagement, meta: { title: '质量管理', roles: ['admin', 'engineer'] } },
        { path: 'equipment', name: 'EquipmentList', component: EquipmentList, meta: { title: '设备管理', roles: ['admin', 'engineer'] } },
        { path: 'equipment/detail/:id', name: 'EquipmentDetail', component: EquipmentDetail, meta: { title: '设备详情', roles: ['admin', 'engineer'] } },
        { path: 'warehouse', name: 'WarehouseManagement', component: WarehouseManagement, meta: { title: '仓储管理', roles: ['admin', 'engineer'] } },
        { path: 'customer', name: 'CustomerList', component: CustomerList, meta: { title: '客户管理', roles: ['admin', 'engineer'] } },
        { path: 'customer/detail/:id', name: 'CustomerDetail', component: CustomerDetail, meta: { title: '客户详情', roles: ['admin', 'engineer'] } },
        { path: 'spare-parts', name: 'SpareParts', component: SpareParts, meta: { title: '备件管理', roles: ['admin', 'engineer'] } },
        { path: 'contract', name: 'ContractManagement', component: ContractManagement, meta: { title: '合同管理', roles: ['admin'] } },
        { path: 'service-report', name: 'ServiceReport', component: ServiceReport, meta: { title: '服务报告', roles: ['admin', 'engineer'] } },
        { path: 'maintenance-plan', name: 'MaintenancePlan', component: MaintenancePlan, meta: { title: '保养计划', roles: ['admin', 'engineer'] } },
        { path: 'financial', name: 'FinancialSettlement', component: FinancialSettlement, meta: { title: '财务结算', roles: ['admin'] } },
        { path: 'training', name: 'TrainingManagement', component: TrainingManagement, meta: { title: '培训管理', roles: ['admin', 'engineer'] } },
        { path: 'knowledge', name: 'KnowledgeBase', component: KnowledgeBase, meta: { title: '知识库', roles: ['admin', 'engineer'] } },
        { path: 'notifications', name: 'Notifications', component: Notifications, meta: { title: '消息通知', roles: ['admin', 'engineer'] } },
        { path: 'statistics', name: 'Statistics', component: Statistics, meta: { title: '数据统计', roles: ['admin', 'engineer'] } },
        { path: 'employee', name: 'EmployeeList', component: EmployeeList, meta: { title: '员工管理', roles: ['admin'] } },
        { path: 'operation-log', name: 'OperationLog', component: OperationLog, meta: { title: '操作日志', roles: ['admin'] } },
        { path: 'evaluation', name: 'EvaluationCenter', component: EvaluationCenter, meta: { title: '客户评价', roles: ['admin', 'engineer'] } },
        { path: 'user-center', name: 'UserCenter', component: UserCenter, meta: { title: '个人中心', roles: ['admin', 'engineer'] } },
        { path: 'settings', name: 'Settings', component: Settings, meta: { title: '系统设置', roles: ['admin'] } }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta.noAuth) return next()
  const userStr = localStorage.getItem('sany_user')
  if (!userStr) return next('/login')

  const user = JSON.parse(userStr)
  if (to.meta.roles && !to.meta.roles.includes(user.role)) {
    Message.warning('无权限访问该页面')
    return next('/dashboard')
  }
  next()
})

export default router

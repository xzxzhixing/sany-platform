<template>
  <div class="page-container">
    <div class="page-detail-header">
      <el-button icon="el-icon-arrow-left" plain @click="$router.push('/work-order')">返回列表</el-button>
      <div class="detail-title-area">
        <h2>
          {{ detail.orderNo }}
          <el-tag :type="statusTag(detail.orderStatus)" size="small" style="margin-left:10px">{{ detail.orderStatusName }}</el-tag>
        </h2>
        <span class="detail-meta">创建时间：{{ detail.createTime }} &nbsp;|&nbsp; 报修人：{{ detail.reporterName }}</span>
      </div>
      <div class="detail-actions">
        <el-button type="primary" size="small" @click="handleEdit" v-if="detail.orderStatus === 0">编辑</el-button>
        <el-button type="success" size="small" @click="handleDispatch" v-if="detail.orderStatus === 0">派单</el-button>
        <el-button type="warning" size="small" @click="handleProcess" v-if="detail.orderStatus === 1 || detail.orderStatus === 2">处理</el-button>
        <el-button type="primary" size="small" @click="handleComplete" v-if="detail.orderStatus === 2">完成</el-button>
        <el-button size="small" @click="handleCancel" v-if="detail.orderStatus === 0 || detail.orderStatus === 1">取消</el-button>
      </div>
    </div>

    <div class="detail-grid">
      <el-card shadow="never" class="detail-card">
        <div slot="header"><span><i class="el-icon-info" style="margin-right:6px"></i>基本信息</span></div>
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="工单编号" :span="2">{{ detail.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="工单类型">{{ detail.orderTypeName }}</el-descriptions-item>
          <el-descriptions-item label="工单状态">
            <el-tag :type="statusTag(detail.orderStatus)" size="small">{{ detail.orderStatusName }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="紧急程度">
            <el-tag :type="faultLevelTag(detail.faultLevel)" size="small" effect="dark">{{ detail.faultLevelName }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="工单来源">{{ detail.orderSourceName || '客户报修' }}</el-descriptions-item>
          <el-descriptions-item label="报修人">{{ detail.reporterName }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ detail.reporterPhone || '-' }}</el-descriptions-item>
          <el-descriptions-item label="报修时间">{{ detail.createTime }}</el-descriptions-item>
        </el-descriptions>
      </el-card>

      <el-card shadow="never" class="detail-card">
        <div slot="header"><span><i class="el-icon-setting" style="margin-right:6px"></i>设备信息</span></div>
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="设备名称">{{ detail.equipmentName }}</el-descriptions-item>
          <el-descriptions-item label="设备编号">{{ detail.equipmentCode }}</el-descriptions-item>
          <el-descriptions-item label="客户名称">{{ detail.customerName }}</el-descriptions-item>
          <el-descriptions-item label="设备位置">{{ detail.installAddress || '-' }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>

    <el-card shadow="never" class="detail-card" style="margin-top:16px">
      <div slot="header"><span><i class="el-icon-document" style="margin-right:6px"></i>故障信息</span></div>
      <el-descriptions :column="1" border size="small">
        <el-descriptions-item label="故障描述">
          <div class="content-block">{{ detail.faultDescription || '暂无' }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="故障分类">{{ detail.faultCategory || '未分类' }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card shadow="never" class="detail-card" style="margin-top:16px">
      <div slot="header"><span><i class="el-icon-s-tools" style="margin-right:6px"></i>处理信息</span></div>
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="派单人员">{{ detail.assigneeName || '未派单' }}</el-descriptions-item>
        <el-descriptions-item label="派单时间">{{ detail.assignTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="接单时间">{{ detail.acceptTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="到达时间">{{ detail.arriveTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="完工时间">{{ detail.finishTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="确认时间">{{ detail.confirmTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="处理详情" :span="2">
          <div class="content-block">{{ detail.handlingDetail || '暂无' }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="处理结果">
          <el-tag v-if="detail.handlingResult !== undefined && detail.handlingResult !== null" :type="resultTag(detail.handlingResult)" size="small">{{ resultName(detail.handlingResult) }}</el-tag>
          <span v-else>-</span>
        </el-descriptions-item>
        <el-descriptions-item label="收费金额">{{ detail.chargeAmount ? '¥' + detail.chargeAmount : '-' }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card shadow="never" class="detail-card" style="margin-top:16px" v-if="evaluation">
      <div slot="header"><span><i class="el-icon-star-on" style="margin-right:6px"></i>客户评价</span></div>
      <div class="evaluation-display">
        <div class="eval-stars">
          <i v-for="s in 5" :key="s" :class="s <= evaluation.rating ? 'el-icon-star-on' : 'el-icon-star-off'" :style="{ color: s <= evaluation.rating ? '#faad14' : '#c0c4cc' }"></i>
          <span style="margin-left:8px;font-weight:600">{{ evaluation.rating }}分</span>
        </div>
        <p class="eval-remark">{{ evaluation.ratingRemark }}</p>
        <span class="eval-meta">评价人：{{ evaluation.evaluator }} &nbsp; 时间：{{ evaluation.evaluateTime }}</span>
      </div>
    </el-card>

    <el-dialog title="处理工单" :visible.sync="handleVisible" width="560px" :close-on-click-modal="false">
      <el-form ref="handleFormRef" :model="handleForm" label-width="100px" size="small">
        <el-form-item label="目标状态">
          <el-select v-model="handleForm.newStatus" placeholder="选择目标状态">
            <el-option label="待接单" :value="1" v-if="detail.orderStatus === 0" />
            <el-option label="处理中" :value="2" v-if="detail.orderStatus === 1" />
            <el-option label="已完成" :value="4" v-if="detail.orderStatus === 2" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理人员">
          <el-select v-model="handleForm.assigneeName" placeholder="选择人员" filterable style="width:100%" v-if="detail.orderStatus === 0">
            <el-option v-for="e in employeeOptions" :key="e.id" :label="e.employeeName" :value="e.employeeName" />
          </el-select>
          <el-input v-else :value="detail.assigneeName" disabled />
        </el-form-item>
        <el-form-item label="处理详情">
          <el-input type="textarea" :rows="4" v-model="handleForm.handlingDetail" placeholder="填写处理过程" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="handleVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmHandle">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'
import { getWorkOrderDetail, updateWorkOrderStatus, deleteWorkOrder } from '../api/workOrder'

export default {
  name: 'WorkOrderDetail',
  data() {
    return {
      detail: {}, loading: false, submitting: false,
      evaluation: null, employeeOptions: [],
      handleVisible: false,
      handleForm: { newStatus: null, assigneeName: '', handlingDetail: '' }
    }
  },
  created() { this.fetchDetail() },
  methods: {
    async fetchDetail() {
      const id = this.$route.params.id
      if (!id) return this.$router.push('/work-order')
      this.loading = true
      try {
        const res = await getWorkOrderDetail(Number(id))
        if (res.code === 200 && res.data) { this.detail = res.data } else { this.$message.error('工单不存在'); this.$router.push('/work-order') }
      } catch { this.$message.error('获取失败') }
      finally { this.loading = false }
      this.fetchEvaluation(id)
      this.fetchEmployees()
    },
    async fetchEvaluation(orderId) {
      try {
        const res = await request({ url: '/api/v1/evaluation/' + orderId, method: 'get' })
        if (res.code === 200) this.evaluation = res.data
      } catch {}
    },
    async fetchEmployees() {
      try {
        const res = await request({ url: '/api/v1/employee/page', method: 'get', params: { pageSize: 50 } })
        if (res.code === 200) this.employeeOptions = res.data.list || []
      } catch {}
    },
    handleEdit() { this.$message.info('编辑功能开发中') },
    handleDispatch() {
      this.handleForm = { newStatus: 1, assigneeName: '', handlingDetail: '' }
      this.handleVisible = true
    },
    handleProcess() {
      this.handleForm = { newStatus: this.detail.orderStatus === 1 ? 2 : null, assigneeName: '', handlingDetail: '' }
      this.handleVisible = true
    },
    handleComplete() {
      this.handleForm = { newStatus: 4, assigneeName: '', handlingDetail: '' }
      this.handleVisible = true
    },
    async confirmHandle() {
      if (!this.handleForm.newStatus) return this.$message.warning('请选择目标状态')
      this.submitting = true
      try {
        if (this.detail.orderStatus === 0 && this.handleForm.newStatus === 1 && this.handleForm.assigneeName) {
          await request({ url: '/api/v1/work-order/' + this.detail.id + '/dispatch', method: 'put', params: { assigneeName: this.handleForm.assigneeName } })
        }
        const res = await updateWorkOrderStatus({ id: this.detail.id, newStatus: this.handleForm.newStatus })
        if (res.code === 200) { this.$message.success('操作成功'); this.handleVisible = false; this.fetchDetail() }
      } catch { this.$message.error('操作失败') }
      finally { this.submitting = false }
    },
    handleCancel() {
      this.$confirm('确认取消此工单?', '提示', { type: 'warning' }).then(async () => {
        try { await updateWorkOrderStatus({ id: this.detail.id, newStatus: 6 }); this.$message.success('已取消'); this.fetchDetail() }
        catch { this.$message.error('操作失败') }
      }).catch(() => {})
    },
    statusTag(s) { return {0:'info',1:'warning',2:'primary',3:'',4:'success',5:'info',6:'danger'}[s] || 'info' },
    faultLevelTag(l) { return {1:'danger',2:'warning',3:'primary',4:'info'}[l] || 'info' },
    resultTag(r) { return {0:'danger',1:'success',2:'warning',3:'primary'}[r] || 'info' },
    resultName(r) { return {0:'未解决',1:'已修复',2:'无法修复',3:'已更换'}[r] || '未知' }
  }
}
</script>

<style scoped>
.page-detail-header {
  background: var(--color-card); border-radius: var(--radius-md); padding: var(--space-md) var(--space-lg); margin-bottom: var(--space-md);
  display: flex; align-items: center; gap: var(--space-md); flex-wrap: wrap;
  box-shadow: var(--shadow-sm); border: 1px solid var(--color-card-border);
}
.detail-title-area { flex: 1; min-width: 200px; }
.detail-title-area h2 { margin: 0; font-size: 18px; display: flex; align-items: center; }
.detail-meta { font-size: var(--font-size-sm); color: var(--color-text-tertiary); }
.detail-actions { display: flex; gap: 8px; }
.detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.detail-card { border-radius: 8px; }
.content-block { white-space: pre-wrap; line-height: 1.8; padding: 4px 0; font-size: 13px; color: #606266; }
.evaluation-display { padding: 8px 0; }
.eval-stars { font-size: 20px; margin-bottom: 8px; display: flex; align-items: center; }
.eval-stars i { margin-right: 4px; }
.eval-remark { font-size: 14px; color: #606266; margin: 8px 0; line-height: 1.6; }
.eval-meta { font-size: 12px; color: #c0c4cc; }

@media (max-width: 900px) { .detail-grid { grid-template-columns: 1fr; } }
</style>

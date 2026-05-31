<template>
  <div class="page-container">
    <div class="page-detail-header">
      <el-button icon="el-icon-arrow-left" plain @click="$router.push('/equipment')">返回列表</el-button>
      <div class="detail-title-area">
        <h2>{{ detail.equipmentName }}</h2>
        <span class="detail-meta">设备编号：{{ detail.equipmentCode }} &nbsp;|&nbsp; 型号：{{ detail.equipmentModel }}</span>
      </div>
      <div class="detail-actions">
        <el-button type="primary" size="small" @click="handleEdit">编辑</el-button>
        <el-button size="small" @click="handleDelete">删除</el-button>
      </div>
    </div>

    <div class="detail-grid">
      <el-card shadow="never" class="detail-card">
        <div slot="header"><span><i class="el-icon-info" style="margin-right:6px"></i>基本信息</span></div>
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="设备名称" :span="2">{{ detail.equipmentName }}</el-descriptions-item>
          <el-descriptions-item label="设备编号">{{ detail.equipmentCode }}</el-descriptions-item>
          <el-descriptions-item label="型号">{{ detail.equipmentModel }}</el-descriptions-item>
          <el-descriptions-item label="分类">{{ detail.category }}</el-descriptions-item>
          <el-descriptions-item label="序列号">{{ detail.serialNumber || '-' }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="statusType(detail.status)" size="small" effect="dark">{{ statusName(detail.status) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="所属客户">{{ detail.customerName }}</el-descriptions-item>
          <el-descriptions-item label="安装地址">{{ detail.installAddress || '-' }}</el-descriptions-item>
        </el-descriptions>
      </el-card>

      <el-card shadow="never" class="detail-card">
        <div slot="header"><span><i class="el-icon-date" style="margin-right:6px"></i>售后信息</span></div>
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="购买日期">{{ detail.purchaseDate || '-' }}</el-descriptions-item>
          <el-descriptions-item label="保修截止">{{ detail.warrantyEnd || '-' }}</el-descriptions-item>
          <el-descriptions-item label="保养周期">{{ detail.maintenanceCycle ? detail.maintenanceCycle + '天' : '-' }}</el-descriptions-item>
          <el-descriptions-item label="维护工时">{{ detail.maintenanceHour ? detail.maintenanceHour + '小时' : '-' }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>

    <el-card shadow="never" class="detail-card" style="margin-top:16px">
      <div slot="header"><span><i class="el-icon-document" style="margin-right:6px"></i>关联工单</span></div>
      <el-table :data="relatedOrders" stripe border size="small" v-loading="ordersLoading" style="width:100%">
        <el-table-column prop="orderNo" label="工单编号" width="170" />
        <el-table-column prop="orderTypeName" label="类型" width="80" />
        <el-table-column label="状态" width="90" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.orderStatus)" size="small">{{ row.orderStatusName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="faultDescription" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" width="155" />
        <el-table-column label="操作" width="80" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="text" size="mini" @click="$router.push('/work-order/detail/' + row.id)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="empty-state" v-if="!relatedOrders.length && !ordersLoading">
        <i class="el-icon-document"></i><p>暂无关联工单</p>
      </div>
    </el-card>

    <el-dialog title="编辑设备" :visible.sync="dialogVisible" width="580px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="设备编号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" />
        </el-form-item>
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="型号"><el-input v-model="form.equipmentModel" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类"><el-input v-model="form.category" /></el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">运行中</el-radio>
            <el-radio :label="2">维修中</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmSave">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'EquipmentDetail',
  data() {
    return {
      detail: {}, loading: false, submitting: false,
      relatedOrders: [], ordersLoading: false,
      dialogVisible: false,
      form: {},
      rules: {
        equipmentCode: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
        equipmentName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }]
      }
    }
  },
  created() { this.fetchDetail() },
  methods: {
    async fetchDetail() {
      const id = this.$route.params.id
      if (!id) return this.$router.push('/equipment')
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/equipment/' + id, method: 'get' })
        if (res.code === 200 && res.data) { this.detail = res.data } else { this.$message.error('设备不存在'); this.$router.push('/equipment') }
      } catch { this.$message.error('获取失败') }
      finally { this.loading = false }
      this.fetchOrders(id)
    },
    async fetchOrders(equipmentId) {
      this.ordersLoading = true
      try {
        const res = await request({ url: '/api/v1/work-order/page', method: 'get', params: { pageSize: 100 } })
        if (res.code === 200) {
          const all = res.data.list || []
          this.relatedOrders = all.filter(o => o.equipmentCode === this.detail.equipmentCode)
        }
      } catch {}
      finally { this.ordersLoading = false }
    },
    handleEdit() {
      this.form = { ...this.detail }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef?.clearValidate())
    },
    confirmSave() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitting = true
        try {
          const res = await request({ url: '/api/v1/equipment', method: 'post', data: this.form })
          if (res.code === 200) { this.$message.success('更新成功'); this.dialogVisible = false; Object.assign(this.detail, this.form) }
        } catch { this.$message.error('更新失败') }
        finally { this.submitting = false }
      })
    },
    handleDelete() {
      this.$confirm('确认删除设备?', '提示', { type: 'warning' }).then(async () => {
        this.$message.success('已删除'); this.$router.push('/equipment')
      }).catch(() => {})
    },
    statusType(s) { return {1:'success',0:'info',2:'warning'}[s] || 'info' },
    statusName(s) { return {1:'运行中',0:'停用',2:'维修中'}[s] || '未知' },
    statusTag(s) { return {0:'info',1:'warning',2:'primary',3:'',4:'success',5:'info',6:'danger'}[s] || 'info' }
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
@media (max-width: 900px) { .detail-grid { grid-template-columns: 1fr; } }
</style>

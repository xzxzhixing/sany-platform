<template>
  <div class="page-container">
    <div class="page-detail-header">
      <el-button icon="el-icon-arrow-left" plain @click="$router.push('/customer')">返回列表</el-button>
      <div class="detail-title-area">
        <h2>{{ detail.customerName }}</h2>
        <span class="detail-meta">联系人：{{ detail.contactPerson }} &nbsp;|&nbsp; 电话：{{ detail.contactPhone }}</span>
      </div>
      <div class="detail-actions">
        <el-button type="primary" size="small" @click="handleEdit">编辑</el-button>
      </div>
    </div>

    <div class="detail-grid">
      <el-card shadow="never" class="detail-card">
        <div slot="header"><span><i class="el-icon-info" style="margin-right:6px"></i>基本信息</span></div>
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="客户名称" :span="2">{{ detail.customerName }}</el-descriptions-item>
          <el-descriptions-item label="联系人">{{ detail.contactPerson }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ detail.contactPhone }}</el-descriptions-item>
          <el-descriptions-item label="所在省">{{ detail.province || '-' }}</el-descriptions-item>
          <el-descriptions-item label="城市">{{ detail.city }}</el-descriptions-item>
          <el-descriptions-item label="地址" :span="2">{{ detail.address }}</el-descriptions-item>
          <el-descriptions-item label="服务等级">
            <el-tag :type="levelType(detail.serviceLevel)" size="small" effect="dark">{{ levelName(detail.serviceLevel) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="detail.status === 1 ? 'success' : 'danger'" size="small">{{ detail.status === 1 ? '启用' : '禁用' }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>

      <el-card shadow="never" class="detail-card">
        <div slot="header"><span><i class="el-icon-data-analysis" style="margin-right:6px"></i>服务数据</span></div>
        <div class="stat-cards">
          <div class="mini-stat">
            <span class="mini-stat-value">{{ detail.totalEquipment || 0 }}</span>
            <span class="mini-stat-label">设备数量</span>
          </div>
          <div class="mini-stat">
            <span class="mini-stat-value">{{ detail.totalOrders || 0 }}</span>
            <span class="mini-stat-label">工单总数</span>
          </div>
          <div class="mini-stat">
            <span class="mini-stat-value">{{ completedRate }}%</span>
            <span class="mini-stat-label">完成率</span>
          </div>
        </div>
      </el-card>
    </div>

    <el-card shadow="never" class="detail-card" style="margin-top:16px">
      <div slot="header"><span><i class="el-icon-setting" style="margin-right:6px"></i>关联设备</span></div>
      <el-table :data="equipmentList" stripe border size="small" v-loading="equipLoading" style="width:100%">
        <el-table-column prop="equipmentCode" label="设备编号" width="150" />
        <el-table-column prop="equipmentName" label="设备名称" min-width="160" />
        <el-table-column prop="equipmentModel" label="型号" width="120" />
        <el-table-column prop="category" label="分类" width="100" />
        <el-table-column label="状态" width="90" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusType(row.status)" size="small" effect="dark">{{ statusName(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="text" size="mini" @click="$router.push('/equipment/detail/' + row.id)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="编辑客户" :visible.sync="dialogVisible" width="580px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactPerson"><el-input v-model="form.contactPerson" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactPhone"><el-input v-model="form.contactPhone" /></el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="城市"><el-input v-model="form.city" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务等级">
              <el-select v-model="form.serviceLevel" style="width:100%">
                <el-option label="VIP" :value="1" />
                <el-option label="重要" :value="2" />
                <el-option label="普通" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="地址"><el-input v-model="form.address" type="textarea" :rows="2" /></el-form-item>
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
  name: 'CustomerDetail',
  data() {
    return {
      detail: {}, loading: false, submitting: false,
      equipmentList: [], equipLoading: false,
      dialogVisible: false,
      form: {},
      rules: {
        customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        contactPerson: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
        contactPhone: [{ required: true, message: '请输入电话', trigger: 'blur' }]
      }
    }
  },
  computed: {
    completedRate() {
      return Math.min(100, Math.round(((this.detail.totalOrders || 0) / 30) * 100))
    }
  },
  created() { this.fetchDetail() },
  methods: {
    async fetchDetail() {
      const id = this.$route.params.id
      if (!id) return this.$router.push('/customer')
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/customer/' + id, method: 'get' })
        if (res.code === 200 && res.data) { this.detail = res.data } else { this.$message.error('客户不存在'); this.$router.push('/customer') }
      } catch { this.$message.error('获取失败') }
      finally { this.loading = false }
      this.fetchEquipment(id)
    },
    async fetchEquipment(customerId) {
      this.equipLoading = true
      try {
        const res = await request({ url: '/api/v1/equipment/list', method: 'get', params: { pageSize: 100 } })
        if (res.code === 200) {
          const customerName = this.detail.customerName
          this.equipmentList = (res.data.list || []).filter(e => e.customerName === customerName)
        }
      } catch {}
      finally { this.equipLoading = false }
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
          const res = await request({ url: '/api/v1/customer', method: 'post', data: this.form })
          if (res.code === 200) { this.$message.success('更新成功'); this.dialogVisible = false; Object.assign(this.detail, this.form) }
        } catch { this.$message.error('更新失败') }
        finally { this.submitting = false }
      })
    },
    levelType(l) { return {1:'danger',2:'warning',3:'info'}[l] || 'info' },
    levelName(l) { return {1:'VIP',2:'重要',3:'普通'}[l] || '未知' },
    statusType(s) { return {1:'success',0:'info',2:'warning'}[s] || 'info' },
    statusName(s) { return {1:'运行中',0:'停用',2:'维修中'}[s] || '未知' }
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
.detail-title-area h2 { margin: 0; font-size: 18px; }
.detail-meta { font-size: var(--font-size-sm); color: var(--color-text-tertiary); }
.detail-actions { display: flex; gap: 8px; }
.detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.detail-card { border-radius: 8px; }
.stat-cards { display: flex; gap: 16px; padding: 12px 0; justify-content: space-around; }
.mini-stat { text-align: center; }
.mini-stat-value { display: block; font-size: 28px; font-weight: 700; color: var(--color-primary); }
.mini-stat-label { display: block; font-size: var(--font-size-sm); color: var(--color-text-tertiary); margin-top: 4px; }
@media (max-width: 900px) { .detail-grid { grid-template-columns: 1fr; } }
</style>

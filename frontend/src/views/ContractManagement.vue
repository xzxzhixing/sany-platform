<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-header-bg"></div>
      <div class="page-header-content">
        <h2><i class="el-icon-document-copy"></i> 合同管理</h2>
        <p>销售合同全生命周期管理与跟踪</p>
      </div>
    </div>

    <div class="stat-grid">
      <el-card v-for="card in statCards" :key="card.label" shadow="never" class="stat-card">
        <div class="stat-card-inner">
          <div class="stat-icon-box" :class="card.color">
            <i :class="card.icon"></i>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ card.value }}</span>
            <span class="stat-label">{{ card.label }}</span>
          </div>
        </div>
      </el-card>
    </div>

    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="合同类型">
          <el-select v-model="queryParam.contractType" placeholder="全部" clearable style="width:130px">
            <el-option label="销售合同" value="销售合同" />
            <el-option label="服务合同" value="服务合同" />
            <el-option label="租赁合同" value="租赁合同" />
            <el-option label="维修合同" value="维修合同" />
          </el-select>
        </el-form-item>
        <el-form-item label="合同状态">
          <el-select v-model="queryParam.status" placeholder="全部" clearable style="width:130px">
            <el-option label="待签署" value="待签署" />
            <el-option label="执行中" value="执行中" />
            <el-option label="已完成" value="已完成" />
            <el-option label="已终止" value="已终止" />
          </el-select>
        </el-form-item>
        <el-form-item label="客户名称">
          <el-input v-model="queryParam.customerName" placeholder="输入客户名称" clearable style="width:180px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="openDialog()" plain>新增合同</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div class="table-toolbar">
        <span class="table-toolbar-title">合同列表</span>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="contractNo" label="合同编号" width="170" />
        <el-table-column prop="contractName" label="合同名称" min-width="160" show-overflow-tooltip />
        <el-table-column prop="customerName" label="客户名称" min-width="140" />
        <el-table-column label="合同类型" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="contractTypeTag(row.contractType)" size="small">{{ row.contractType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="合同期限" width="190" align="center">
          <template slot-scope="{ row }">
            {{ row.startDate }} ~ {{ row.endDate }}
          </template>
        </el-table-column>
        <el-table-column label="合同金额" width="130" align="right">
          <template slot-scope="{ row }">
            <span class="amount-text">¥{{ row.contractAmount != null ? row.contractAmount.toLocaleString() : '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="90" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.status)" size="small" effect="dark">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="responsiblePerson" label="负责人" width="90" />
        <el-table-column label="操作" width="230" fixed="right" align="center">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain @click="openDetail(row)">查看</el-button>
            <el-button type="success" size="mini" plain @click="openDialog(row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-wrapper">
        <el-pagination @size-change="s=>{queryParam.pageSize=s;queryParam.pageNum=1;fetchData()}"
                       @current-change="p=>{queryParam.pageNum=p;fetchData()}"
                       :current-page="queryParam.pageNum" :page-sizes="[10,20,50,100]"
                       :page-size="queryParam.pageSize"
                       layout="total, sizes, prev, pager, next, jumper" :total="total" background />
      </div>
    </div>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="620px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="自动生成或手动输入" :disabled="!!editingId" />
        </el-form-item>
        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="form.contractName" placeholder="输入合同名称" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="客户名称" prop="customerName">
              <el-input v-model="form.customerName" placeholder="输入客户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同类型" prop="contractType">
              <el-select v-model="form.contractType" placeholder="选择类型" style="width:100%">
                <el-option label="销售合同" value="销售合同" />
                <el-option label="服务合同" value="服务合同" />
                <el-option label="租赁合同" value="租赁合同" />
                <el-option label="维修合同" value="维修合同" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="开始日期" prop="startDate">
              <el-date-picker v-model="form.startDate" type="date" placeholder="开始日期" value-format="yyyy-MM-dd" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker v-model="form.endDate" type="date" placeholder="结束日期" value-format="yyyy-MM-dd" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="合同金额(元)" prop="contractAmount">
              <el-input-number v-model="form.contractAmount" :min="0" :precision="2" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="选择状态" style="width:100%">
                <el-option label="待签署" value="待签署" />
                <el-option label="执行中" value="执行中" />
                <el-option label="已完成" value="已完成" />
                <el-option label="已终止" value="已终止" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="负责人">
          <el-input v-model="form.responsiblePerson" placeholder="负责人姓名" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="合同备注" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmSave">保存</el-button>
      </span>
    </el-dialog>

    <el-dialog title="合同详情" :visible.sync="detailVisible" width="650px" :close-on-click-modal="false">
      <template v-if="detail">
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="合同编号" :span="2">{{ detail.contractNo }}</el-descriptions-item>
          <el-descriptions-item label="合同名称" :span="2">{{ detail.contractName }}</el-descriptions-item>
          <el-descriptions-item label="客户名称">{{ detail.customerName }}</el-descriptions-item>
          <el-descriptions-item label="合同类型">
            <el-tag :type="contractTypeTag(detail.contractType)" size="small">{{ detail.contractType }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="合同期限" :span="2">{{ detail.startDate }} ~ {{ detail.endDate }}</el-descriptions-item>
          <el-descriptions-item label="合同金额">
            <span class="amount-text">¥{{ detail.contractAmount != null ? detail.contractAmount.toLocaleString() : '-' }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="statusTag(detail.status)" size="small" effect="dark">{{ detail.status }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="负责人">{{ detail.responsiblePerson || '-' }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ detail.createTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">{{ detail.remark || '无' }}</el-descriptions-item>
        </el-descriptions>
      </template>
      <span slot="footer">
        <el-button @click="detailVisible=false">关闭</el-button>
        <el-button type="primary" v-if="detail" @click="openDialog(detail)" plain>编辑</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'ContractManagement',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { contractType: '', status: '', customerName: '', pageNum: 1, pageSize: 10 },
      tableData: [], dialogVisible: false, detailVisible: false, editingId: null, detail: null,
      form: { contractNo: '', contractName: '', customerName: '', contractType: '', startDate: '', endDate: '', contractAmount: 0, status: '待签署', responsiblePerson: '', remark: '' },
      rules: {
        contractNo: [{ required: true, message: '请输入合同编号', trigger: 'blur' }],
        contractName: [{ required: true, message: '请输入合同名称', trigger: 'blur' }],
        customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        contractType: [{ required: true, message: '请选择合同类型', trigger: 'change' }],
        startDate: [{ required: true, message: '请选择开始日期', trigger: 'change' }],
        endDate: [{ required: true, message: '请选择结束日期', trigger: 'change' }],
        contractAmount: [{ required: true, message: '请输入合同金额', trigger: 'blur' }],
        status: [{ required: true, message: '请选择状态', trigger: 'change' }]
      },
      statCards: [
        { icon: 'el-icon-document', label: '总合同数', value: '-', color: 'blue' },
        { icon: 'el-icon-loading', label: '执行中', value: '-', color: 'orange' },
        { icon: 'el-icon-warning', label: '本月到期', value: '-', color: 'red' },
        { icon: 'el-icon-money', label: '合同总额', value: '-', color: 'green' }
      ]
    }
  },
  computed: {
    dialogTitle() { return this.editingId ? '编辑合同' : '新增合同' }
  },
  created() { this.fetchData(); this.fetchStats() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/contract/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取合同列表失败') }
      finally { this.loading = false }
    },
    async fetchStats() {
      try {
        const res = await request({ url: '/api/v1/contract/stats', method: 'get' })
        if (res.code === 200 && res.data) {
          this.statCards[0].value = res.data.totalCount ?? '-'
          this.statCards[1].value = res.data.activeCount ?? '-'
          this.statCards[2].value = res.data.monthlyExpiring ?? '-'
          this.statCards[3].value = res.data.totalAmount != null ? '¥' + Number(res.data.totalAmount).toLocaleString() : '-'
        }
      } catch { /* stats silently fail */ }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() {
      this.queryParam = { contractType: '', status: '', customerName: '', pageNum: 1, pageSize: 10 }
      this.fetchData()
    },
    openDialog(row) {
      this.editingId = row ? row.id : null
      this.form = row
        ? { ...row }
        : { contractNo: '', contractName: '', customerName: '', contractType: '', startDate: '', endDate: '', contractAmount: 0, status: '待签署', responsiblePerson: '', remark: '' }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef?.clearValidate())
    },
    openDetail(row) {
      this.detail = row
      this.detailVisible = true
    },
    confirmSave() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitting = true
        try {
          const api = this.editingId
            ? request({ url: '/api/v1/contract', method: 'put', data: { ...this.form, id: this.editingId } })
            : request({ url: '/api/v1/contract', method: 'post', data: this.form })
          const res = await api
          if (res.code === 200) {
            this.$message.success(this.editingId ? '更新成功' : '创建成功')
            this.dialogVisible = false
            this.fetchData()
            this.fetchStats()
          }
        } catch { this.$message.error('操作失败') }
        finally { this.submitting = false }
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除合同 "' + row.contractName + '"?', '提示', { type: 'warning' }).then(async () => {
        try {
          const res = await request({ url: '/api/v1/contract/' + row.id, method: 'delete' })
          if (res.code === 200) { this.$message.success('已删除'); this.fetchData(); this.fetchStats() }
        } catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    contractTypeTag(t) {
      return { '销售合同': 'primary', '服务合同': 'success', '租赁合同': 'warning', '维修合同': 'danger' }[t] || 'info'
    },
    statusTag(s) {
      return { '待签署': 'info', '执行中': 'warning', '已完成': 'success', '已终止': 'danger' }[s] || 'info'
    }
  }
}
</script>

<style scoped>
.page-container { padding: 20px; }
.page-header { position: relative; overflow: hidden; border-radius: 8px; margin-bottom: 20px; min-height: 90px; }
.page-header-bg { position: absolute; inset: 0; background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%); border-radius: 8px; }
.page-header-content { position: relative; padding: 20px 24px; color: #fff; z-index: 1; }
.page-header-content h2 { margin: 0 0 4px; font-size: 20px; font-weight: 600; }
.page-header-content h2 i { margin-right: 8px; }
.page-header-content p { margin: 0; font-size: 13px; opacity: 0.75; }

.stat-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 16px; }
.stat-card { border-radius: 8px; }
.stat-card-inner { display: flex; align-items: center; gap: 16px; }
.stat-icon-box { width: 48px; height: 48px; border-radius: 10px; display: flex; align-items: center; justify-content: center; font-size: 22px; flex-shrink: 0; }
.stat-icon-box.blue { background: var(--color-accent-bg); color: var(--color-accent); }
.stat-icon-box.orange { background: var(--color-warning-bg); color: var(--color-warning); }
.stat-icon-box.red { background: var(--color-danger-bg); color: var(--color-danger); }
.stat-icon-box.green { background: var(--color-success-bg); color: var(--color-success); }
.stat-info { display: flex; flex-direction: column; }
.stat-value { font-size: 22px; font-weight: 700; color: var(--color-text); line-height: 1.2; }
.stat-label { font-size: 13px; color: var(--color-text-tertiary); margin-top: 2px; }

.filter-bar { background: var(--color-card); padding: 16px 20px 0; border-radius: var(--radius-md); margin-bottom: var(--space-md); box-shadow: var(--shadow-sm); }
.table-container { background: var(--color-card); border-radius: var(--radius-md); padding: 16px 20px; box-shadow: var(--shadow-sm); }
.table-toolbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px; }
.table-toolbar-title { font-size: 15px; font-weight: 600; color: var(--color-text); position: relative; padding-left: 12px; }
.table-toolbar-title::before { content: ''; position: absolute; left: 0; top: 2px; bottom: 2px; width: 3px; background: var(--color-primary); border-radius: 2px; }
.pagination-wrapper { display: flex; justify-content: flex-end; margin-top: 16px; }
.amount-text { font-weight: 600; color: var(--color-danger); }
</style>

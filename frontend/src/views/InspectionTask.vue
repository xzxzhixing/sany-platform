<template>
  <div class="page-container">
    <div class="stat-grid">
      <div v-for="s in stats" :key="s.label" class="stat-card" :style="{ borderTop: '3px solid ' + s.color }">
        <span class="stat-num" :style="{ color: s.color }">{{ s.value }}</span>
        <span class="stat-label">{{ s.label }}</span>
      </div>
    </div>

    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="任务类型">
          <el-select v-model="queryParam.taskType" placeholder="全部类型" clearable style="width:140px">
            <el-option label="日常巡检" value="DAILY" />
            <el-option label="专项检查" value="SPECIAL" />
            <el-option label="安全排查" value="SAFETY" />
            <el-option label="质量检查" value="QUALITY" />
          </el-select>
        </el-form-item>
        <el-form-item label="任务状态">
          <el-select v-model="queryParam.status" placeholder="全部状态" clearable style="width:130px">
            <el-option label="待执行" value="PENDING" />
            <el-option label="执行中" value="IN_PROGRESS" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="已延期" value="DELAYED" />
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称">
          <el-input v-model="queryParam.equipmentName" placeholder="输入设备名称" clearable
                    style="width:180px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="openCreateDialog" plain>创建任务</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="taskNo" label="任务编号" width="170" fixed />
        <el-table-column prop="equipmentName" label="设备名称" min-width="130" />
        <el-table-column prop="equipmentCode" label="设备编号" width="130" />
        <el-table-column prop="customerName" label="客户名称" min-width="120" />
        <el-table-column label="任务类型" width="90" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="taskTypeTag(row.taskType)" size="small" effect="dark">
              {{ taskTypeName(row.taskType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="assignee" label="负责人" width="80" />
        <el-table-column prop="planDate" label="计划日期" width="105" />
        <el-table-column prop="actualDate" label="完成日期" width="105" />
        <el-table-column label="状态" width="85" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.status)" size="small">
              {{ statusName(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="结果" width="80" align="center">
          <template slot-scope="{ row }">
            <el-tag v-if="row.result" :type="row.result==='NORMAL'?'success':'danger'" size="small">
              {{ row.result==='NORMAL' ? '正常' : '异常' }}
            </el-tag>
            <span v-else class="no-result">-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
          <template slot-scope="{ row }">
            <el-button v-if="row.status==='PENDING'" type="primary" size="mini" plain @click="startTask(row)">开始执行</el-button>
            <el-button v-if="row.status==='IN_PROGRESS'" type="success" size="mini" plain @click="completeTask(row)">完成</el-button>
            <el-button type="danger" size="mini" plain @click="handleDelete(row)">删除</el-button>
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

    <el-dialog title="创建巡检任务" :visible.sync="dialogVisible" width="600px" :close-on-click-modal="false">
      <el-form ref="createForm" :model="createForm" :rules="rules" label-width="100px" size="small">
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="createForm.equipmentName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备编号" prop="equipmentCode">
          <el-input v-model="createForm.equipmentCode" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="createForm.customerName" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="任务类型" prop="taskType">
          <el-select v-model="createForm.taskType" placeholder="请选择" style="width:100%">
            <el-option label="日常巡检" value="DAILY" />
            <el-option label="专项检查" value="SPECIAL" />
            <el-option label="安全排查" value="SAFETY" />
            <el-option label="质量检查" value="QUALITY" />
          </el-select>
        </el-form-item>
        <el-form-item label="负责人" prop="assignee">
          <el-input v-model="createForm.assignee" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="计划日期" prop="planDate">
          <el-date-picker v-model="createForm.planDate" type="date" placeholder="选择日期"
                          value-format="yyyy-MM-dd" style="width:100%" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmCreate">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'InspectionTask',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { taskType: '', status: '', equipmentName: '', pageNum: 1, pageSize: 10 },
      tableData: [],
      stats: [
        { label: '待执行', value: 0, color: '#1890ff' },
        { label: '执行中', value: 0, color: '#faad14' },
        { label: '已完成', value: 0, color: '#52c41a' },
        { label: '已延期', value: 0, color: '#f5222d' }
      ],
      dialogVisible: false,
      createForm: { equipmentName: '', equipmentCode: '', customerName: '', taskType: '', assignee: '', planDate: '' },
      rules: {
        equipmentName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
        equipmentCode: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
        customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        taskType: [{ required: true, message: '请选择任务类型', trigger: 'change' }],
        planDate: [{ required: true, message: '请选择计划日期', trigger: 'change' }]
      }
    }
  },
  created() { this.fetchData(); this.fetchStats() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/inspection-tasks/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    async fetchStats() {
      try {
        const res = await request({ url: '/api/v1/inspection-tasks/stats', method: 'get' })
        if (res.code === 200 && res.data) {
          this.stats[0].value = res.data.pending || 0
          this.stats[1].value = res.data.inProgress || 0
          this.stats[2].value = res.data.completed || 0
          this.stats[3].value = res.data.delayed || 0
        }
      } catch {}
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() {
      this.queryParam = { taskType: '', status: '', equipmentName: '', pageNum: 1, pageSize: 10 }
      this.fetchData()
    },
    openCreateDialog() {
      this.createForm = { equipmentName: '', equipmentCode: '', customerName: '', taskType: '', assignee: '', planDate: '' }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.createForm?.clearValidate())
    },
    confirmCreate() {
      this.$refs.createForm.validate(async ok => {
        if (!ok) return
        this.submitting = true
        try {
          const res = await request({ url: '/api/v1/inspection-tasks', method: 'post', data: this.createForm })
          if (res.code === 200) { this.$message.success('创建成功'); this.dialogVisible = false; this.fetchData(); this.fetchStats() }
        } catch { this.$message.error('创建失败') }
        finally { this.submitting = false }
      })
    },
    async startTask(row) {
      try {
        const res = await request({ url: '/api/v1/inspection-tasks/' + row.id + '/start', method: 'put' })
        if (res.code === 200) { this.$message.success('任务已开始执行'); this.fetchData(); this.fetchStats() }
      } catch { this.$message.error('操作失败') }
    },
    async completeTask(row) {
      try {
        const res = await request({ url: '/api/v1/inspection-tasks/' + row.id + '/complete', method: 'put' })
        if (res.code === 200) { this.$message.success('任务已完成'); this.fetchData(); this.fetchStats() }
      } catch { this.$message.error('操作失败') }
    },
    handleDelete(row) {
      this.$confirm('确认删除巡检任务 ' + row.taskNo + '?', '提示', { type: 'warning' }).then(async () => {
        try {
          const res = await request({ url: '/api/v1/inspection-tasks/' + row.id, method: 'delete' })
          if (res.code === 200) { this.$message.success('已删除'); this.fetchData(); this.fetchStats() }
        } catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    taskTypeTag(t) { return { DAILY: 'primary', SPECIAL: 'warning', SAFETY: 'danger', QUALITY: 'success' }[t] || 'info' },
    taskTypeName(t) { return { DAILY: '日常巡检', SPECIAL: '专项检查', SAFETY: '安全排查', QUALITY: '质量检查' }[t] || t },
    statusTag(s) { return { PENDING: 'info', IN_PROGRESS: 'warning', COMPLETED: 'success', DELAYED: 'danger' }[s] || 'info' },
    statusName(s) { return { PENDING: '待执行', IN_PROGRESS: '执行中', COMPLETED: '已完成', DELAYED: '已延期' }[s] || s }
  }
}
</script>

<style scoped>
.page-container { padding: 20px; }
.stat-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 20px; }
.stat-card {
  background: var(--color-card); border-radius: var(--radius-md); padding: 20px var(--space-lg);
  display: flex; flex-direction: column; align-items: center;
  box-shadow: var(--shadow-sm); cursor: pointer; transition: all .3s;
}
.stat-card:hover { transform: translateY(-2px); box-shadow: 0 4px 16px rgba(0,0,0,.1); }
.stat-num { font-size: 32px; font-weight: 700; line-height: 1.2; }
.stat-label { font-size: 14px; color: var(--color-text-secondary); margin-top: 6px; }
.filter-bar { background: var(--color-card); border-radius: var(--radius-md); padding: 16px 20px 0; margin-bottom: var(--space-md); box-shadow: var(--shadow-sm); }
.table-container { background: var(--color-card); border-radius: var(--radius-md); padding: 16px; box-shadow: var(--shadow-sm); }
.pagination-wrapper { display: flex; justify-content: flex-end; margin-top: 16px; }
.no-result { color: var(--color-text-placeholder); }
</style>

<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="课程类型">
          <el-select v-model="queryParam.courseType" placeholder="全部类型" clearable style="width:140px">
            <el-option label="技术培训" value="TECH" />
            <el-option label="安全培训" value="SAFETY" />
            <el-option label="管理培训" value="MANAGEMENT" />
            <el-option label="产品培训" value="PRODUCT" />
          </el-select>
        </el-form-item>
        <el-form-item label="培训状态">
          <el-select v-model="queryParam.status" placeholder="全部状态" clearable style="width:130px">
            <el-option label="计划中" value="PLANNED" />
            <el-option label="进行中" value="IN_PROGRESS" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="已取消" value="CANCELED" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="openCreateDialog" plain>创建培训</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="recordNo" label="培训编号" width="170" fixed />
        <el-table-column prop="courseName" label="课程名称" min-width="160" />
        <el-table-column label="课程类型" width="90" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="courseTypeTag(row.courseType)" size="small" effect="dark">
              {{ courseTypeName(row.courseType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="trainer" label="讲师" width="80" />
        <el-table-column prop="traineeCount" label="参训人数" width="80" align="center" />
        <el-table-column label="培训日期" width="200">
          <template slot-scope="{ row }">{{ row.startDate }} ~ {{ row.endDate }}</template>
        </el-table-column>
        <el-table-column prop="duration" label="时长(小时)" width="90" align="center" />
        <el-table-column label="通过率" width="150">
          <template slot-scope="{ row }">
            <div class="pass-rate-cell">
              <el-progress :percentage="row.examPassRate || 0" :color="passRateColor(row.examPassRate)" :stroke-width="14" />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="85" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.status)" size="small">
              {{ statusName(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain @click="openEditDialog(row)">编辑</el-button>
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

    <el-dialog :title="editMode?'编辑培训':'创建培训'" :visible.sync="dialogVisible" width="640px" :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-select v-model="form.courseType" placeholder="请选择" style="width:100%">
            <el-option label="技术培训" value="TECH" />
            <el-option label="安全培训" value="SAFETY" />
            <el-option label="管理培训" value="MANAGEMENT" />
            <el-option label="产品培训" value="PRODUCT" />
          </el-select>
        </el-form-item>
        <el-form-item label="讲师" prop="trainer">
          <el-input v-model="form.trainer" placeholder="请输入讲师姓名" />
        </el-form-item>
        <el-form-item label="参训人数" prop="traineeCount">
          <el-input-number v-model="form.traineeCount" :min="1" :max="500" style="width:100%" />
        </el-form-item>
        <el-form-item label="培训日期">
          <el-date-picker v-model="dateRange" type="daterange" range-separator="至"
                          start-placeholder="开始日期" end-placeholder="结束日期"
                          value-format="yyyy-MM-dd" style="width:100%" />
        </el-form-item>
        <el-form-item label="时长(小时)" prop="duration">
          <el-input-number v-model="form.duration" :min="0.5" :max="999" :step="0.5" :precision="1" style="width:100%" />
        </el-form-item>
        <el-form-item label="通过率(%)" prop="examPassRate">
          <el-slider v-model="form.examPassRate" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="培训状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择" style="width:100%">
            <el-option label="计划中" value="PLANNED" />
            <el-option label="进行中" value="IN_PROGRESS" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="已取消" value="CANCELED" />
          </el-select>
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
  name: 'TrainingManagement',
  data() {
    return {
      loading: false, submitting: false, total: 0, editMode: false,
      queryParam: { courseType: '', status: '', pageNum: 1, pageSize: 10 },
      tableData: [],
      dialogVisible: false,
      dateRange: null,
      form: { courseName: '', courseType: '', trainer: '', traineeCount: 10, startDate: '', endDate: '', duration: 2, examPassRate: 0, status: 'PLANNED' },
      rules: {
        courseName: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
        courseType: [{ required: true, message: '请选择课程类型', trigger: 'change' }],
        trainer: [{ required: true, message: '请输入讲师姓名', trigger: 'blur' }],
        traineeCount: [{ required: true, message: '请输入参训人数', trigger: 'blur' }]
      }
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const p = { ...this.queryParam }
        const res = await request({ url: '/api/v1/training/page', method: 'get', params: p })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() {
      this.queryParam = { courseType: '', status: '', pageNum: 1, pageSize: 10 }
      this.fetchData()
    },
    openCreateDialog() {
      this.editMode = false
      this.form = { courseName: '', courseType: '', trainer: '', traineeCount: 10, startDate: '', endDate: '', duration: 2, examPassRate: 0, status: 'PLANNED' }
      this.dateRange = null
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.form?.clearValidate())
    },
    openEditDialog(row) {
      this.editMode = true
      this.form = { ...row }
      this.dateRange = row.startDate ? [row.startDate, row.endDate] : null
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.form?.clearValidate())
    },
    confirmSave() {
      this.$refs.form.validate(async ok => {
        if (!ok) return
        if (this.dateRange) { this.form.startDate = this.dateRange[0]; this.form.endDate = this.dateRange[1] }
        this.submitting = true
        try {
          const method = this.editMode ? 'put' : 'post'
          const url = this.editMode ? '/api/v1/training/' + this.form.id : '/api/v1/training'
          const res = await request({ url, method, data: this.form })
          if (res.code === 200) { this.$message.success(this.editMode ? '更新成功' : '创建成功'); this.dialogVisible = false; this.fetchData() }
        } catch { this.$message.error('保存失败') }
        finally { this.submitting = false }
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除培训记录 ' + row.recordNo + '?', '提示', { type: 'warning' }).then(async () => {
        try {
          const res = await request({ url: '/api/v1/training/' + row.id, method: 'delete' })
          if (res.code === 200) { this.$message.success('已删除'); this.fetchData() }
        } catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    courseTypeTag(t) { return { TECH: 'primary', SAFETY: 'danger', MANAGEMENT: 'warning', PRODUCT: 'success' }[t] || 'info' },
    courseTypeName(t) { return { TECH: '技术培训', SAFETY: '安全培训', MANAGEMENT: '管理培训', PRODUCT: '产品培训' }[t] || t },
    statusTag(s) { return { PLANNED: 'info', IN_PROGRESS: 'warning', COMPLETED: 'success', CANCELED: 'danger' }[s] || 'info' },
    statusName(s) { return { PLANNED: '计划中', IN_PROGRESS: '进行中', COMPLETED: '已完成', CANCELED: '已取消' }[s] || s },
    passRateColor(r) { if (r >= 80) return '#52c41a'; if (r >= 60) return '#faad14'; return '#f5222d' }
  }
}
</script>

<style scoped>
.page-container { padding: 20px; }
.filter-bar { background: var(--color-card); border-radius: var(--radius-md); padding: 16px 20px 0; margin-bottom: var(--space-md); box-shadow: var(--shadow-sm); }
.table-container { background: var(--color-card); border-radius: var(--radius-md); padding: 16px; box-shadow: var(--shadow-sm); }
.pagination-wrapper { display: flex; justify-content: flex-end; margin-top: 16px; }
.pass-rate-cell { padding: 4px 0; }
</style>

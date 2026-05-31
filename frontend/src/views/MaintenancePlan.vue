<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="计划类型">
          <el-select v-model="queryParam.planType" placeholder="全部" clearable style="width:120px">
            <el-option label="定期保养" :value="1" />
            <el-option label="年检" :value="2" />
            <el-option label="巡检" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParam.status" placeholder="全部" clearable style="width:120px">
            <el-option label="待执行" :value="0" />
            <el-option label="执行中" :value="1" />
            <el-option label="已完成" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称">
          <el-input v-model="queryParam.equipmentName" placeholder="输入设备名称" clearable style="width:170px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="openCreate" plain>新建计划</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div class="table-toolbar">
        <span class="table-toolbar-title">保养计划</span>
        <el-button type="warning" size="small" icon="el-icon-warning" plain>本月待执行：{{ pendingCount }}项</el-button>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="planNo" label="计划编号" width="170" />
        <el-table-column prop="equipmentName" label="设备名称" min-width="150" />
        <el-table-column prop="equipmentCode" label="设备编号" width="130" />
        <el-table-column prop="customerName" label="客户" width="140" />
        <el-table-column label="计划类型" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="typeTag(row.planType)" size="small">{{ row.planTypeName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="planDate" label="计划日期" width="110" />
        <el-table-column label="状态" width="90" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.status)" size="small" effect="dark">{{ row.statusName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="assigneeName" label="负责人" width="100" />
        <el-table-column label="操作" width="180" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain v-if="row.status === 0" @click="handleStart(row)">开始执行</el-button>
            <el-button type="success" size="mini" plain v-if="row.status === 1" @click="handleComplete(row)">完成</el-button>
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

    <el-dialog title="新建保养计划" :visible.sync="dialogVisible" width="580px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="设备名称" prop="equipmentName">
          <el-select v-model="form.equipmentName" filterable placeholder="选择设备" style="width:100%" @change="onEquipChange">
            <el-option v-for="e in equipOptions" :key="e.id" :label="e.equipmentName + ' (' + e.equipmentCode + ')'" :value="e.equipmentName" />
          </el-select>
        </el-form-item>
        <el-form-item label="计划类型" prop="planType">
          <el-radio-group v-model="form.planType">
            <el-radio :label="1">定期保养</el-radio>
            <el-radio :label="2">年检</el-radio>
            <el-radio :label="3">巡检</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="计划日期" prop="planDate">
          <el-date-picker v-model="form.planDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width:100%" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-select v-model="form.assigneeName" filterable placeholder="选择负责人" style="width:100%">
            <el-option v-for="e in employeeOptions" :key="e.id" :label="e.employeeName" :value="e.employeeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="备注信息" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmCreate">确认创建</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'MaintenancePlan',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { planType: null, status: null, equipmentName: '', pageNum: 1, pageSize: 10 },
      tableData: [], dialogVisible: false, equipOptions: [], employeeOptions: [],
      form: { equipmentName: '', planType: 1, planDate: '', assigneeName: '', remark: '' },
      rules: {
        equipmentName: [{ required: true, message: '请选择设备', trigger: 'change' }],
        planDate: [{ required: true, message: '请选择计划日期', trigger: 'change' }]
      }
    }
  },
  computed: {
    pendingCount() { return this.tableData.filter(x => x.status === 0).length }
  },
  created() { this.fetchData(); this.fetchOptions() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/maintenance-plan/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取失败') }
      finally { this.loading = false }
    },
    async fetchOptions() {
      try {
        const [eRes, empRes] = await Promise.all([
          request({ url: '/api/v1/equipment/list', method: 'get', params: { pageSize: 200 } }),
          request({ url: '/api/v1/employee/page', method: 'get', params: { pageSize: 50 } })
        ])
        if (eRes.code === 200) this.equipOptions = eRes.data.list || []
        if (empRes.code === 200) this.employeeOptions = empRes.data.list || []
      } catch {}
    },
    onEquipChange(name) {
      const eq = this.equipOptions.find(e => e.equipmentName === name)
      if (eq) { this.form.equipmentCode = eq.equipmentCode; this.form.customerName = eq.customerName }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { planType: null, status: null, equipmentName: '', pageNum: 1, pageSize: 10 }; this.fetchData() },
    openCreate() {
      this.form = { equipmentName: '', planType: 1, planDate: '', assigneeName: '', remark: '' }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef?.clearValidate())
    },
    confirmCreate() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitting = true
        try {
          const payload = { ...this.form, status: 0, statusName: '待执行' }
          const eq = this.equipOptions.find(e => e.equipmentName === this.form.equipmentName)
          if (eq) { payload.equipmentCode = eq.equipmentCode; payload.equipmentId = eq.id; payload.customerName = eq.customerName }
          payload.planTypeName = { 1: '定期保养', 2: '年检', 3: '巡检' }[payload.planType]
          const res = await request({ url: '/api/v1/maintenance-plan', method: 'post', data: payload })
          if (res.code === 200) { this.$message.success('创建成功'); this.dialogVisible = false; this.fetchData() }
        } catch { this.$message.error('创建失败') }
        finally { this.submitting = false }
      })
    },
    async handleStart(row) {
      try {
        await request({ url: '/api/v1/maintenance-plan', method: 'put', data: { id: row.id, status: 1, statusName: '执行中' } })
        this.$message.success('已开始执行'); this.fetchData()
      } catch { this.$message.error('操作失败') }
    },
    async handleComplete(row) {
      try {
        await request({ url: '/api/v1/maintenance-plan', method: 'put', data: { id: row.id, status: 2, statusName: '已完成' } })
        this.$message.success('已完成'); this.fetchData()
      } catch { this.$message.error('操作失败') }
    },
    handleDelete(row) {
      this.$confirm('确认删除计划?', '提示', { type: 'warning' }).then(async () => {
        try { await request({ url: '/api/v1/maintenance-plan/' + row.id, method: 'delete' }); this.$message.success('已删除'); this.fetchData() }
        catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    typeTag(t) { return {1:'primary',2:'warning',3:'info'}[t] || 'info' },
    statusTag(s) { return {0:'info',1:'warning',2:'success'}[s] || 'info' }
  }
}
</script>

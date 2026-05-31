<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="员工姓名">
          <el-input v-model="queryParam.employeeName" placeholder="输入姓名" clearable style="width:150px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="工号">
          <el-input v-model="queryParam.employeeNo" placeholder="输入工号" clearable style="width:150px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="岗位">
          <el-select v-model="queryParam.position" placeholder="全部" clearable style="width:130px">
            <el-option label="维修工程师" value="维修工程师" /><el-option label="服务经理" value="服务经理" />
            <el-option label="客服专员" value="客服专员" /><el-option label="技术支持" value="技术支持" />
            <el-option label="管理员" value="管理员" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParam.status" placeholder="全部" clearable style="width:100px">
            <el-option label="在职" :value="1" /><el-option label="休假" :value="2" /><el-option label="离职" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div class="table-toolbar">
        <span class="table-toolbar-title">员工管理</span>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="openDialog()">新增员工</el-button>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="employeeNo" label="工号" width="110" />
        <el-table-column prop="employeeName" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="60" align="center">
          <template slot-scope="{ row }">{{ row.gender === 1 ? '男' : row.gender === 0 ? '女' : '-' }}</template>
        </el-table-column>
        <el-table-column prop="position" label="岗位" width="120" />
        <el-table-column prop="phone" label="联系电话" width="140" />
        <el-table-column prop="email" label="邮箱" min-width="180" />
        <el-table-column prop="department" label="部门" width="140" />
        <el-table-column label="状态" width="80" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.status)" size="small" effect="dark">{{ statusName(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain @click="openDialog(row)">编辑</el-button>
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

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="工号" prop="employeeNo">
              <el-input v-model="form.employeeNo" placeholder="输入工号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="employeeName">
              <el-input v-model="form.employeeName" placeholder="输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="性别">
              <el-radio-group v-model="form.gender"><el-radio :label="1">男</el-radio><el-radio :label="0">女</el-radio></el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="岗位" prop="position">
              <el-select v-model="form.position" placeholder="选择岗位" style="width:100%">
                <el-option label="维修工程师" value="维修工程师" /><el-option label="服务经理" value="服务经理" />
                <el-option label="客服专员" value="客服专员" /><el-option label="技术支持" value="技术支持" />
                <el-option label="管理员" value="管理员" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="输入电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input v-model="form.email" placeholder="输入邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="部门">
          <el-input v-model="form.department" placeholder="所属部门" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status"><el-radio :label="1">在职</el-radio><el-radio :label="2">休假</el-radio><el-radio :label="3">离职</el-radio></el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="备注信息" />
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
import { getEmployeePage, createEmployee, updateEmployee, deleteEmployee } from '../api/employee'

export default {
  name: 'EmployeeList',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { employeeName: '', employeeNo: '', position: '', status: null, pageNum: 1, pageSize: 10 },
      tableData: [], dialogVisible: false, editingId: null,
      form: { employeeNo: '', employeeName: '', gender: 1, position: '', phone: '', email: '', department: '', status: 1, remark: '' },
      rules: {
        employeeNo: [{ required: true, message: '请输入工号', trigger: 'blur' }],
        employeeName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        position: [{ required: true, message: '请选择岗位', trigger: 'change' }],
        phone: [{ required: true, message: '请输入电话', trigger: 'blur' }]
      }
    }
  },
  computed: { dialogTitle() { return this.editingId ? '编辑员工' : '新增员工' } },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try { const res = await getEmployeePage(this.queryParam); if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total } }
      catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { employeeName: '', employeeNo: '', position: '', status: null, pageNum: 1, pageSize: 10 }; this.fetchData() },
    openDialog(row) {
      this.editingId = row ? row.id : null
      this.form = row ? { ...row } : { employeeNo: '', employeeName: '', gender: 1, position: '', phone: '', email: '', department: '', status: 1, remark: '' }
      this.dialogVisible = true; this.$nextTick(() => this.$refs.formRef?.clearValidate())
    },
    confirmSave() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return; this.submitting = true
        try { const api = this.editingId ? updateEmployee : createEmployee; const res = await api(this.form); if (res.code === 200) { this.$message.success(this.editingId ? '更新成功' : '创建成功'); this.dialogVisible = false; this.fetchData() } }
        catch { this.$message.error('操作失败') }
        finally { this.submitting = false }
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除员工 "' + row.employeeName + '"?', '提示', { type: 'warning' }).then(async () => {
        try { const res = await deleteEmployee(row.id); if (res.code === 200) { this.$message.success('已删除'); this.fetchData() } }
        catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    statusTag(s) { return {1:'success',2:'warning',3:'info'}[s] || 'info' },
    statusName(s) { return {1:'在职',2:'休假',3:'离职'}[s] || '未知' }
  }
}
</script>

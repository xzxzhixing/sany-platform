<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="设备名称">
          <el-input v-model="queryParam.equipmentName" placeholder="输入名称" clearable style="width:170px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="设备编号">
          <el-input v-model="queryParam.equipmentCode" placeholder="输入编号" clearable style="width:170px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParam.status" placeholder="全部" clearable style="width:110px">
            <el-option label="运行中" :value="1" />
            <el-option label="维修中" :value="2" />
            <el-option label="停用" :value="0" />
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
        <span class="table-toolbar-title">设备列表</span>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="openDialog()">新增设备</el-button>
      </div>
      <el-table :data="list" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="equipmentCode" label="设备编号" width="150" />
        <el-table-column prop="equipmentName" label="设备名称" min-width="160" />
        <el-table-column prop="equipmentModel" label="型号" width="140" />
        <el-table-column prop="category" label="分类" width="100" />
        <el-table-column prop="customerName" label="所属客户" min-width="140" />
        <el-table-column label="状态" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusType(row.status)" size="small" effect="dark">
              {{ statusName(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain @click="$router.push('/equipment/detail/' + row.id)">查看</el-button>
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

    <el-dialog title="新增设备" :visible.sync="dialogVisible" width="580px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="设备编号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="输入设备编号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="输入设备名称" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="型号">
              <el-input v-model="form.equipmentModel" placeholder="如：SY485H" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类">
              <el-select v-model="form.category" placeholder="选择分类" style="width:100%">
                <el-option label="挖掘机" value="挖掘机" />
                <el-option label="起重机" value="起重机" />
                <el-option label="混凝土泵车" value="混凝土泵车" />
                <el-option label="旋挖钻机" value="旋挖钻机" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="所属客户" prop="customerId">
          <el-select v-model="form.customerId" filterable placeholder="选择客户" style="width:100%">
            <el-option v-for="c in customerOptions" :key="c.id" :label="c.customerName" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">运行中</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmAdd">确认新增</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'EquipmentList',
  data() {
    return {
      list: [], loading: false, total: 0,
      queryParam: { equipmentName: '', equipmentCode: '', status: null, pageNum: 1, pageSize: 10 },
      dialogVisible: false, submitting: false, customerOptions: [],
      form: { equipmentCode: '', equipmentName: '', equipmentModel: '', category: '', customerId: null, status: 1 },
      rules: {
        equipmentCode: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
        equipmentName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
        customerId: [{ required: true, message: '请选择所属客户', trigger: 'change' }]
      }
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/equipment/list', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.list = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { equipmentName: '', equipmentCode: '', status: null, pageNum: 1, pageSize: 10 }; this.fetchData() },
    async openDialog() {
      this.form = { equipmentCode: '', equipmentName: '', equipmentModel: '', category: '', customerId: null, status: 1 }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef?.clearValidate())
      try {
        const res = await request({ url: '/api/v1/customer/list', method: 'get', params: { pageSize: 200 } })
        this.customerOptions = res.data?.list || []
      } catch {}
    },
    confirmAdd() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitting = true
        try {
          const res = await request({ url: '/api/v1/equipment', method: 'post', data: this.form })
          if (res.code === 200) { this.$message.success('设备添加成功'); this.dialogVisible = false; this.fetchData() }
        } catch { this.$message.error('添加失败') }
        finally { this.submitting = false }
      })
    },
    statusType(s) { return {1:'success',0:'info',2:'warning'}[s] || 'info' },
    statusName(s) { return {1:'运行中',0:'停用',2:'维修中'}[s] || '未知' }
  }
}
</script>

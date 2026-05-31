<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="客户名称">
          <el-input v-model="queryParam.customerName" placeholder="输入客户名称" clearable style="width:180px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="服务等级">
          <el-select v-model="queryParam.serviceLevel" placeholder="全部" clearable style="width:120px">
            <el-option label="VIP" :value="1" />
            <el-option label="重要" :value="2" />
            <el-option label="普通" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="queryParam.city" placeholder="输入城市" clearable style="width:140px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div class="table-toolbar">
        <span class="table-toolbar-title">客户列表</span>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="openDialog()">新增客户</el-button>
      </div>
      <el-table :data="list" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="customerName" label="客户名称" min-width="180" />
        <el-table-column prop="contactPerson" label="联系人" width="120" />
        <el-table-column prop="contactPhone" label="联系电话" width="140" />
        <el-table-column prop="city" label="城市" width="100" />
        <el-table-column prop="address" label="地址" min-width="200" />
        <el-table-column label="服务等级" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="levelType(row.serviceLevel)" size="small" effect="dark">
              {{ levelName(row.serviceLevel) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="140" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain @click="$router.push('/customer/detail/' + row.id)">详情</el-button>
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

    <el-dialog title="新增客户" :visible.sync="dialogVisible" width="580px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="输入企业名称" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactPerson">
              <el-input v-model="form.contactPerson" placeholder="输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactPhone">
              <el-input v-model="form.contactPhone" placeholder="输入电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="城市">
              <el-input v-model="form.city" placeholder="输入城市" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务等级">
              <el-select v-model="form.serviceLevel" placeholder="选择等级" style="width:100%">
                <el-option label="VIP" :value="1" />
                <el-option label="重要" :value="2" />
                <el-option label="普通" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="地址">
          <el-input v-model="form.address" type="textarea" :rows="2" placeholder="输入详细地址" />
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
  name: 'CustomerList',
  data() {
    return {
      list: [], loading: false, total: 0,
      queryParam: { customerName: '', serviceLevel: null, city: '', pageNum: 1, pageSize: 10 },
      dialogVisible: false, submitting: false,
      form: { customerName: '', contactPerson: '', contactPhone: '', city: '', address: '', serviceLevel: 3 },
      rules: {
        customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        contactPerson: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
        contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
      }
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/customer/list', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.list = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { customerName: '', serviceLevel: null, city: '', pageNum: 1, pageSize: 10 }; this.fetchData() },
    openDialog() {
      this.form = { customerName: '', contactPerson: '', contactPhone: '', city: '', address: '', serviceLevel: 3 }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef?.clearValidate())
    },
    confirmAdd() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitting = true
        try {
          const res = await request({ url: '/api/v1/customer', method: 'post', data: this.form })
          if (res.code === 200) { this.$message.success('客户添加成功'); this.dialogVisible = false; this.fetchData() }
        } catch { this.$message.error('添加失败') }
        finally { this.submitting = false }
      })
    },
    levelType(l) { return {1:'danger',2:'warning',3:'info'}[l] || 'info' },
    levelName(l) { return {1:'VIP',2:'重要',3:'普通'}[l] || '未知' }
  }
}
</script>

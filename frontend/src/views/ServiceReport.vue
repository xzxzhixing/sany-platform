<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="报告编号">
          <el-input v-model="queryParam.reportNo" placeholder="输入编号" clearable style="width:170px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="工单编号">
          <el-input v-model="queryParam.orderNo" placeholder="输入工单编号" clearable style="width:170px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="报告类型">
          <el-select v-model="queryParam.reportType" placeholder="全部" clearable style="width:120px">
            <el-option label="维修报告" :value="1" /><el-option label="保养报告" :value="2" />
            <el-option label="巡检报告" :value="3" /><el-option label="安装报告" :value="4" />
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
        <span class="table-toolbar-title">服务报告</span>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="openCreate = true">新建报告</el-button>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="reportNo" label="报告编号" width="160" />
        <el-table-column prop="orderNo" label="关联工单" width="160" />
        <el-table-column label="报告类型" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="typeTag(row.reportType)" size="small">{{ typeName(row.reportType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="报告标题" min-width="200" />
        <el-table-column prop="createByName" label="创建人" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="160" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain @click="openDetail(row)">查看</el-button>
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

    <el-dialog title="报告详情" :visible.sync="detailVisible" width="760px" :close-on-click-modal="false">
      <template v-if="detail">
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="报告编号" :span="2">{{ detail.reportNo }}</el-descriptions-item>
          <el-descriptions-item label="报告标题" :span="2">{{ detail.title }}</el-descriptions-item>
          <el-descriptions-item label="关联工单">{{ detail.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="报告类型">
            <el-tag :type="typeTag(detail.reportType)" size="small">{{ typeName(detail.reportType) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="设备名称">{{ detail.equipmentName }}</el-descriptions-item>
          <el-descriptions-item label="设备编号">{{ detail.equipmentCode }}</el-descriptions-item>
          <el-descriptions-item label="客户名称">{{ detail.customerName }}</el-descriptions-item>
          <el-descriptions-item label="创建人">{{ detail.createByName }}</el-descriptions-item>
          <el-descriptions-item label="创建时间" :span="2">{{ detail.createTime }}</el-descriptions-item>
          <el-descriptions-item label="报告内容" :span="2">
            <div class="report-content">{{ detail.content }}</div>
          </el-descriptions-item>
        </el-descriptions>
      </template>
      <span slot="footer"><el-button type="primary" @click="detailVisible=false">关闭</el-button></span>
    </el-dialog>

    <el-dialog title="新建服务报告" :visible.sync="openCreate" width="680px" :close-on-click-modal="false">
      <el-form :model="createForm" :rules="createRules" ref="createFormRef" label-width="100px" size="small">
        <el-form-item label="报告标题" prop="title">
          <el-input v-model="createForm.title" placeholder="输入报告标题" />
        </el-form-item>
        <el-form-item label="关联工单" prop="orderNo">
          <el-input v-model="createForm.orderNo" placeholder="输入工单编号" />
        </el-form-item>
        <el-form-item label="报告类型" prop="reportType">
          <el-radio-group v-model="createForm.reportType">
            <el-radio :label="1">维修报告</el-radio><el-radio :label="2">保养报告</el-radio>
            <el-radio :label="3">巡检报告</el-radio><el-radio :label="4">安装报告</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="报告内容" prop="content">
          <el-input type="textarea" :rows="8" v-model="createForm.content" placeholder="请输入报告详细内容..." />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="openCreate=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmCreate">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getReportPage, getReportDetail, createReport, deleteReport } from '../api/report'

export default {
  name: 'ServiceReport',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { reportNo: '', orderNo: '', reportType: null, pageNum: 1, pageSize: 10 },
      tableData: [], detailVisible: false, detail: null, openCreate: false,
      createForm: { title: '', orderNo: '', reportType: 1, content: '' },
      createRules: {
        title: [{ required: true, message: '请输入报告标题', trigger: 'blur' }],
        orderNo: [{ required: true, message: '请输入关联工单', trigger: 'blur' }],
        content: [{ required: true, message: '请输入报告内容', trigger: 'blur' }]
      }
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try { const res = await getReportPage(this.queryParam); if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total } }
      catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { reportNo: '', orderNo: '', reportType: null, pageNum: 1, pageSize: 10 }; this.fetchData() },
    async openDetail(row) {
      try { const res = await getReportDetail(row.id); if (res.code === 200) { this.detail = res.data; this.detailVisible = true } }
      catch { this.$message.error('获取详情失败') }
    },
    confirmCreate() {
      this.$refs.createFormRef.validate(async valid => {
        if (!valid) return; this.submitting = true
        try { const res = await createReport(this.createForm); if (res.code === 200) { this.$message.success('报告创建成功'); this.openCreate = false; this.fetchData() } }
        catch { this.$message.error('创建失败') }
        finally { this.submitting = false }
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除报告 "' + row.title + '"?', '提示', { type: 'warning' }).then(async () => {
        try { const res = await deleteReport(row.id); if (res.code === 200) { this.$message.success('已删除'); this.fetchData() } }
        catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    typeTag(t) { return {1:'primary',2:'success',3:'warning',4:'info'}[t] || 'info' },
    typeName(t) { return {1:'维修报告',2:'保养报告',3:'巡检报告',4:'安装报告'}[t] || '未知' }
  }
}
</script>

<style scoped>
.report-content { white-space: pre-wrap; line-height: 1.8; padding: 8px 0; font-size: 13px; color: var(--color-text-secondary); }
</style>

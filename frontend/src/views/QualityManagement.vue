<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-header-bg"></div>
      <div class="page-header-content">
        <h2><i class="el-icon-s-marketing"></i> 质量管理</h2>
        <p>检验记录管理与追溯</p>
      </div>
    </div>

    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="检验类型">
          <el-select v-model="queryParam.inspectionType" placeholder="全部" clearable style="width:130px">
            <el-option label="来料检验" value="来料检验" />
            <el-option label="过程检验" value="过程检验" />
            <el-option label="完工检验" value="完工检验" />
            <el-option label="出货检验" value="出货检验" />
          </el-select>
        </el-form-item>
        <el-form-item label="检验结果">
          <el-select v-model="queryParam.result" placeholder="全部" clearable style="width:120px">
            <el-option label="合格" value="合格" />
            <el-option label="不合格" value="不合格" />
            <el-option label="待复检" value="待复检" />
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称">
          <el-input v-model="queryParam.equipmentName" placeholder="输入设备名称" clearable style="width:170px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div class="table-toolbar">
        <span class="table-toolbar-title">检验记录</span>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="openDialog()">新增记录</el-button>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="recordNo" label="检验编号" width="170" />
        <el-table-column prop="equipmentName" label="设备名称" min-width="150" />
        <el-table-column prop="equipmentCode" label="设备编号" width="130" />
        <el-table-column prop="customerName" label="客户名称" min-width="140" />
        <el-table-column prop="inspector" label="检验人" width="90" />
        <el-table-column prop="inspectionDate" label="检验日期" width="110" />
        <el-table-column label="检验类型" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="inspectionTypeTag(row.inspectionType)" size="small">{{ row.inspectionType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="检验结果" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="resultTag(row.result)" size="small" effect="dark">{{ row.result }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="defectCount" label="缺陷数" width="80" align="center" />
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

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="检验编号" prop="recordNo">
          <el-input v-model="form.recordNo" placeholder="自动生成或手动输入" :disabled="!!editingId" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input v-model="form.equipmentName" placeholder="输入设备名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="customerName">
              <el-input v-model="form.customerName" placeholder="输入客户名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="检验类型" prop="inspectionType">
              <el-select v-model="form.inspectionType" placeholder="选择类型" style="width:100%">
                <el-option label="来料检验" value="来料检验" />
                <el-option label="过程检验" value="过程检验" />
                <el-option label="完工检验" value="完工检验" />
                <el-option label="出货检验" value="出货检验" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检验结果" prop="result">
              <el-select v-model="form.result" placeholder="选择结果" style="width:100%">
                <el-option label="合格" value="合格" />
                <el-option label="不合格" value="不合格" />
                <el-option label="待复检" value="待复检" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="缺陷数">
              <el-input-number v-model="form.defectCount" :min="0" :max="9999" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检验人">
              <el-input v-model="form.inspector" placeholder="检验人姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="填写检验备注" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmSave">保存</el-button>
      </span>
    </el-dialog>

    <el-dialog title="检验详情" :visible.sync="detailVisible" width="620px" :close-on-click-modal="false">
      <template v-if="detail">
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="检验编号" :span="2">{{ detail.recordNo }}</el-descriptions-item>
          <el-descriptions-item label="设备名称">{{ detail.equipmentName }}</el-descriptions-item>
          <el-descriptions-item label="设备编号">{{ detail.equipmentCode }}</el-descriptions-item>
          <el-descriptions-item label="客户名称">{{ detail.customerName }}</el-descriptions-item>
          <el-descriptions-item label="检验类型">
            <el-tag :type="inspectionTypeTag(detail.inspectionType)" size="small">{{ detail.inspectionType }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="检验结果">
            <el-tag :type="resultTag(detail.result)" size="small" effect="dark">{{ detail.result }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="检验人">{{ detail.inspector }}</el-descriptions-item>
          <el-descriptions-item label="检验日期">{{ detail.inspectionDate }}</el-descriptions-item>
          <el-descriptions-item label="缺陷数">{{ detail.defectCount }}</el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">{{ detail.remark || '无' }}</el-descriptions-item>
        </el-descriptions>
      </template>
      <span slot="footer"><el-button type="primary" @click="detailVisible=false">关闭</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'QualityManagement',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { inspectionType: '', result: '', equipmentName: '', pageNum: 1, pageSize: 10 },
      tableData: [], dialogVisible: false, detailVisible: false, editingId: null, detail: null,
      form: { recordNo: '', equipmentName: '', customerName: '', inspectionType: '', result: '', defectCount: 0, inspector: '', remark: '' },
      rules: {
        recordNo: [{ required: true, message: '请输入检验编号', trigger: 'blur' }],
        equipmentName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
        customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        inspectionType: [{ required: true, message: '请选择检验类型', trigger: 'change' }],
        result: [{ required: true, message: '请选择检验结果', trigger: 'change' }]
      }
    }
  },
  computed: {
    dialogTitle() { return this.editingId ? '编辑检验记录' : '新增检验记录' }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/quality/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() {
      this.queryParam = { inspectionType: '', result: '', equipmentName: '', pageNum: 1, pageSize: 10 }
      this.fetchData()
    },
    openDialog(row) {
      this.editingId = row ? row.id : null
      this.form = row ? { ...row } : { recordNo: '', equipmentName: '', customerName: '', inspectionType: '', result: '', defectCount: 0, inspector: '', remark: '' }
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
            ? request({ url: '/api/v1/quality', method: 'put', data: { ...this.form, id: this.editingId } })
            : request({ url: '/api/v1/quality', method: 'post', data: this.form })
          const res = await api
          if (res.code === 200) {
            this.$message.success(this.editingId ? '更新成功' : '创建成功')
            this.dialogVisible = false
            this.fetchData()
          }
        } catch { this.$message.error('操作失败') }
        finally { this.submitting = false }
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除检验记录 "' + row.recordNo + '"?', '提示', { type: 'warning' }).then(async () => {
        try {
          const res = await request({ url: '/api/v1/quality/' + row.id, method: 'delete' })
          if (res.code === 200) { this.$message.success('已删除'); this.fetchData() }
        } catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    inspectionTypeTag(t) {
      return { '来料检验': 'primary', '过程检验': 'warning', '完工检验': 'success', '出货检验': 'info' }[t] || 'info'
    },
    resultTag(r) {
      return { '合格': 'success', '不合格': 'danger', '待复检': 'warning' }[r] || 'info'
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
.filter-bar { background: var(--color-card); padding: 16px 20px 0; border-radius: var(--radius-md); margin-bottom: var(--space-md); box-shadow: var(--shadow-sm); }
.table-container { background: var(--color-card); border-radius: var(--radius-md); padding: 16px 20px; box-shadow: var(--shadow-sm); }
.table-toolbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px; }
.table-toolbar-title { font-size: 15px; font-weight: 600; color: var(--color-text); position: relative; padding-left: 12px; }
.table-toolbar-title::before { content: ''; position: absolute; left: 0; top: 2px; bottom: 2px; width: 3px; background: var(--color-primary); border-radius: 2px; }
.pagination-wrapper { display: flex; justify-content: flex-end; margin-top: 16px; }
</style>

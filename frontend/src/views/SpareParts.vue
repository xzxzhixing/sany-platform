<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="备件名称">
          <el-input v-model="queryParam.partName" placeholder="输入备件名称" clearable style="width:180px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="备件编号">
          <el-input v-model="queryParam.partCode" placeholder="输入编号" clearable style="width:160px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="库存状态">
          <el-select v-model="queryParam.stockStatus" placeholder="全部" clearable style="width:120px">
            <el-option label="充足" :value="1" />
            <el-option label="偏少" :value="2" />
            <el-option label="缺货" :value="3" />
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
        <span class="table-toolbar-title">备件清单</span>
        <el-button type="primary" icon="el-icon-plus" size="small" @click="openDialog()">新增备件</el-button>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="partCode" label="备件编号" width="150" />
        <el-table-column prop="partName" label="备件名称" min-width="160" />
        <el-table-column prop="specification" label="规格型号" width="160" />
        <el-table-column prop="unit" label="单位" width="60" align="center" />
        <el-table-column prop="stockQuantity" label="库存数量" width="100" align="center" />
        <el-table-column prop="minStock" label="最低库存" width="90" align="center" />
        <el-table-column label="库存状态" width="100" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="stockTag(row).type" size="small" effect="dark">
              {{ stockTag(row).label }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="单价(元)" width="110" align="right">
          <template slot-scope="{ row }">{{ row.price ? '¥' + row.price : '-' }}</template>
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

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="580px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="备件编号" prop="partCode">
          <el-input v-model="form.partCode" placeholder="自动生成或手动输入" />
        </el-form-item>
        <el-form-item label="备件名称" prop="partName">
          <el-input v-model="form.partName" placeholder="输入备件名称" />
        </el-form-item>
        <el-form-item label="规格型号">
          <el-input v-model="form.specification" placeholder="如：Φ20×300mm" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-select v-model="form.unit" placeholder="选择单位" style="width:120px">
            <el-option label="个" value="个" /><el-option label="件" value="件" />
            <el-option label="套" value="套" /><el-option label="箱" value="箱" /><el-option label="kg" value="kg" />
          </el-select>
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="库存数量" prop="stockQuantity">
              <el-input-number v-model="form.stockQuantity" :min="0" :max="99999" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低库存" prop="minStock">
              <el-input-number v-model="form.minStock" :min="0" :max="99999" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="单价(元)">
          <el-input-number v-model="form.price" :min="0" :precision="2" style="width:200px" />
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
import { getSparePartPage, createSparePart, updateSparePart, deleteSparePart } from '../api/sparepart'

export default {
  name: 'SpareParts',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { partName: '', partCode: '', stockStatus: null, pageNum: 1, pageSize: 10 },
      tableData: [], dialogVisible: false, editingId: null,
      form: { partCode: '', partName: '', specification: '', unit: '个', stockQuantity: 0, minStock: 10, price: 0, remark: '' },
      rules: {
        partCode: [{ required: true, message: '请输入备件编号', trigger: 'blur' }],
        partName: [{ required: true, message: '请输入备件名称', trigger: 'blur' }],
        unit: [{ required: true, message: '请选择单位', trigger: 'change' }]
      }
    }
  },
  computed: { dialogTitle() { return this.editingId ? '编辑备件' : '新增备件' } },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try { const res = await getSparePartPage(this.queryParam); if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total } }
      catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { partName: '', partCode: '', stockStatus: null, pageNum: 1, pageSize: 10 }; this.fetchData() },
    stockTag(r) { if (r.stockQuantity <= 0) return { type: 'danger', label: '缺货' }; if (r.stockQuantity <= r.minStock) return { type: 'warning', label: '偏少' }; return { type: 'success', label: '充足' } },
    openDialog(row) {
      this.editingId = row ? row.id : null
      this.form = row ? { ...row } : { partCode: '', partName: '', specification: '', unit: '个', stockQuantity: 0, minStock: 10, price: 0, remark: '' }
      this.dialogVisible = true; this.$nextTick(() => this.$refs.formRef?.clearValidate())
    },
    confirmSave() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return; this.submitting = true
        try { const api = this.editingId ? updateSparePart : createSparePart; const res = await api(this.form); if (res.code === 200) { this.$message.success(this.editingId ? '更新成功' : '创建成功'); this.dialogVisible = false; this.fetchData() } }
        catch { this.$message.error('操作失败') }
        finally { this.submitting = false }
      })
    },
    handleDelete(row) {
      this.$confirm('确认删除备件 "' + row.partName + '"?', '提示', { type: 'warning' }).then(async () => {
        try { const res = await deleteSparePart(row.id); if (res.code === 200) { this.$message.success('已删除'); this.fetchData() } }
        catch { this.$message.error('删除失败') }
      }).catch(() => {})
    }
  }
}
</script>

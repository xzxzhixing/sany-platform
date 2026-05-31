<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-header-bg"></div>
      <div class="page-header-content">
        <h2><i class="el-icon-s-home"></i> 仓储管理</h2>
        <p>库存明细与出入库记录统一管理</p>
      </div>
    </div>

    <el-tabs v-model="activeTab" @tab-click="onTabChange">
      <el-tab-pane label="库存明细" name="warehouse-items">
        <div class="filter-bar">
          <el-form :inline="true" :model="itemQuery" size="medium">
            <el-form-item label="分类">
              <el-select v-model="itemQuery.category" placeholder="全部" clearable style="width:130px">
                <el-option label="原材料" value="原材料" />
                <el-option label="半成品" value="半成品" />
                <el-option label="成品" value="成品" />
                <el-option label="包材" value="包材" />
                <el-option label="辅料" value="辅料" />
              </el-select>
            </el-form-item>
            <el-form-item label="物品名称">
              <el-input v-model="itemQuery.itemName" placeholder="输入名称" clearable style="width:170px" @keyup.enter.native="handleItemSearch" />
            </el-form-item>
            <el-form-item label="库存状态">
              <el-select v-model="itemQuery.status" placeholder="全部" clearable style="width:120px">
                <el-option label="正常" value="正常" />
                <el-option label="偏低" value="偏低" />
                <el-option label="缺货" value="缺货" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="handleItemSearch">查询</el-button>
              <el-button icon="el-icon-refresh" @click="handleItemReset">重置</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div class="table-container">
          <div class="table-toolbar">
            <span class="table-toolbar-title">库存明细</span>
            <el-button type="primary" icon="el-icon-plus" size="small" @click="openItemDialog()">新增物品</el-button>
          </div>
          <el-table :data="itemData" stripe border v-loading="itemLoading" style="width:100%" size="medium">
            <el-table-column prop="itemCode" label="物品编号" width="150" />
            <el-table-column prop="itemName" label="物品名称" min-width="150" />
            <el-table-column prop="category" label="分类" width="90" />
            <el-table-column prop="specification" label="规格型号" width="140" />
            <el-table-column prop="unit" label="单位" width="60" align="center" />
            <el-table-column label="库存数量" width="110" align="center">
              <template slot-scope="{ row }">
                <span :style="stockQuantityStyle(row)" class="stock-num">{{ row.stockQuantity }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="location" label="库位" width="100" />
            <el-table-column prop="lastInbound" label="最近入库" width="110" />
            <el-table-column label="操作" width="150" fixed="right">
              <template slot-scope="{ row }">
                <el-button type="primary" size="mini" plain @click="openItemDialog(row)">编辑</el-button>
                <el-button type="danger" size="mini" plain @click="handleItemDelete(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination-wrapper">
            <el-pagination @size-change="s=>{itemQuery.pageSize=s;itemQuery.pageNum=1;fetchItemData()}"
                           @current-change="p=>{itemQuery.pageNum=p;fetchItemData()}"
                           :current-page="itemQuery.pageNum" :page-sizes="[10,20,50,100]"
                           :page-size="itemQuery.pageSize"
                           layout="total, sizes, prev, pager, next, jumper" :total="itemTotal" background />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="出入库记录" name="warehouse-transactions">
        <div class="filter-bar">
          <el-form :inline="true" :model="txQuery" size="medium">
            <el-form-item label="类型">
              <el-select v-model="txQuery.type" placeholder="全部" clearable style="width:110px">
                <el-option label="入库" value="入库" />
                <el-option label="出库" value="出库" />
              </el-select>
            </el-form-item>
            <el-form-item label="物品名称">
              <el-input v-model="txQuery.itemName" placeholder="输入名称" clearable style="width:170px" @keyup.enter.native="handleTxSearch" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="handleTxSearch">查询</el-button>
              <el-button icon="el-icon-refresh" @click="handleTxReset">重置</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div class="table-container">
          <div class="table-toolbar">
            <span class="table-toolbar-title">出入库记录</span>
          </div>
          <el-table :data="txData" stripe border v-loading="txLoading" style="width:100%" size="medium">
            <el-table-column prop="transactionNo" label="流水编号" width="170" />
            <el-table-column label="类型" width="80" align="center">
              <template slot-scope="{ row }">
                <el-tag :type="row.type === '入库' ? 'success' : 'warning'" size="small" effect="dark">{{ row.type }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="itemCode" label="物品编号" width="140" />
            <el-table-column prop="itemName" label="物品名称" min-width="140" />
            <el-table-column prop="quantity" label="数量" width="80" align="center" />
            <el-table-column prop="operator" label="操作人" width="90" />
            <el-table-column prop="transactionDate" label="操作日期" width="110" />
            <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip />
          </el-table>
          <div class="pagination-wrapper">
            <el-pagination @size-change="s=>{txQuery.pageSize=s;txQuery.pageNum=1;fetchTxData()}"
                           @current-change="p=>{txQuery.pageNum=p;fetchTxData()}"
                           :current-page="txQuery.pageNum" :page-sizes="[10,20,50,100]"
                           :page-size="txQuery.pageSize"
                           layout="total, sizes, prev, pager, next, jumper" :total="txTotal" background />
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :title="itemDialogTitle" :visible.sync="itemDialogVisible" width="580px" :close-on-click-modal="false">
      <el-form ref="itemFormRef" :model="itemForm" :rules="itemRules" label-width="100px" size="small">
        <el-form-item label="物品编号" prop="itemCode">
          <el-input v-model="itemForm.itemCode" placeholder="自动生成或手动输入" :disabled="!!itemEditingId" />
        </el-form-item>
        <el-form-item label="物品名称" prop="itemName">
          <el-input v-model="itemForm.itemName" placeholder="输入物品名称" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="分类" prop="category">
              <el-select v-model="itemForm.category" placeholder="选择分类" style="width:100%">
                <el-option label="原材料" value="原材料" />
                <el-option label="半成品" value="半成品" />
                <el-option label="成品" value="成品" />
                <el-option label="包材" value="包材" />
                <el-option label="辅料" value="辅料" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格型号">
              <el-input v-model="itemForm.specification" placeholder="如：500×300mm" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="单位" prop="unit">
              <el-select v-model="itemForm.unit" placeholder="单位" style="width:100%">
                <el-option label="个" value="个" /><el-option label="件" value="件" />
                <el-option label="套" value="套" /><el-option label="箱" value="箱" />
                <el-option label="kg" value="kg" /><el-option label="m" value="m" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="库存数量">
              <el-input-number v-model="itemForm.stockQuantity" :min="0" :max="999999" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="最低库存">
              <el-input-number v-model="itemForm.minStock" :min="0" :max="999999" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="库位">
          <el-input v-model="itemForm.location" placeholder="如：A-01-03" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="itemForm.remark" type="textarea" :rows="2" placeholder="备注信息" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="itemDialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="itemSubmitting" @click="confirmItemSave">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'WarehouseManagement',
  data() {
    return {
      activeTab: 'warehouse-items',

      itemLoading: false, itemSubmitting: false, itemTotal: 0,
      itemQuery: { category: '', itemName: '', status: '', pageNum: 1, pageSize: 10 },
      itemData: [], itemDialogVisible: false, itemEditingId: null,
      itemForm: { itemCode: '', itemName: '', category: '', specification: '', unit: '个', stockQuantity: 0, minStock: 10, location: '', remark: '' },
      itemRules: {
        itemCode: [{ required: true, message: '请输入物品编号', trigger: 'blur' }],
        itemName: [{ required: true, message: '请输入物品名称', trigger: 'blur' }],
        category: [{ required: true, message: '请选择分类', trigger: 'change' }],
        unit: [{ required: true, message: '请选择单位', trigger: 'change' }]
      },

      txLoading: false, txTotal: 0,
      txQuery: { type: '', itemName: '', pageNum: 1, pageSize: 10 },
      txData: []
    }
  },
  computed: {
    itemDialogTitle() { return this.itemEditingId ? '编辑物品' : '新增物品' }
  },
  created() { this.fetchItemData() },
  methods: {
    async fetchItemData() {
      this.itemLoading = true
      try {
        const res = await request({ url: '/api/v1/warehouse/items/page', method: 'get', params: this.itemQuery })
        if (res.code === 200) { this.itemData = res.data.list || []; this.itemTotal = res.data.total }
      } catch { this.$message.error('获取库存列表失败') }
      finally { this.itemLoading = false }
    },
    async fetchTxData() {
      this.txLoading = true
      try {
        const res = await request({ url: '/api/v1/warehouse/transactions/page', method: 'get', params: this.txQuery })
        if (res.code === 200) { this.txData = res.data.list || []; this.txTotal = res.data.total }
      } catch { this.$message.error('获取出入库记录失败') }
      finally { this.txLoading = false }
    },
    onTabChange(tab) {
      if (tab.name === 'warehouse-items') this.fetchItemData()
      else if (tab.name === 'warehouse-transactions') this.fetchTxData()
    },
    handleItemSearch() { this.itemQuery.pageNum = 1; this.fetchItemData() },
    handleItemReset() {
      this.itemQuery = { category: '', itemName: '', status: '', pageNum: 1, pageSize: 10 }
      this.fetchItemData()
    },
    handleTxSearch() { this.txQuery.pageNum = 1; this.fetchTxData() },
    handleTxReset() {
      this.txQuery = { type: '', itemName: '', pageNum: 1, pageSize: 10 }
      this.fetchTxData()
    },
    stockQuantityStyle(row) {
      if (row.stockQuantity <= 0) return { color: '#f56c6c', fontWeight: 700 }
      if (row.minStock && row.stockQuantity <= row.minStock) return { color: '#e6a23c', fontWeight: 600 }
      return { color: '#67c23a', fontWeight: 600 }
    },
    openItemDialog(row) {
      this.itemEditingId = row ? row.id : null
      this.itemForm = row
        ? { ...row }
        : { itemCode: '', itemName: '', category: '', specification: '', unit: '个', stockQuantity: 0, minStock: 10, location: '', remark: '' }
      this.itemDialogVisible = true
      this.$nextTick(() => this.$refs.itemFormRef?.clearValidate())
    },
    confirmItemSave() {
      this.$refs.itemFormRef.validate(async valid => {
        if (!valid) return
        this.itemSubmitting = true
        try {
          const api = this.itemEditingId
            ? request({ url: '/api/v1/warehouse/items', method: 'put', data: { ...this.itemForm, id: this.itemEditingId } })
            : request({ url: '/api/v1/warehouse/items', method: 'post', data: this.itemForm })
          const res = await api
          if (res.code === 200) {
            this.$message.success(this.itemEditingId ? '更新成功' : '创建成功')
            this.itemDialogVisible = false
            this.fetchItemData()
          }
        } catch { this.$message.error('操作失败') }
        finally { this.itemSubmitting = false }
      })
    },
    handleItemDelete(row) {
      this.$confirm('确认删除物品 "' + row.itemName + '"?', '提示', { type: 'warning' }).then(async () => {
        try {
          const res = await request({ url: '/api/v1/warehouse/items/' + row.id, method: 'delete' })
          if (res.code === 200) { this.$message.success('已删除'); this.fetchItemData() }
        } catch { this.$message.error('删除失败') }
      }).catch(() => {})
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
.stock-num { font-size: 14px; }
</style>

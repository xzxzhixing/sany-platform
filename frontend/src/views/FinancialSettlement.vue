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
        <el-form-item label="服务类型">
          <el-select v-model="queryParam.serviceType" placeholder="全部类型" clearable style="width:140px">
            <el-option label="维修" :value="1" />
            <el-option label="保养" :value="2" />
            <el-option label="安装" :value="3" />
            <el-option label="巡检" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="结算状态">
          <el-select v-model="queryParam.status" placeholder="全部状态" clearable style="width:130px">
            <el-option label="待开票" :value="0" />
            <el-option label="已开票" :value="1" />
            <el-option label="已收款" :value="2" />
            <el-option label="已核销" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="客户名称">
          <el-input v-model="queryParam.customerName" placeholder="输入客户名称" clearable
                    style="width:180px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="settlementNo" label="结算编号" width="170" fixed />
        <el-table-column prop="orderNo" label="关联工单" width="150" />
        <el-table-column prop="customerName" label="客户名称" min-width="120" />
        <el-table-column prop="equipmentName" label="设备名称" min-width="130" />
        <el-table-column label="服务类型" width="80" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="serviceTypeTag(row.serviceType)" size="small" effect="dark">
              {{ serviceTypeName(row.serviceType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="serviceFee" label="服务费" width="100" align="right">
          <template slot-scope="{ row }">{{ row.serviceFee | currency }}</template>
        </el-table-column>
        <el-table-column prop="partsFee" label="备件费" width="100" align="right">
          <template slot-scope="{ row }">{{ row.partsFee | currency }}</template>
        </el-table-column>
        <el-table-column label="合计" width="110" align="right">
          <template slot-scope="{ row }">
            <span class="total-amount">{{ row.totalAmount | currency }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="billingDate" label="开票日期" width="105" />
        <el-table-column label="状态" width="85" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.status)" size="small">
              {{ statusName(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="paymentMethod" label="付款方式" width="90" />
        <el-table-column label="操作" width="230" fixed="right">
          <template slot-scope="{ row }">
            <el-button v-if="row.status===0" type="primary" size="mini" plain @click="updateStatus(row,1)">开票</el-button>
            <el-button v-if="row.status===1" type="warning" size="mini" plain @click="updateStatus(row,2)">收款</el-button>
            <el-button v-if="row.status===2" type="success" size="mini" plain @click="updateStatus(row,3)">核销</el-button>
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

    <el-dialog title="结算操作确认" :visible.sync="dialogVisible" width="480px" :close-on-click-modal="false">
      <div class="dialog-body">
        <p class="dialog-tip">确定将结算单 <strong>{{ curRow?.settlementNo }}</strong> 的状态更新为 <strong>{{ nextStatusName }}</strong>？</p>
        <el-form ref="statusForm" :model="statusForm" label-width="90px" size="small">
          <el-form-item label="备注说明">
            <el-input type="textarea" :rows="3" v-model="statusForm.remark" placeholder="可选填写备注" />
          </el-form-item>
          <el-form-item v-if="targetStatus===1" label="开票日期">
            <el-date-picker v-model="statusForm.billingDate" type="date" placeholder="选择日期"
                            value-format="yyyy-MM-dd" style="width:100%" />
          </el-form-item>
          <el-form-item v-if="targetStatus===2" label="付款方式">
            <el-select v-model="statusForm.paymentMethod" placeholder="请选择" style="width:100%">
              <el-option label="银行转账" value="银行转账" />
              <el-option label="支票" value="支票" />
              <el-option label="现金" value="现金" />
              <el-option label="承兑汇票" value="承兑汇票" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmStatus">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'FinancialSettlement',
  filters: {
    currency(v) { if (v == null) return '-'; return '¥' + Number(v).toLocaleString('zh-CN', { minimumFractionDigits: 2 }) }
  },
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { serviceType: '', status: '', customerName: '', pageNum: 1, pageSize: 10 },
      tableData: [],
      stats: [
        { label: '待开票金额', value: '¥0.00', color: '#1890ff' },
        { label: '本月已收款', value: '¥0.00', color: '#52c41a' },
        { label: '本季度总额', value: '¥0.00', color: '#722ed1' },
        { label: '应收未收', value: '¥0.00', color: '#f5222d' }
      ],
      dialogVisible: false,
      curRow: null, targetStatus: '',
      statusForm: { remark: '', billingDate: '', paymentMethod: '' }
    }
  },
  computed: {
    nextStatusName() {
      return { 0: '待开票', 1: '已开票', 2: '已收款', 3: '已核销' }[this.targetStatus] || ''
    }
  },
  created() { this.fetchData(); this.fetchStats() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/settlement/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    async fetchStats() {
      try {
        const res = await request({ url: '/api/v1/settlement/stats', method: 'get' })
        if (res.code === 200 && res.data) {
          this.stats[0].value = '¥' + (res.data.pendingAmount || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
          this.stats[1].value = '¥' + (res.data.monthReceived || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
          this.stats[2].value = '¥' + (res.data.quarterTotal || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
          this.stats[3].value = '¥' + (res.data.uncollected || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
        }
      } catch {}
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() {
      this.queryParam = { serviceType: '', status: '', customerName: '', pageNum: 1, pageSize: 10 }
      this.fetchData()
    },
    updateStatus(row, target) {
      this.curRow = row
      this.targetStatus = target
      this.statusForm = { remark: '', billingDate: '', paymentMethod: '' }
      if (target === 1) this.statusForm.billingDate = new Date()
      this.dialogVisible = true
    },
    async confirmStatus() {
      this.submitting = true
      try {
        const data = { id: this.curRow.id, status: this.targetStatus, ...this.statusForm }
        const res = await request({ url: '/api/v1/settlement/status', method: 'put', data })
        if (res.code === 200) { this.$message.success('操作成功'); this.dialogVisible = false; this.fetchData(); this.fetchStats() }
      } catch { this.$message.error('操作失败') }
      finally { this.submitting = false }
    },
    serviceTypeTag(t) { return { 1: 'danger', 2: 'success', 3: 'primary', 4: 'warning' }[t] || 'info' },
    serviceTypeName(t) { return { 1: '维修', 2: '保养', 3: '安装', 4: '巡检' }[t] || t },
    statusTag(s) { return { 0: 'info', 1: 'primary', 2: 'warning', 3: 'success' }[s] || 'info' },
    statusName(s) { return { 0: '待开票', 1: '已开票', 2: '已收款', 3: '已核销' }[s] || s }
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
.stat-num { font-size: 28px; font-weight: 700; line-height: 1.2; white-space: nowrap; }
.stat-label { font-size: 14px; color: var(--color-text-secondary); margin-top: 6px; }
.filter-bar { background: var(--color-card); border-radius: var(--radius-md); padding: 16px 20px 0; margin-bottom: var(--space-md); box-shadow: var(--shadow-sm); }
.table-container { background: var(--color-card); border-radius: var(--radius-md); padding: 16px; box-shadow: var(--shadow-sm); }
.pagination-wrapper { display: flex; justify-content: flex-end; margin-top: 16px; }
.total-amount { font-weight: 700; color: var(--color-danger); }
.dialog-body { padding: 8px 0; }
.dialog-tip { font-size: 14px; color: var(--color-text); margin-bottom: var(--space-md); line-height: var(--line-height-relaxed); }
</style>

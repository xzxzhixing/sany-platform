<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="工单状态">
          <el-select v-model="queryParam.orderStatus" placeholder="全部状态" clearable style="width:130px">
            <el-option label="待派单" :value="0" />
            <el-option label="待接单" :value="1" />
            <el-option label="处理中" :value="2" />
            <el-option label="待验收" :value="3" />
            <el-option label="已完成" :value="4" />
            <el-option label="已关闭" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="设备编号">
          <el-input v-model="queryParam.equipmentCode" placeholder="输入设备编号" clearable
                    style="width:180px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="报修时间">
          <el-date-picker v-model="dateRange" type="daterange" range-separator="至"
                          start-placeholder="开始日期" end-placeholder="结束日期"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          :default-time="['00:00:00', '23:59:59']" style="width:260px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="$router.push('/work-order/create')" plain>创建工单</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="orderNo" label="工单编号" width="170" fixed />
        <el-table-column prop="equipmentName" label="设备名称" min-width="140" />
        <el-table-column prop="equipmentCode" label="设备编号" width="130" />
        <el-table-column prop="reporterName" label="报修人" width="80" />
        <el-table-column prop="createTime" label="报修时间" width="155" />
        <el-table-column label="紧急程度" width="85" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="faultLevelTag(row.faultLevel)" size="small" effect="dark">
              {{ row.faultLevelName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="工单状态" width="95" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="statusTag(row.orderStatus)" size="small">
              {{ row.orderStatusName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="assigneeName" label="派单人员" width="90" />
        <el-table-column label="操作" width="215" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" plain @click="openHandle(row)">处理</el-button>
            <el-button type="info" size="mini" plain @click="$router.push('/work-order/detail/' + row.id)">详情</el-button>
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

    <el-dialog title="处理工单" :visible.sync="handleVisible" width="620px" :close-on-click-modal="false">
      <el-form ref="handleForm" :model="handleForm" label-width="100px" size="small">
        <el-form-item label="工单编号"><el-input v-model="handleForm.orderNo" disabled /></el-form-item>
        <el-form-item label="设备名称"><el-input v-model="handleForm.equipmentName" disabled /></el-form-item>
        <el-form-item label="故障描述">
          <el-input type="textarea" :rows="3" v-model="handleForm.faultDescription" disabled />
        </el-form-item>
        <el-form-item label="目标状态">
          <el-select v-model="handleForm.newStatus" placeholder="选择目标状态">
            <el-option label="待接单" :value="1" />
            <el-option label="处理中" :value="2" />
            <el-option label="已完成" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理详情">
          <el-input type="textarea" :rows="4" v-model="handleForm.handlingDetail"
                    placeholder="填写维修处理过程、更换备件等信息" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="handleVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmHandle">确认</el-button>
      </span>
    </el-dialog>

    <el-dialog title="工单详情" :visible.sync="detailVisible" width="720px" :close-on-click-modal="false">
      <template v-if="detail">
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="工单编号" :span="2">{{ detail.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="工单类型">{{ detail.orderTypeName }}</el-descriptions-item>
          <el-descriptions-item label="工单状态">
            <el-tag :type="statusTag(detail.orderStatus)" size="small">{{ detail.orderStatusName }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="设备名称">{{ detail.equipmentName }}</el-descriptions-item>
          <el-descriptions-item label="设备编号">{{ detail.equipmentCode }}</el-descriptions-item>
          <el-descriptions-item label="客户名称">{{ detail.customerName }}</el-descriptions-item>
          <el-descriptions-item label="报修人">{{ detail.reporterName }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ detail.reporterPhone }}</el-descriptions-item>
          <el-descriptions-item label="紧急程度">
            <el-tag :type="faultLevelTag(detail.faultLevel)" size="small" effect="dark">{{ detail.faultLevelName }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="报修时间">{{ detail.createTime }}</el-descriptions-item>
          <el-descriptions-item label="故障描述" :span="2">{{ detail.faultDescription }}</el-descriptions-item>
          <el-descriptions-item label="派单人员">{{ detail.assigneeName || '未派单' }}</el-descriptions-item>
          <el-descriptions-item label="派单时间">{{ detail.assignTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="到达时间">{{ detail.arriveTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="完工时间">{{ detail.finishTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="处理详情" :span="2">{{ detail.handlingDetail || '暂无' }}</el-descriptions-item>
        </el-descriptions>
      </template>
      <span slot="footer"><el-button type="primary" @click="detailVisible=false">关闭</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
import { getWorkOrderPage, getWorkOrderDetail, updateWorkOrderStatus, deleteWorkOrder } from '../api/workOrder'

export default {
  name: 'WorkOrderList',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { equipmentCode: '', orderStatus: null, pageNum: 1, pageSize: 10 },
      dateRange: null, tableData: [],
      handleVisible: false,
      handleForm: { id: null, orderNo: '', equipmentName: '', faultDescription: '', newStatus: null, handlingDetail: '' },
      detailVisible: false, detail: null
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const p = { ...this.queryParam }
        if (this.dateRange) { p.startTime = this.dateRange[0]; p.endTime = this.dateRange[1] }
        const res = await getWorkOrderPage(p)
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取列表失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() {
      this.queryParam = { equipmentCode: '', orderStatus: null, pageNum: 1, pageSize: 10 }
      this.dateRange = null; this.fetchData()
    },
    openHandle(row) {
      this.handleForm = { id: row.id, orderNo: row.orderNo, equipmentName: row.equipmentName,
                          faultDescription: row.faultDescription, newStatus: null, handlingDetail: '' }
      this.handleVisible = true
    },
    async confirmHandle() {
      if (!this.handleForm.newStatus) return this.$message.warning('请选择目标状态')
      this.submitting = true
      try {
        const res = await updateWorkOrderStatus({ id: this.handleForm.id, newStatus: this.handleForm.newStatus })
        if (res.code === 200) { this.$message.success('操作成功'); this.handleVisible = false; this.fetchData() }
      } catch { this.$message.error('操作失败') }
      finally { this.submitting = false }
    },
    async openDetail(row) {
      try {
        const res = await getWorkOrderDetail(row.id)
        if (res.code === 200) { this.detail = res.data; this.detailVisible = true }
      } catch { this.$message.error('获取详情失败') }
    },
    handleDelete(row) {
      this.$confirm('确认删除工单 ' + row.orderNo + '?', '提示', { type: 'warning' }).then(async () => {
        try { await deleteWorkOrder(row.id); this.$message.success('已删除'); this.fetchData() }
        catch { this.$message.error('删除失败') }
      }).catch(() => {})
    },
    statusTag(s) { return {0:'info',1:'warning',2:'primary',3:'',4:'success',5:'info',6:'danger'}[s] || 'info' },
    faultLevelTag(l) { return {1:'danger',2:'warning',3:'primary',4:'info'}[l] || 'info' }
  }
}
</script>

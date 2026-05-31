<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="设备编号">
          <el-input v-model="queryParam.equipmentCode" placeholder="输入编号" clearable style="width:170px" @keyup.enter.native="fetchData" />
        </el-form-item>
        <el-form-item label="紧急程度">
          <el-select v-model="queryParam.faultLevel" placeholder="全部" clearable style="width:120px">
            <el-option label="紧急" :value="1" />
            <el-option label="高" :value="2" />
            <el-option label="中" :value="3" />
            <el-option label="低" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="fetchData">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="$router.push('/work-order/create')" plain>创建工单</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="kanban-board">
      <div class="kanban-column" v-for="col in columns" :key="col.status">
        <div class="kanban-header" :style="{ background: col.color }">
          <span>{{ col.label }}</span>
          <span class="kanban-count">{{ grouped[col.status]?.length || 0 }}</span>
        </div>
        <div class="kanban-list">
          <div v-for="item in grouped[col.status]" :key="item.id" class="kanban-card" @click="$router.push('/work-order/detail/' + item.id)">
            <div class="kanban-card-header">
              <span class="kanban-order-no">{{ item.orderNo }}</span>
              <el-tag :type="faultLevelTag(item.faultLevel)" size="mini" effect="dark" v-if="item.faultLevel === 1">紧急</el-tag>
            </div>
            <p class="kanban-equip">{{ item.equipmentName }}</p>
            <p class="kanban-customer">{{ item.customerName }}</p>
            <div class="kanban-card-footer">
              <span>{{ item.createTime?.slice(5, 16) }}</span>
              <span v-if="item.assigneeName">{{ item.assigneeName }}</span>
            </div>
          </div>
        </div>
        <div class="empty-state" v-if="!(grouped[col.status]?.length)" style="padding:30px 10px">
          <i class="el-icon-document" style="font-size:32px"></i>
          <p style="font-size:12px">暂无工单</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'WorkOrderKanban',
  data() {
    return {
      queryParam: { equipmentCode: '', faultLevel: null },
      orders: [],
      columns: [
        { status: 0, label: '待派单', color: '#909399' },
        { status: 1, label: '待接单', color: '#faad14' },
        { status: 2, label: '处理中', color: '#1890ff' },
        { status: 3, label: '待验收', color: '#722ed1' },
        { status: 4, label: '已完成', color: '#52c41a' }
      ]
    }
  },
  computed: {
    grouped() {
      const map = {}
      this.columns.forEach(c => { map[c.status] = [] })
      this.orders.forEach(o => {
        if (map[o.orderStatus] !== undefined) map[o.orderStatus].push(o)
      })
      return map
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      try {
        const res = await request({ url: '/api/v1/work-order/page', method: 'get', params: { ...this.queryParam, pageSize: 200 } })
        if (res.code === 200) this.orders = res.data.list || []
      } catch { this.$message.error('获取失败') }
    },
    handleReset() { this.queryParam = { equipmentCode: '', faultLevel: null }; this.fetchData() },
    faultLevelTag(l) { return {1:'danger',2:'warning',3:'primary',4:'info'}[l] || 'info' }
  }
}
</script>

<style scoped>
.kanban-board { display: flex; gap: 12px; overflow-x: auto; padding-bottom: 12px; min-height: calc(100vh - 280px); }
.kanban-column { flex: 1; min-width: 220px; background: var(--color-bg-secondary); border-radius: var(--radius-md); overflow: hidden; }
.kanban-header {
  padding: 10px 14px; color: #fff; font-weight: 600; font-size: 14px;
  display: flex; justify-content: space-between; align-items: center;
}
.kanban-count {
  background: rgba(255,255,255,.3); padding: 1px 8px; border-radius: 10px; font-size: 12px;
}
.kanban-list { padding: 8px; min-height: 80px; }
.kanban-card {
  background: var(--color-card); border-radius: var(--radius-sm); padding: 10px 12px; margin-bottom: 8px;
  cursor: pointer; border: 1px solid var(--color-card-border); transition: all 0.2s;
}
.kanban-card:hover { transform: translateY(-2px); box-shadow: var(--shadow-md); }
.kanban-card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 4px; }
.kanban-order-no { font-weight: 600; font-size: var(--font-size-sm); color: var(--color-primary); }
.kanban-equip { margin: 4px 0; font-size: var(--font-size-sm); color: var(--color-text); font-weight: 500; }
.kanban-customer { margin: 2px 0; font-size: var(--font-size-sm); color: var(--color-text-tertiary); }
.kanban-card-footer { display: flex; justify-content: space-between; font-size: var(--font-size-xs); color: var(--color-text-placeholder); margin-top: 6px; }
.ghost { opacity: 0.3; }
</style>

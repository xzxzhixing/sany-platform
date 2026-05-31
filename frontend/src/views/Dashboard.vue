<template>
  <div class="dashboard">
    <div class="stat-grid">
      <el-card v-for="card in cards" :key="card.title" shadow="never" class="stat-card card-hover">
        <div class="stat-card-inner">
          <div :class="['stat-icon-box', card.color]">
            <i :class="card.icon"></i>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ card.value }}</span>
            <span class="stat-label">{{ card.title }}</span>
          </div>
        </div>
      </el-card>
    </div>

    <div class="dashboard-row">
      <div class="dashboard-main">
        <el-card shadow="never" class="chart-card">
          <div slot="header" class="card-header-flex">
            <span>工单趋势（近7天）</span>
            <el-button type="text" icon="el-icon-refresh" size="mini">刷新</el-button>
          </div>
          <div class="chart-area">
            <div v-for="(d, i) in trendData" :key="i" class="chart-bar-group">
              <span class="chart-bar-value">{{ d.count }}</span>
              <div class="chart-bar" :style="{ height: d.height + 'px', background: d.color }"></div>
              <span class="chart-bar-label">{{ d.day }}</span>
            </div>
          </div>
        </el-card>
      </div>
      <div class="dashboard-side">
        <el-card shadow="never" class="todo-card">
          <div slot="header" class="card-header-flex">
            <span>待办事项</span>
            <el-tag v-if="todos.length" size="mini" type="danger" effect="dark">{{ todos.length }}项</el-tag>
          </div>
          <div v-if="todos.length" class="todo-list">
            <div v-for="t in todos" :key="t.text" class="todo-item">
              <el-tag :type="t.type" size="mini" effect="dark" class="todo-tag">{{ t.tag }}</el-tag>
              <span class="todo-text">{{ t.text }}</span>
            </div>
          </div>
          <div v-else class="empty-state">
            <i class="el-icon-check"></i>
            <p>暂无待办事项</p>
          </div>
        </el-card>
      </div>
    </div>

    <div class="dashboard-row">
      <div class="dashboard-col-4">
        <el-card shadow="never">
          <div slot="header">快捷操作</div>
          <div class="quick-actions">
            <el-button type="primary" plain @click="$router.push('/work-order/create')" class="action-btn">
              <i class="el-icon-plus"></i><span>创建工单</span>
            </el-button>
            <el-button type="success" plain @click="$router.push('/work-order')" class="action-btn">
              <i class="el-icon-document"></i><span>工单列表</span>
            </el-button>
            <el-button type="warning" plain @click="$router.push('/spare-parts')" class="action-btn">
              <i class="el-icon-box"></i><span>备件管理</span>
            </el-button>
            <el-button type="info" plain @click="$router.push('/statistics')" class="action-btn">
              <i class="el-icon-pie-chart"></i><span>数据统计</span>
            </el-button>
          </div>
        </el-card>
      </div>
      <div class="dashboard-col-4">
        <el-card shadow="never">
          <div slot="header">工单状态分布</div>
          <div class="status-list">
            <div v-for="s in statusDist" :key="s.label" class="status-item">
              <span class="status-label">{{ s.label }}</span>
              <el-progress :percentage="s.percent" :color="s.color" :stroke-width="14" class="status-progress" />
              <span class="status-count">{{ s.count }}单</span>
            </div>
          </div>
        </el-card>
      </div>
      <div class="dashboard-col-4">
        <el-card shadow="never">
          <div slot="header">系统信息</div>
          <div class="sys-info">
            <div class="sys-row">
              <span class="sys-key">系统版本</span>
              <span class="sys-val">v2.0.0</span>
            </div>
            <div class="sys-row">
              <span class="sys-key">服务器状态</span>
              <el-tag size="mini" type="success" effect="dark">运行正常</el-tag>
            </div>
            <div class="sys-row">
              <span class="sys-key">数据库连接</span>
              <el-tag size="mini" type="success" effect="dark">正常</el-tag>
            </div>
            <div class="sys-row">
              <span class="sys-key">上次同步</span>
              <span class="sys-val">2026-05-26 10:30</span>
            </div>
            <div class="sys-row">
              <span class="sys-key">在线用户</span>
              <span class="sys-val sys-val-bold">3</span>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      cards: [
        { icon: 'el-icon-document', title: '待处理工单', value: '12', color: 'orange' },
        { icon: 'el-icon-s-data', title: '本月工单', value: '86', color: 'blue' },
        { icon: 'el-icon-success', title: '完成率', value: '94%', color: 'green' },
        { icon: 'el-icon-warning', title: '设备告警', value: '3', color: 'red' }
      ],
      todos: [
        { text: '工单 WO202605250012 待派单', tag: '紧急', type: 'danger' },
        { text: 'SY485H挖掘机保养到期', tag: '保养', type: 'warning' },
        { text: '长沙工厂本月服务报告待审核', tag: '审核', type: 'info' }
      ],
      trendData: [
        { day: '05-20', count: 8, height: 80, color: '#1890ff' },
        { day: '05-21', count: 12, height: 120, color: '#1890ff' },
        { day: '05-22', count: 6, height: 60, color: '#1890ff' },
        { day: '05-23', count: 15, height: 150, color: '#52c41a' },
        { day: '05-24', count: 10, height: 100, color: '#1890ff' },
        { day: '05-25', count: 14, height: 140, color: '#1890ff' },
        { day: '05-26', count: 9, height: 90, color: '#1890ff' }
      ],
      statusDist: [
        { label: '待处理', count: 18, percent: 25, color: '#faad14' },
        { label: '处理中', count: 24, percent: 33, color: '#1890ff' },
        { label: '已完成', count: 28, percent: 39, color: '#52c41a' },
        { label: '已关闭', count: 2, percent: 3, color: '#909399' }
      ]
    }
  }
}
</script>

<style scoped>
.dashboard { padding: 20px; }
.stat-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 16px; }
.stat-card { border-radius: var(--radius-md); }
.dashboard-row { display: flex; gap: 16px; margin-bottom: 16px; }
.dashboard-main { flex: 1; min-width: 0; }
.dashboard-side { width: 340px; flex-shrink: 0; }
.dashboard-col-4 { flex: 1; min-width: 0; }

.card-header-flex { display: flex; justify-content: space-between; align-items: center; }

.chart-area {
  height: 280px; display: flex; align-items: flex-end;
  justify-content: space-around; padding: 16px 8px 8px;
  background: var(--color-bg-secondary); border-radius: var(--radius-sm);
}
.chart-bar-group { display: flex; flex-direction: column; align-items: center; flex: 1; }
.chart-bar-value { font-size: 11px; color: var(--color-text-tertiary); margin-bottom: 4px; }
.chart-bar {
  width: 28px; border-radius: 4px 4px 0 0;
  transition: all 0.5s; cursor: pointer; position: relative;
}
.chart-bar:hover { opacity: 0.8; transform: scaleY(1.05); transform-origin: bottom; }
.chart-bar-label { font-size: 11px; color: var(--color-text-placeholder); margin-top: 6px; }

.todo-list { padding: 4px 0; }
.todo-item { display: flex; align-items: center; padding: 7px 0; line-height: 1.5; }
.todo-tag { border-radius: 10px; flex-shrink: 0; }
.todo-text { margin-left: 8px; font-size: 13px; color: var(--color-text-secondary); }

.quick-actions { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; padding: 4px 0; }
.action-btn {
  height: 58px !important; display: flex !important; flex-direction: column;
  align-items: center; justify-content: center; gap: 4px; font-size: 12px !important;
  border-radius: var(--radius-md) !important; margin: 0 !important;
  padding: 8px !important; line-height: 1.3 !important;
}
.action-btn i { font-size: 20px; margin: 0 !important; }

.status-list { padding: 8px 0; }
.status-item { display: flex; align-items: center; gap: 10px; padding: 6px 0; }
.status-label { width: 56px; font-size: 13px; color: var(--color-text-secondary); flex-shrink: 0; }
.status-progress { flex: 1; }
.status-count { width: 36px; text-align: right; font-size: 12px; color: var(--color-text-tertiary); flex-shrink: 0; }

.sys-info { font-size: 13px; color: var(--color-text-secondary); padding: 4px 0; }
.sys-row { display: flex; justify-content: space-between; align-items: center; padding: 7px 0; border-bottom: 1px dashed var(--color-border-light); }
.sys-row:last-child { border-bottom: none; }
.sys-key { color: var(--color-text-tertiary); }
.sys-val { color: var(--color-text); }
.sys-val-bold { font-weight: 600; }

@media (max-width: 1200px) {
  .stat-grid { grid-template-columns: repeat(2, 1fr); }
  .dashboard-row { flex-direction: column; }
  .dashboard-side { width: auto; }
}
</style>

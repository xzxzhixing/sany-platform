<template>
  <div class="page-container">
    <div class="stat-grid">
      <el-card v-for="s in summaryCards" :key="s.label" shadow="never" class="card-hover">
        <div class="stat-card-inner">
          <div :class="['stat-icon-box', s.color]">
            <i :class="s.icon"></i>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ s.value }}</span>
            <span class="stat-label">{{ s.label }}</span>
            <span class="stat-trend">{{ s.trend }}</span>
          </div>
        </div>
      </el-card>
    </div>

    <div class="dashboard-row">
      <div class="dashboard-col-2">
        <el-card shadow="never">
          <div slot="header">月度工单统计</div>
          <div class="chart-area">
            <div v-for="(m, i) in monthlyOrders" :key="i" class="chart-item">
              <span class="chart-val">{{ m.count }}</span>
              <div class="chart-bar" :style="{ height: m.height + 'px', background: m.color }"></div>
              <span class="chart-label">{{ m.month }}</span>
            </div>
          </div>
        </el-card>
      </div>
      <div class="dashboard-col-2">
        <el-card shadow="never">
          <div slot="header">工单状态分布</div>
          <div class="dist-list">
            <div v-for="s in statusDistribution" :key="s.label" class="dist-item">
              <span class="dist-label">{{ s.label }}</span>
              <el-progress :percentage="s.percent" :color="s.color" :stroke-width="16" class="dist-progress" />
              <span class="dist-count">{{ s.count }}单</span>
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <div class="dashboard-row">
      <div class="dashboard-col-3">
        <el-card shadow="never">
          <div slot="header">设备类型分布</div>
          <div class="dist-list">
            <div v-for="d in deviceDistribution" :key="d.name" class="dist-item">
              <span class="dist-label dist-label-sm">{{ d.name }}</span>
              <div class="dist-bar-bg">
                <div class="dist-bar-fill" :style="{ width: d.percent + '%', background: d.color }"></div>
              </div>
              <span class="dist-count">{{ d.count }}台</span>
            </div>
          </div>
        </el-card>
      </div>
      <div class="dashboard-col-3">
        <el-card shadow="never">
          <div slot="header">紧急程度统计</div>
          <div class="dist-list">
            <div v-for="l in levelDistribution" :key="l.label" class="dist-item">
              <span class="dist-label dist-label-sm">{{ l.label }}</span>
              <div class="dist-bar-bg">
                <div class="dist-bar-fill" :style="{ width: l.percent + '%', background: l.color }"></div>
              </div>
              <span class="dist-count">{{ l.count }}</span>
            </div>
          </div>
        </el-card>
      </div>
      <div class="dashboard-col-3">
        <el-card shadow="never">
          <div slot="header">本月完成率</div>
          <div class="completion-wrap">
            <el-progress type="circle" :percentage="completionRate" :stroke-width="10" width="140" color="#52c41a" />
            <div class="completion-text">
              已完成 <strong>{{ completedCount }}</strong> / {{ totalCount }} 单
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Statistics',
  computed: {
    completionRate() { return 88 }, completedCount() { return 142 }, totalCount() { return 162 }
  },
  data() {
    return {
      summaryCards: [
        { icon: 'el-icon-document', label: '总工单数', value: '1,284', color: 'blue', trend: '↑ 较上月增长 12.5%' },
        { icon: 'el-icon-success', label: '完成工单', value: '1,136', color: 'green', trend: '完成率 88.5%' },
        { icon: 'el-icon-setting', label: '设备总量', value: '486', color: 'purple', trend: '↑ 较上月新增 18 台' },
        { icon: 'el-icon-user', label: '服务客户', value: '156', color: 'cyan', trend: '↑ 较上月新增 6 家' }
      ],
      monthlyOrders: [
        { month: '1月', count: 65, height: 80, color: '#1890ff' },
        { month: '2月', count: 48, height: 60, color: '#1890ff' },
        { month: '3月', count: 92, height: 115, color: '#1890ff' },
        { month: '4月', count: 78, height: 98, color: '#1890ff' },
        { month: '5月', count: 112, height: 140, color: '#52c41a' },
        { month: '6月', count: 95, height: 119, color: '#1890ff' }
      ],
      statusDistribution: [
        { label: '待派单', count: 18, percent: 14, color: '#909399' },
        { label: '处理中', count: 32, percent: 25, color: '#1890ff' },
        { label: '待验收', count: 24, percent: 19, color: '#faad14' },
        { label: '已完成', count: 48, percent: 38, color: '#52c41a' },
        { label: '已关闭', count: 6, percent: 5, color: '#ff4d4f' }
      ],
      deviceDistribution: [
        { name: '挖掘机', count: 186, percent: 38, color: '#1890ff' },
        { name: '起重机', count: 98, percent: 20, color: '#52c41a' },
        { name: '混凝土泵车', count: 72, percent: 15, color: '#faad14' },
        { name: '旋挖钻机', count: 54, percent: 11, color: '#722ed1' },
        { name: '其他', count: 76, percent: 16, color: '#909399' }
      ],
      levelDistribution: [
        { label: '紧急', count: 28, percent: 22, color: '#ff4d4f' },
        { label: '高', count: 35, percent: 27, color: '#fa8c16' },
        { label: '中', count: 42, percent: 33, color: '#faad14' },
        { label: '低', count: 23, percent: 18, color: '#52c41a' }
      ]
    }
  }
}
</script>

<style scoped>
.stat-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 16px; }
.stat-trend { font-size: 12px; color: var(--color-text-tertiary); margin-top: 4px; }
.dashboard-row { display: flex; gap: 16px; margin-bottom: 16px; }
.dashboard-col-2 { flex: 1; min-width: 0; }
.dashboard-col-3 { flex: 1; min-width: 0; }

.chart-area {
  height: 280px; display: flex; align-items: flex-end;
  justify-content: space-around; padding: 16px 12px 8px;
  background: var(--color-bg-secondary); border-radius: var(--radius-sm);
}
.chart-item { display: flex; flex-direction: column; align-items: center; flex: 1; }
.chart-val { font-size: 11px; color: var(--color-text-tertiary); margin-bottom: 4px; }
.chart-bar { width: 32px; border-radius: 4px 4px 0 0; transition: height 0.6s; }
.chart-label { font-size: 11px; color: var(--color-text-placeholder); margin-top: 6px; }

.dist-list { padding: 8px 0; }
.dist-item { display: flex; align-items: center; gap: 10px; padding: 7px 0; }
.dist-label { width: 60px; font-size: 13px; color: var(--color-text-secondary); flex-shrink: 0; }
.dist-label-sm { width: 80px; }
.dist-progress { flex: 1; }
.dist-count { width: 40px; text-align: right; font-size: 12px; color: var(--color-text-tertiary); flex-shrink: 0; }
.dist-bar-bg { flex: 1; height: 8px; background: var(--color-border-light); border-radius: 4px; overflow: hidden; }
.dist-bar-fill { height: 100%; border-radius: 4px; transition: width 0.6s; }

.completion-wrap { display: flex; flex-direction: column; align-items: center; padding: 20px 0; }
.completion-text { margin-top: 16px; font-size: 13px; color: var(--color-text-tertiary); text-align: center; }
.completion-text strong { color: var(--color-success); font-weight: 600; }

@media (max-width: 1200px) {
  .stat-grid { grid-template-columns: repeat(2, 1fr); }
  .dashboard-row { flex-direction: column; }
}
</style>

<template>
  <div class="monitor-dashboard">
    <div class="monitor-header">
      <h1 class="monitor-title"><i class="el-icon-monitor"></i> 生产监控中心</h1>
      <div class="monitor-time">{{ currentTime }}</div>
    </div>

    <div class="stat-grid-dark">
      <div v-for="s in stats" :key="s.label" class="stat-card-dark">
        <div class="stat-card-glow" :style="{ background: s.glow }"></div>
        <i :class="s.icon" class="stat-card-icon" :style="{ color: s.color }"></i>
        <div class="stat-card-info">
          <span class="stat-card-value" :style="{ color: s.color }">{{ s.value }}</span>
          <span class="stat-card-label">{{ s.label }}</span>
        </div>
      </div>
    </div>

    <div class="monitor-row">
      <div class="monitor-col-6">
        <div class="panel-dark">
          <div class="panel-header"><i class="el-icon-data-line"></i> 设备运行状态</div>
          <div class="panel-body" style="display:flex;align-items:center;justify-content:center;height:260px">
            <div class="donut-container">
              <svg viewBox="0 0 120 120" class="donut-svg">
                <circle cx="60" cy="60" r="50" fill="none" stroke="var(--color-border-light)" stroke-width="14" />
                <circle cx="60" cy="60" r="50" fill="none" stroke="var(--color-success)" stroke-width="14"
                        :stroke-dasharray="donutDash('running')" stroke-dashoffset="0" transform="rotate(-90 60 60)"
                        stroke-linecap="round" />
                <circle cx="60" cy="60" r="50" fill="none" stroke="var(--color-warning)" stroke-width="14"
                        :stroke-dasharray="donutDash('idle')" :stroke-dashoffset="donutOffset('running')"
                        transform="rotate(-90 60 60)" stroke-linecap="round" />
                <circle cx="60" cy="60" r="50" fill="none" stroke="var(--color-danger)" stroke-width="14"
                        :stroke-dasharray="donutDash('fault')" :stroke-dashoffset="donutOffset('running','idle')"
                        transform="rotate(-90 60 60)" stroke-linecap="round" />
              </svg>
              <div class="donut-center">
                <span class="donut-total">{{ deviceTotal }}</span>
                <span class="donut-label">总设备</span>
              </div>
            </div>
            <div class="donut-legend">
              <div v-for="d in deviceDist" :key="d.label" class="legend-item">
                <span class="legend-dot" :style="{ background: d.color }"></span>
                <span class="legend-text">{{ d.label }}</span>
                <span class="legend-count">{{ d.count }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="monitor-col-6">
        <div class="panel-dark">
          <div class="panel-header"><i class="el-icon-timer"></i> 今日工单动态</div>
          <div class="panel-body timeline-body">
            <div v-if="orderTimeline.length" class="timeline">
              <div v-for="(item, i) in orderTimeline" :key="i" class="timeline-item">
                <div class="timeline-dot" :style="{ background: item.color }"></div>
                <div class="timeline-content">
                  <div class="timeline-title">{{ item.title }}</div>
                  <div class="timeline-desc">{{ item.desc }}</div>
                  <div class="timeline-time">{{ item.time }}</div>
                </div>
              </div>
            </div>
            <div v-else class="empty-state">
              <i class="el-icon-document"></i>
              <p>暂无今日工单</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="monitor-row">
      <div class="monitor-col-4">
        <div class="panel-dark">
          <div class="panel-header"><i class="el-icon-warning"></i> 实时报警 <el-tag size="mini" type="danger" effect="dark" class="alarm-badge">{{ alarms.length }}</el-tag></div>
          <div class="panel-body alarm-body">
            <div v-if="alarms.length" class="alarm-list">
              <div v-for="(a, i) in alarms" :key="i" class="alarm-item" :class="'alarm-' + a.level">
                <span class="alarm-level">{{ a.level==='CRITICAL'?'紧急':a.level==='WARNING'?'警告':'提示' }}</span>
                <span class="alarm-text">{{ a.message }}</span>
                <span class="alarm-time">{{ a.time }}</span>
              </div>
            </div>
            <div v-else class="empty-state">
              <i class="el-icon-success" style="color:var(--color-success);font-size:36px"></i>
              <p style="color:var(--color-success)">当前无报警</p>
            </div>
          </div>
        </div>
      </div>
      <div class="monitor-col-4">
        <div class="panel-dark">
          <div class="panel-header"><i class="el-icon-medal"></i> 设备健康度</div>
          <div class="panel-body health-body">
            <div v-if="deviceHealth.length" class="health-list">
              <div v-for="(h, i) in deviceHealth" :key="i" class="health-item">
                <div class="health-top">
                  <span class="health-name">{{ h.name }}</span>
                  <span class="health-score" :style="{ color: healthColor(h.score) }">{{ h.score }}%</span>
                </div>
                <el-progress :percentage="h.score" :color="healthColor(h.score)" :stroke-width="10" />
              </div>
            </div>
            <div v-else class="empty-state">
              <i class="el-icon-data-board"></i>
              <p>暂无健康度数据</p>
            </div>
          </div>
        </div>
      </div>
      <div class="monitor-col-4">
        <div class="panel-dark">
          <div class="panel-header"><i class="el-icon-setting"></i> 系统运行状态</div>
          <div class="panel-body sys-body">
            <div v-for="sys in systemStatus" :key="sys.name" class="sys-status-item">
              <span class="sys-name">{{ sys.name }}</span>
              <div class="sys-indicator">
                <span class="sys-dot" :class="sys.status === 'normal' ? 'dot-green' : sys.status === 'warning' ? 'dot-yellow' : 'dot-red'"></span>
                <span class="sys-label" :style="{ color: sys.status === 'normal' ? 'var(--color-success)' : sys.status === 'warning' ? 'var(--color-warning)' : 'var(--color-danger)' }">
                  {{ sys.status === 'normal' ? '正常' : sys.status === 'warning' ? '告警' : '故障' }}
                </span>
              </div>
              <span class="sys-value">{{ sys.value }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'ProductionMonitor',
  data() {
    return {
      currentTime: '',
      timer: null,
      stats: [
        { label: '在线设备', value: 0, icon: 'el-icon-connection', color: '#059669', glow: 'rgba(5,150,105,.15)' },
        { label: '运行率', value: '0%', icon: 'el-icon-data-line', color: '#4f46e5', glow: 'rgba(79,70,229,.15)' },
        { label: '今日报警', value: 0, icon: 'el-icon-warning', color: '#dc2626', glow: 'rgba(220,38,38,.15)' },
        { label: '在线工程师', value: 0, icon: 'el-icon-user', color: '#d97706', glow: 'rgba(217,119,6,.15)' }
      ],
      deviceDist: [],
      deviceTotal: 0,
      orderTimeline: [],
      alarms: [],
      deviceHealth: [],
      systemStatus: [
        { name: '数据库', status: 'normal', value: '已连接' },
        { name: '消息队列', status: 'normal', value: '正常' },
        { name: '文件服务', status: 'normal', value: '正常' },
        { name: '网关服务', status: 'normal', value: '正常' },
        { name: 'AI分析服务', status: 'normal', value: '运行中' }
      ]
    }
  },
  created() {
    this.fetchAll(); this.updateTime()
    this.timer = setInterval(() => { this.updateTime(); this.fetchAll() }, 30000)
  },
  beforeDestroy() { clearInterval(this.timer) },
  methods: {
    updateTime() {
      const d = new Date()
      this.currentTime = d.toLocaleDateString('zh-CN') + ' ' + d.toLocaleTimeString('zh-CN', { hour12: false })
    },
    async fetchAll() {
      this.fetchStats(); this.fetchDeviceDist(); this.fetchTimeline()
      this.fetchAlarms(); this.fetchHealth()
    },
    async fetchStats() {
      try {
        const res = await request({ url: '/api/v1/dashboard/stats', method: 'get' })
        if (res.code === 200 && res.data) {
          this.stats[0].value = res.data.onlineDevices ?? 0
          this.stats[1].value = (res.data.runRate ?? 0) + '%'
          this.stats[2].value = res.data.todayAlarms ?? 0
          this.stats[3].value = res.data.onlineEngineers ?? 0
        }
      } catch {}
    },
    async fetchDeviceDist() {
      try {
        const res = await request({ url: '/api/v1/dashboard/device-dist', method: 'get' })
        if (res.code === 200 && res.data) {
          this.deviceDist = res.data.dist || []
          this.deviceTotal = res.data.total || 0
        }
      } catch {}
    },
    async fetchTimeline() {
      try {
        const res = await request({ url: '/api/v1/dashboard/timeline', method: 'get' })
        if (res.code === 200) this.orderTimeline = res.data || []
      } catch {}
    },
    async fetchAlarms() {
      try {
        const res = await request({ url: '/api/v1/dashboard/alarms', method: 'get' })
        if (res.code === 200) this.alarms = res.data || []
      } catch {}
    },
    async fetchHealth() {
      try {
        const res = await request({ url: '/api/v1/dashboard/device-health', method: 'get' })
        if (res.code === 200) this.deviceHealth = res.data || []
      } catch {}
    },
    donutDash(key) {
      const total = this.deviceTotal || 1
      const item = this.deviceDist.find(d => d.key === key)
      if (!item) return '0 314'
      const pct = item.count / total
      return (pct * 314) + ' 314'
    },
    donutOffset(...keys) {
      const total = this.deviceTotal || 1
      let offset = 0
      for (const k of keys) {
        const item = this.deviceDist.find(d => d.key === k)
        if (item) offset += (item.count / total) * 314
      }
      return -offset
    },
    healthColor(s) {
      if (s >= 85) return '#059669'
      if (s >= 70) return '#d97706'
      return '#dc2626'
    }
  }
}
</script>

<style scoped>
.monitor-dashboard {
  padding: var(--space-lg); min-height: calc(100vh - var(--topbar-height));
  background: var(--color-bg-secondary);
  color: var(--color-text);
}
.monitor-header {
  display: flex; justify-content: space-between; align-items: center; margin-bottom: var(--space-lg);
}
.monitor-title {
  font-size: 22px; font-weight: 600; margin: 0;
  background: linear-gradient(90deg, var(--color-success), var(--color-primary));
  -webkit-background-clip: text; -webkit-text-fill-color: transparent;
  background-clip: text;
}
.monitor-title i { margin-right: 8px; }
.monitor-time { font-size: var(--font-size-base); color: var(--color-text-tertiary); font-family: monospace; letter-spacing: 1px; }

.stat-grid-dark {
  display: grid; grid-template-columns: repeat(4, 1fr); gap: var(--space-md); margin-bottom: var(--space-lg);
}
.stat-card-dark {
  position: relative; overflow: hidden;
  background: var(--color-bg-card);
  border: 1px solid var(--color-card-border); border-radius: var(--radius-lg);
  padding: 22px var(--space-lg); display: flex; align-items: center; gap: var(--space-md);
  backdrop-filter: blur(10px); transition: all var(--transition-normal); cursor: pointer;
}
.stat-card-dark:hover { transform: translateY(-2px); border-color: var(--color-primary); box-shadow: var(--shadow-md); }
.stat-card-glow {
  position: absolute; top: -50%; right: -20%;
  width: 140px; height: 140px; border-radius: 50%; filter: blur(40px); opacity: .5;
  pointer-events: none; transition: all .5s;
}
.stat-card-dark:hover .stat-card-glow { opacity: .8; width: 160px; height: 160px; }
.stat-card-icon { font-size: 32px; position: relative; z-index: 1; }
.stat-card-info { display: flex; flex-direction: column; position: relative; z-index: 1; }
.stat-card-value { font-size: 28px; font-weight: 700; line-height: 1.2; }
.stat-card-label { font-size: var(--font-size-sm); color: var(--color-text-tertiary); margin-top: 2px; }

.monitor-row { display: flex; gap: var(--space-md); margin-bottom: var(--space-md); }
.monitor-col-6 { flex: 1; min-width: 0; width: 50%; }
.monitor-col-4 { flex: 1; min-width: 0; width: 33.33%; }

.panel-dark {
  background: var(--color-bg-card);
  border: 1px solid var(--color-card-border); border-radius: var(--radius-lg); overflow: hidden;
  backdrop-filter: blur(10px); height: 100%;
}
.panel-header {
  padding: 14px var(--space-lg); font-size: var(--font-size-base); font-weight: 600;
  border-bottom: 1px solid var(--color-border-light);
  background: var(--color-bg-secondary);
  display: flex; align-items: center; gap: var(--space-sm);
  color: var(--color-text);
}
.panel-header i { font-size: var(--font-size-lg); color: var(--color-primary); }
.panel-body { padding: var(--space-md) var(--space-lg); }

.donut-container { position: relative; width: 140px; height: 140px; flex-shrink: 0; }
.donut-svg { width: 100%; height: 100%; }
.donut-center {
  position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);
  text-align: center;
}
.donut-total { display: block; font-size: 26px; font-weight: 700; color: var(--color-text); line-height: 1.1; }
.donut-label { font-size: var(--font-size-xs); color: var(--color-text-tertiary); }
.donut-legend { margin-left: var(--space-lg); display: flex; flex-direction: column; gap: 10px; }
.legend-item { display: flex; align-items: center; gap: var(--space-sm); }
.legend-dot { width: 10px; height: 10px; border-radius: 50%; flex-shrink: 0; }
.legend-text { font-size: var(--font-size-sm); color: var(--color-text-secondary); }
.legend-count { font-size: var(--font-size-sm); font-weight: 600; color: var(--color-text); margin-left: auto; }

.timeline-body { max-height: 260px; overflow-y: auto; padding: var(--space-sm) var(--space-lg); }
.timeline { position: relative; padding-left: var(--space-lg); }
.timeline::before {
  content: ''; position: absolute; left: 5px; top: 4px; bottom: 4px;
  width: 2px; background: linear-gradient(to bottom, var(--color-primary), var(--color-success));
}
.timeline-item { position: relative; padding-bottom: var(--space-md); }
.timeline-item:last-child { padding-bottom: 0; }
.timeline-dot {
  position: absolute; left: -17px; top: 4px;
  width: 10px; height: 10px; border-radius: 50%;
  border: 2px solid var(--color-bg-secondary); box-shadow: 0 0 6px currentColor;
}
.timeline-title { font-size: var(--font-size-sm); font-weight: 600; color: var(--color-text); }
.timeline-desc { font-size: var(--font-size-xs); color: var(--color-text-secondary); margin-top: 2px; }
.timeline-time { font-size: var(--font-size-xs); color: var(--color-text-tertiary); margin-top: 2px; }

.alarm-body { max-height: 220px; overflow-y: auto; padding: var(--space-sm) var(--space-lg); }
.alarm-list { display: flex; flex-direction: column; gap: 6px; }
.alarm-item {
  display: flex; align-items: center; gap: var(--space-sm);
  padding: var(--space-sm) 12px; border-radius: var(--radius-sm); font-size: var(--font-size-sm);
  background: var(--color-bg-secondary);
}
.alarm-CRITICAL { border-left: 3px solid var(--color-danger); background: var(--color-danger-bg); }
.alarm-WARNING { border-left: 3px solid var(--color-warning); background: var(--color-warning-bg); }
.alarm-INFO { border-left: 3px solid var(--color-primary); background: var(--color-primary-bg); }
.alarm-level { font-weight: 600; flex-shrink: 0; width: 36px; }
.alarm-CRITICAL .alarm-level { color: var(--color-danger); }
.alarm-WARNING .alarm-level { color: var(--color-warning); }
.alarm-INFO .alarm-level { color: var(--color-primary); }
.alarm-text { flex: 1; color: var(--color-text-secondary); overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.alarm-time { flex-shrink: 0; color: var(--color-text-tertiary); font-size: var(--font-size-xs); }

.health-body { max-height: 220px; overflow-y: auto; padding: var(--space-sm) var(--space-lg); }
.health-list { display: flex; flex-direction: column; gap: 12px; }
.health-top { display: flex; justify-content: space-between; margin-bottom: 4px; }
.health-name { font-size: var(--font-size-sm); color: var(--color-text-secondary); }
.health-score { font-size: var(--font-size-sm); font-weight: 600; color: var(--color-text); }

.sys-body { padding: 12px var(--space-lg); }
.sys-status-item {
  display: flex; align-items: center; padding: 10px 0;
  border-bottom: 1px solid var(--color-border-light);
}
.sys-status-item:last-child { border-bottom: none; }
.sys-name { width: 100px; font-size: var(--font-size-sm); color: var(--color-text-secondary); }
.sys-indicator { flex: 1; display: flex; align-items: center; gap: 6px; }
.sys-dot { width: 8px; height: 8px; border-radius: 50%; display: inline-block; }
.dot-green { background: var(--color-success); box-shadow: 0 0 8px rgba(5,150,105,.5); }
.dot-yellow { background: var(--color-warning); box-shadow: 0 0 8px rgba(217,119,6,.5); }
.dot-red { background: var(--color-danger); box-shadow: 0 0 8px rgba(220,38,38,.5); }
.sys-label { font-size: var(--font-size-sm); color: var(--color-text-secondary); }
.sys-value { font-size: var(--font-size-sm); color: var(--color-text-tertiary); }

.empty-state { text-align: center; padding: 30px 0; }
.empty-state i { font-size: 32px; color: var(--color-text-tertiary); display: block; margin-bottom: var(--space-sm); }
.empty-state p { font-size: var(--font-size-sm); color: var(--color-text-tertiary); margin: 0; }

.alarm-badge { margin-left: auto; }

.monitor-dashboard::-webkit-scrollbar { width: 4px; }
.monitor-dashboard::-webkit-scrollbar-thumb { background: var(--color-border-strong); border-radius: 4px; }
.monitor-dashboard::-webkit-scrollbar-track { background: transparent; }

@media (max-width: 1200px) {
  .stat-grid-dark { grid-template-columns: repeat(2, 1fr); }
  .monitor-row { flex-direction: column; }
  .monitor-col-6, .monitor-col-4 { width: auto; }
}
</style>

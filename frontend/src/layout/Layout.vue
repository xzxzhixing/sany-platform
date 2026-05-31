<template>
  <div class="layout-container">
    <div class="sidebar" :class="{ collapsed }">
      <div class="sidebar-logo">
        <div class="logo-glow"></div>
        <div class="logo-icon">S</div>
        <span v-show="!collapsed" class="logo-text">三一智能服务</span>
      </div>
      <div class="sidebar-body">
        <div class="menu-section" v-show="!collapsed">核心业务</div>
        <el-menu
          :default-active="activeMenu"
          :collapse="collapsed"
          background-color="transparent"
          text-color="var(--sidebar-text)"
          active-text-color="#818cf8"
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/dashboard">
            <i class="el-icon-s-data"></i>
            <span slot="title">首页看板</span>
          </el-menu-item>
          <el-menu-item index="/prod-monitor">
            <i class="el-icon-monitor"></i>
            <span slot="title">生产监控</span>
          </el-menu-item>
          <el-menu-item index="/work-order">
            <i class="el-icon-document"></i>
            <span slot="title">工单管理</span>
          </el-menu-item>
          <el-menu-item index="/work-order/kanban">
            <i class="el-icon-view"></i>
            <span slot="title">工单看板</span>
          </el-menu-item>
          <el-menu-item index="/inspection-task">
            <i class="el-icon-search"></i>
            <span slot="title">巡检任务</span>
          </el-menu-item>
          <el-menu-item index="/quality">
            <i class="el-icon-circle-check"></i>
            <span slot="title">质量管理</span>
          </el-menu-item>
        </el-menu>

        <div class="menu-section" v-show="!collapsed">资源管理</div>
        <el-menu
          :default-active="activeMenu"
          :collapse="collapsed"
          background-color="transparent"
          text-color="var(--sidebar-text)"
          active-text-color="#818cf8"
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/equipment">
            <i class="el-icon-setting"></i>
            <span slot="title">设备管理</span>
          </el-menu-item>
          <el-menu-item index="/customer">
            <i class="el-icon-user"></i>
            <span slot="title">客户管理</span>
          </el-menu-item>
          <el-menu-item index="/spare-parts">
            <i class="el-icon-box"></i>
            <span slot="title">备件管理</span>
          </el-menu-item>
          <el-menu-item index="/warehouse">
            <i class="el-icon-s-home"></i>
            <span slot="title">仓储管理</span>
          </el-menu-item>
          <el-menu-item v-if="userRole === 'admin'" index="/contract">
            <i class="el-icon-document-copy"></i>
            <span slot="title">合同管理</span>
          </el-menu-item>
          <el-menu-item v-if="userRole === 'admin'" index="/employee">
            <i class="el-icon-s-custom"></i>
            <span slot="title">员工管理</span>
          </el-menu-item>
        </el-menu>

        <div class="menu-section" v-show="!collapsed">服务保障</div>
        <el-menu
          :default-active="activeMenu"
          :collapse="collapsed"
          background-color="transparent"
          text-color="var(--sidebar-text)"
          active-text-color="#818cf8"
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/service-report">
            <i class="el-icon-tickets"></i>
            <span slot="title">服务报告</span>
          </el-menu-item>
          <el-menu-item index="/maintenance-plan">
            <i class="el-icon-date"></i>
            <span slot="title">保养计划</span>
          </el-menu-item>
          <el-menu-item v-if="userRole === 'admin'" index="/financial">
            <i class="el-icon-coin"></i>
            <span slot="title">财务结算</span>
          </el-menu-item>
          <el-menu-item index="/training">
            <i class="el-icon-reading"></i>
            <span slot="title">培训管理</span>
          </el-menu-item>
          <el-menu-item index="/knowledge">
            <i class="el-icon-collection"></i>
            <span slot="title">知识库</span>
          </el-menu-item>
        </el-menu>

        <div class="menu-section" v-show="!collapsed">运营管理</div>
        <el-menu
          :default-active="activeMenu"
          :collapse="collapsed"
          background-color="transparent"
          text-color="var(--sidebar-text)"
          active-text-color="#818cf8"
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/statistics">
            <i class="el-icon-pie-chart"></i>
            <span slot="title">数据统计</span>
          </el-menu-item>
          <el-menu-item index="/evaluation">
            <i class="el-icon-star-on"></i>
            <span slot="title">客户评价</span>
          </el-menu-item>
          <el-menu-item index="/notifications">
            <i class="el-icon-bell"></i>
            <span slot="title">消息通知</span>
          </el-menu-item>
          <el-menu-item v-if="userRole === 'admin'" index="/operation-log">
            <i class="el-icon-s-order"></i>
            <span slot="title">操作日志</span>
          </el-menu-item>
          <el-menu-item v-if="userRole === 'admin'" index="/settings">
            <i class="el-icon-s-tools"></i>
            <span slot="title">系统设置</span>
          </el-menu-item>
        </el-menu>
      </div>
      <div class="sidebar-footer" v-show="!collapsed">
        <div class="footer-line"></div>
        <span class="footer-text">三一重工 © 2026</span>
      </div>
    </div>

    <div class="main-area" :class="{ 'sidebar-collapsed': collapsed }">
      <div class="topbar">
        <div class="topbar-left">
          <i :class="collapsed ? 'el-icon-s-unfold' : 'el-icon-s-fold'" class="toggle-btn" @click="collapsed = !collapsed"></i>
          <div class="breadcrumb-divider"></div>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ breadcrumbName }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="topbar-right">
          <div class="theme-toggle" @click="toggleTheme" :title="isDark ? '切换为浅色模式' : '切换为深色模式'">
            <i :class="isDark ? 'el-icon-sunny' : 'el-icon-moon'"></i>
          </div>
          <el-badge :value="notifCount" :hidden="notifCount === 0" class="notif-badge" type="danger">
            <i class="el-icon-bell topbar-icon" @click="$router.push('/notifications')"></i>
          </el-badge>
          <div class="topbar-divider"></div>
          <div class="sys-status">
            <span class="status-pulse"></span>
            <span class="status-text">系统运行中</span>
          </div>
          <div class="topbar-divider"></div>
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="user-info">
              <div class="user-avatar">{{ userAvatar }}</div>
              <span class="user-name">{{ userName }}</span>
              <span class="role-badge" :class="userRole">{{ userRole === 'admin' ? '管理员' : '工程师' }}</span>
              <i class="el-icon-arrow-down el-icon--right" style="color:#64748b"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="center" icon="el-icon-user">个人中心</el-dropdown-item>
              <el-dropdown-item command="settings" icon="el-icon-s-tools">个人设置</el-dropdown-item>
              <el-dropdown-item command="logout" icon="el-icon-switch-button" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
      <div class="content-area">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request'
import { getTheme, setTheme } from '../utils/theme'

export default {
  name: 'Layout',
  data() {
    return {
      collapsed: false,
      notifCount: 0,
      userName: '管理员',
      userAvatar: 'A',
      userRole: 'admin',
      pollTimer: null,
      theme: getTheme()
    }
  },
  computed: {
    activeMenu() {
      const path = this.$route.path
      if (path.startsWith('/work-order')) return '/work-order'
      if (path.startsWith('/equipment')) return '/equipment'
      if (path.startsWith('/customer')) return '/customer'
      return path
    },
    breadcrumbName() {
      const map = {
        '/dashboard': '首页看板', '/prod-monitor': '生产监控',
        '/work-order': '工单管理', '/work-order/create': '创建工单', '/work-order/kanban': '工单看板',
        '/inspection-task': '巡检任务', '/quality': '质量管理',
        '/equipment': '设备管理', '/warehouse': '仓储管理',
        '/customer': '客户管理', '/spare-parts': '备件管理', '/contract': '合同管理',
        '/service-report': '服务报告', '/maintenance-plan': '保养计划',
        '/financial': '财务结算', '/training': '培训管理', '/knowledge': '知识库',
        '/statistics': '数据统计', '/evaluation': '客户评价', '/employee': '员工管理',
        '/notifications': '消息通知', '/operation-log': '操作日志',
        '/user-center': '个人中心', '/settings': '系统设置'
      }
      const path = this.$route.path
      for (const [k, v] of Object.entries(map)) {
        if (path.startsWith(k)) return v
      }
      if (path.startsWith('/work-order/detail')) return '工单详情'
      if (path.startsWith('/equipment/detail')) return '设备详情'
      if (path.startsWith('/customer/detail')) return '客户详情'
      return '未知页面'
    },
    isDark() {
      return this.theme === 'dark'
    }
  },
  created() {
    const stored = localStorage.getItem('sany_user');
    if (stored) {
      const u = JSON.parse(stored)
      this.userName = u.realName || u.username || '管理员'
      this.userAvatar = this.userName.charAt(0)
      this.userRole = u.role || 'admin'
    }
    this.fetchNotifCount()
    this.pollTimer = setInterval(() => this.fetchNotifCount(), 30000)
  },
  beforeDestroy() { if (this.pollTimer) clearInterval(this.pollTimer) },
  methods: {
    async fetchNotifCount() {
      try {
        const res = await request({ url: '/api/v1/notification/page', method: 'get', params: { pageSize: 100 } })
        if (res.code === 200) {
          const list = res.data.list || []
          this.notifCount = list.filter(n => !n.isRead).length
        }
      } catch {}
    },
    toggleTheme() {
      this.theme = this.theme === 'dark' ? 'light' : 'dark'
      setTheme(this.theme)
    },
    handleCommand(cmd) {
      if (cmd === 'center') this.$router.push('/user-center')
      else if (cmd === 'settings') this.$router.push('/settings')
      else if (cmd === 'logout') {
        localStorage.removeItem('sany_user')
        this.$router.push('/login')
        this.$message.success('已退出登录')
      }
    }
  }
}
</script>

<style scoped>
.layout-container { height: 100vh; display: flex; background: var(--color-bg); }

/* ===== Sidebar ===== */
.sidebar {
  position: fixed; left: 0; top: 0; bottom: 0; z-index: 100;
  width: var(--sidebar-width); background: var(--sidebar-bg);
  transition: width var(--transition-normal);
  display: flex; flex-direction: column;
  box-shadow: var(--shadow-xl);
}
.sidebar.collapsed { width: var(--sidebar-collapsed-width); }

.sidebar-logo {
  height: var(--topbar-height); display: flex; align-items: center;
  padding: 0 var(--space-md); white-space: nowrap; flex-shrink: 0;
  position: relative; overflow: hidden;
  border-bottom: 1px solid var(--sidebar-divider);
}
.logo-glow {
  position: absolute; left: -20px; top: -20px; width: 80px; height: 80px;
  background: radial-gradient(circle, rgba(99,102,241,.15), transparent 70%);
  pointer-events: none;
}
.logo-icon {
  width: 32px; height: 32px; border-radius: 8px;
  background: linear-gradient(135deg, #4f46e5, #0891b2);
  color: #fff; font-size: 16px; font-weight: 700;
  display: flex; align-items: center; justify-content: center; flex-shrink: 0;
  box-shadow: 0 0 20px rgba(99,102,241,.3);
}
.logo-text {
  color: #e2e8f0; font-size: var(--font-size-lg); font-weight: var(--font-weight-semibold);
  margin-left: 10px; letter-spacing: 1px;
  background: linear-gradient(90deg, #818cf8, #22d3ee);
  -webkit-background-clip: text; -webkit-text-fill-color: transparent;
}

.sidebar-body { flex: 1; overflow-y: auto; overflow-x: hidden; }
.sidebar-body::-webkit-scrollbar { width: 3px; }
.sidebar-body::-webkit-scrollbar-thumb { background: var(--sidebar-divider); border-radius: 2px; }

.menu-section {
  font-size: 10px; font-weight: var(--font-weight-semibold); letter-spacing: 2px;
  color: var(--sidebar-section-text); padding: var(--space-md) 20px 4px;
  text-transform: uppercase;
}

.sidebar-menu { background: transparent !important; border-right: none; }
.sidebar-menu .el-menu-item {
  margin: 1px 8px; border-radius: var(--radius-sm); padding: 0 12px !important;
  height: 38px; line-height: 38px; transition: all var(--transition-fast);
  position: relative; font-size: var(--font-size-base);
}
.sidebar-menu .el-menu-item i {
  font-size: 16px; color: var(--sidebar-text);
  transition: all var(--transition-fast);
}
.sidebar-menu .el-menu-item:hover {
  background: var(--sidebar-item-hover-bg);
}
.sidebar-menu .el-menu-item:hover span { color: #e2e8f0; }
.sidebar-menu .el-menu-item.is-active {
  background: var(--sidebar-item-active-bg) !important;
}
.sidebar-menu .el-menu-item.is-active::before {
  content: ''; position: absolute; left: -8px; top: 50%; transform: translateY(-50%);
  width: 3px; height: 18px; border-radius: 0 3px 3px 0;
  background: #818cf8;
  box-shadow: 0 0 10px rgba(129,140,248,.4);
}
.sidebar-menu .el-menu-item.is-active span { color: #fff; }
.sidebar-menu:not(.el-menu--collapse) { width: var(--sidebar-width); }

.sidebar-footer {
  padding: 12px var(--space-lg); flex-shrink: 0;
}
.footer-line {
  height: 1px; background: linear-gradient(90deg, transparent, var(--sidebar-divider), transparent);
  margin-bottom: 10px;
}
.footer-text { font-size: var(--font-size-xs); color: rgba(148,163,184,.25); }

/* ===== Main Area ===== */
.main-area {
  flex: 1; margin-left: var(--sidebar-width); transition: margin-left var(--transition-normal);
  display: flex; flex-direction: column; min-width: 0;
}
.main-area.sidebar-collapsed { margin-left: var(--sidebar-collapsed-width); }

/* ===== Topbar ===== */
.topbar {
  height: var(--topbar-height);
  background: var(--color-bg-topbar);
  backdrop-filter: blur(8px);
  display: flex; align-items: center; justify-content: space-between;
  padding: 0 var(--space-lg);
  border-bottom: 1px solid var(--color-border);
  position: sticky; top: 0; z-index: 50; flex-shrink: 0;
}
.topbar-left { display: flex; align-items: center; gap: 12px; }
.toggle-btn {
  font-size: 18px; cursor: pointer; color: var(--color-text-tertiary);
  transition: all var(--transition-fast); padding: 4px;
  border-radius: var(--radius-xs);
}
.toggle-btn:hover { color: var(--color-primary); background: var(--color-primary-bg); }
.breadcrumb-divider {
  width: 1px; height: 20px; background: var(--color-border);
}

.topbar-right { display: flex; align-items: center; gap: 12px; }

.theme-toggle {
  width: 34px; height: 34px; display: flex; align-items: center; justify-content: center;
  cursor: pointer; border-radius: var(--radius-sm); transition: all var(--transition-fast);
  color: var(--color-text-tertiary); font-size: 17px;
}
.theme-toggle:hover { background: var(--color-bg-secondary); color: var(--color-primary); }

.topbar-icon {
  font-size: 18px; color: var(--color-text-tertiary);
  cursor: pointer; transition: all var(--transition-fast); padding: 4px;
  border-radius: var(--radius-xs);
}
.topbar-icon:hover { color: var(--color-primary); background: var(--color-primary-bg); }
.notif-badge :deep(.el-badge__content) {
  top: 6px; right: 4px; cursor: pointer;
  background: var(--color-danger);
}
.topbar-divider {
  width: 1px; height: 20px; background: var(--color-border);
}

.sys-status {
  display: flex; align-items: center; gap: 6px;
  padding: 4px 10px; border-radius: var(--radius-xs);
  background: var(--color-success-bg);
  border: 1px solid rgba(5,150,105,0.12);
}
.status-pulse {
  width: 6px; height: 6px; border-radius: 50%;
  background: var(--color-success);
  animation: pulse 2s infinite;
}
@keyframes pulse {
  0%, 100% { box-shadow: 0 0 0 0 rgba(5,150,105,.4); }
  50% { box-shadow: 0 0 0 6px rgba(5,150,105,0); }
}
.status-text { font-size: var(--font-size-xs); color: var(--color-success); letter-spacing: 0.5px; }

.user-info {
  cursor: pointer; display: flex; align-items: center; gap: 8px;
  padding: 4px 8px; border-radius: var(--radius-sm); transition: all var(--transition-fast);
}
.user-info:hover { background: var(--color-bg-secondary); }
.user-avatar {
  width: 30px; height: 30px; border-radius: 50%;
  background: linear-gradient(135deg, #4f46e5, #0891b2);
  color: #fff; font-size: 13px; font-weight: var(--font-weight-semibold);
  display: flex; align-items: center; justify-content: center;
  box-shadow: 0 0 12px rgba(99,102,241,.25);
}
.user-name { font-size: var(--font-size-base); color: var(--color-text); font-weight: var(--font-weight-medium); }
.role-badge {
  font-size: 10px; font-weight: var(--font-weight-semibold); padding: 1px 6px; border-radius: 3px;
  letter-spacing: 0.5px; line-height: 1.6;
}
.role-badge.admin { background: var(--color-primary-bg); color: var(--color-primary-dark); border: 1px solid rgba(79,70,229,.2); }
.role-badge.engineer { background: rgba(139,92,246,.1); color: #7c3aed; border: 1px solid rgba(139,92,246,.15); }

/* ===== Content ===== */
.content-area {
  flex: 1; overflow-y: auto;
  background: var(--color-bg);
}
</style>

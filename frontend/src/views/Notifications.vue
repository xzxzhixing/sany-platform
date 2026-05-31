<template>
  <div class="page-container">
    <el-card shadow="never" class="page-card">
      <div class="page-header">
        <h3><i class="el-icon-bell" style="margin-right:6px"></i>消息通知</h3>
        <div>
          <el-badge :value="unreadCount" :hidden="!unreadCount" class="unread-badge">
            <el-button size="small" icon="el-icon-reading" @click="markAllRead" :disabled="!unreadCount" plain>全部已读</el-button>
          </el-badge>
        </div>
      </div>
      <div v-loading="loading" class="notif-body-wrap">
        <template v-if="tableData.length">
          <div v-for="item in tableData" :key="item.id"
               class="notif-item" :class="{ unread: !item.isRead }"
               @click="handleRead(item)">
            <div class="notif-icon">
              <i :class="iconMap[item.type] || 'el-icon-bell'" :style="{ color: colorMap[item.type] || '#909399' }"></i>
            </div>
            <div class="notif-body">
              <div class="notif-header">
                <span class="notif-title">{{ item.title }}</span>
                <span class="notif-time">{{ item.createTime }}</span>
              </div>
              <div class="notif-content">{{ item.content }}</div>
            </div>
            <div class="notif-actions">
              <el-button type="text" size="mini" icon="el-icon-delete" @click.stop="handleDelete(item)">删除</el-button>
            </div>
          </div>
        </template>
        <div v-else class="empty-state">
          <i class="el-icon-bell"></i>
          <p>暂无通知消息</p>
        </div>
      </div>
      <div class="pagination-wrapper" v-if="total > 0">
        <el-pagination @current-change="p=>{queryParam.pageNum=p;fetchData()}"
                       :current-page="queryParam.pageNum" :page-size="queryParam.pageSize"
                       layout="total, prev, pager, next" :total="total" small background />
      </div>
    </el-card>
  </div>
</template>

<script>
import { getNotificationPage, readNotification, readAllNotification, deleteNotification } from '../api/notification'

export default {
  name: 'Notifications',
  data() {
    return {
      loading: false, total: 0, unreadCount: 0,
      queryParam: { pageNum: 1, pageSize: 15 }, tableData: [],
      iconMap: { 1: 'el-icon-warning', 2: 'el-icon-info', 3: 'el-icon-success', 4: 'el-icon-s-order' },
      colorMap: { 1: '#ff4d4f', 2: '#1890ff', 3: '#52c41a', 4: '#faad14' }
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await getNotificationPage(this.queryParam)
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total; this.unreadCount = this.tableData.filter(n => !n.isRead).length }
      } catch {}
      finally { this.loading = false }
    },
    async handleRead(item) {
      if (item.isRead) return
      try { await readNotification(item.id); item.isRead = true; this.unreadCount = Math.max(0, this.unreadCount - 1) }
      catch {}
    },
    async markAllRead() {
      try { await readAllNotification(); this.tableData.forEach(n => { n.isRead = true }); this.unreadCount = 0; this.$message.success('已全部标记为已读') }
      catch { this.$message.error('操作失败') }
    },
    async handleDelete(item) {
      try { await deleteNotification(item.id); this.$message.success('已删除'); this.fetchData() }
      catch { this.$message.error('删除失败') }
    }
  }
}
</script>

<style scoped>
.unread-badge :deep(.el-badge__content) { top: 8px; right: 6px; }
.notif-body-wrap { min-height: 200px; }
</style>

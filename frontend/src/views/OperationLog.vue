<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="操作人">
          <el-input v-model="queryParam.operator" placeholder="输入操作人" clearable style="width:150px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item label="操作类型">
          <el-input v-model="queryParam.action" placeholder="输入操作类型" clearable style="width:150px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div class="table-toolbar">
        <span class="table-toolbar-title">操作日志</span>
        <el-tag type="info" size="small">仅展示最近30天记录</el-tag>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="createTime" label="操作时间" width="170" />
        <el-table-column prop="operator" label="操作人" width="110" />
        <el-table-column prop="action" label="操作类型" width="120">
          <template slot-scope="{ row }">
            <el-tag :type="actionTag(row.action)" size="small">{{ row.action }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="target" label="操作对象" width="160" />
        <el-table-column prop="detail" label="详情" min-width="250" show-overflow-tooltip />
        <el-table-column prop="ip" label="IP地址" width="140" />
      </el-table>
      <div class="pagination-wrapper">
        <el-pagination @size-change="s=>{queryParam.pageSize=s;queryParam.pageNum=1;fetchData()}"
                       @current-change="p=>{queryParam.pageNum=p;fetchData()}"
                       :current-page="queryParam.pageNum" :page-sizes="[10,20,50,100]"
                       :page-size="queryParam.pageSize"
                       layout="total, sizes, prev, pager, next, jumper" :total="total" background />
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'OperationLog',
  data() {
    return {
      loading: false, total: 0,
      queryParam: { operator: '', action: '', pageNum: 1, pageSize: 15 },
      tableData: []
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/operation-log/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { operator: '', action: '', pageNum: 1, pageSize: 15 }; this.fetchData() },
    actionTag(a) {
      const map = { '登录': '', '创建': 'success', '新增': 'success', '编辑': 'primary', '派单': 'warning', '完成': 'success', '删除': 'danger', '修改': 'primary', '评价': 'warning' }
      for (const k of Object.keys(map)) { if (a.includes(k)) return map[k] }
      return 'info'
    }
  }
}
</script>

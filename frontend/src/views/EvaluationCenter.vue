<template>
  <div class="page-container">
    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="工单编号">
          <el-input v-model="queryParam.orderNo" placeholder="输入工单编号" clearable style="width:170px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div class="table-toolbar">
        <span class="table-toolbar-title">客户评价</span>
        <el-rate v-model="filterRating" :allow-half="true" disabled show-score score-template="{value}分" style="display:inline-flex;align-items:center"></el-rate>
      </div>
      <el-table :data="tableData" stripe border v-loading="loading" style="width:100%" size="medium">
        <el-table-column prop="orderNo" label="工单编号" width="170" />
        <el-table-column prop="customerName" label="客户名称" width="150" />
        <el-table-column prop="equipmentName" label="设备名称" min-width="150" />
        <el-table-column label="评分" width="160" align="center">
          <template slot-scope="{ row }">
            <el-rate v-model="row.rating" disabled show-score score-template="{value}分" style="display:inline-flex"></el-rate>
          </template>
        </el-table-column>
        <el-table-column prop="ratingRemark" label="评价内容" min-width="220" show-overflow-tooltip />
        <el-table-column prop="evaluator" label="评价人" width="100" />
        <el-table-column prop="evaluateTime" label="评价时间" width="160" />
      </el-table>
      <div class="pagination-wrapper" v-if="total > 0">
        <el-pagination @current-change="p=>{queryParam.pageNum=p;fetchData()}"
                       :current-page="queryParam.pageNum" :page-size="queryParam.pageSize"
                       layout="total, prev, pager, next" :total="total" background />
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'EvaluationCenter',
  data() {
    return {
      loading: false, total: 0, filterRating: 0,
      queryParam: { orderNo: '', pageNum: 1, pageSize: 10 },
      tableData: []
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/evaluation/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.tableData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() { this.queryParam = { orderNo: '', pageNum: 1, pageSize: 10 }; this.fetchData() }
  }
}
</script>

<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-header-bg"></div>
      <div class="page-header-content">
        <h2><i class="el-icon-notebook-2"></i> 知识库</h2>
        <p>维修指南、操作手册、故障案例等知识沉淀</p>
      </div>
    </div>

    <div class="filter-bar">
      <el-form :inline="true" :model="queryParam" size="medium">
        <el-form-item label="分类">
          <el-select v-model="queryParam.category" placeholder="全部分类" clearable style="width:140px">
            <el-option label="全部" value="" />
            <el-option label="维修指南" value="维修指南" />
            <el-option label="操作手册" value="操作手册" />
            <el-option label="故障案例" value="故障案例" />
            <el-option label="培训资料" value="培训资料" />
            <el-option label="安全规范" value="安全规范" />
          </el-select>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="queryParam.title" placeholder="输入文章标题" clearable style="width:200px" @keyup.enter.native="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="openDialog()" plain>新增文章</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div v-loading="loading" class="card-grid">
      <div v-if="cardData.length" class="card-list">
        <div v-for="item in cardData" :key="item.id" class="kb-card" @click="openDetail(item)">
          <div class="kb-card-header">
            <el-tag :type="categoryTag(item.category)" size="mini" effect="dark" class="kb-category-tag">{{ item.category }}</el-tag>
            <div class="kb-card-meta-top">
              <span class="kb-view-count"><i class="el-icon-view"></i> {{ item.viewCount || 0 }}</span>
            </div>
          </div>
          <h3 class="kb-card-title">{{ item.title }}</h3>
          <p class="kb-card-summary">{{ item.summary || '暂无摘要' }}</p>
          <div class="kb-card-tags" v-if="item.tags && item.tags.length">
            <el-tag v-for="tag in item.tags.slice(0, 3)" :key="tag" size="mini" type="info" plain>{{ tag }}</el-tag>
          </div>
          <div class="kb-card-footer">
            <span class="kb-card-author"><i class="el-icon-user"></i> {{ item.author || '未知' }}</span>
            <span class="kb-card-date"><i class="el-icon-date"></i> {{ item.publishDate }}</span>
            <el-tag :type="item.status === '已发布' ? 'success' : 'info'" size="mini" plain>{{ item.status || '草稿' }}</el-tag>
          </div>
        </div>
      </div>
      <div v-else-if="!loading" class="empty-state">
        <i class="el-icon-document"></i>
        <p>暂无知识文章</p>
        <el-button type="primary" icon="el-icon-plus" @click="openDialog()" plain>新增第一篇文章</el-button>
      </div>

      <div class="pagination-wrapper" v-if="total > queryParam.pageSize">
        <el-pagination @size-change="s=>{queryParam.pageSize=s;queryParam.pageNum=1;fetchData()}"
                       @current-change="p=>{queryParam.pageNum=p;fetchData()}"
                       :current-page="queryParam.pageNum" :page-sizes="[12,24,48]"
                       :page-size="queryParam.pageSize"
                       layout="total, sizes, prev, pager, next, jumper" :total="total" background />
      </div>
    </div>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="650px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small">
        <el-form-item label="文章标题" prop="title">
          <el-input v-model="form.title" placeholder="输入文章标题" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="分类" prop="category">
              <el-select v-model="form.category" placeholder="选择分类" style="width:100%">
                <el-option label="维修指南" value="维修指南" />
                <el-option label="操作手册" value="操作手册" />
                <el-option label="故障案例" value="故障案例" />
                <el-option label="培训资料" value="培训资料" />
                <el-option label="安全规范" value="安全规范" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-select v-model="form.status" placeholder="选择状态" style="width:100%">
                <el-option label="草稿" value="草稿" />
                <el-option label="已发布" value="已发布" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="摘要" prop="summary">
          <el-input v-model="form.summary" type="textarea" :rows="2" placeholder="文章摘要" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="6" placeholder="文章内容（支持Markdown格式）" />
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="form.tags" multiple filterable allow-create default-first-option placeholder="输入标签后回车" style="width:100%">
            <el-option v-for="t in tagOptions" :key="t" :label="t" :value="t" />
          </el-select>
        </el-form-item>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="作者">
              <el-input v-model="form.author" placeholder="作者姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布时间">
              <el-date-picker v-model="form.publishDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="confirmSave">保存</el-button>
      </span>
    </el-dialog>

    <el-dialog title="文章详情" :visible.sync="detailVisible" width="720px" :close-on-click-modal="false">
      <template v-if="detail">
        <div class="detail-header">
          <el-tag :type="categoryTag(detail.category)" size="small" effect="dark">{{ detail.category }}</el-tag>
          <h2 class="detail-title">{{ detail.title }}</h2>
          <div class="detail-meta">
            <span><i class="el-icon-user"></i> {{ detail.author || '未知' }}</span>
            <span><i class="el-icon-date"></i> {{ detail.publishDate }}</span>
            <span><i class="el-icon-view"></i> {{ detail.viewCount || 0 }}次阅读</span>
            <el-tag :type="detail.status === '已发布' ? 'success' : 'info'" size="mini" plain>{{ detail.status }}</el-tag>
          </div>
        </div>
        <div class="detail-tags" v-if="detail.tags && detail.tags.length">
          <el-tag v-for="tag in detail.tags" :key="tag" size="mini" type="info" plain style="margin-right:6px">{{ tag }}</el-tag>
        </div>
        <div class="detail-summary" v-if="detail.summary">
          <strong>摘要：</strong>{{ detail.summary }}
        </div>
        <div class="detail-content" v-html="renderedContent"></div>
      </template>
      <span slot="footer">
        <el-button @click="detailVisible=false">关闭</el-button>
        <el-button type="primary" v-if="detail" @click="openDialog(detail)" plain>编辑</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'KnowledgeBase',
  data() {
    return {
      loading: false, submitting: false, total: 0,
      queryParam: { category: '', title: '', pageNum: 1, pageSize: 12 },
      cardData: [], dialogVisible: false, detailVisible: false, editingId: null, detail: null,
      form: { title: '', category: '', summary: '', content: '', tags: [], author: '', publishDate: '', status: '草稿' },
      tagOptions: ['液压系统', '电气系统', '发动机', '底盘', '保养', '安全', '操作技巧'],
      rules: {
        title: [{ required: true, message: '请输入文章标题', trigger: 'blur' }],
        category: [{ required: true, message: '请选择分类', trigger: 'change' }],
        summary: [{ required: true, message: '请输入摘要', trigger: 'blur' }],
        content: [{ required: true, message: '请输入文章内容', trigger: 'blur' }]
      }
    }
  },
  computed: {
    dialogTitle() { return this.editingId ? '编辑文章' : '新增文章' },
    renderedContent() {
      if (!this.detail || !this.detail.content) return ''
      return this.detail.content.replace(/\n/g, '<br>')
    }
  },
  created() { this.fetchData() },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/knowledge/page', method: 'get', params: this.queryParam })
        if (res.code === 200) { this.cardData = res.data.list || []; this.total = res.data.total }
      } catch { this.$message.error('获取知识库失败') }
      finally { this.loading = false }
    },
    handleSearch() { this.queryParam.pageNum = 1; this.fetchData() },
    handleReset() {
      this.queryParam = { category: '', title: '', pageNum: 1, pageSize: 12 }
      this.fetchData()
    },
    openDialog(row) {
      this.detailVisible = false
      this.editingId = row ? row.id : null
      this.form = row
        ? { ...row, tags: row.tags || [] }
        : { title: '', category: '', summary: '', content: '', tags: [], author: '', publishDate: '', status: '草稿' }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef?.clearValidate())
    },
    openDetail(row) {
      this.detail = row
      this.detailVisible = true
    },
    confirmSave() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitting = true
        try {
          const api = this.editingId
            ? request({ url: '/api/v1/knowledge', method: 'put', data: { ...this.form, id: this.editingId } })
            : request({ url: '/api/v1/knowledge', method: 'post', data: this.form })
          const res = await api
          if (res.code === 200) {
            this.$message.success(this.editingId ? '更新成功' : '创建成功')
            this.dialogVisible = false
            this.fetchData()
          }
        } catch { this.$message.error('操作失败') }
        finally { this.submitting = false }
      })
    },
    categoryTag(c) {
      return { '维修指南': 'primary', '操作手册': 'success', '故障案例': 'danger', '培训资料': 'warning', '安全规范': 'info' }[c] || 'info'
    }
  }
}
</script>

<style scoped>
.page-container { padding: 20px; }
.page-header { position: relative; overflow: hidden; border-radius: 8px; margin-bottom: 20px; min-height: 90px; }
.page-header-bg { position: absolute; inset: 0; background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%); border-radius: 8px; }
.page-header-content { position: relative; padding: 20px 24px; color: #fff; z-index: 1; }
.page-header-content h2 { margin: 0 0 4px; font-size: 20px; font-weight: 600; }
.page-header-content h2 i { margin-right: 8px; }
.page-header-content p { margin: 0; font-size: 13px; opacity: 0.75; }
.filter-bar { background: var(--color-card); padding: 16px 20px 0; border-radius: var(--radius-md); margin-bottom: var(--space-lg); box-shadow: var(--shadow-sm); }

.card-grid { min-height: 400px; }
.card-list { display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 16px; }
.kb-card {
  background: var(--color-card); border-radius: var(--radius-md); padding: var(--space-lg); cursor: pointer;
  box-shadow: var(--shadow-sm); transition: all 0.25s ease;
  display: flex; flex-direction: column; border: 1px solid var(--color-card-border);
}
.kb-card:hover { transform: translateY(-3px); box-shadow: var(--shadow-lg); border-color: var(--color-primary); }
.kb-card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
.kb-card-meta-top { font-size: var(--font-size-sm); color: var(--color-text-tertiary); }
.kb-view-count i { margin-right: 3px; }
.kb-card-title { margin: 0 0 8px; font-size: var(--font-size-lg); font-weight: 600; color: var(--color-text); line-height: 1.4; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.kb-card-summary { margin: 0 0 12px; font-size: var(--font-size-sm); color: var(--color-text-tertiary); line-height: 1.6; flex: 1; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden; }
.kb-card-tags { margin-bottom: 12px; display: flex; flex-wrap: wrap; gap: 4px; }
.kb-card-footer { display: flex; align-items: center; gap: 12px; font-size: var(--font-size-sm); color: var(--color-text-tertiary); padding-top: 12px; border-top: 1px solid var(--color-border-light); flex-wrap: wrap; }
.kb-card-author i, .kb-card-date i { margin-right: 3px; }

.empty-state { display: flex; flex-direction: column; align-items: center; justify-content: center; padding: 80px 20px; color: var(--color-text-tertiary); }
.empty-state i { font-size: 56px; margin-bottom: 12px; color: var(--color-border-strong); }
.empty-state p { font-size: var(--font-size-base); margin: 0 0 16px; }

.detail-header { margin-bottom: var(--space-md); }
.detail-title { margin: 10px 0 8px; font-size: 22px; font-weight: 600; color: var(--color-text); }
.detail-meta { display: flex; gap: var(--space-md); font-size: var(--font-size-sm); color: var(--color-text-tertiary); align-items: center; flex-wrap: wrap; }
.detail-meta i { margin-right: 3px; }
.detail-tags { margin-bottom: 12px; }
.detail-summary { background: var(--color-bg-secondary); padding: 12px var(--space-md); border-radius: var(--radius-sm); font-size: var(--font-size-base); color: var(--color-text-secondary); margin-bottom: var(--space-md); line-height: var(--line-height-relaxed); }
.detail-content { font-size: var(--font-size-base); color: var(--color-text); line-height: 1.8; padding: 8px 0; }

.pagination-wrapper { display: flex; justify-content: center; margin-top: 24px; }
</style>

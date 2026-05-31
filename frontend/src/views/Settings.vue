<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :md="14" :xs="24">
        <el-card shadow="never" class="page-card settings-card">
          <div class="page-header"><h3>基本设置</h3></div>
          <div class="page-body">
            <el-form label-width="140px" size="small" class="settings-form">
              <el-form-item label="系统名称">
                <el-input value="三一工业智能服务平台" />
              </el-form-item>
              <el-form-item label="系统语言">
                <el-select value="zh-CN" style="width:200px">
                  <el-option label="简体中文" value="zh-CN" />
                  <el-option label="English" value="en" />
                </el-select>
              </el-form-item>
              <el-form-item label="工单自动派单">
                <el-switch :value="true" />
              </el-form-item>
              <el-divider />
              <el-form-item label="保养提前提醒">
                <el-input-number :value="3" :min="1" :max="30" /> 天
              </el-form-item>
              <el-form-item label="每页显示条数">
                <el-input-number :value="10" :min="5" :max="100" /> 条
              </el-form-item>
              <el-form-item label="工单超时提醒">
                <el-input-number :value="48" :min="1" :max="720" /> 小时
              </el-form-item>
              <el-divider />
              <el-form-item>
                <el-button type="primary" size="medium" @click="$message.success('设置已保存')">
                  <i class="el-icon-check"></i> 保存设置
                </el-button>
                <el-button size="medium" @click="$message.info('已重置')">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-col>
      <el-col :md="10" :xs="24">
        <el-card shadow="never" class="page-card settings-card">
          <div class="page-header"><h3>关于系统</h3></div>
          <div class="page-body">
            <el-descriptions :column="1" border size="small">
              <el-descriptions-item label="系统名称">三一工业智能服务平台</el-descriptions-item>
              <el-descriptions-item label="当前版本">v2.0.0</el-descriptions-item>
              <el-descriptions-item label="前端框架">Vue 2.7 + Element UI</el-descriptions-item>
              <el-descriptions-item label="后端框架">Spring Boot 2.7</el-descriptions-item>
              <el-descriptions-item label="数据库">MySQL 8.0</el-descriptions-item>
              <el-descriptions-item label="部署环境">Windows Server</el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default { name: 'Settings' }
</script>

<style scoped>
.settings-card { margin-bottom: 20px; }
.settings-form { max-width: 560px; }
</style>

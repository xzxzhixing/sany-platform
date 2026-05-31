<template>
  <div class="page-container">
    <el-card shadow="never" class="page-card">
      <div class="page-header">
        <h3><i class="el-icon-user" style="margin-right:6px"></i>个人中心</h3>
      </div>
      <div class="page-body">
        <div class="profile-header">
          <div class="profile-avatar">{{ user.realName?.charAt(0) || 'U' }}</div>
          <div class="profile-info">
            <h3>{{ user.realName }}</h3>
            <p>{{ roleName }}</p>
          </div>
        </div>
        <el-divider></el-divider>
        <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" size="small" style="max-width:560px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" disabled />
          </el-form-item>
          <el-form-item label="姓名" prop="realName">
            <el-input v-model="form.realName" placeholder="输入姓名" />
          </el-form-item>
          <el-form-item label="角色">
            <el-tag>{{ roleName }}</el-tag>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" placeholder="输入手机号" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="输入邮箱" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="saving" @click="handleSave">保存修改</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'UserCenter',
  data() {
    return {
      saving: false,
      user: { realName: '管理员', username: 'admin', role: 'admin' },
      form: { username: 'admin', realName: '管理员', phone: '18812340000', email: 'admin@sany.com.cn' },
      rules: { realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }] }
    }
  },
  computed: {
    roleName() {
      return { admin: '系统管理员', engineer: '工程师', customer: '客户' }[this.user.role] || '未知'
    }
  },
  created() {
    const stored = localStorage.getItem('sany_user')
    if (stored) {
      this.user = JSON.parse(stored)
      this.form = { username: this.user.username, realName: this.user.realName, phone: this.user.phone || '', email: this.user.email || '' }
    }
  },
  methods: {
    async handleSave() {
      const valid = await this.$refs.formRef.validate().catch(() => {})
      if (!valid) return
      this.saving = true
      try {
        const res = await request({ url: '/api/v1/user/profile', method: 'put', data: this.form })
        if (res.code === 200) {
          const stored = JSON.parse(localStorage.getItem('sany_user') || '{}')
          Object.assign(stored, this.form)
          localStorage.setItem('sany_user', JSON.stringify(stored))
          this.$message.success('保存成功')
        }
      } catch { this.$message.error('保存失败') }
      finally { this.saving = false }
    }
  }
}
</script>

<style scoped>
.profile-header { display: flex; align-items: center; gap: 20px; padding: 20px 0; }
.profile-avatar {
  width: 72px; height: 72px; border-radius: 50%;
  background: linear-gradient(135deg, var(--color-primary), var(--color-accent));
  color: #fff; font-size: 28px; font-weight: 600;
  display: flex; align-items: center; justify-content: center; flex-shrink: 0;
}
.profile-info h3 { margin: 0 0 4px; font-size: 20px; }
.profile-info p { margin: 0; color: var(--color-text-tertiary); font-size: var(--font-size-base); }
</style>

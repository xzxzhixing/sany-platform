<template>
  <div class="login-container">
    <div class="login-bg">
      <div class="grid-overlay"></div>
      <div class="orb orb-1"></div>
      <div class="orb orb-2"></div>
      <div class="orb orb-3"></div>
    </div>
    <div class="login-card">
      <div class="login-header">
        <img :src="baseUrl + 'logo.png'" class="login-logo-img" />
        <h2>三一工业智能服务平台</h2>
        <p>SANY Industrial Intelligent Service Platform</p>
      </div>
      <el-form ref="formRef" :model="form" :rules="rules" size="large" @keyup.enter.native="handleLogin">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="el-icon-user">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="el-icon-lock" show-password>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" class="login-btn" @click="handleLogin">
            {{ loading ? '登录中...' : '登 录' }}
          </el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
        <div class="footer-divider"></div>
        <div class="demo-accounts">
          <span class="demo-account admin">管理员：admin / admin123</span>
          <span class="demo-account engineer">工程师：engineer / 123456</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'Login',
  data() {
    return {
      loading: false,
      baseUrl: process.env.BASE_URL,
      form: { username: 'admin', password: 'admin123' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    async handleLogin() {
      const valid = await this.$refs.formRef.validate().catch(() => {})
      if (!valid) return
      this.loading = true
      try {
        const res = await request({ url: '/api/v1/auth/login', method: 'post', data: this.form })
        if (res.code === 200) {
          localStorage.setItem('sany_user', JSON.stringify(res.data))
          this.$message.success('登录成功')
          this.$router.push('/dashboard')
        } else {
          this.$message.error(res.message || '登录失败')
        }
      } catch {
        this.$message.error('登录失败')
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh; display: flex; align-items: center; justify-content: center;
  background: linear-gradient(135deg, var(--color-primary-bg) 0%, var(--color-bg) 40%, var(--color-bg-secondary) 100%);
  position: relative; overflow: hidden;
}
.login-bg {
  position: absolute; inset: 0;
  background:
    radial-gradient(ellipse at 20% 50%, rgba(79,70,229,.05) 0%, transparent 50%),
    radial-gradient(ellipse at 80% 20%, rgba(8,145,178,.04) 0%, transparent 50%),
    radial-gradient(ellipse at 50% 80%, rgba(79,70,229,.03) 0%, transparent 40%);
}
.grid-overlay {
  position: absolute; inset: 0;
  background-image:
    linear-gradient(rgba(79,70,229,.03) 1px, transparent 1px),
    linear-gradient(90deg, rgba(79,70,229,.03) 1px, transparent 1px);
  background-size: 48px 48px;
}
.orb {
  position: absolute; border-radius: 50%;
  filter: blur(80px);
  animation: orbFloat 20s ease-in-out infinite;
}
.orb-1 {
  width: 400px; height: 400px; top: -100px; right: -100px;
  background: rgba(79,70,229,.06);
  animation-delay: 0s;
}
.orb-2 {
  width: 300px; height: 300px; bottom: -50px; left: -50px;
  background: rgba(124,58,237,.04);
  animation-delay: -7s;
}
.orb-3 {
  width: 250px; height: 250px; top: 40%; left: 30%;
  background: rgba(79,70,229,.03);
  animation-delay: -14s;
}
@keyframes orbFloat {
  0%, 100% { transform: translate(0, 0) scale(1); }
  33% { transform: translate(30px, -20px) scale(1.1); }
  66% { transform: translate(-20px, 30px) scale(.95); }
}

.login-card {
  position: relative; z-index: 1;
  width: 420px; padding: 40px;
  background: var(--color-card);
  border-radius: 16px;
  border: 1px solid var(--color-border);
  box-shadow: var(--shadow-xl), var(--shadow-glow);
}

.login-header { text-align: center; margin-bottom: 32px; }
.login-logo-img {
  width: 64px; height: 64px; border-radius: 14px; margin: 0 auto 16px;
  object-fit: contain; display: block;
}
.login-header h2 {
  margin: 0 0 6px; font-size: 22px; font-weight: 600;
  background: linear-gradient(90deg, var(--color-primary), var(--color-accent));
  -webkit-background-clip: text; -webkit-text-fill-color: transparent;
}
.login-header p { margin: 0; font-size: 13px; color: var(--color-text-tertiary); letter-spacing: .5px; }

.login-btn {
  width: 100%; height: 44px; font-size: 16px; font-weight: 600;
  margin-top: 4px; border-radius: 8px !important;
}

.login-footer { text-align: center; margin-top: 24px; }
.footer-divider {
  height: 1px; background: linear-gradient(90deg, transparent, var(--color-border), transparent);
  margin-bottom: 16px;
}
.login-footer p { margin: 4px 0; font-size: 12px; color: var(--color-text-tertiary); }
.demo-accounts { display: flex; flex-direction: column; gap: 6px; }
.demo-account { font-size: 12px; padding: 3px 10px; border-radius: 4px; display: inline-block; }
.demo-account.admin { background: var(--color-primary-bg); color: var(--color-primary-dark); }
.demo-account.engineer { background: var(--color-accent-bg); color: var(--color-accent-dark); }
</style>

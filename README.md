# 三一工业智能服务平台 - 部署文档

## 环境要求

| 组件 | 版本 |
|------|------|
| JDK | 1.8+ |
| Maven | 3.6+ |
| MySQL | 8.0+ |
| Node.js | 14+ |
| npm | 6+ |

---

## 快速部署

### 1. 初始化数据库

```sql
CREATE DATABASE IF NOT EXISTS sany_platform
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

执行建表脚本：

```bash
mysql -u root -p sany_platform < sql/work_order.sql
```

### 2. 后端部署

**配置数据库连接**（可选）:

编辑 `backend/src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sany_platform?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root        # 修改为你的数据库用户名
    password: 123456      # 修改为你的数据库密码
```

**打包并启动**：

```bash
cd backend
mvn clean package -DskipTests
java -jar target/sany-platform.jar
```

启动后访问：`http://localhost:8080`

### 3. 前端部署

```bash
cd frontend
npm install
npm run build
```

构建产物在 `frontend/dist/` 目录，将其部署到 Nginx 或直接使用 serve 预览：

```bash
# 使用 npx serve 快速预览
npx serve dist -l 3000
```

**Nginx 配置示例**：

```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 前端静态文件
    root /path/to/frontend/dist;
    index index.html;

    # 路由 history 模式 fallback
    location / {
        try_files $uri $uri/ /index.html;
    }

    # API 反向代理
    location /api/ {
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

---

## 开发模式

```bash
# 后端（在 backend/ 目录下）
mvn spring-boot:run

# 前端（在 frontend/ 目录下）
npm run dev
```

前端开发服务器默认端口 `9528`，已配置代理 `/api` → `http://localhost:8080`。

---

## 默认账号

| 用户名 | 密码       | 角色     |
|--------|-----------|----------|
| admin  | 123456    | 管理员   |
| engineer01 | 123456 | 运维人员 |
| engineer02 | 123456 | 运维人员 |

---

## 目录结构

```
sany-platform/
├── backend/               # Spring Boot 后端
│   ├── src/
│   └── pom.xml
├── frontend/              # Vue 2 前端
│   ├── src/
│   ├── public/
│   ├── vue.config.js
│   └── package.json
├── sql/                   # 数据库脚本
│   └── work_order.sql
└── DEPLOY.md
```

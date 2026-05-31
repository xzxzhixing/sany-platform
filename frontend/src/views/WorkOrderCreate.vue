<template>
  <div class="page-container">
    <el-card shadow="never" class="page-card">
      <div class="page-header">
        <h3>创建工单</h3>
        <el-button @click="$router.push('/work-order')" icon="el-icon-back" size="small">返回列表</el-button>
      </div>
      <div class="page-body">
        <el-form :model="form" :rules="rules" ref="formRef" label-width="110px" size="medium" class="create-form">
          <el-form-item label="客户名称" prop="customerName">
            <el-select v-model="form.customerName" filterable placeholder="搜索客户" style="width:100%">
              <el-option v-for="c in customerOptions" :key="c.id" :label="c.customerName" :value="c.customerName" />
            </el-select>
          </el-form-item>
          <el-form-item label="设备信息" prop="equipmentCode">
            <el-row :gutter="12">
              <el-col :span="12">
                <el-select v-model="form.equipmentCode" filterable placeholder="选择设备编号" style="width:100%">
                  <el-option v-for="e in equipmentOptions" :key="e.id" :label="e.equipmentCode" :value="e.equipmentCode" />
                </el-select>
              </el-col>
              <el-col :span="12">
                <el-input v-model="form.equipmentName" placeholder="设备名称" />
              </el-col>
            </el-row>
          </el-form-item>
          <el-row :gutter="12">
            <el-col :span="12">
              <el-form-item label="报修人" prop="reporterName">
                <el-input v-model="form.reporterName" placeholder="请输入报修人姓名" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系电话" prop="reporterPhone">
                <el-input v-model="form.reporterPhone" placeholder="请输入联系电话" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="紧急程度" prop="faultLevel">
            <el-radio-group v-model="form.faultLevel">
              <el-radio-button :label="1">紧急</el-radio-button>
              <el-radio-button :label="2">高</el-radio-button>
              <el-radio-button :label="3">中</el-radio-button>
              <el-radio-button :label="4">低</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="故障描述" prop="faultDescription">
            <el-input type="textarea" :rows="4" v-model="form.faultDescription" placeholder="请详细描述故障现象" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" :rows="2" v-model="form.remark" placeholder="其他补充信息" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" :loading="submitting" @click="submitForm" size="medium">提交工单</el-button>
            <el-button icon="el-icon-refresh" @click="resetForm" size="medium">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import { createWorkOrder } from '../api/workOrder'
import request from '../utils/request'

export default {
  name: 'WorkOrderCreate',
  data() {
    return {
      submitting: false, customerOptions: [], equipmentOptions: [],
      form: { customerName: '', equipmentCode: '', equipmentName: '', reporterName: '', reporterPhone: '', faultLevel: 3, faultDescription: '', remark: '' },
      rules: {
        customerName: [{ required: true, message: '请选择客户', trigger: 'change' }],
        equipmentCode: [{ required: true, message: '请选择设备', trigger: 'change' }],
        reporterName: [{ required: true, message: '请输入报修人', trigger: 'blur' }],
        reporterPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
        faultLevel: [{ required: true, message: '请选择紧急程度', trigger: 'change' }],
        faultDescription: [{ required: true, message: '请输入故障描述', trigger: 'blur' }]
      }
    }
  },
  created() { this.loadOptions() },
  methods: {
    async loadOptions() {
      try {
        const [cRes, eRes] = await Promise.all([
          request({ url: '/api/v1/customer/list', method: 'get', params: { pageSize: 200 } }),
          request({ url: '/api/v1/equipment/list', method: 'get', params: { pageSize: 200 } })
        ])
        this.customerOptions = cRes.data?.list || []; this.equipmentOptions = eRes.data?.list || []
      } catch {}
    },
    submitForm() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return; this.submitting = true
        try { const res = await createWorkOrder(this.form); if (res.code === 200) { this.$message.success('工单创建成功'); this.$router.push('/work-order') } }
        catch { this.$message.error('创建失败') }
        finally { this.submitting = false }
      })
    },
    resetForm() { this.$refs.formRef.resetFields() }
  }
}
</script>

<style scoped>
.create-form { max-width: 680px; }
</style>

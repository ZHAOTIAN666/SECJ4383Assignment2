<template>
  <div class="main-content">
    <div class="container">
      <!-- Header Section -->
      <div class="header-section">
        <div class="header-left">
          <h1 class="page-title">My Fitness Experience</h1>
          <p class="page-subtitle">Total: {{ total }} experiences</p>
        </div>
        <div class="header-right">
          <el-button type="primary" class="add-btn" @click="handleAdd" icon="el-icon-plus">
            Share Experience
          </el-button>
        </div>
      </div>

      <!-- Table Section -->
      <div class="table-section">
        <el-table :data="tableData" stripe class="custom-table">
          <el-table-column prop="id" label="ID" width="80" align="center" sortable>
            <template slot-scope="scope">
              <span class="id-badge">#{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="Experience Title" show-overflow-tooltip></el-table-column>
          <el-table-column prop="content" label="Details" show-overflow-tooltip width="120">
            <template slot-scope="scope">
              <el-button type="text" @click="viewEditor(scope.row.content)" class="view-btn">
                View Details
              </el-button>
            </template>
          </el-table-column>
          <el-table-column prop="time" label="Published Time" width="150"></el-table-column>
          <el-table-column label="Actions" width="160" align="center">
            <template slot-scope="scope">
              <el-button plain type="primary" size="mini" @click="handleEdit(scope.row)" icon="el-icon-edit">
                Edit
              </el-button>
              <el-button plain type="danger" size="mini" @click="del(scope.row.id)" icon="el-icon-delete">
                Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination-container">
          <el-pagination
              background
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 20]"
              :page-size="pageSize"
              layout="total, prev, pager, next"
              :total="total">
          </el-pagination>
        </div>
      </div>
    </div>

    <!-- Experience Form Dialog -->
    <el-dialog
        title="Experience Information"
        :visible.sync="fromVisible"
        width="60%"
        :close-on-click-modal="false"
        destroy-on-close
        class="custom-dialog">
      <el-form label-width="120px" :model="form" :rules="rules" ref="formRef" class="experience-form">
        <el-form-item prop="name" label="Experience Title">
          <el-input v-model="form.name" autocomplete="off" placeholder="Enter experience title"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="Experience Details">
          <div id="editor" class="editor-container"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save" icon="el-icon-check">Save</el-button>
      </div>
    </el-dialog>

    <!-- View Experience Dialog -->
    <el-dialog
        title="Experience Details"
        :visible.sync="viewVisible"
        width="60%"
        :close-on-click-modal="false"
        destroy-on-close
        class="custom-dialog">
      <div class="content-wrapper">
        <div v-html="viewData" class="experience-content w-e-text w-e-text-container"></div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import E from 'wangeditor'

export default {
  name: 'MyExperience',
  data() {
    return {
      user: {},
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      form: {},
      fromVisible: false,
      editor: null,
      viewData: null,
      viewVisible: false,
      rules: {
        name: [{ required: true, message: 'Please enter experience title', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.initUser()
    this.load(1)
  },
  methods: {
    initUser() {
      try {
        const userStr = localStorage.getItem('xm-user')
        this.user = userStr ? JSON.parse(userStr) : {}
      } catch (error) {
        this.user = {}
      }
    },
    viewEditor(content) {
      this.viewData = content
      this.viewVisible = true
    },
    initWangEditor(content) {
      this.$nextTick(() => {
        this.editor = new E('#editor')
        this.editor.config.placeholder = 'Please enter content'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgServer = 'http://localhost:9090/files/wang/upload'
        this.editor.create()
        setTimeout(() => {
          this.editor.txt.html(content)
        })
      })
    },
    handleAdd() {
      this.form = {
        userId: this.user.id,
        role: this.user.role
      }
      this.initWangEditor('')
      this.fromVisible = true
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.initWangEditor(this.form.content)
      this.fromVisible = true
    },
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/experience/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id,
          role: this.user.role
        }
      }).then(res => {
        this.tableData = res.data?.list || []
        this.total = res.data?.total || 0
      }).catch(() => {
        this.$message.error('Failed to load data')
      })
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.content = this.editor.txt.html()
          this.$request({
            url: this.form.id ? '/experience/update' : '/experience/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('Saved successfully')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg || 'Save failed')
            }
          }).catch(() => {
            this.$message.error('Save failed')
          })
        }
      })
    },
    del(id) {
      this.$confirm('Are you sure you want to delete this experience?', 'Confirm Delete', {
        type: "warning",
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
      }).then(() => {
        this.$request.delete('/experience/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('Deleted successfully')
            this.load(1)
          } else {
            this.$message.error(res.msg || 'Delete failed')
          }
        }).catch(() => {
          this.$message.error('Delete failed')
        })
      }).catch(() => {})
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    }
  }
}
</script>

<style scoped>
.main-content {
  background: linear-gradient(135deg, #f3f0ff 0%, #e5d3f7 100%);
  min-height: 100vh;
  padding: 40px 0;
}

.container {
  width: 85%;
  max-width: 1400px;
  margin: 0 auto;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  border-radius: 16px;
  padding: 30px;
  margin-bottom: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.page-title {
  font-size: 28px;
  font-weight: 700;
  margin: 0 0 8px 0;
  background: linear-gradient(135deg, #a855f7 0%, #c084fc 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.page-subtitle {
  color: #6b7280;
  margin: 0;
  font-size: 14px;
}

.add-btn {
  background: linear-gradient(135deg, #a855f7 0%, #c084fc 100%);
  border: none;
  border-radius: 25px;
  padding: 12px 24px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(168, 85, 247, 0.3);
}

.table-section {
  background: white;
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.custom-table {
  border-radius: 8px;
  overflow: hidden;
}

.custom-table /deep/ .el-table__header th {
  background: #faf5ff !important;
  color: #374151;
  font-weight: 600;
  border-bottom: 2px solid #e5e7eb;
}

.custom-table /deep/ .el-table__row:hover {
  background: #faf5ff !important;
}

.id-badge {
  background: linear-gradient(135deg, #a855f7 0%, #c084fc 100%);
  color: white;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
}

.view-btn {
  color: #a855f7;
  font-weight: 600;
}

.view-btn:hover {
  color: #7c3aed;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #e5e7eb;
}

.custom-dialog /deep/ .el-dialog {
  border-radius: 16px;
}

.custom-dialog /deep/ .el-dialog__header {
  background: linear-gradient(135deg, #a855f7 0%, #c084fc 100%);
  color: white;
  padding: 25px 30px;
}

.custom-dialog /deep/ .el-dialog__title {
  color: white;
  font-weight: 600;
}

.custom-dialog /deep/ .el-dialog__headerbtn .el-dialog__close {
  color: white;
}

.experience-form /deep/ .el-input__inner {
  border-radius: 8px;
  border: 2px solid #e5e7eb;
}

.experience-form /deep/ .el-input__inner:focus {
  border-color: #a855f7;
}

.editor-container {
  border: 2px solid #e5e7eb;
  border-radius: 8px;
  min-height: 300px;
}

.content-wrapper {
  background: #faf5ff;
  border-radius: 12px;
  padding: 25px;
  border: 1px solid #e5e7eb;
}

.experience-content {
  line-height: 1.8;
  color: #4b5563;
}

.dialog-footer {
  text-align: right;
  padding-top: 20px;
  border-top: 1px solid #e5e7eb;
}

/* Responsive */
@media (max-width: 768px) {
  .main-content { padding: 20px 0; }
  .container { width: 95%; }
  .header-section { flex-direction: column; gap: 20px; text-align: center; }
  .table-section { padding: 20px; }
  .custom-dialog /deep/ .el-dialog { width: 95% !important; }
}
</style>

<style>
.experience-content p {
  color: #4b5563 !important;
  margin-bottom: 16px;
}

.experience-content h2 {
  color: #a855f7 !important;
  font-weight: 600;
}

.experience-content strong {
  color: #a855f7 !important;
}
</style>
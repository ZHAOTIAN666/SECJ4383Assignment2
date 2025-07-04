<style scoped>
.admin-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.page-header {
  margin-bottom: 24px;
  padding: 0 4px;
}

.page-header h2 {
  margin: 0 0 8px 0;
  color: #303133;
  font-size: 24px;
  font-weight: 600;
}

.page-header p {
  margin: 0;
  color: #909399;
  font-size: 14px;
}

.search-section {
  background: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.search-wrapper {
  display: flex;
  align-items: center;
  gap: 12px;
}

.search-input {
  width: 280px;
}

.search-btn, .reset-btn {
  min-width: 100px;
}

.operation-section {
  background: white;
  padding: 20px 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.operation-left {
  display: flex;
  gap: 12px;
}

.add-btn, .delete-btn {
  min-width: 120px;
}

.table-section {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.custom-table {
  width: 100%;
}

.custom-table /deep/ .table-header {
  background: #f8f9fa !important;
}

.custom-table /deep/ .table-header th {
  background: #f8f9fa !important;
  color: #606266 !important;
  font-weight: 600 !important;
  border-bottom: 2px solid #e4e7ed !important;
}

.custom-table /deep/ .el-table__row:hover {
  background-color: #f5f7fa;
}

.username-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.username-cell i {
  color: #409eff;
}

.phone-cell, .email-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.phone-cell i {
  color: #67c23a;
}

.email-cell i {
  color: #e6a23c;
}

.empty-text {
  color: #c0c4cc;
  font-style: italic;
}

.avatar-cell {
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar-preview {
  cursor: pointer;
  transition: transform 0.2s;
}

.avatar-preview:hover {
  transform: scale(1.1);
}

.action-btn {
  margin: 0 4px;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  padding: 24px;
  background: white;
  border-top: 1px solid #e4e7ed;
}

.custom-pagination {
  display: flex;
  justify-content: center;
}

.custom-dialog /deep/ .el-dialog__header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  margin: 0;
  padding: 20px 24px;
}

.custom-dialog /deep/ .el-dialog__title {
  color: white;
  font-size: 18px;
  font-weight: 600;
}

.custom-dialog /deep/ .el-dialog__headerbtn .el-dialog__close {
  color: white;
  font-size: 20px;
}

.custom-dialog /deep/ .el-dialog__body {
  padding: 30px 24px;
}

.custom-form {
  padding: 0;
}

.custom-form /deep/ .el-form-item__label {
  font-weight: 600;
  color: #303133;
}

.custom-form /deep/ .el-input__inner {
  border-radius: 6px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
}

.custom-form /deep/ .el-input__inner:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.avatar-upload-section {
  display: flex;
  align-items: center;
  gap: 20px;
}

.avatar-uploader /deep/ .el-upload {
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.3s;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-uploader /deep/ .el-upload:hover {
  border-color: #409eff;
  background-color: #f5f7fa;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}

.avatar-upload-tip {
  flex: 1;
}

.avatar-upload-tip p {
  margin: 0 0 4px 0;
  color: #606266;
  font-size: 14px;
}

.tip-text {
  color: #909399 !important;
  font-size: 12px !important;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 20px 24px;
  background: #f8f9fa;
  border-top: 1px solid #e4e7ed;
  margin: 0 -24px -30px -24px;
}

.cancel-btn, .confirm-btn {
  min-width: 100px;
  padding: 10px 20px;
  border-radius: 6px;
}

.avatar-preview-content {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.preview-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 8px;
}

/* Responsive */
@media (max-width: 768px) {
  .admin-container {
    padding: 16px;
  }

  .search-wrapper {
    flex-direction: column;
    align-items: stretch;
  }

  .search-input {
    width: 100%;
  }

  .operation-left {
    flex-direction: column;
  }

  .add-btn, .delete-btn {
    width: 100%;
  }

  .avatar-upload-section {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
}

/* Loading and Empty States */
.custom-table /deep/ .el-table__empty-text {
  color: #909399;
  font-size: 14px;
}

/* Hover Effects */
.search-btn:hover, .reset-btn:hover, .add-btn:hover, .delete-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.action-btn:hover {
  transform: translateY(-1px);
}

/* Animation */
.admin-container {
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Table Loading */
.custom-table /deep/ .el-loading-mask {
  background-color: rgba(255, 255, 255, 0.9);
}

/* Success States */
.custom-table /deep/ .el-table__row.success-row {
  background-color: #f0f9ff;
}

/* Dark Mode Support */
@media (prefers-color-scheme: dark) {
  .admin-container {
    background-color: #1a1a1a;
  }

  .search-section, .operation-section, .table-section {
    background: #2d2d2d;
    color: #ffffff;
  }

  .page-header h2 {
    color: #ffffff;
  }

  .page-header p {
    color: #cccccc;
  }
}
</style><template>
  <div class="admin-container">
    <div class="page-header">
      <h2>Administrator Management</h2>
      <p>Manage system administrators and their permissions</p>
    </div>

    <div class="search-section">
      <div class="search-wrapper">
        <el-input
            placeholder="Enter account to search"
            v-model="username"
            class="search-input"
            prefix-icon="el-icon-search"
            clearable>
        </el-input>
        <el-button type="primary" @click="load(1)" class="search-btn">
          <i class="el-icon-search"></i> Search
        </el-button>
        <el-button type="info" @click="reset" class="reset-btn">
          <i class="el-icon-refresh"></i> Reset
        </el-button>
      </div>
    </div>

    <div class="operation-section">
      <div class="operation-left">
        <el-button type="primary" @click="handleAdd" class="add-btn">
          <i class="el-icon-plus"></i> Add New
        </el-button>
        <el-button type="danger" @click="delBatch" class="delete-btn">
          <i class="el-icon-delete"></i> Batch Delete
        </el-button>
      </div>
    </div>

    <div class="table-section">
      <el-table
          :data="tableData"
          stripe
          @selection-change="handleSelectionChange"
          class="custom-table"
          empty-text="No data available"
          header-row-class-name="table-header">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="ID" width="80" align="center" sortable>
          <template v-slot="scope">
            <el-tag size="mini" type="info">#{{ scope.row.id }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="Username" min-width="120">
          <template v-slot="scope">
            <div class="username-cell">
              <i class="el-icon-user"></i>
              <span>{{ scope.row.username }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="Name" min-width="100"></el-table-column>
        <el-table-column prop="phone" label="Phone" min-width="120">
          <template v-slot="scope">
            <div class="phone-cell" v-if="scope.row.phone">
              <i class="el-icon-phone"></i>
              <span>{{ scope.row.phone }}</span>
            </div>
            <span v-else class="empty-text">-</span>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="Email" min-width="150">
          <template v-slot="scope">
            <div class="email-cell" v-if="scope.row.email">
              <i class="el-icon-message"></i>
              <span>{{ scope.row.email }}</span>
            </div>
            <span v-else class="empty-text">-</span>
          </template>
        </el-table-column>
        <el-table-column label="Avatar" width="80" align="center">
          <template v-slot="scope">
            <div class="avatar-cell">
              <el-avatar
                  :size="40"
                  :src="scope.row.avatar"
                  @click.native="previewAvatar(scope.row.avatar)"
                  class="avatar-preview">
                <i class="el-icon-user-solid"></i>
              </el-avatar>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="Role" width="100">
          <template v-slot="scope">
            <el-tag
                :type="scope.row.role === 'admin' ? 'danger' : 'success'"
                size="small">
              {{ scope.row.role }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" align="center" width="180" fixed="right">
          <template v-slot="scope">
            <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit"
                @click="handleEdit(scope.row)"
                class="action-btn">
              Edit
            </el-button>
            <el-button
                size="mini"
                type="danger"
                icon="el-icon-delete"
                @click="del(scope.row.id)"
                class="action-btn">
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            class="custom-pagination">
        </el-pagination>
      </div>
    </div>

    <el-dialog
        title="Administrator Details"
        :visible.sync="fromVisible"
        width="500px"
        :close-on-click-modal="false"
        destroy-on-close
        class="custom-dialog">
      <el-form
          :model="form"
          label-width="100px"
          :rules="rules"
          ref="formRef"
          class="custom-form">
        <el-form-item label="Username" prop="username">
          <el-input
              v-model="form.username"
              placeholder="Enter username"
              prefix-icon="el-icon-user">
          </el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input
              v-model="form.name"
              placeholder="Enter full name"
              prefix-icon="el-icon-user-solid">
          </el-input>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input
              v-model="form.phone"
              placeholder="Enter phone number"
              prefix-icon="el-icon-phone">
          </el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input
              v-model="form.email"
              placeholder="Enter email address"
              type="email"
              prefix-icon="el-icon-message">
          </el-input>
        </el-form-item>
        <el-form-item label="Avatar">
          <div class="avatar-upload-section">
            <el-upload
                class="avatar-uploader"
                :action="$baseUrl + '/files/upload'"
                :headers="{ token: user.token }"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <el-avatar
                  v-if="form.avatar"
                  :src="form.avatar"
                  :size="80"
                  class="avatar-preview">
              </el-avatar>
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="avatar-upload-tip">
              <p>Click to upload avatar</p>
              <p class="tip-text">Support JPG, PNG format, max 2MB</p>
            </div>
          </div>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false" class="cancel-btn">
          <i class="el-icon-close"></i> Cancel
        </el-button>
        <el-button type="primary" @click="save" class="confirm-btn">
          <i class="el-icon-check"></i> Confirm
        </el-button>
      </div>
    </el-dialog>

    <!-- Avatar Preview Dialog -->
    <el-dialog
        title="Avatar Preview"
        :visible.sync="avatarPreviewVisible"
        width="400px"
        center>
      <div class="avatar-preview-content">
        <el-image
            :src="previewAvatarUrl"
            fit="contain"
            class="preview-image">
        </el-image>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Admin",
  data() {
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page number
      pageSize: 10,  // Items per page
      total: 0,
      username: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          {required: true, message: 'Please enter username', trigger: 'blur'},
        ]
      },
      ids: [],
      avatarPreviewVisible: false,
      previewAvatarUrl: ''
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleAdd() {   // Add new data
      this.form = {}  // Clear form data when adding new
      this.fromVisible = true   // Open dialog
    },
    handleEdit(row) {   // Edit data
      this.form = JSON.parse(JSON.stringify(row))  // Assign value to form object with deep copy
      this.fromVisible = true   // Open dialog
    },
    save() {   // Save button logic - triggers add or update
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/admin/update' : '/admin/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // Success
              this.$message.success('Saved successfully')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // Show error message
            }
          })
        }
      })
    },
    del(id) {   // Single delete
      this.$confirm('Are you sure you want to delete?', 'Confirm Delete', {type: "warning"}).then(response => {
        this.$request.delete('/admin/delete/' + id).then(res => {
          if (res.code === '200') {   // Success
            this.$message.success('Operation successful')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // Show error message
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // Currently selected rows
      this.ids = rows.map(v => v.id)
    },
    delBatch() {   // Batch delete
      if (!this.ids.length) {
        this.$message.warning('Please select data')
        return
      }
      this.$confirm('Are you sure you want to batch delete these records?', 'Confirm Delete', {type: "warning"}).then(response => {
        this.$request.delete('/admin/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // Success
            this.$message.success('Operation successful')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // Show error message
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // Paginated query
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/admin/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.username = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      // Set avatar property to uploaded image link
      this.form.avatar = response.data
    },
    beforeAvatarUpload(file) {
      const isJPGorPNG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPGorPNG) {
        this.$message.error('Avatar picture must be JPG or PNG format!')
      }
      if (!isLt2M) {
        this.$message.error('Avatar picture size can not exceed 2MB!')
      }
      return isJPGorPNG && isLt2M
    },
    previewAvatar(url) {
      if (url) {
        this.previewAvatarUrl = url
        this.avatarPreviewVisible = true
      }
    }
  }
}
</script>

<style scoped>

</style>
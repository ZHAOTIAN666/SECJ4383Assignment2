<template>
  <div class="user-container">
    <!-- Search Section -->
    <div class="search-section">
      <div class="search-header">
        <i class="el-icon-search"></i>
        <span class="header-title">Search Users</span>
      </div>
      <div class="search-content">
        <el-input
            placeholder="Enter username to search"
            style="width: 250px"
            v-model="username"
            prefix-icon="el-icon-user"
            clearable>
        </el-input>
        <el-button
            type="primary"
            plain
            style="margin-left: 15px"
            @click="load(1)"
            icon="el-icon-search">
          Search
        </el-button>
        <el-button
            type="warning"
            plain
            style="margin-left: 10px"
            @click="reset"
            icon="el-icon-refresh">
          Reset
        </el-button>
      </div>
    </div>

    <!-- Operation Section -->
    <div class="operation-section">
      <div class="operation-header">
        <i class="el-icon-setting"></i>
        <span class="header-title">Operations</span>
      </div>
      <div class="operation-content">
        <el-button
            type="success"
            plain
            @click="handleAdd"
            icon="el-icon-plus">
          Add User
        </el-button>
        <el-button
            type="danger"
            plain
            @click="delBatch"
            icon="el-icon-delete"
            :disabled="!ids.length">
          Batch Delete ({{ ids.length }})
        </el-button>
      </div>
    </div>

    <!-- Table Section -->
    <div class="table-section">
      <div class="table-header">
        <i class="el-icon-user"></i>
        <span class="header-title">User List</span>
        <el-tag
            size="small"
            type="info"
            style="margin-left: 15px">
          Total: {{ total }} users
        </el-tag>
      </div>

      <el-table
          :data="tableData"
          stripe
          border
          @selection-change="handleSelectionChange"
          v-loading="loading"
          element-loading-text="Loading users..."
          empty-text="No users found"
          class="user-table">

        <el-table-column
            type="selection"
            width="55"
            align="center">
        </el-table-column>

        <el-table-column
            prop="id"
            label="ID"
            width="70"
            align="center"
            sortable>
          <template v-slot="scope">
            <el-tag size="mini" type="primary">
              {{ scope.row.id }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column
            label="Avatar"
            width="80"
            align="center">
          <template v-slot="scope">
            <div class="avatar-container">
              <el-avatar
                  :size="45"
                  :src="scope.row.avatar"
                  :alt="scope.row.name"
                  icon="el-icon-user-solid"
                  class="user-avatar">
              </el-avatar>
            </div>
          </template>
        </el-table-column>

        <el-table-column
            prop="username"
            label="Username"
            show-overflow-tooltip
            min-width="120">
          <template v-slot="scope">
            <div class="username-info">
              <i class="el-icon-user"></i>
              <span>{{ scope.row.username }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column
            prop="name"
            label="Full Name"
            show-overflow-tooltip
            min-width="100">
        </el-table-column>

        <el-table-column
            prop="phone"
            label="Phone"
            show-overflow-tooltip
            min-width="120">
          <template v-slot="scope">
            <div class="phone-info">
              <i class="el-icon-phone"></i>
              <span>{{ scope.row.phone || 'N/A' }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column
            prop="email"
            label="Email"
            show-overflow-tooltip
            min-width="150">
          <template v-slot="scope">
            <div class="email-info">
              <i class="el-icon-message"></i>
              <span>{{ scope.row.email || 'N/A' }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column
            prop="role"
            label="Role"
            width="100"
            align="center">
          <template v-slot="scope">
            <el-tag
                :type="getRoleType(scope.row.role)"
                size="small">
              {{ scope.row.role || 'User' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column
            prop="account"
            label="Balance"
            width="100"
            align="center">
          <template v-slot="scope">
            <span class="balance-amount">
              ¥{{ scope.row.account || '0.00' }}
            </span>
          </template>
        </el-table-column>

        <el-table-column
            label="Actions"
            align="center"
            width="160"
            fixed="right">
          <template v-slot="scope">
            <el-button
                size="mini"
                type="primary"
                plain
                @click="handleEdit(scope.row)"
                icon="el-icon-edit">
              Edit
            </el-button>
            <el-button
                size="mini"
                type="danger"
                plain
                @click="del(scope.row.id)"
                icon="el-icon-delete">
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- Pagination -->
      <div class="pagination-section">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <!-- User Form Dialog -->
    <el-dialog
        :title="form.id ? 'Edit User' : 'Add New User'"
        :visible.sync="fromVisible"
        width="45%"
        :close-on-click-modal="false"
        destroy-on-close
        class="user-dialog">

      <el-form
          :model="form"
          label-width="120px"
          style="padding-right: 30px"
          :rules="rules"
          ref="formRef"
          @submit.native.prevent>

        <el-form-item label="Avatar">
          <div class="avatar-upload-section">
            <el-avatar
                :size="80"
                :src="form.avatar"
                icon="el-icon-user-solid"
                class="preview-avatar">
            </el-avatar>
            <el-upload
                class="avatar-uploader"
                :action="$baseUrl + '/files/upload'"
                :headers="{ token: user.token }"
                list-type="picture"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <el-button type="primary" size="small" icon="el-icon-upload">
                Upload Avatar
              </el-button>
            </el-upload>
          </div>
        </el-form-item>

        <el-form-item label="Username" prop="username">
          <el-input
              v-model="form.username"
              placeholder="Enter username"
              prefix-icon="el-icon-user">
          </el-input>
        </el-form-item>

        <el-form-item label="Full Name" prop="name">
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
              prefix-icon="el-icon-message">
          </el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false" icon="el-icon-close">
          Cancel
        </el-button>
        <el-button
            type="primary"
            @click="save"
            :loading="saving"
            icon="el-icon-check">
          {{ saving ? 'Saving...' : 'Save' }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      saving: false,
      username: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          { required: true, message: 'Please enter username', trigger: 'blur' },
          { min: 3, message: 'Username must be at least 3 characters', trigger: 'blur' }
        ],
        name: [
          { required: true, message: 'Please enter full name', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: 'Please enter a valid email address', trigger: 'blur' }
        ]
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    // Add new user
    handleAdd() {
      this.form = {}
      this.fromVisible = true
    },

    // Edit user
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.fromVisible = true
    },

    // Save user (add or update)
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.saving = true
          this.$request({
            url: this.form.id ? '/user/update' : '/user/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.$message.success(this.form.id ? 'User updated successfully' : 'User added successfully')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg || 'Operation failed')
            }
          }).catch(err => {
            this.$message.error('Network error, please try again')
            console.error('Save user error:', err)
          }).finally(() => {
            this.saving = false
          })
        }
      })
    },

    // Delete single user
    del(id) {
      this.$confirm('Are you sure you want to delete this user?', 'Confirm Delete', {
        type: "warning",
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
      }).then(response => {
        this.$request.delete('/user/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('User deleted successfully')
            this.load(1)
          } else {
            this.$message.error(res.msg || 'Delete failed')
          }
        })
      }).catch(() => {
        this.$message.info('Delete cancelled')
      })
    },

    // Handle selection change
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
    },

    // Batch delete
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('Please select users to delete')
        return
      }
      this.$confirm(`Are you sure you want to delete ${this.ids.length} selected users?`, 'Confirm Batch Delete', {
        type: "warning",
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
      }).then(response => {
        this.$request.delete('/user/delete/batch', { data: this.ids }).then(res => {
          if (res.code === '200') {
            this.$message.success('Users deleted successfully')
            this.load(1)
          } else {
            this.$message.error(res.msg || 'Batch delete failed')
          }
        })
      }).catch(() => {
        this.$message.info('Batch delete cancelled')
      })
    },

    // Load data with pagination
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum
      this.loading = true

      this.$request.get('/user/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(res => {
        this.tableData = res.data?.list || []
        this.total = res.data?.total || 0
      }).catch(err => {
        this.$message.error('Failed to load users')
        console.error('Load users error:', err)
      }).finally(() => {
        this.loading = false
      })
    },

    // Reset search
    reset() {
      this.username = null
      this.load(1)
      this.$message.success('Search has been reset')
    },

    // Handle page change
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },

    // Handle page size change
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load(1)
    },

    // Handle avatar upload success
    handleAvatarSuccess(response, file, fileList) {
      this.form.avatar = response.data
      this.$message.success('Avatar uploaded successfully')
    },

    // Before avatar upload
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('Avatar must be JPG or PNG format!')
      }
      if (!isLt2M) {
        this.$message.error('Avatar size cannot exceed 2MB!')
      }
      return isJPG && isLt2M
    },

    // Get role tag type
    getRoleType(role) {
      const roleMap = {
        'admin': 'danger',
        'manager': 'warning',
        'user': 'info',
        'vip': 'success'
      }
      return roleMap[role?.toLowerCase()] || 'info'
    }
  }
}
</script>

<style scoped>
.user-container {
  padding: 20px;
  background: #f0f2f5;
  min-height: 100vh;
}

.search-section,
.operation-section,
.table-section {
  background: white;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.search-header,
.operation-header,
.table-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.search-header i,
.operation-header i,
.table-header i {
  font-size: 18px;
  color: #409eff;
  margin-right: 8px;
}

.header-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.search-content,
.operation-content {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.user-table {
  border-radius: 8px;
  overflow: hidden;
}

.avatar-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.user-avatar {
  border: 2px solid #ebeef5;
  transition: all 0.3s ease;
}

.user-avatar:hover {
  border-color: #409eff;
}

.username-info,
.phone-info,
.email-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.username-info i {
  color: #409eff;
}

.phone-info i {
  color: #67c23a;
}

.email-info i {
  color: #e6a23c;
}

.balance-amount {
  font-weight: bold;
  color: #f56c6c;
}

.pagination-section {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.avatar-upload-section {
  display: flex;
  align-items: center;
  gap: 20px;
}

.preview-avatar {
  border: 2px solid #ebeef5;
}

.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button {
  margin-left: 10px;
}

/* Table styling */
/deep/ .el-table th {
  background-color: #fafafa;
  color: #333;
  font-weight: 600;
}

/deep/ .el-table td {
  padding: 12px 0;
}

/* Button styling */
/deep/ .el-button {
  border-radius: 6px;
  transition: all 0.3s ease;
}

/deep/ .el-button:hover {
  transform: translateY(-1px);
}

/* Dialog styling */
/deep/ .user-dialog .el-dialog__header {
  background: #f8f9fa;
  padding: 20px;
  border-bottom: 1px solid #ebeef5;
}

/deep/ .user-dialog .el-dialog__title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

/* Input styling */
/deep/ .el-input__inner {
  border-radius: 6px;
}

/* Upload styling */
/deep/ .avatar-uploader .el-upload {
  border-radius: 6px;
}

/* Responsive design */
@media (max-width: 768px) {
  .user-container {
    padding: 15px;
  }

  .search-content,
  .operation-content {
    flex-direction: column;
    align-items: stretch;
  }

  .search-content .el-input {
    width: 100% !important;
    margin-bottom: 10px;
  }

  .pagination-section {
    justify-content: center;
  }

  /deep/ .el-dialog {
    width: 90% !important;
  }
}
</style>
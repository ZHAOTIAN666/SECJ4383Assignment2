<template>
  <div class="orders-container">
    <!-- Search Section -->
    <div class="search-section">
      <el-input
          placeholder="Enter order number"
          style="width: 200px"
          v-model="orderNo"
          clearable>
      </el-input>
      <el-button
          type="primary"
          plain
          style="margin-left: 10px"
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

    <!-- Operation Section -->
    <div class="operation-section">
      <el-button
          type="danger"
          plain
          @click="delBatch"
          icon="el-icon-delete"
          :disabled="!ids.length">
        Batch Delete
      </el-button>
    </div>

    <!-- Table Section -->
    <div class="table-section">
      <el-table
          :data="tableData"
          stripe
          border
          @selection-change="handleSelectionChange"
          v-loading="loading"
          element-loading-text="Loading...">

        <el-table-column
            type="selection"
            width="55"
            align="center">
        </el-table-column>

        <el-table-column
            prop="id"
            label="ID"
            width="80"
            align="center"
            sortable>
        </el-table-column>

        <el-table-column
            prop="courseName"
            label="Course Name"
            show-overflow-tooltip>
        </el-table-column>

        <el-table-column
            prop="coachName"
            label="Coach Name"
            show-overflow-tooltip>
        </el-table-column>

        <el-table-column
            prop="userName"
            label="Customer"
            show-overflow-tooltip>
        </el-table-column>

        <el-table-column
            prop="orderNo"
            label="Order Number"
            show-overflow-tooltip>
        </el-table-column>

        <el-table-column
            prop="price"
            label="Price"
            show-overflow-tooltip>
          <template v-slot="scope">
            <span style="color: #f56c6c; font-weight: bold;">
              ¥{{ scope.row.price }}
            </span>
          </template>
        </el-table-column>

        <el-table-column
            prop="time"
            label="Order Time"
            width="180">
        </el-table-column>

        <el-table-column
            label="Actions"
            width="120"
            align="center">
          <template v-slot="scope">
            <el-button
                plain
                type="danger"
                size="mini"
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
            :current-page="pageNum"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Orders",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      orderNo: null,
      loading: false,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {},
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    // Single delete
    del(id) {
      this.$confirm('Are you sure you want to delete this item?', 'Confirm Delete', {
        type: "warning",
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
      }).then(response => {
        this.$request.delete('/orders/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('Operation successful')
            this.load(1)
          } else {
            this.$message.error(res.msg)
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
        this.$message.warning('Please select data to delete')
        return
      }
      this.$confirm(`Are you sure you want to delete ${this.ids.length} selected items?`, 'Confirm Batch Delete', {
        type: "warning",
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
      }).then(response => {
        this.$request.delete('/orders/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {
            this.$message.success('Batch delete successful')
            this.load(1)
          } else {
            this.$message.error(res.msg)
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

      this.$request.get('/orders/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderNo: this.orderNo,
        }
      }).then(res => {
        this.tableData = res.data?.list || []
        this.total = res.data?.total || 0
      }).catch(err => {
        this.$message.error('Failed to load data')
        console.error(err)
      }).finally(() => {
        this.loading = false
      })
    },

    // Reset search
    reset() {
      this.orderNo = null
      this.load(1)
    },

    // Handle page change
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>
.orders-container {
  padding: 20px;
  background: #f5f5f5;
  min-height: 100vh;
}

.search-section {
  background: white;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.operation-section {
  background: white;
  padding: 15px 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.table-section {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.pagination-section {
  margin-top: 20px;
  text-align: right;
}

/* Table styling */
.el-table {
  border-radius: 8px;
  overflow: hidden;
}

.el-table th {
  background-color: #fafafa;
  color: #333;
  font-weight: 600;
}

/* Button styling */
.el-button {
  border-radius: 4px;
  transition: all 0.3s ease;
}

.el-button:hover {
  transform: translateY(-1px);
}

/* Input styling */
.el-input__inner {
  border-radius: 4px;
}

/* Responsive design */
@media (max-width: 768px) {
  .orders-container {
    padding: 10px;
  }

  .search-section,
  .operation-section,
  .table-section {
    padding: 15px;
  }
}
</style>
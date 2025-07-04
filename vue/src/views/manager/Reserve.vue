<template>
  <div class="reserve-container">
    <!-- Search Section -->
    <div class="search-section">
      <div class="search-header">
        <i class="el-icon-search"></i>
        <span class="header-title">Search Reservations</span>
      </div>
      <div class="search-content">
        <el-input
            placeholder="Enter coach name"
            style="width: 250px"
            v-model="coachName"
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

    <!-- Table Section -->
    <div class="table-section">
      <div class="table-header">
        <i class="el-icon-tickets"></i>
        <span class="header-title">Reservation List</span>
        <el-tag
            size="small"
            type="info"
            style="margin-left: 15px">
          Total: {{ total }} records
        </el-tag>
      </div>

      <el-table
          :data="tableData"
          stripe
          border
          v-loading="loading"
          element-loading-text="Loading reservations..."
          element-loading-spinner="el-icon-loading"
          empty-text="No reservations found"
          class="reservation-table">

        <el-table-column
            prop="id"
            label="ID"
            width="80"
            align="center"
            sortable>
          <template v-slot="scope">
            <el-tag size="mini" type="primary">
              {{ scope.row.id }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column
            prop="userName"
            label="Customer"
            show-overflow-tooltip
            min-width="120">
          <template v-slot="scope">
            <div class="customer-info">
              <i class="el-icon-user-solid"></i>
              <span>{{ scope.row.userName }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column
            prop="coachName"
            label="Coach"
            show-overflow-tooltip
            min-width="120">
          <template v-slot="scope">
            <div class="coach-info">
              <i class="el-icon-s-custom"></i>
              <span>{{ scope.row.coachName }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column
            prop="content"
            label="Description"
            show-overflow-tooltip
            min-width="200">
          <template v-slot="scope">
            <div class="description-text">
              {{ scope.row.content || 'No description' }}
            </div>
          </template>
        </el-table-column>

        <el-table-column
            prop="time"
            label="Created Time"
            width="180"
            sortable>
          <template v-slot="scope">
            <div class="time-info">
              <i class="el-icon-time"></i>
              <span>{{ scope.row.time }}</span>
            </div>
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

    <!-- Statistics Card -->
    <div class="stats-section" v-if="tableData.length > 0">
      <el-card class="stats-card" shadow="hover">
        <div class="stats-content">
          <div class="stat-item">
            <i class="el-icon-tickets"></i>
            <div class="stat-info">
              <div class="stat-number">{{ total }}</div>
              <div class="stat-label">Total Reservations</div>
            </div>
          </div>
          <div class="stat-item">
            <i class="el-icon-user"></i>
            <div class="stat-info">
              <div class="stat-number">{{ uniqueCustomers }}</div>
              <div class="stat-label">Unique Customers</div>
            </div>
          </div>
          <div class="stat-item">
            <i class="el-icon-s-custom"></i>
            <div class="stat-info">
              <div class="stat-number">{{ uniqueCoaches }}</div>
              <div class="stat-label">Active Coaches</div>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "Reserve",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      coachName: null,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {}
    }
  },
  computed: {
    uniqueCustomers() {
      const customers = new Set(this.tableData.map(item => item.userName))
      return customers.size
    },
    uniqueCoaches() {
      const coaches = new Set(this.tableData.map(item => item.coachName))
      return coaches.size
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    // Load data with pagination
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum
      this.loading = true

      this.$request.get('/reserve/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          coachName: this.coachName,
        }
      }).then(res => {
        this.tableData = res.data?.list || []
        this.total = res.data?.total || 0

        if (this.tableData.length === 0 && this.coachName) {
          this.$message.info('No reservations found for this coach')
        }
      }).catch(err => {
        this.$message.error('Failed to load reservations')
        console.error('Load reservations error:', err)
      }).finally(() => {
        this.loading = false
      })
    },

    // Reset search
    reset() {
      this.coachName = null
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
    }
  }
}
</script>

<style scoped>
.reserve-container {
  padding: 20px;
  background: #f0f2f5;
  min-height: 100vh;
}

.search-section {
  background: white;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.search-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.search-header i {
  font-size: 18px;
  color: #409eff;
  margin-right: 8px;
}

.header-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.search-content {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.table-section {
  background: white;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.table-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ebeef5;
}

.table-header i {
  font-size: 18px;
  color: #409eff;
  margin-right: 8px;
}

.reservation-table {
  border-radius: 8px;
  overflow: hidden;
}

.customer-info,
.coach-info,
.time-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.customer-info i {
  color: #67c23a;
}

.coach-info i {
  color: #e6a23c;
}

.time-info i {
  color: #909399;
}

.description-text {
  color: #606266;
  font-size: 14px;
}

.pagination-section {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.stats-section {
  margin-top: 20px;
}

.stats-card {
  border-radius: 8px;
  overflow: hidden;
}

.stats-content {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px 0;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 12px;
  text-align: center;
}

.stat-item i {
  font-size: 24px;
  color: #409eff;
}

.stat-info {
  text-align: left;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  line-height: 1;
}

.stat-label {
  font-size: 12px;
  color: #666;
  margin-top: 4px;
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

/deep/ .el-table--striped .el-table__body tr.el-table__row--striped td {
  background: #fafafa;
}

/* Button styling */
/deep/ .el-button {
  border-radius: 6px;
  transition: all 0.3s ease;
}

/deep/ .el-button:hover {
  transform: translateY(-1px);
}

/* Input styling */
/deep/ .el-input__inner {
  border-radius: 6px;
}

/* Pagination styling */
/deep/ .el-pagination {
  margin-top: 20px;
}

/deep/ .el-pagination.is-background .el-pager li:not(.disabled):hover {
  color: #409eff;
}

/* Responsive design */
@media (max-width: 768px) {
  .reserve-container {
    padding: 15px;
  }

  .search-content {
    flex-direction: column;
    align-items: stretch;
  }

  .search-content .el-input {
    width: 100% !important;
    margin-bottom: 10px;
  }

  .stats-content {
    flex-direction: column;
    gap: 20px;
  }

  .pagination-section {
    justify-content: center;
  }

  /deep/ .el-pagination {
    flex-wrap: wrap;
  }
}

@media (max-width: 480px) {
  .table-section {
    padding: 15px;
  }

  /deep/ .el-table__body-wrapper {
    overflow-x: auto;
  }
}
</style>
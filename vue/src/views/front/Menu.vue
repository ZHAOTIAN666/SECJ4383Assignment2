<template>
  <div class="main-content">
    <div class="container">
      <!-- Page Header -->
      <div class="page-header">
        <h1 class="page-title">Healthy Recipes</h1>
        <p class="page-subtitle">Discover nutritious and delicious recipes for your fitness journey</p>
      </div>

      <!-- Search Section -->
      <div class="search-section">
        <div class="search-container">
          <el-input
              placeholder="Enter recipe name to search"
              class="search-input"
              v-model="name"
              clearable>
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
          <el-button
              type="primary"
              class="search-btn"
              @click="loadMenu"
              icon="el-icon-search">
            Search
          </el-button>
          <el-button
              type="warning"
              plain
              class="reset-btn"
              @click="reset"
              icon="el-icon-refresh-left">
            Reset
          </el-button>
        </div>
      </div>

      <!-- Recipes Grid -->
      <div class="recipes-grid">
        <el-row :gutter="30">
          <el-col :span="6" v-for="item in menuData" :key="item.id" class="recipe-col">
            <div class="recipe-card">
              <div class="image-container">
                <img :src="item.img" alt="Recipe Image" class="recipe-image">
                <div class="image-overlay">
                  <i class="el-icon-view recipe-icon"></i>
                  <span>View Recipe</span>
                </div>
              </div>

              <div class="recipe-info">
                <h3 class="recipe-name">{{ item.name }}</h3>
                <div class="recipe-meta">
                  <i class="el-icon-time"></i>
                  <span>Published: {{ item.time }}</span>
                </div>
                <el-button
                    type="primary"
                    class="view-btn"
                    @click="viewDescri(item.content)"
                    icon="el-icon-document">
                  View Recipe
                </el-button>
              </div>
            </div>
          </el-col>
        </el-row>

        <!-- Empty State -->
        <div v-if="menuData.length === 0" class="empty-state">
          <i class="el-icon-food"></i>
          <p>No recipes found</p>
        </div>
      </div>
    </div>

    <!-- Recipe Detail Dialog -->
    <el-dialog
        title="Recipe Details"
        :visible.sync="fromVisible"
        width="60%"
        :close-on-click-modal="false"
        destroy-on-close
        class="custom-dialog">
      <div class="recipe-content-wrapper">
        <div v-html="viewData" class="recipe-content w-e-text w-e-text-container"></div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import E from 'wangeditor'

export default {
  name: 'MenuList',
  data() {
    return {
      user: {},
      menuData: [],
      fromVisible: false,
      name: null,
      viewData: null,
    }
  },
  mounted() {
    this.initUser()
    this.loadMenu()
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
    loadMenu() {
      this.$request.get('/menu/selectAll', {
        params: { name: this.name }
      }).then(res => {
        if (res.code === '200') {
          this.menuData = res.data
        } else {
          this.$message.error(res.msg || 'Failed to load recipes')
        }
      }).catch(() => {
        this.$message.error('Failed to load recipes')
      })
    },
    viewDescri(content) {
      this.viewData = content
      this.fromVisible = true
    },
    reset() {
      this.name = null
      this.loadMenu()
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

.page-header {
  text-align: center;
  margin-bottom: 40px;
  background: white;
  border-radius: 20px;
  padding: 40px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.page-title {
  font-size: 36px;
  font-weight: 700;
  margin: 0 0 15px 0;
  background: linear-gradient(135deg, #a855f7 0%, #c084fc 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.page-subtitle {
  font-size: 16px;
  color: #6b7280;
  margin: 0;
}

.search-section {
  background: white;
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  margin-bottom: 40px;
}

.search-container {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.search-input {
  width: 350px;
}

.search-input /deep/ .el-input__inner {
  border-radius: 25px;
  height: 45px;
  border: 2px solid #e5e7eb;
  transition: all 0.3s ease;
}

.search-input /deep/ .el-input__inner:focus {
  border-color: #a855f7;
  box-shadow: 0 0 0 3px rgba(168, 85, 247, 0.1);
}

.search-btn {
  height: 45px;
  padding: 0 25px;
  border-radius: 25px;
  background: linear-gradient(135deg, #a855f7 0%, #c084fc 100%);
  border: none;
  font-weight: 600;
  transition: all 0.3s ease;
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(168, 85, 247, 0.3);
}

.reset-btn {
  height: 45px;
  padding: 0 25px;
  border-radius: 25px;
  border: 2px solid #f59e0b;
  color: #f59e0b;
  font-weight: 600;
  transition: all 0.3s ease;
}

.reset-btn:hover {
  background: #f59e0b;
  color: white;
  transform: translateY(-2px);
}

.recipes-grid {
  margin-top: 40px;
}

.recipe-col {
  margin-bottom: 40px;
}

.recipe-card {
  background: white;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  transition: all 0.4s ease;
  height: 100%;
}

.recipe-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 20px 60px rgba(168, 85, 247, 0.15);
}

.image-container {
  position: relative;
  overflow: hidden;
  height: 220px;
}

.recipe-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: all 0.4s ease;
  cursor: pointer;
}

.recipe-card:hover .recipe-image {
  transform: scale(1.1);
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(168, 85, 247, 0.8) 0%, rgba(192, 132, 252, 0.8) 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: all 0.3s ease;
  color: white;
}

.recipe-card:hover .image-overlay {
  opacity: 1;
}

.recipe-icon {
  font-size: 48px;
  margin-bottom: 10px;
}

.recipe-info {
  padding: 25px;
}

.recipe-name {
  font-size: 18px;
  font-weight: 700;
  color: #374151;
  margin: 0 0 15px 0;
  line-height: 1.4;
}

.recipe-meta {
  display: flex;
  align-items: center;
  color: #6b7280;
  font-size: 14px;
  margin-bottom: 20px;
}

.recipe-meta i {
  margin-right: 8px;
  color: #a855f7;
}

.view-btn {
  background: linear-gradient(135deg, #a855f7 0%, #c084fc 100%);
  border: none;
  border-radius: 20px;
  padding: 12px 30px;
  font-weight: 600;
  width: 100%;
  transition: all 0.3s ease;
}

.view-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(168, 85, 247, 0.3);
}

.empty-state {
  text-align: center;
  padding: 80px 20px;
  color: #9ca3af;
}

.empty-state i {
  font-size: 80px;
  margin-bottom: 20px;
  color: #c084fc;
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

.recipe-content-wrapper {
  background: #faf5ff;
  border-radius: 12px;
  padding: 25px;
  border: 1px solid #e5e7eb;
}

.recipe-content {
  line-height: 1.8;
  color: #4b5563;
}

/* Responsive */
@media (max-width: 1200px) {
  .recipe-col { width: 33.333333%; }
}

@media (max-width: 992px) {
  .container { width: 95%; }
  .search-container { flex-direction: column; gap: 15px; }
  .search-input { width: 100%; max-width: 400px; }
  .recipe-col { width: 50%; }
}

@media (max-width: 768px) {
  .main-content { padding: 20px 0; }
  .page-header { padding: 30px 20px; }
  .page-title { font-size: 28px; }
  .search-section { padding: 20px; }
  .recipe-col { width: 100%; }
  .custom-dialog /deep/ .el-dialog { width: 95% !important; }
}
</style>

<style>
.recipe-content p {
  color: #4b5563 !important;
  margin-bottom: 16px;
  line-height: 1.8;
}

.recipe-content h1,
.recipe-content h2,
.recipe-content h3 {
  color: #374151 !important;
  font-weight: 600;
  margin: 24px 0 16px 0;
}

.recipe-content h2 {
  color: #a855f7 !important;
}

.recipe-content strong {
  color: #a855f7 !important;
  font-weight: 700;
}
</style>
<template>
  <div class="main-content">
    <!-- Course Section -->
    <div class="course-section">
      <div class="section-header">
        <div class="section-title">Professional Courses, Scientific Fitness</div>
        <div class="view-more-btn" @click="$router.push('/front/course')">
          View More
        </div>
      </div>

      <div class="course-grid">
        <el-row :gutter="30">
          <el-col :span="6" v-for="item in courseData" :key="item.id">
            <div class="course-card">
              <img
                  :src="item.img"
                  alt="Course Image"
                  class="course-image"
                  @click="$router.push('/front/courseDetail?id=' + item.id)"
              >
              <div class="course-name">{{ item.name }}</div>
              <div class="course-details">
                <div class="detail-item">
                  <span class="detail-label">Instructor:</span>
                  <span class="detail-value">{{ item.coachName }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">Duration:</span>
                  <span class="detail-value">{{ item.time }}</span>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      courseData: []
    }
  },
  mounted() {
    this.loadCourse()
  },
  methods: {
    loadCourse() {
      this.$request.get('/course/selectFour').then(res => {
        if (res.code === '200') {
          this.courseData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
.main-content {
  padding: 20px 0;
  background-color: #f8f9fa;
  min-height: 100vh;
}

.course-section {
  width: 80%;
  margin: 0 auto;
  padding: 40px 0;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
  padding-bottom: 20px;
  border-bottom: 2px solid #e9ecef;
}

.section-title {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  text-align: center;
  flex: 1;
  letter-spacing: 1px;
}

.view-more-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 12px 24px;
  border-radius: 25px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.view-more-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

.course-grid {
  margin-top: 40px;
}

.course-card {
  background: white;
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  margin-bottom: 30px;
  position: relative;
}

.course-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.15);
}

.course-image {
  width: 100%;
  height: 280px;
  object-fit: cover;
  cursor: pointer;
  transition: all 0.3s ease;
}

.course-card:hover .course-image {
  transform: scale(1.05);
}

.course-name {
  font-size: 18px;
  font-weight: 700;
  color: #2c3e50;
  padding: 20px 20px 10px;
  line-height: 1.4;
}

.course-details {
  padding: 0 20px 25px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  padding: 8px 0;
  border-bottom: 1px solid #f1f3f4;
}

.detail-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
}

.detail-label {
  font-weight: 600;
  color: #5a6c7d;
  font-size: 14px;
}

.detail-value {
  color: #2c3e50;
  font-weight: 500;
  font-size: 14px;
}

/* Responsive Design */
@media (max-width: 1200px) {
  .course-section {
    width: 90%;
  }
}

@media (max-width: 768px) {
  .course-section {
    width: 95%;
    padding: 20px 0;
  }

  .section-title {
    font-size: 22px;
  }

  .section-header {
    flex-direction: column;
    gap: 20px;
  }

  .view-more-btn {
    padding: 10px 20px;
    font-size: 14px;
  }
}
</style>
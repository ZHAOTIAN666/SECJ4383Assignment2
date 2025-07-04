<template>
  <div class="main-content">
    <!-- Content Section -->
    <div class="content-container">
      <div class="section-header">
        <h2 class="section-title">Our Expert Coaches</h2>
        <p class="section-description">Choose from our team of certified professionals</p>
      </div>

      <!-- Coach Cards -->
      <div class="coaches-grid">
        <el-row :gutter="24">
          <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in coachData" :key="item.id" class="coach-col">
            <div class="coach-card">
              <div class="coach-image-container" @click="$router.push('/front/coachDetail?id=' + item.id)">
                <img
                    :src="item.avatar"
                    alt="Coach Photo"
                    class="coach-image"
                >
                <div class="image-overlay">
                  <span class="view-details">View Details</span>
                </div>
              </div>
              <div class="coach-info">
                <h3 class="coach-name">{{ item.name }}</h3>
                <div class="coach-contact">
                  <span class="phone-info">
                    <i class="el-icon-phone"></i>
                    {{ item.phone }}
                  </span>
                </div>
                <el-button
                    type="primary"
                    class="book-btn"
                    @click="reserveInit(item.id)"
                    round
                >
                  <i class="el-icon-calendar"></i>
                  Book Now
                </el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- Booking Dialog -->
    <el-dialog
        title="Book Your Session"
        :visible.sync="fromVisible"
        width="500px"
        :close-on-click-modal="false"
        destroy-on-close
        class="booking-dialog"
    >
      <el-form label-width="120px" class="booking-form">
        <el-form-item prop="content" label="Special Notes">
          <el-input
              type="textarea"
              :rows="4"
              v-model="content"
              placeholder="Tell us about your goals, preferences, or any special requirements..."
              autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false" class="cancel-btn">Cancel</el-button>
        <el-button type="primary" @click="submit" class="submit-btn">
          <i class="el-icon-check"></i>
          Confirm Booking
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      coachData: [],
      content: null,
      coachId: null,
      fromVisible: false,
    }
  },
  mounted() {
    this.loadCoach()
  },
  methods: {
    loadCoach() {
      this.$request.get('/coach/selectAll').then(res => {
        if (res.code === '200') {
          this.coachData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reserveInit(coachId) {
      if (this.user.role !== 'USER') {
        this.$message.warning('Access denied: Only users can book coaching sessions')
        return
      }
      this.coachId = coachId
      this.fromVisible = true
    },
    submit() {
      const data = {
        userId: this.user.id,
        coachId: this.coachId,
        content: this.content,
      }
      this.$request.post('/reserve/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('Booking confirmed! Your coach will contact you soon.')
          this.coachId = null
          this.content = null
          this.fromVisible = false
        }
      })
    }
  }
}
</script>

<style scoped>
.main-content {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

/* Content Section */
.content-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 60px 20px;
}

.section-header {
  text-align: center;
  margin-bottom: 50px;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 15px;
}

.section-description {
  font-size: 1.1rem;
  color: #7f8c8d;
  max-width: 500px;
  margin: 0 auto;
}

/* Coach Cards */
.coaches-grid {
  margin-top: 40px;
}

.coach-col {
  margin-bottom: 30px;
}

.coach-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
  height: 100%;
}

.coach-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

.coach-image-container {
  position: relative;
  overflow: hidden;
  cursor: pointer;
}

.coach-image {
  width: 100%;
  height: 280px;
  object-fit: cover;
  transition: transform 0.3s ease;
  pointer-events: none; /* 防止图片阻挡容器的点击事件 */
}

.coach-image-container:hover .coach-image {
  transform: scale(1.05);
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
  pointer-events: none; /* 确保覆盖层不会阻挡点击 */
}

.coach-image-container:hover .image-overlay {
  opacity: 1;
}

.view-details {
  color: white;
  font-size: 1.1rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.coach-info {
  padding: 25px;
  text-align: center;
}

.coach-name {
  font-size: 1.4rem;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 15px;
}

.coach-contact {
  margin-bottom: 20px;
}

.phone-info {
  color: #7f8c8d;
  font-size: 0.95rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.book-btn {
  width: 100%;
  padding: 12px 0;
  font-size: 1rem;
  font-weight: 600;
  background: linear-gradient(45deg, #667eea 0%, #764ba2 100%);
  border: none;
  transition: all 0.3s ease;
}

.book-btn:hover {
  background: linear-gradient(45deg, #5a67d8 0%, #6b46c1 100%);
  transform: translateY(-2px);
}

/* Dialog Styles */
.booking-dialog .el-dialog {
  border-radius: 15px;
}

.booking-dialog .el-dialog__header {
  background: linear-gradient(45deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 20px 30px;
  border-radius: 15px 15px 0 0;
}

.booking-dialog .el-dialog__title {
  font-size: 1.3rem;
  font-weight: 600;
}

.booking-form {
  padding: 30px;
}

.booking-form .el-textarea__inner {
  border-radius: 10px;
  border: 2px solid #e9ecef;
  transition: border-color 0.3s ease;
}

.booking-form .el-textarea__inner:focus {
  border-color: #667eea;
}

.dialog-footer {
  padding: 20px 30px;
  text-align: right;
  background: #f8f9fa;
  border-radius: 0 0 15px 15px;
}

.cancel-btn {
  margin-right: 15px;
  padding: 10px 25px;
  border-radius: 25px;
}

.submit-btn {
  padding: 10px 30px;
  border-radius: 25px;
  background: linear-gradient(45deg, #667eea 0%, #764ba2 100%);
  border: none;
  font-weight: 600;
}

.submit-btn:hover {
  background: linear-gradient(45deg, #5a67d8 0%, #6b46c1 100%);
}

/* Responsive Design */
@media (max-width: 768px) {
  .section-title {
    font-size: 2rem;
  }

  .content-container {
    padding: 40px 15px;
  }

  .coach-card {
    margin-bottom: 20px;
  }
}

@media (max-width: 480px) {
  .booking-dialog {
    width: 90% !important;
  }
}
</style>
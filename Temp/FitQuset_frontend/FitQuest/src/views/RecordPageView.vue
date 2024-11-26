<template>
  <div class="record-page">
    <header>
      <h1>운동 및 식단 기록</h1>
      <p>운동과 식단 기록을 추가하고 관리하세요!</p>
    </header>

    <div class="content">
      <!-- 운동 기록 섹션 -->
      <section class="exercise-section">
        <h2>🏋️ 운동 기록</h2>
        <ExerciseFormComponent @record-added="fetchTodayExercises" />
        <!-- <RecordListComponent
          :records="todayExercises"
          type="exercise"
          @record-updated="fetchTodayExercises"
          @record-deleted="fetchTodayExercises"
        /> -->
      </section>

      <!-- 식단 기록 섹션 -->
      <section class="diet-section">
        <h2>🍴 식단 기록</h2>
        <DietFormComponent @record-added="fetchTodayDiets" />
        <!-- <RecordListComponent
          :records="todayDiets"
          type="diet"
          @record-updated="fetchTodayDiets"
          @record-deleted="fetchTodayDiets"
        /> -->
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import apiClient from '@/api/axios';
import ExerciseFormComponent from '@/components/ExerciseFormComponent.vue';
import DietFormComponent from '@/components/DietFormComponent.vue';
import RecordListComponent from '@/components/RecordListComponent.vue';

const userStore = useUserStore();
const router = useRouter();

// 상태 관리
const todayExercises = ref([]);
const todayDiets = ref([]);

// API 호출: 당일 운동 기록
const fetchTodayExercises = async () => {
  try {
    const response = await apiClient.get(`/exercises/today/${userStore.user.userId}`, {
      headers: { Authorization: `Bearer ${userStore.token}` },
    });
    console.log('Fetched Exercises:', response.data); // 디버깅용 로그
    todayExercises.value = response.data || [];
  } catch (error) {
    console.error('Error fetching today exercises:', error);
  }
};

// API 호출: 당일 식단 기록
const fetchTodayDiets = async () => {
  try {
    const response = await apiClient.get(`/diets/today/${userStore.user.userId}`, {
      headers: { Authorization: `Bearer ${userStore.token}` },
    });
    console.log('Fetched Diets:', response.data); // 디버깅용 로그
    todayDiets.value = response.data || [];
  } catch (error) {
    console.error('Error fetching today diets:', error);
  }
};

// 컴포넌트가 마운트되면 데이터 로드
onMounted(() => {
  fetchTodayExercises();
  fetchTodayDiets();
});
</script>

<style scoped>
.record-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

header {
  text-align: center;
  margin-bottom: 30px;
}

.content {
  display: flex;
  flex-direction: column;
  gap: 40px;
}

.exercise-section,
.diet-section {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>

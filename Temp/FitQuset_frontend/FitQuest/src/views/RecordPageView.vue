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
        <RecordListComponent
          :records="todayExercises"
          type="exercise"
          @record-updated="fetchTodayExercises"
          @record-deleted="fetchTodayExercises"
        />
      </section>

      <!-- 식단 기록 섹션 -->
      <section class="diet-section">
        <h2>🍴 식단 기록</h2>
        <DietFormComponent @record-added="fetchTodayDiets" />
        <RecordListComponent
          :records="todayDiets"
          type="diet"
          @record-updated="fetchTodayDiets"
          @record-deleted="fetchTodayDiets"
        />
      </section>

      <!-- 요약 섹션 -->
      <section class="summary-section">
        <h2>📊 요약</h2>
        <SummaryComponent
          :exerciseDistribution="exerciseDistribution"
          :dietDistribution="dietDistribution"
        />
      </section>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import ExerciseFormComponent from '@/components/ExerciseFormComponent.vue';
import DietFormComponent from '@/components/DietFormComponent.vue';
import RecordListComponent from '@/components/RecordListComponent.vue';
import SummaryComponent from '@/components/SummaryComponent.vue';
import apiClient from '@/api/axios';

const userStore = useUserStore();
const router = useRouter();

// 상태 관리
const todayExercises = ref([]);
const todayDiets = ref([]);
const exerciseDistribution = ref(null);
const dietDistribution = ref(null);

// 로그인 상태 확인 및 리다이렉트
const ensureAuthenticated = async () => {
  await userStore.restoreSession(); // 세션 복원
  if (!userStore.isAuthenticated || !userStore.user) {
    router.push({ name: 'login' });
  }
};

// API 호출: 당일 운동 기록
const fetchTodayExercises = async () => {
  try {
    const response = await apiClient.get(`/exercises/today/${userStore.user.userId}`, {
      headers: { Authorization: `Bearer ${userStore.token}` },
    });
    todayExercises.value = response.data;
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
    todayDiets.value = response.data;
  } catch (error) {
    console.error('Error fetching today diets:', error);
  }
};

// API 호출: 운동 유형 비중
const fetchExerciseDistribution = async () => {
  try {
    const response = await apiClient.get(`/exercises/${userStore.user.userId}/distribution`, {
      headers: { Authorization: `Bearer ${userStore.token}` },
      params: { date: new Date().toISOString().split('T')[0] },
    });
    exerciseDistribution.value = response.data;
  } catch (error) {
    console.error('Error fetching exercise distribution:', error);
  }
};

// API 호출: 식단 유형 비중
const fetchDietDistribution = async () => {
  try {
    const response = await apiClient.get(`/diets/${userStore.user.userId}/distribution`, {
      headers: { Authorization: `Bearer ${userStore.token}` },
      params: { date: new Date().toISOString().split('T')[0] },
    });
    dietDistribution.value = response.data;
  } catch (error) {
    console.error('Error fetching diet distribution:', error);
  }
};

// 컴포넌트가 마운트되면 데이터 로드
onMounted(async () => {
  await ensureAuthenticated();
  if (userStore.user) {
    fetchTodayExercises();
    fetchTodayDiets();
    fetchExerciseDistribution();
    fetchDietDistribution();
  }
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
.diet-section,
.summary-section {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>

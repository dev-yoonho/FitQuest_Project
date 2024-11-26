<template>
    <div class="exercises-page">
      <h2>내 운동 기록</h2>
  
      <!-- 필터 및 검색 -->
      <div class="filters">
        <select v-model="selectedType" @change="filterRecords">
          <option value="">전체</option>
          <option value="근력">근력 운동</option>
          <option value="유산소">유산소 운동</option>
          <option value="코어">코어 운동</option>
        </select>
        <input
          type="date"
          v-model="selectedDate"
          @change="filterRecords"
          placeholder="날짜 선택"
        />
      </div>
  
      <!-- 로딩 상태 -->
      <p v-if="loading">로딩 중...</p>
  
      <!-- 운동 기록 목록 -->
      <div v-else>
        <div v-if="filteredRecords.length > 0" class="record-list">
          <div
            v-for="record in filteredRecords"
            :key="record.exerciseId"
            class="record-item"
          >
            <p>
              <strong>{{ record.exerciseName }}</strong> ({{ record.exerciseType }}) - 
              {{ record.createdAt.split('T')[0] }}
            </p>
            <ul>
              <li v-if="record.exerciseSet">세트 수: {{ record.exerciseSet }}</li>
              <li v-if="record.repetitions">반복 횟수: {{ record.repetitions }}</li>
              <li v-if="record.weight">무게: {{ record.weight }} kg</li>
              <li v-if="record.distance">거리: {{ record.distance }} km</li>
              <li v-if="record.time">시간: {{ record.time }} 분</li>
              <li v-if="record.speed">속도: {{ record.speed }} km/h</li>
              <li v-if="record.indoorOutdoor">실내/실외: {{ record.indoorOutdoor }}</li>
            </ul>
          </div>
        </div>
        <p v-else>운동 기록이 없습니다.</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useUserStore } from '@/stores/userStore';
  import apiClient from '@/api/axios';
  
  const userStore = useUserStore();
  
  // 상태 관리
  const allRecords = ref([]);
  const filteredRecords = ref([]);
  const selectedType = ref('');
  const selectedDate = ref('');
  const loading = ref(false);
  
  // API 호출: 특정 사용자의 운동 기록 조회
  const fetchExercisesByUserId = async () => {
    loading.value = true;
    try {
      const response = await apiClient.get(`/admin/users/${userStore.user.userId}/exercises`, {
        headers: { Authorization: `Bearer ${userStore.token}` },
      });
      allRecords.value = response.data;
      filteredRecords.value = response.data;
    } catch (error) {
      console.error('Error fetching exercises:', error);
      alert('운동 기록을 불러오는 데 실패했습니다.');
    } finally {
      loading.value = false;
    }
  };
  
  // 필터 및 검색 적용
  const filterRecords = () => {
    filteredRecords.value = allRecords.value.filter((record) => {
      const matchesType = selectedType.value
        ? record.exerciseType === selectedType.value
        : true;
      const matchesDate = selectedDate.value
        ? record.createdAt.startsWith(selectedDate.value)
        : true;
      return matchesType && matchesDate;
    });
  };
  
  // 컴포넌트 마운트 시 데이터 로드
  onMounted(() => {
    fetchExercisesByUserId();
  });
  </script>
  
  <style scoped>
  .exercises-page {
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 5px;
  }
  
  .filters {
    display: flex;
    gap: 10px;
    margin-bottom: 15px;
  }
  
  .filters select,
  .filters input {
    padding: 8px;
    font-size: 14px;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
  
  .record-list {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  .record-item {
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #fff;
  }
  
  .record-item p {
    margin: 0;
    font-weight: bold;
  }
  
  .record-item ul {
    margin: 5px 0 0;
    padding: 0 0 0 15px;
    list-style: disc;
    font-size: 14px;
  }
  
  .record-item li {
    margin: 2px 0;
  }
  </style>
  
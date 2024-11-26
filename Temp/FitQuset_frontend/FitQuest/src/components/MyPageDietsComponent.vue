<template>
    <div class="diets-page">
      <h2>내 식단 기록</h2>
  
      <!-- 필터 및 검색 -->
      <div class="filters">
        <select v-model="selectedMealType" @change="filterRecords">
          <option value="">전체</option>
          <option value="아침">아침</option>
          <option value="점심">점심</option>
          <option value="저녁">저녁</option>
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
  
      <!-- 식단 기록 목록 -->
      <div v-else>
        <div v-if="filteredRecords.length > 0" class="record-list">
          <div
            v-for="record in filteredRecords"
            :key="record.dietId"
            class="record-item"
          >
            <p>
              <strong>{{ record.foodName }}</strong> ({{ record.mealType }}) - 
              {{ record.calories }}kcal - {{ record.createdAt.split('T')[0] }}
            </p>
          </div>
        </div>
        <p v-else>식단 기록이 없습니다.</p>
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
  const selectedMealType = ref('');
  const selectedDate = ref('');
  const loading = ref(false);
  
  // API 호출: 특정 사용자의 식단 기록 조회
  const fetchDietsByUserId = async () => {
    loading.value = true;
    try {
      const response = await apiClient.get(`/admin/users/${userStore.user.userId}/diets`, {
        headers: { Authorization: `Bearer ${userStore.token}` },
      });
      allRecords.value = response.data;
      filteredRecords.value = response.data;
    } catch (error) {
      console.error('Error fetching diets:', error);
      alert('식단 기록을 불러오는 데 실패했습니다.');
    } finally {
      loading.value = false;
    }
  };
  
  // 필터 및 검색 적용
  const filterRecords = () => {
    filteredRecords.value = allRecords.value.filter((record) => {
      const matchesMealType = selectedMealType.value
        ? record.mealType === selectedMealType.value
        : true;
      const matchesDate = selectedDate.value
        ? record.createdAt.startsWith(selectedDate.value)
        : true;
      return matchesMealType && matchesDate;
    });
  };
  
  // 컴포넌트 마운트 시 데이터 로드
  onMounted(() => {
    fetchDietsByUserId();
  });
  </script>
  
  <style scoped>
  .diets-page {
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
  }
  </style>
  
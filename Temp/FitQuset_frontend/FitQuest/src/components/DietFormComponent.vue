<template>
    <div class="diet-form">
      <h3>식단 기록 추가</h3>
      <form @submit.prevent="handleSubmit">
        <!-- 음식 이름 -->
        <div class="form-group">
          <label for="foodName" class="required">음식 이름</label>
          <input
            id="foodName"
            v-model="diet.foodName"
            placeholder="음식 이름 입력"
            required
          />
        </div>
  
        <!-- 칼로리 -->
        <div class="form-group">
          <label for="calories" class="required">칼로리</label>
          <input
            id="calories"
            type="number"
            v-model="diet.calories"
            min="0"
            required
          />
        </div>
  
        <!-- 식사 유형 -->
        <div class="form-group">
          <label for="mealType" class="required">식사 유형</label>
          <select id="mealType" v-model="diet.mealType" required>
            <option disabled value="">식사 유형 선택</option>
            <option value="아침">아침</option>
            <option value="점심">점심</option>
            <option value="저녁">저녁</option>
          </select>
        </div>
  
        <!-- 제출 버튼 -->
        <button type="submit" class="submit-button">추가</button>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useUserStore } from '@/stores/userStore';
  import { useRouter } from 'vue-router';
  import apiClient from '@/api/axios';
  
  // Vue emit 설정
  const emit = defineEmits(['record-added']);
  
  // Pinia 상태
  const userStore = useUserStore();
  const router = useRouter();
  
  // 식단 데이터
  const diet = ref({
    userId: userStore.user?.userId,
    foodName: '',
    calories: null,
    mealType: '',
  });
  
  // 제출 처리
  const handleSubmit = async () => {
    try {
      await apiClient.post('/diets', diet.value, {
        headers: { Authorization: `Bearer ${userStore.token}` },
      });
  
      alert('식단 기록이 성공적으로 추가되었습니다.');
  
      // 폼 초기화
      diet.value = {
        userId: userStore.user?.userId,
        foodName: '',
        calories: null,
        mealType: '',
      };
  
      emit('record-added');
    } catch (error) {
      console.error('Error adding diet record:', error);
  
      // 권한 문제 처리
      if (error.response && error.response.status === 403) {
        alert('권한이 없습니다. 다시 로그인해주세요.');
        userStore.logout(); // 토큰 제거
        router.push({ name: 'login' }); // 로그인 페이지로 이동
      } else {
        alert('식단 기록 추가에 실패했습니다. 다시 시도해주세요.');
      }
    }
  };
  </script>
  
  <style scoped>
  .diet-form {
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
  }
  
  h3 {
    margin-bottom: 15px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  
  input,
  select {
    width: 100%;
    padding: 8px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }
  
  button.submit-button {
    padding: 10px;
    font-size: 16px;
    background-color: #4caf50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button.submit-button:hover {
    background-color: #45a049;
  }
  
  label.required::after {
    content: " *";
    color: red;
  }
  </style>
  
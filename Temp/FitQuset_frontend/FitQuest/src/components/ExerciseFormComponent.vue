<template>
  <div class="exercise-form">
    <h3>운동 기록 추가</h3>
    <form @submit.prevent="handleSubmit">
      <!-- 운동 이름 -->
      <div class="form-group">
        <label for="exerciseName" class="required">운동 이름</label>
        <input
          id="exerciseName"
          v-model="exercise.exerciseName"
          placeholder="운동 이름 입력"
          required
        />
      </div>

      <!-- 운동 유형 -->
      <div class="form-group">
        <label for="exerciseType" class="required">운동 유형</label>
        <select id="exerciseType" v-model="exercise.exerciseType" required>
          <option disabled value="">운동 유형 선택</option>
          <option value="근력">근력 운동</option>
          <option value="유산소">유산소 운동</option>
          <option value="코어">코어 운동</option>
        </select>
      </div>

      <!-- 동적 입력 필드 -->
      <div v-if="exercise.exerciseType === '근력'">
        <div class="form-group">
          <label for="exerciseSet" class="required">세트 수</label>
          <input
            id="exerciseSet"
            type="number"
            v-model="exercise.exerciseSet"
            min="1"
            required
          />
        </div>
        <div class="form-group">
          <label for="repetitions" class="required">반복 횟수</label>
          <input
            id="repetitions"
            type="number"
            v-model="exercise.repetitions"
            min="1"
            required
          />
        </div>
        <div class="form-group">
          <label for="weight">무게 (kg)</label>
          <input id="weight" type="number" v-model="exercise.weight" min="0" />
        </div>
      </div>

      <div v-else-if="exercise.exerciseType === '유산소'">
        <div class="form-group">
          <label for="distance" class="required">거리 (km)</label>
          <input
            id="distance"
            type="number"
            v-model="exercise.distance"
            @input="calculateSpeed"
            min="0.1"
            step="0.1"
            required
          />
        </div>
        <div class="form-group">
          <label for="time" class="required">시간 (분)</label>
          <input
            id="time"
            type="number"
            v-model="exercise.time"
            @input="calculateSpeed"
            min="1"
            required
          />
        </div>
        <div class="form-group">
          <label for="speed">속도 (km/h)</label>
          <input
            id="speed"
            :value="calculatedSpeed"
            readonly
            placeholder="자동 계산"
          />
        </div>
      </div>

      <div v-else-if="exercise.exerciseType === '코어'">
        <div class="form-group">
          <label for="exerciseSet" class="required">세트 수</label>
          <input
            id="exerciseSet"
            type="number"
            v-model="exercise.exerciseSet"
            min="1"
            required
          />
        </div>
        <div class="form-group">
          <label for="repetitions" class="required">반복 횟수</label>
          <input
            id="repetitions"
            type="number"
            v-model="exercise.repetitions"
            min="1"
            required
          />
        </div>
        <div class="form-group">
          <label for="time">시간 (분)</label>
          <input id="time" type="number" v-model="exercise.time" min="1" />
        </div>
      </div>

      <!-- 실내/실외 -->
      <div class="form-group">
        <label for="indoorOutdoor">실내/실외</label>
        <select id="indoorOutdoor" v-model="exercise.indoorOutdoor">
          <option value="실내">실내</option>
          <option value="실외">실외</option>
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
import apiClient from '@/api/axios';
import { useRouter } from 'vue-router';

// emit 선언
const emit = defineEmits(['record-added']);

// Pinia 상태
const userStore = useUserStore();
const router = useRouter();

// 운동 데이터
const exercise = ref({
  userId: userStore.user?.userId,
  exerciseName: '',
  exerciseType: '',
  exerciseSet: null,
  repetitions: null,
  weight: null,
  distance: null,
  time: null,
  speed: null,
  indoorOutdoor: '실내',
});

// 계산된 속도
const calculatedSpeed = ref('');

// 속도 계산 함수
const calculateSpeed = () => {
  const { distance, time } = exercise.value;
  if (distance && time) {
    calculatedSpeed.value = ((distance / (time / 60)).toFixed(2) || '0');
    exercise.value.speed = parseFloat(calculatedSpeed.value);
  }
};

// 제출 처리
const handleSubmit = async () => {
  try {
    if (!userStore.token) {
      alert('로그인이 필요합니다.');
      router.push({ name: 'login' });
      return;
    }

    await apiClient.post('/exercises', exercise.value, {
      headers: { Authorization: `Bearer ${userStore.token}` },
    });

    alert('운동 기록이 성공적으로 추가되었습니다.');
    // 폼 초기화
    exercise.value = {
      userId: userStore.user?.userId,
      exerciseName: '',
      exerciseType: '',
      exerciseSet: null,
      repetitions: null,
      weight: null,
      distance: null,
      time: null,
      speed: null,
      indoorOutdoor: '실내',
    };
    calculatedSpeed.value = '';
    emit('record-added');
  } catch (error) {
    console.error('Error adding exercise record:', error);

    if (error.response?.status === 403) {
      alert('접근 권한이 없습니다. 다시 로그인해주세요.');
      router.push({ name: 'login' });
    } else if (error.response?.status === 401) {
      alert('세션이 만료되었습니다. 다시 로그인해주세요.');
      router.push({ name: 'login' });
    } else {
      alert('운동 기록 추가에 실패했습니다.');
    }
  }
};
</script>

<style scoped>
.exercise-form {
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

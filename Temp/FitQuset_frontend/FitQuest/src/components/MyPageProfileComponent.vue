<template>
  <div class="profile-edit-page">
    <h1>프로필 수정</h1>
    <form @submit.prevent="handleProfileUpdate" class="profile-edit-form">
      <!-- 이메일 (수정 불가) -->
      <div class="form-group">
        <label for="email" class="required">이메일</label>
        <input id="email" type="email" v-model="profile.email" disabled />
      </div>

      <!-- 새 비밀번호 -->
      <div class="form-group">
        <label for="password">새 비밀번호</label>
        <input
          id="password"
          type="password"
          v-model="password"
          placeholder="비밀번호를 변경하려면 입력하세요"
          @input="validatePassword"
        />
      </div>

      <!-- 새 비밀번호 확인 -->
      <div class="form-group">
        <label for="confirmPassword">새 비밀번호 확인</label>
        <input
          id="confirmPassword"
          type="password"
          v-model="confirmPassword"
          placeholder="비밀번호를 다시 입력하세요"
          @input="validatePassword"
        />
        <p v-if="!passwordsMatch" class="error-message">비밀번호가 일치하지 않습니다.</p>
      </div>

      <!-- 닉네임 -->
      <div class="form-group">
        <label for="nickname" class="required">닉네임</label>
        <input id="nickname" type="text" v-model="profile.nickname" required />
      </div>

      <!-- 이름 -->
      <div class="form-group">
        <label for="name" class="required">이름</label>
        <input id="name" type="text" v-model="profile.name" required />
      </div>

      <!-- 전화번호 -->
      <div class="form-group">
        <label for="phone">전화번호</label>
        <input id="phone" type="tel" v-model="profile.phoneNumber" />
      </div>

      <!-- 키 -->
      <div class="form-group">
        <label for="height" class="required">키 (cm)</label>
        <input id="height" type="number" step="0.1" v-model="profile.height" required />
      </div>

      <!-- 체중 -->
      <div class="form-group">
        <label for="weight" class="required">체중 (kg)</label>
        <input id="weight" type="number" step="0.1" v-model="profile.weight" required />
      </div>

      <!-- 나이 -->
      <div class="form-group">
        <label for="age" class="required">나이</label>
        <input id="age" type="number" v-model="profile.age" required />
      </div>

      <!-- 버튼 -->
      <div class="button-group">
        <button
          type="submit"
          class="save-button"
          :disabled="!canSubmit"
        >
          저장하기
        </button>
        <button type="button" @click="cancelEdit" class="cancel-button">취소하기</button>
      </div>
    </form>
  </div>
</template>

---

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import apiClient from '@/api/axios';

const router = useRouter();
const userStore = useUserStore();

// 사용자 프로필 데이터
const profile = ref({
  email: '',
  nickname: '',
  name: '',
  phoneNumber: '',
  height: null,
  weight: null,
  age: null,
});

// 새 비밀번호 필드
const password = ref('');
const confirmPassword = ref('');
const passwordsMatch = ref(true);

// 비밀번호 검증
const validatePassword = () => {
  passwordsMatch.value =
    password.value === confirmPassword.value || (!password.value && !confirmPassword.value);
};

// 수정 버튼 활성화 조건
const canSubmit = computed(() => {
  return passwordsMatch.value && profile.value.nickname && profile.value.name;
});

// 프로필 데이터 로드
const fetchProfile = async () => {
  try {
    const userId = userStore.user.userId;
    const response = await apiClient.get(`/users/${userId}`, {
      headers: { Authorization: `Bearer ${sessionStorage.getItem('authToken')}` },
    });
    profile.value = response.data; // 서버에서 가져온 데이터로 초기화
  } catch (error) {
    console.error('Error fetching profile:', error);
    alert('프로필 정보를 가져오는 데 실패했습니다.');
  }
};

// 프로필 업데이트
const handleProfileUpdate = async () => {
  try {
    const userId = userStore.user.userId;
    const updatedProfile = {
      ...profile.value,
      ...(password.value && { password: password.value }), // 비밀번호가 입력된 경우에만 추가
    };

    await apiClient.put(`/users/${userId}`, updatedProfile, {
      headers: { Authorization: `Bearer ${sessionStorage.getItem('authToken')}` },
    });

    alert('프로필이 성공적으로 수정되었습니다.');
    userStore.setUser(profile.value); // Pinia 스토어 업데이트
    router.push({ name: 'mypage-home' }); // 마이페이지 홈으로 이동
  } catch (error) {
    console.error('Error updating profile:', error);
    alert('프로필 수정에 실패했습니다.');
  }
};

// 수정 취소
const cancelEdit = () => {
  router.push({ name: 'mypage-home' });
};

// 마운트 시 프로필 데이터 로드
onMounted(fetchProfile);
</script>
<style scoped>
.profile-edit-page {
  max-width: 500px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.profile-edit-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

label {
  font-weight: bold;
  font-size: 14px;
}

input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}

input[disabled] {
  background-color: #eee;
  cursor: not-allowed;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

.save-button {
  background-color: #4caf50;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-button {
  background-color: #f44336;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>

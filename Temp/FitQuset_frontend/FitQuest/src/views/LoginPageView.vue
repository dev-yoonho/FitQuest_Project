<template>
  <div class="login-page">
    <h1>로그인</h1>

    <!-- 로그인 폼 -->
    <form @submit.prevent="handleLogin" class="login-form">
      <!-- 이메일 필드 -->
      <div class="form-group">
        <label for="email">아이디 또는 이메일</label>
        <input
          v-model="email"
          id="email"
          type="text"
          placeholder="아이디 또는 이메일 입력"
          class="form-input"
          required
        />
      </div>

      <!-- 비밀번호 필드 -->
      <div class="form-group">
        <label for="password">비밀번호</label>
        <input
          v-model="password"
          id="password"
          type="password"
          placeholder="비밀번호 입력"
          class="form-input"
          required
        />
      </div>

      <!-- 오류 메시지 -->
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <!-- 버튼들 -->
      <div class="button-group">
        <button
          type="submit"
          class="login-button"
          :disabled="!canSubmit"
        >
          로그인
        </button>
        <button
          type="button"
          class="signup-button"
          @click="goToSignup"
        >
          회원가입
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import apiClient from '@/api/axios';
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore'; // Import Pinia store

// 상태 관리
const email = ref('');
const password = ref('');
const errorMessage = ref('');
const router = useRouter();
const userStore = useUserStore(); // Access the Pinia store

// 로그인 버튼 활성화 여부
const canSubmit = computed(() => email.value && password.value);

// 로그인 핸들러
const handleLogin = async () => {
  try {
    errorMessage.value = ''; // 기존 오류 메시지 초기화

    // 로그인 API 요청
    const response = await apiClient.post('/auth/login', {
      email: email.value,
      password: password.value,
    });

    const token = response.data.token; // 토큰 추출
    if (token) {
      // Pinia store를 통해 사용자 로그인 처리
      await userStore.login(token);
      router.push('/'); // 로그인 성공 시 메인 페이지로 이동
    } else {
      throw new Error('응답에 토큰이 포함되지 않았습니다.');
    }
  } catch (error) {
    console.error('로그인 실패:', error);
    errorMessage.value = '로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.';
  }
};

// 회원가입 페이지로 이동
const goToSignup = () => {
  router.push('/signup');
};
</script>

  
  <style scoped>
  .login-page {
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  h1 {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  
  .form-input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }
  
  .error-message {
    color: red;
    font-size: 14px;
    margin-bottom: 15px;
    text-align: center;
  }
  
  .button-group {
    display: flex;
    justify-content: space-between;
    gap: 10px;
  }
  
  .login-button,
  .signup-button {
    flex: 1;
    padding: 10px;
    font-size: 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .login-button {
    background-color: #4caf50;
    color: white;
  }
  
  .login-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  
  .signup-button {
    background-color: #2196f3;
    color: white;
  }
  </style>
  
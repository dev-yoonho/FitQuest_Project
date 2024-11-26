<template>
    <div class="signup-page">
      <h1>회원가입</h1>
      <form @submit.prevent="handleSignup" class="signup-form">
        <div class="form-group">
          <label for="email" class="required">이메일</label>
          <input id="email" type="email" v-model="email" required />
        </div>
        <div class="form-group">
          <label for="password" class="required">비밀번호</label>
          <input id="password" type="password" v-model="password" required />
        </div>
        <div class="form-group">
          <label for="confirmPassword" class="required">비밀번호 확인</label>
          <input id="confirmPassword" type="password" v-model="confirmPassword" required />
        </div>
        <div class="form-group">
          <label for="name" class="required">이름</label>
          <input id="name" type="text" v-model="name" required />
        </div>
        <div class="form-group">
          <label for="phone">전화번호</label>
          <input id="phone" type="tel" v-model="phoneNumber" />
        </div>
        <div class="form-group">
          <label for="nickname" class="required">닉네임</label>
          <input id="nickname" type="text" v-model="nickname" required />
        </div>
        <div class="form-group">
          <label for="height" class="required">키 (cm)</label>
          <input id="height" type="number" v-model="height" required />
        </div>
        <div class="form-group">
          <label for="weight" class="required">체중 (kg)</label>
          <input id="weight" type="number" v-model="weight" required />
        </div>
        <div class="form-group">
          <label for="age" class="required">나이</label>
          <input id="age" type="number" v-model="age" required />
        </div>
        <button type="submit" class="signup-button">회원가입</button>
      </form>
    </div>
  </template>
  
  
  <script setup>
  import { ref, computed } from 'vue';
  import { useRouter } from 'vue-router';
  import apiClient from '@/api/axios';
  
  const router = useRouter();
  
  // 상태 관리
  const email = ref('');
  const password = ref('');
  const confirmPassword = ref('');
  const name = ref('');
  const phoneNumber = ref('');
  const nickname = ref('');
  const height = ref('');
  const weight = ref('');
  const age = ref('');
  const errorMessage = ref('');
  
  // 유효성 검사 상태
  const isEmailValid = ref(false);
  const isPasswordValid = ref(false);
  const isMatchingPassword = ref(false);
  
  // 유효성 검사 함수
  const validateEmail = () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    isEmailValid.value = emailRegex.test(email.value);
  };
  
  const validatePassword = () => {
    isPasswordValid.value = password.value.length >= 8;
  };
  
  const validatePasswordMatch = () => {
    isMatchingPassword.value = password.value === confirmPassword.value;
  };
  
  // 모든 유효성 조건이 충족되었는지 확인
  const canSubmit = computed(
    () =>
      isEmailValid.value &&
      isPasswordValid.value &&
      isMatchingPassword.value &&
      name.value &&
      nickname.value &&
      height.value &&
      weight.value &&
      age.value
  );
  
  // 회원가입 처리
  const handleSignup = async () => {
    try {
      errorMessage.value = ''; // 오류 메시지 초기화
  
      // API 요청
      await apiClient.post('/users/signup', {
        email: email.value,
        password: password.value,
        name: name.value,
        phoneNumber: phoneNumber.value,
        nickname: nickname.value,
        height: parseFloat(height.value),
        weight: parseFloat(weight.value),
        age: parseInt(age.value, 10),
      });
  
      // 성공 시 로그인 페이지로 이동
      router.push('/login');
    } catch (error) {
      console.error('회원가입 실패:', error);
      errorMessage.value =
        error.response?.data?.message || '회원가입에 실패했습니다. 다시 시도해주세요.';
    }
  };
  </script>
  
  <style scoped>
  .signup-page {
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .signup-form {
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
    width: 100%;
    box-sizing: border-box;
  }
  
  .error-message {
    color: red;
    font-size: 12px;
    margin-top: 5px;
  }
  
  .signup-button {
    padding: 10px;
    font-size: 16px;
    background-color: #4caf50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .signup-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  
  label.required::after {
  content: " *";
  color: red;
  font-weight: bold;
}
  h1 {
    text-align: center;
    margin-bottom: 20px;
  }
  </style>
  
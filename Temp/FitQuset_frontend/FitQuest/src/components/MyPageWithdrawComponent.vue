<template>
    <div class="withdraw-page">
      <h2>회원 탈퇴</h2>
      <p class="warning">
        정말로 회원 탈퇴를 진행하시겠습니까? <br />
        탈퇴 후에는 복구가 불가능하며, 모든 데이터가 삭제됩니다.
      </p>
  
      <div class="actions">
        <button @click="handleWithdraw" class="confirm-button">확인</button>
        <button @click="cancelWithdraw" class="cancel-button">취소</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router';
  import { useUserStore } from '@/stores/userStore';
  import apiClient from '@/api/axios';
  
  const router = useRouter();
  const userStore = useUserStore();
  
  // 회원 탈퇴 처리
  const handleWithdraw = async () => {
    try {
      const confirmed = confirm("정말로 탈퇴하시겠습니까?");
      if (!confirmed) return;
  
      await apiClient.delete(`/users/${userStore.user.userId}`, {
        headers: { Authorization: `Bearer ${userStore.token}` },
      });
  
      alert("회원 탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
      userStore.logout();
      router.push({ name: 'login' });
    } catch (error) {
      console.error("Error during withdrawal:", error);
      if (error.response?.status === 403 || error.response?.status === 401) {
        alert("접근 권한이 없습니다. 다시 로그인해주세요.");
        router.push({ name: 'login' });
      } else {
        alert("회원 탈퇴에 실패했습니다. 다시 시도해주세요.");
      }
    }
  };
  
  // 탈퇴 취소
  const cancelWithdraw = () => {
    router.push({ name: 'mypage-home' });
  };
  </script>
  
  <style scoped>
  .withdraw-page {
    padding: 20px;
    text-align: center;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
  
  .warning {
    font-size: 16px;
    color: #d9534f;
    margin-bottom: 20px;
    line-height: 1.5;
  }
  
  .actions {
    display: flex;
    justify-content: center;
    gap: 10px;
  }
  
  .confirm-button,
  .cancel-button {
    padding: 10px 20px;
    font-size: 14px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .confirm-button {
    background-color: #d9534f;
    color: white;
  }
  
  .confirm-button:hover {
    background-color: #c9302c;
  }
  
  .cancel-button {
    background-color: #ccc;
    color: black;
  }
  
  .cancel-button:hover {
    background-color: #bbb;
  }
  </style>
  
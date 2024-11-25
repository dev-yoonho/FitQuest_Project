<template>
    <header class="header">
      <div class="logo">
        <img src="" alt="서비스 로고" />
        <span class="service-name">FitQuest</span>
      </div>
      <nav class="nav-bar">
        <ul>
          <li><RouterLink :to="{ name: 'main' }">홈</RouterLink></li>
          <li><RouterLink :to="{ name: 'community' }">커뮤니티</RouterLink></li>
          <li><RouterLink :to="{ name: 'record' }">기록하기</RouterLink></li>
          <li v-if="isLoggedIn"><RouterLink :to="{ name: 'mypage' }">마이페이지</RouterLink></li>
          <li v-if="isAdmin"><RouterLink :to="{ name: 'admin' }">관리자 페이지</RouterLink></li>
          <li v-if="isLoggedIn" @click="logout">로그아웃</li>
          <li v-else><RouterLink :to="{ name: 'login' }">로그인</RouterLink></li>
        </ul>
      </nav>
    </header>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { useUserStore } from '@/stores/userStore';
  import { useRouter } from 'vue-router';
  
  const userStore = useUserStore();
  const router = useRouter();
  
  // 로그인 상태 확인
  const isLoggedIn = computed(() => !!userStore.user);
  const isAdmin = computed(() => userStore.user?.isAdmin || false);
  
  // 로그아웃 처리
  const logout = () => {
    console.log('Logging out...');
    userStore.logout();
    router.push('/login');
  };
  </script>
  
  <style scoped>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 2rem;
    background-color: #4caf50;
    color: white;
  }
  
  .logo {
    display: flex;
    align-items: center;
  }
  
  .logo img {
    height: 40px;
    margin-right: 10px;
  }
  
  .service-name {
    font-size: 1.5rem;
    font-weight: bold;
  }
  
  .nav-bar ul {
    list-style: none;
    display: flex;
    gap: 1rem;
    margin: 0;
    padding: 0;
  }
  
  .nav-bar ul li {
    cursor: pointer;
  }
  
  .nav-bar ul li a {
    color: white;
    text-decoration: none;
  }
  
  .nav-bar ul li a:hover {
    text-decoration: underline;
  }
  </style>
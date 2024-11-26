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
        <li @click="navigateToMyPage">마이페이지</li>
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

const isAuthenticated = computed(() => userStore.isAuthenticated);

// 마이페이지로 이동
const navigateToMyPage = () => {
  if (isAuthenticated.value) {
    router.push({ name: 'mypage-home' }); // 로그인 상태이면 마이페이지 이동
  } else {
    router.push({ name: 'login' }); // 비로그인 상태이면 로그인 페이지 이동
  }
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

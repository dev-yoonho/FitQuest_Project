<template>
    <div class="main-page">
      <!-- 상단 헤더 -->
      <header class="header">
        <div v-if="isLoggedIn" class="welcome-box">
          <p>{{ userNickname }} 님! 환영합니다.</p>
        </div>
      </header>
  
      <!-- 인기 게시글 -->
      <section class="popular-posts">
        <h2>🔥 인기 게시글</h2>
        <div v-if="popularPosts.length > 0" class="post-list">
          <div
            v-for="post in popularPosts"
            :key="post.postId"
            class="post-item"
            @click="goToPost(post.postId)"
          >
            <h3>[{{ post.category }}] {{ post.title }}</h3>
            <p>작성자: {{ post.nickname }}</p>
            <p>추천 수: {{ post.likes }}</p>
          </div>
        </div>
        <div v-else>
          <p>현재 인기 게시글이 없습니다. 첫 게시글을 작성해 보세요!</p>
        </div>
      </section>
  
      <!-- 기록하기 배너 -->
      <section class="record-banner" @click="goToRecord">
        <h2>📋 지금 기록하기</h2>
        <p>운동과 식단을 기록하고 당신의 성취를 관리하세요!</p>
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue';
  import { useRouter } from 'vue-router';
  import { useUserStore } from '@/stores/userStore'; // Pinia store 임포트
  import apiClient from '@/api/axios';
  
  const userStore = useUserStore(); // Pinia store 인스턴스 생성
  const router = useRouter();
  
  // 상태 관리
  const popularPosts = ref([]);
  const userNickname = ref('');
  
  // 로그인 상태 확인
  const isLoggedIn = computed(() => userStore.isAuthenticated);
  
  // JWT 디코딩 함수
  function decodeJwt(token) {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error('Invalid token:', error);
      return null;
    }
  }
  
  // 유저 정보 가져오기
  const fetchUserInfo = async () => {
    try {
      const token = sessionStorage.getItem('authToken');
      if (!token) throw new Error('토큰이 없습니다. 로그인이 필요합니다.');
  
      const decodedToken = decodeJwt(token);
      if (!decodedToken) throw new Error('토큰 디코딩에 실패했습니다.');
  
      const userEmail = decodedToken.sub;
      const response = await apiClient.get(`/users/email/${userEmail}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
  
      userNickname.value = response.data.nickname; // 닉네임 업데이트
      userStore.user = response.data; // Pinia 상태 업데이트
    } catch (error) {
      console.error('Error fetching user info:', error);
    }
  };
  
  // 게시글 상세 페이지로 이동
  const goToPost = (postId) => {
    router.push(`/community/${postId}`);
  };
  
  // 기록하기 페이지로 이동
  const goToRecord = () => {
    router.push('/record');
  };
  
  // 인기 게시글 가져오기
  const fetchPopularPosts = async () => {
    try {
      const response = await apiClient.get('/posts/search', {
        params: { sortBy: 'likes', limit: 10 },
      });
      popularPosts.value = response.data;
    } catch (error) {
      console.error('Error fetching popular posts:', error);
    }
  };
  
  // 컴포넌트가 마운트될 때 실행
  onMounted(() => {
    fetchPopularPosts();
    if (isLoggedIn.value) fetchUserInfo();
  });
  </script>
  
  
  <style scoped>
  .main-page {
    padding: 20px;
  }
  
  /* 상단 헤더 스타일 */
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
  
  /* 환영 상자 스타일 */
  .welcome-box {
    background-color: #f0f8ff;
    border: 1px solid #ccc;
    padding: 10px 20px;
    border-radius: 8px;
    font-size: 16px;
    color: #333;
    text-align: center;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  
  /* 인기 게시글 */
  .popular-posts {
    margin-bottom: 40px;
  }
  
  .post-list {
    display: grid;
    gap: 20px;
  }
  
  .post-item {
    border: 1px solid #ccc;
    padding: 15px;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .post-item:hover {
    background-color: #f9f9f9;
  }
  
  /* 기록하기 배너 */
  .record-banner {
    background-color: #f0f8ff;
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .record-banner:hover {
    background-color: #e0f0ff;
  }
  </style>
  
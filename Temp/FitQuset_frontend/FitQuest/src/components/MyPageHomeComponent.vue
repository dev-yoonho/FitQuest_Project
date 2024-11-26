<template>
    <div class="mypage-home">
      <div class="container">
        <!-- 왼쪽: 버튼 영역 -->
        <div class="buttons-container">
          <h2>마이페이지</h2>
          <p>프로필 수정 및 기록을 확인하세요.</p>
          <button @click="navigateTo('mypage-profile')" class="action-button">
            프로필 수정
          </button>
          <button @click="navigateTo('mypage-exercises')" class="action-button">
            운동 기록 보기
          </button>
          <button @click="navigateTo('mypage-diets')" class="action-button">
            식단 기록 보기
          </button>
          <button @click="showMyPosts" class="action-button">
            내 작성글 보기
          </button>
          <button @click="showMyComments" class="action-button">
            내 댓글 보기
          </button>
        </div>
  
        <!-- 오른쪽: 데이터 출력 영역 -->
        <div class="data-container">
          <!-- 작성글 목록 -->
          <div v-if="currentView === 'posts'" class="posts-section">
            <h3>내 작성글</h3>
            <div v-if="loading">로딩 중...</div>
            <ul v-else-if="myPosts.length > 0">
              <li
                v-for="post in myPosts"
                :key="post.postId"
                @click="goToPostDetail(post.postId)"
                class="clickable"
              >
                {{ post.title }}
              </li>
            </ul>
            <p v-else>작성한 글이 없습니다.</p>
          </div>
  
          <!-- 댓글 목록 -->
          <div v-if="currentView === 'comments'" class="comments-section">
            <h3>내 댓글</h3>
            <div v-if="loading">로딩 중...</div>
            <ul v-else-if="myComments.length > 0">
              <li v-for="comment in myComments" :key="comment.id">
                {{ comment.content }}
              </li>
            </ul>
            <p v-else>작성한 댓글이 없습니다.</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue';
  import { useUserStore } from '@/stores/userStore';
  import apiClient from '@/api/axios';
  import { useRouter } from 'vue-router';
  
  // Pinia 상태 및 라우터
  const userStore = useUserStore();
  const router = useRouter();
  const user = computed(() => userStore.user);
  
  // 상태 관리
  const currentView = ref(null); // 현재 활성화된 보기 ('posts' 또는 'comments')
  const myPosts = ref([]);
  const myComments = ref([]);
  const loading = ref(false);
  
  // 버튼 동작
  const navigateTo = (routeName) => {
    router.push({ name: routeName });
  };
  
  // 내 작성글 보기
  const showMyPosts = async () => {
    if (!user.value) {
      console.error('User not logged in');
      return;
    }
    currentView.value = 'posts';
    loading.value = true;
    try {
      const response = await apiClient.get(`/posts/user/${user.value.userId}`, {
        headers: { Authorization: `Bearer ${sessionStorage.getItem('authToken')}` },
      });
      myPosts.value = response.data;
    } catch (error) {
      console.error('Error fetching my posts:', error);
      myPosts.value = [];
    } finally {
      loading.value = false;
    }
  };
  
  // 게시글 상세페이지로 이동
  const goToPostDetail = (postId) => {
    router.push({ name: 'postDetail', params: { postId } });
  };
  
  // 내 댓글 보기
  const showMyComments = async () => {
    if (!user.value) {
      console.error('User not logged in');
      return;
    }
    currentView.value = 'comments';
    loading.value = true;
    try {
      const response = await apiClient.get(`/comments/user/${user.value.userId}`, {
        headers: { Authorization: `Bearer ${sessionStorage.getItem('authToken')}` },
      });
      myComments.value = response.data;
    } catch (error) {
      console.error('Error fetching my comments:', error);
      myComments.value = [];
    } finally {
      loading.value = false;
    }
  };
  </script>
  
  <style scoped>
  .mypage-home {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding: 20px;
  }
  
  .container {
    display: flex;
    width: 100%;
    max-width: 1200px;
    gap: 20px;
  }
  
  .buttons-container {
    flex: 1;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
  }
  
  .action-button {
    width: 100%;
    padding: 10px 15px;
    margin-bottom: 10px;
    font-size: 14px;
    color: white;
    background-color: #4caf50;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .action-button:hover {
    background-color: #45a049;
  }
  
  .data-container {
    flex: 2;
    padding: 20px;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
  }
  
  .posts-section ul,
  .comments-section ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }
  
  .posts-section li,
  .comments-section li {
    padding: 5px 0;
    border-bottom: 1px solid #ddd;
    cursor: pointer;
  }
  
  .posts-section li:hover,
  .comments-section li:hover {
    background-color: #f0f0f0;
  }
  
  .clickable {
    cursor: pointer;
    color: #4caf50;
    transition: color 0.3s ease;
  }
  
  .clickable:hover {
    color: #2e7d32;
  }
  </style>
  
<template>
    <div class="main-page">
        <!-- 상단 헤더 -->
        <header class="header">
            <div v-if="isLoggedIn" class="user-info">
            <p>{{ userNickname }} 님! 환영합니다.</p>
            <button @click="handleLogout" class="logout-button">로그아웃</button>
            </div>
            <div v-else class="auth-actions">
            <button @click="goToLogin" class="login-button">로그인</button>
            <button @click="goToSignup" class="signup-button">회원가입</button>
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
    import apiClient from '@/api/axios';
    
    // 상태 관리
    const popularPosts = ref([]); // 인기 게시글 데이터
    const userInfo = ref(null); // 사용자 정보 상태
    const userNickname = ref(''); // 사용자 닉네임 상태
    const router = useRouter();
    
    // 로그인 상태 확인
    const isLoggedIn = computed(() => !!sessionStorage.getItem('authToken'));
    
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
        if (!token) {
            throw new Error('토큰이 없습니다. 로그인이 필요합니다.');
        }
    
        const decodedToken = decodeJwt(token);
        if (!decodedToken) {
            throw new Error('토큰 디코딩에 실패했습니다.');
        }
    
        const userEmail = decodedToken.sub; // 'sub' 필드에서 이메일 추출
        const response = await apiClient.get(`/users/email/${userEmail}`, {
            headers: {
            Authorization: `Bearer ${token}`,
            },
        });
    
        userInfo.value = response.data; // 유저 정보 상태 업데이트
        userNickname.value = userInfo.value.nickname; // 닉네임 상태 업데이트
        } catch (error) {
        console.error('Error fetching user info:', error);
        userInfo.value = null; // 유저 정보 초기화
        }
    };
    
    // 로그아웃 처리
    const handleLogout = () => {
        sessionStorage.removeItem('authToken'); // 토큰 삭제
        userInfo.value = null; // 유저 정보 초기화
        userNickname.value = ''; // 상태 초기화
        router.push('/'); // 메인 페이지로 이동
    };
    
    // 로그인 페이지로 이동
    const goToLogin = () => {
        router.push('/login');
    };
    
    // 회원가입 페이지로 이동
    const goToSignup = () => {
        router.push('/signup');
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
        if (isLoggedIn.value) {
        fetchUserInfo();
        }
    });
</script>

<style scoped>
    .main-page {
        padding: 20px;
    }
    
    /* 헤더 스타일 */
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
    }
    
    .user-info {
        display: flex;
        gap: 10px;
        align-items: center;
    }
    
    .auth-actions {
        display: flex;
        gap: 10px;
    }
    
    .login-button,
    .signup-button,
    .logout-button {
        padding: 5px 10px;
        font-size: 14px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    
    .login-button {
        background-color: #4caf50;
        color: white;
    }
    
    .signup-button {
        background-color: #2196f3;
        color: white;
    }
    
    .logout-button {
        background-color: #f44336;
        color: white;
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

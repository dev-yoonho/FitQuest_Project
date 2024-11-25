<template>
    <header class="header">
        <div class="logo">
            <img src="/logo.png" alt="서비스 로고" />
            <span class="service-name">FitQuest</span>
        </div>
        <nav class="nav-bar">
            <ul>
            <li><router-link to="/">홈</router-link></li>
            <li><router-link to="/community">커뮤니티</router-link></li>
            <li><router-link to="/record">기록하기</router-link></li>
            <li v-if="isLoggedIn"><router-link to="/mypage">마이페이지</router-link></li>
            <li v-if="isAdmin"><router-link to="/admin">관리자 페이지</router-link></li>
            <li v-if="isLoggedIn" @click="logout">로그아웃</li>
            <li v-else><router-link to="/login">로그인</router-link></li>
            </ul>
        </nav>
    </header>
</template>

<script setup>
    import { computed } from 'vue';
    import { useUserStore } from '@/store/userStore';
    import { useRouter } from 'vue-router';
    
    const userStore = useUserStore();
    const router = useRouter();
    
    // 로그인 상태 확인
    const isLoggedIn = computed(() => !!userStore.user);
    const isAdmin = computed(() => userStore.user?.isAdmin || false);
    
    // 로그아웃 처리
    const logout = () => {
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
        background-color: #4CAF50;
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

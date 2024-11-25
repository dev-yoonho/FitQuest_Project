<template>
    <div class="main-page">

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
    
        <section class="record-banner" @click="goToRecord">
            <h2>📋 지금 기록하기</h2>
            <p>운동과 식단을 기록하고 당신의 성취를 관리하세요!</p>
        </section>
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import { useRouter } from 'vue-router';
    import apiClient from '@/api/axios';
    
    // 상태 관리
    const popularPosts = ref([]); // 인기 게시글 데이터
    const router = useRouter();
    
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
                params: { sortBy: 'likes', limit: 10 }, // sortBy를 likes로 설정
            });
            popularPosts.value = response.data; // API로 가져온 데이터를 상태에 저장
        } catch (error) {
            console.error('Error fetching popular posts:', error);
        }
    };
    
    // 컴포넌트가 마운트될 때 실행
    onMounted(() => {
        fetchPopularPosts();
    });
</script>

<style scoped>
    .main-page {
        padding: 20px;
    }
    
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

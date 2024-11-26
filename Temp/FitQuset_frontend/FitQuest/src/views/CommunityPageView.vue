<template>
    <div class="community-page">
        <h1>커뮤니티 게시판</h1>

        <!-- 조건 검색 필터 -->
        <div class="search-container">
            <h2>조건 검색</h2>
            <div class="search-bar">
                <div class="filter">
                    <div class="search-group">
                        <label for="title">제목</label>
                        <input
                            id="title"
                            v-model="searchCriteria.title"
                            type="text"
                            placeholder="제목 검색"
                            class="search-input"
                        />
                    </div>
                    <div class="search-group">
                        <label for="content">내용</label>
                        <input
                            id="content"
                            v-model="searchCriteria.content"
                            type="text"
                            placeholder="내용 검색"
                            class="search-input"
                        />
                    </div>
                    <div class="search-group">
                        <label for="nickname">작성자 닉네임</label>
                        <input
                            id="nickname"
                            v-model="searchCriteria.nickname"
                            type="text"
                            placeholder="닉네임 검색"
                            class="search-input"
                        />
                    </div>
                    <div class="search-group">
                        <label for="category">카테고리</label>
                        <select id="category" v-model="searchCriteria.category" class="search-select">
                            <option value="">카테고리 선택</option>
                            <option value="자유">자유</option>
                            <option value="질문">질문</option>
                            <option value="모임">모임</option>
                        </select>
                    </div>
                </div>

                <div class="sort">
                    <div class="search-group">
                        <label for="sort">정렬</label>
                        <select id="sort" v-model="searchCriteria.sortBy" class="sort-select">
                            <option value="created_at_reverse">최신순</option>
                            <option value="created_at">오래된순</option>
                            <option value="likes">좋아요순</option>
                            <option value="views">조회수순</option>
                        </select>
                    </div>
                </div>

                <button @click="fetchPosts" class="search-button">검색</button>
            </div>
        </div>

        <!-- 게시글 목록 -->
        <div v-if="posts.length > 0" class="post-list">
            <div
                v-for="post in posts"
                :key="post.postId"
                class="post-item"
                @click="goToPost(post.postId)"
            >
                <h3>{{ post.title }}</h3>
                <p>작성자: {{ post.nickname }}</p>
                <p>카테고리: {{ post.category }}</p>
                <p>조회수: {{ post.views }} | 추천수: {{ post.likes }}</p>
            </div>
        </div>
        <div v-else>
            <p>게시글이 없습니다.</p>
        </div>

        <!-- 페이지네이션 -->
        <div v-if="totalPages > 0" class="pagination">
            <button
                :disabled="currentPage === 1"
                @click="changePage(currentPage - 1)"
            >
                이전
            </button>
            <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
            <button
                :disabled="currentPage === totalPages"
                @click="changePage(currentPage + 1)"
            >
                다음
            </button>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import apiClient from '@/api/axios';

// 상태
const posts = ref([]);
const totalPosts = ref(0);
const currentPage = ref(1);
const pageSize = ref(10); // 한 페이지에 표시할 게시글 수
const searchCriteria = ref({
    title: '',
    content: '',
    category: '',
    nickname: '',
    sortBy: 'created_at_reverse', // 기본값: 최신순
});

// 계산된 속성
const totalPages = computed(() =>
    Math.ceil(totalPosts.value / pageSize.value)
);

// 라우터
const router = useRouter();

// 게시글 상세 페이지 이동
const goToPost = (postId) => {
    router.push(`/community/${postId}`);
};

// 게시글 데이터 가져오기
const fetchPosts = async () => {
    try {
        const offset = (currentPage.value - 1) * pageSize.value;
        const response = await apiClient.get('/posts/search', {
            params: {
                ...searchCriteria.value,
                limit: pageSize.value,
                offset,
            },
        });

        posts.value = response.data;
        totalPosts.value = parseInt(response.headers['x-total-count'], 10) || 0;
    } catch (error) {
        console.error('게시글을 가져오는 중 오류 발생:', error);
        posts.value = [];
    }
};

// 페이지 변경
const changePage = (newPage) => {
    if (newPage > 0 && newPage <= totalPages.value) {
        currentPage.value = newPage;
        fetchPosts();
    }
};

// 컴포넌트 마운트 시 데이터 가져오기
onMounted(() => {
    fetchPosts();
});
</script>

<style scoped>
.community-page {
    padding: 20px;
}

.search-container {
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
    margin-bottom: 20px;
}

.search-bar {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    align-items: flex-end;
}

.filter {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    flex: 3;
}

.sort {
    display: flex;
    gap: 10px;
    flex: 1;
}

.search-group {
    display: flex;
    flex-direction: column;
    gap: 5px;
    flex: 1;
}

.search-input,
.search-select,
.sort-select {
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.search-button {
    padding: 8px 16px;
    background-color: #4caf50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.search-button:hover {
    background-color: #45a049;
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
    background-color: #f0f0f0;
}

.pagination {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
}

.pagination button {
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: white;
    cursor: pointer;
}

.pagination button:disabled {
    background-color: #f0f0f0;
    cursor: not-allowed;
}
</style>

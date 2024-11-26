<template>
    <div class="post-detail-page">
        <!-- 상단 게시글 정보 -->
        <div class="post-header">
            <h1 class="post-title">{{ post.title }}</h1>
            <div class="post-info">
            <p>작성자: {{ post.nickname }} | 카테고리: {{ post.category }} | 작성 시간: {{ post.createdAt }}</p>
            <p>조회수: {{ post.views }} | 추천수: {{ post.likes }}</p>
            </div>
            <div v-if="isAuthor" class="post-actions">
            <button @click="editPost">수정</button>
            <button @click="deletePost">삭제</button>
            </div>
        </div>
    
        <!-- 게시글 본문 -->
        <div class="post-content">
            <p>{{ post.content }}</p>
        </div>
    
        <!-- 댓글 영역 -->
        <div class="comment-section">
            <h2>댓글</h2>
            <div v-if="comments.length > 0" class="comment-list">
            <div v-for="comment in comments" :key="comment.commentId" class="comment-item">
                <p><strong>{{ comment.nickname }}</strong>: {{ comment.content }}</p>
                <p class="comment-meta">작성 시간: {{ comment.createdAt }}</p>
                <button v-if="isCommentAuthor(comment)" @click="deleteComment(comment.commentId)">삭제</button>
            </div>
            </div>
            <div v-else>
            <p>댓글이 없습니다. 첫 댓글을 작성해 보세요!</p>
            </div>
    
            <!-- 댓글 작성 -->
            <div class="comment-form">
            <textarea v-model="newComment" placeholder="댓글을 입력하세요..."></textarea>
            <button @click="addComment">댓글 등록</button>
            </div>
        </div>
    
        <!-- 하단 버튼 -->
        <div class="post-footer">
            <button @click="likePost">좋아요</button>
            <button @click="goBack">뒤로가기</button>
        </div>
    </div>
</template>

<script setup>
    import { ref, onMounted, computed } from 'vue';
    import { useRouter, useRoute } from 'vue-router';
    import apiClient from '@/api/axios';

    // 상태
    const post = ref({});
    const comments = ref([]);
    const newComment = ref('');
    const router = useRouter();
    const route = useRoute();

    // 로그인 사용자 정보 (가정: 전역 상태 또는 API에서 가져옴)
    const currentUser = ref({
    nickname: '현재사용자닉네임', // 실제 로그인 정보를 여기에 반영
    });

    // 게시글 ID 가져오기
    const postId = route.params.postId;

    // 게시글 작성자인지 확인
    const isAuthor = computed(() => post.value.nickname === currentUser.value.nickname);

    // 댓글 작성자인지 확인
    const isCommentAuthor = (comment) =>
    comment.nickname === currentUser.value.nickname;

    // 게시글 데이터 가져오기
    const fetchPost = async () => {
    try {
        const response = await apiClient.get(`/posts/${postId}`);
        post.value = response.data;
    } catch (error) {
        console.error('게시글을 가져오는 중 오류 발생:', error);
        router.push('/community'); // 오류 시 커뮤니티 목록으로 이동
    }
    };

    // 댓글 데이터 가져오기
    const fetchComments = async () => {
        try {
            const response = await apiClient.get(`/comments/post/${postId}`);
            comments.value = response.data || [];
        } catch (error) {
            console.error('댓글을 가져오는 중 오류 발생:', error);
        }
    };


    // 댓글 추가
    const addComment = async () => {
    if (!newComment.value.trim()) return;

    try {
        await apiClient.post(`/posts/${postId}/comments`, {
        content: newComment.value,
        });
        newComment.value = '';
        fetchComments(); // 댓글 목록 갱신
    } catch (error) {
        console.error('댓글 등록 중 오류 발생:', error);
    }
    };

    // 댓글 삭제
    const deleteComment = async (commentId) => {
    if (!confirm('댓글을 삭제하시겠습니까?')) return;

    try {
        await apiClient.delete(`/posts/${postId}/comments/${commentId}`);
        fetchComments(); // 댓글 목록 갱신
    } catch (error) {
        console.error('댓글 삭제 중 오류 발생:', error);
    }
    };

    // 게시글 수정
    const editPost = () => {
    router.push(`/community/edit/${postId}`);
    };

    // 게시글 삭제
    const deletePost = async () => {
    if (!confirm('게시글을 삭제하시겠습니까?')) return;

    try {
        await apiClient.delete(`/posts/${postId}`);
        router.push('/community'); // 삭제 후 커뮤니티 목록으로 이동
    } catch (error) {
        console.error('게시글 삭제 중 오류 발생:', error);
    }
    };

    // 좋아요
    const likePost = async () => {
    try {
        await apiClient.post(`/posts/${postId}/recommend`);
        fetchPost(); // 좋아요 수 갱신
    } catch (error) {
        console.error('좋아요 처리 중 오류 발생:', error);
    }
    };

    // 뒤로가기
    const goBack = () => {
    router.push('/community');
    };

    // 데이터 초기화
    onMounted(() => {
    fetchPost();
    fetchComments();
    });
</script>

<style scoped>
    .post-detail-page {
        padding: 20px;
    }
    
    .post-header {
        margin-bottom: 20px;
    }
    
    .post-title {
        font-size: 1.8rem;
        font-weight: bold;
    }
    
    .post-info {
        margin-top: 10px;
        font-size: 0.9rem;
        color: gray;
    }
    
    .post-actions button {
        margin-right: 10px;
        padding: 5px 10px;
        border: none;
        background-color: #4caf50;
        color: white;
        border-radius: 5px;
        cursor: pointer;
    }
    
    .post-content {
        margin-bottom: 20px;
        padding: 15px;
        background-color: #f9f9f9;
        border-radius: 5px;
        border: 1px solid #ddd;
    }
    
    .comment-section {
        margin-top: 30px;
    }
    
    .comment-item {
        padding: 10px;
        border-bottom: 1px solid #ccc;
    }
    
    .comment-meta {
        font-size: 0.8rem;
        color: gray;
    }
    
    .comment-form {
        margin-top: 10px;
    }
    
    .comment-form textarea {
        width: 100%;
        height: 80px;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-bottom: 10px;
    }
    
    .post-footer {
        margin-top: 20px;
    }
    
    .post-footer button {
        margin-right: 10px;
        padding: 10px 20px;
        border: none;
        background-color: #4caf50;
        color: white;
        border-radius: 5px;
        cursor: pointer;
    }
</style>

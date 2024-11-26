<template>
    <footer class="footer">
        <div class="footer-content">
            <div class="footer-logo">
            <span class="service-name">FitQuest</span>
            </div>
            <div class="footer-links">
            <button @click="showTerms">이용약관</button>
            <button @click="showPrivacyPolicy">개인정보처리방침</button>
            </div>
        </div>
        <div v-if="showModal" class="modal">
            <div class="modal-content">
            <h2>{{ modalTitle }}</h2>
            <p>{{ modalContent }}</p>
            <button @click="closeModal">닫기</button>
            </div>
        </div>
    </footer>
</template>

<script setup>
    import { ref } from 'vue';
    
    const showModal = ref(false);
    const modalTitle = ref('');
    const modalContent = ref('');
    
    // 이용약관 모달 표시
    const showTerms = () => {
        modalTitle.value = '이용약관';
        modalContent.value = '이용약관 내용이 여기에 표시됩니다.';
        showModal.value = true;
    };
    
    // 개인정보처리방침 모달 표시
    const showPrivacyPolicy = () => {
        modalTitle.value = '개인정보처리방침';
        modalContent.value = '개인정보처리방침 내용이 여기에 표시됩니다.';
        showModal.value = true;
    };
    
    // 모달 닫기
    const closeModal = () => {
        showModal.value = false;
    };
</script>

<style scoped>
    .footer {
        background-color: #333;
        color: white;
        padding: 1rem 2rem;
        text-align: center;
        position: relative;
    }
    
    .footer-content {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
    }
    
    .footer-logo img {
        height: 40px;
        margin-right: 10px;
    }
    
    .footer-links button {
        background: none;
        border: none;
        color: #4CAF50;
        margin: 0 0.5rem;
        cursor: pointer;
        font-size: 1rem;
    }
    
    .footer-links button:hover {
        text-decoration: underline;
    }
    
    .modal {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }
    
    .modal-content {
        background: white;
        padding: 2rem;
        border-radius: 8px;
        text-align: center;
        width: 90%;
        max-width: 500px;
    }
    
    .modal-content h2 {
        margin-bottom: 1rem;
        font-size: 1.5rem;
    }
    
    .modal-content p {
        margin-bottom: 1.5rem;
        font-size: 1rem;
    }
    
    .modal-content button {
        background: #4CAF50;
        color: white;
        border: none;
        padding: 0.5rem 1rem;
        cursor: pointer;
        border-radius: 4px;
    }
    
    .modal-content button:hover {
        background: #45a049;
    }
</style>
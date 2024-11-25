// src/store/userStore.js

import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        user: null, // 사용자 정보 저장
        isAuthenticated: false,
    }),
    actions: {
        login(userData) {
        this.user = userData;
        this.isAuthenticated = true;
        },
        logout() {
        this.user = null;
        this.isAuthenticated = false;
        },
    },
});

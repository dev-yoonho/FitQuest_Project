// src/store/userStore.js
import { defineStore } from 'pinia';
import apiClient from '@/api/axios';

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    token: sessionStorage.getItem('authToken') || null, // 세션 스토리지에서 초기화
  }),

  getters: {
    isAuthenticated: (state) => !!state.token, // 로그인 상태 확인
  },

  actions: {
    async login(token) {
      try {
        this.token = token;
        sessionStorage.setItem('authToken', token);

        const decodedToken = this.decodeJwt(token);
        const userEmail = decodedToken.sub;

        const response = await apiClient.get(`/users/email/${userEmail}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        this.user = response.data;
      } catch (error) {
        console.error('로그인 실패:', error);
        this.logout();
      }
    },

    logout() {
      this.token = null;
      sessionStorage.removeItem('authToken');
      this.user = null;
    },

    async restoreSession() {
      const token = sessionStorage.getItem('authToken');
      if (token) {
        await this.login(token); // 세션 복원
      }
    },

    decodeJwt(token) {
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
    },
  },
});

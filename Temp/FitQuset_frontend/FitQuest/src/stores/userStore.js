// src/store/userStore.js
import { defineStore } from 'pinia';
import apiClient from '@/api/axios';

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null, // 사용자 정보 저장
  }),

  getters: {
    isAuthenticated: () => !!sessionStorage.getItem('authToken'), // 로그인 상태 동기화
  },

  actions: {
    async login(token) {
      try {
        sessionStorage.setItem('authToken', token); // 토큰 저장
        const decodedToken = this.decodeJwt(token); // JWT 디코딩
        const userEmail = decodedToken.sub;

        // 사용자 정보 가져오기
        const response = await apiClient.get(`/users/email/${userEmail}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        this.user = response.data; // 사용자 정보 저장
      } catch (error) {
        console.error('로그인 실패:', error);
        this.logout(); // 실패 시 로그아웃
      }
    },

    logout() {
      sessionStorage.removeItem('authToken'); // 세션 스토리지에서 토큰 제거
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

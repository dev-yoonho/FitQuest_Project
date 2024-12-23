// src/main.js
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';
import { useUserStore } from '@/stores/userStore';

const app = createApp(App);
const pinia = createPinia();

app.use(pinia);
app.use(router);

// 세션 복원
const userStore = useUserStore();
userStore.restoreSession();

app.mount('#app');

import { createRouter, createWebHistory } from 'vue-router';
import MainPageView from '@/views/MainPageView.vue';
import LoginPageView from '@/views/LoginPageView.vue';
import SignupPageView from '@/views/SignupPageView.vue';
import CommunityPageView from '@/views/CommunityPageView.vue';
import PostDetailPageView from '@/views/PostDetailPageView.vue';
import RecordPageView from '@/views/RecordPageView.vue';
import MyPageView from '@/views/MyPageView.vue';
import AdminPageView from '@/views/AdminPageView.vue';
import { useUserStore } from '../stores/userStore.js'; // Pinia 스토어

const routes = [
  {
    path: '/',
    name: 'main',
    component: MainPageView,
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPageView,
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignupPageView,
  },
  {
    path: '/community',
    name: 'community',
    component: CommunityPageView,
  },
  {
    path: '/community/:postId',
    name: 'postDetail',
    component: PostDetailPageView,
    props: true, // postId를 props로 전달
  },
  {
    path: '/record',
    name: 'record',
    component: RecordPageView,
  },
  {
    path: '/mypage',
    component: MyPageView,
    children: [
      {
        path: '',
        name: 'mypage-home',
        component: () => import('@/components/MyPageHomeComponent.vue'),
      },
      {
        path: 'profile',
        name: 'mypage-profile',
        component: () => import('@/components/MyPageProfileComponent.vue'),
      },
      {
        path: 'exercises',
        name: 'mypage-exercises',
        component: () => import('@/components/MyPageExercisesComponent.vue'),
      },
      {
        path: 'diets',
        name: 'mypage-diets',
        component: () => import('@/components/MyPageDietsComponent.vue'),
      },
      {
        path: 'withdraw',
        name: 'mypage-withdraw',
        component: () => import('@/components/MyPageWithdrawComponent.vue'),
      },
    ],
    meta: { requiresAuth: true }, // 인증 필요 플래그 추가
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminPageView,
    meta: { requiresAdmin: true }, // 관리자 권한 플래그 추가
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

// 전역 가드로 인증 및 권한 처리
router.beforeEach((to, from, next) => {
  const userStore = useUserStore(); // Pinia에서 사용자 상태 가져오기

  // 인증이 필요한 페이지 처리
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next('/login'); // 로그인 안 되어 있으면 로그인 페이지로 리다이렉트
  } 
  // 관리자 권한이 필요한 페이지 처리
  else if (to.meta.requiresAdmin && !userStore.user?.isAdmin) {
    next('/login'); // 관리자 권한이 없으면 로그인 페이지로 리다이렉트
  } 
  // 모든 조건 통과 시
  else {
    next();
  }
});

export default router;

<template>
  <div>
    <HeaderComponent />
    <RouterView />
    <FooterComponent />
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore'; // Import Pinia store
import FooterComponent from './components/FooterComponent.vue';
import HeaderComponent from './components/HeaderComponent.vue';

const userStore = useUserStore();

// Restore session on app initialization
onMounted(() => {
  userStore.restoreSession();
});
</script>


<style scoped>

</style>
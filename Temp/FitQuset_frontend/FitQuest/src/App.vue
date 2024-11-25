<template>
  <div>
    <HeaderComponent />
    <RouterView />
    <FooterComponent />
  </div>
</template>

<script setup>
import FooterComponent from './components/FooterComponent.vue';
import HeaderComponent from './components/HeaderComponent.vue';


</script>

<style scoped>

</style>
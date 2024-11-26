<template>
    <div class="record-list">
      <h3>{{ type === 'exercise' ? '운동 기록 목록' : '식단 기록 목록' }}</h3>
  
      <div v-if="records.length > 0">
        <div
          v-for="record in records"
          :key="record.id"
          class="record-item"
        >
          <p v-if="type === 'exercise'">
            {{ record.exerciseName }} ({{ record.exerciseType }})
            <button @click="deleteRecord(record.id)">삭제</button>
          </p>
          <p v-else>
            {{ record.foodName }} ({{ record.mealType }})
            <button @click="deleteRecord(record.id)">삭제</button>
          </p>
        </div>
      </div>
  
      <p v-else>기록이 없습니다. 추가해주세요!</p>
    </div>
  </template>
  
  <script setup>
  import { defineProps, defineEmits } from 'vue';
  import apiClient from '@/api/axios';
  import { useUserStore } from '@/stores/userStore';
  
  const props = defineProps({
    records: {
      type: Array,
      required: true,
    },
    type: {
      type: String,
      required: true,
    },
  });
  
  const emit = defineEmits(['record-deleted']);
  const userStore = useUserStore();
  
  const deleteRecord = async (id) => {
    try {
      const endpoint = type === 'exercise' ? `/exercises/${id}` : `/diets/${id}`;
      await apiClient.delete(endpoint, {
        headers: { Authorization: `Bearer ${userStore.token}` },
      });
      emit('record-deleted');
      alert('기록이 삭제되었습니다.');
    } catch (error) {
      console.error('Error deleting record:', error);
      alert('기록 삭제에 실패했습니다.');
    }
  };
  </script>
  
  <style scoped>
  .record-list {
    padding: 10px;
  }
  
  .record-item {
    padding: 5px 0;
    border-bottom: 1px solid #ddd;
  }
  
  button {
    margin-left: 10px;
    padding: 5px 10px;
    background-color: #e74c3c;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #c0392b;
  }
  </style>
  
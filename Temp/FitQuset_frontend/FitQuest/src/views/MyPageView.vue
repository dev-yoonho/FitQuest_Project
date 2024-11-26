<template>
    <div class="mypage">
      <!-- 상단 헤더 -->
      <header class="mypage-header">
        <h1>마이페이지</h1>
        <nav class="mypage-nav">
          <ul>
            <li>
              <RouterLink :to="{ name: 'mypage-home' }" class="nav-link" active-class="active">
                홈
              </RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'mypage-profile' }" class="nav-link" active-class="active">
                프로필 수정
              </RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'mypage-exercises' }" class="nav-link" active-class="active">
                운동 기록
              </RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'mypage-diets' }" class="nav-link" active-class="active">
                식단 기록
              </RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'mypage-withdraw' }" class="nav-link" active-class="active">
                회원 탈퇴
              </RouterLink>
            </li>
          </ul>
        </nav>
      </header>
  
      <!-- 자식 컴포넌트 렌더링 -->
      <main class="mypage-content">
        <RouterView />
      </main>
    </div>
  </template>
  
  <script setup>
  // 현재는 자식 컴포넌트의 렌더링만 담당하므로 별도의 로직은 없습니다.
  </script>
  
  <style scoped>
  .mypage {
    display: flex;
    flex-direction: column;
    padding: 20px;
    font-family: Arial, sans-serif;
  }
  
  .mypage-header {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 20px;
    border-bottom: 2px solid #ccc;
    padding-bottom: 10px;
  }
  
  .mypage-header h1 {
    font-size: 24px;
    margin-bottom: 10px;
  }
  
  .mypage-nav ul {
    list-style: none;
    display: flex;
    gap: 15px;
    padding: 0;
    margin: 0;
  }
  
  .mypage-nav .nav-link {
    text-decoration: none;
    color: #333;
    padding: 5px 10px;
    border: 1px solid transparent;
    border-radius: 5px;
    transition: background-color 0.3s, border 0.3s;
  }
  
  .mypage-nav .nav-link:hover {
    background-color: #f5f5f5;
    border: 1px solid #ddd;
  }
  
  .mypage-nav .active {
    background-color: #4caf50;
    color: white;
    border: 1px solid #4caf50;
  }
  
  .mypage-content {
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f9f9f9;
    min-height: 300px;
  }
  </style>
  
<template>
    <div class="search-container">
      <div class="search-container frame">
        <h2>Search by</h2>
        <div class="search-box" @mouseover="startShake" @animationiteration="stopShake">
          <select v-model="searchBy" id="searchBy">
            <option value="date">Date</option>
            <option value="sender">Sender</option>
            <option value="receivers">Receivers</option>
            <option value="importance">Importance</option>
            <option value="subject">Subject</option>
            <option value="body">Body</option>
            <option value="attachments">Attachments</option>
          </select>
          <input v-model="searchQuery" id="searchInput" placeholder="Enter search query..." class="flex-grow large-input" />
          <button @click="applySearch"><img src="@/assets/search.png" alt="Search Icon" class="search-icon" width="30" height="30"/></button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const searchQuery = ref('');
  const searchBy = ref('date');
  
  const applySearch = () => {
    console.log('Search Query:', searchQuery.value, 'Search By:', searchBy.value);
    startShake();
    setTimeout(() => {
      stopShake();
    }, 500);
  };
  
  const startShake = () => {
    const box = document.querySelector('.search-box');
    box.classList.add('shake');
  };
  
  const stopShake = () => {
    const box = document.querySelector('.search-box');
    box.classList.remove('shake');
  };
  </script>
  
  <style scoped>
  .search-container h2 {
    font-size: 1.5rem;
    margin-bottom: 10px;
    color: #007BFF;
  }
  
  .frame {
    margin-bottom: 20px;
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s;
  }
  
  .frame:hover {
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  }
  
  .search-box {
    display: flex;
    gap: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 12px;
    transition: box-shadow 0.3s;
  }
  
  .search-box:hover {
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  }
  
  select,
  input {
    padding: 15px;
    border: 1px solid #ffffff;
    border-radius: 8px;
    box-sizing: border-box;
    font-size: 16px;
  }
  
  select {
    min-width: 120px;
  }
  
  .input.flex-grow {
    flex-grow: 1;
  }
  
  .large-input {
    width: 300px;
  }
  
  button {
    padding: 12px;
    background-color: #007BFF;
    color: #fff;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  .shake {
    animation: shake 0.3s ease-in-out 3;
  }
  
  @keyframes shake {
    0%, 100% {
      transform: translateX(0);
    }
    25%, 75% {
      transform: translateX(-5px);
    }
    50% {
      transform: translateX(5px);
    }
  }
  </style>
  

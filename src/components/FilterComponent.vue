<template>
  <div class="filter-search-container">
    <SearchComponent />
    <div class="filter-container frame" @mouseover="startShake" @animationiteration="stopShake">
      <h2>Filter by</h2>
      <div class="filter-box">
        <select v-model="filterBy" id="filterBy">
          <option value="date">Date</option>
          <option value="sender">Sender</option>
          <option value="subject">Subject</option>
          <option value="importance">Importance</option>
        </select>
        <button @click="applyFilter"><img src="@/assets/filter.jpg" alt="Filter Icon" class="filter-icon" width="30" height="30"/></button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import SearchComponent from './SearchComponent.vue';

const filterBy = ref('date');

const applyFilter = () => {
  console.log('Filter By:', filterBy.value);
  startShake();
  setTimeout(() => {
    stopShake();
  }, 500);
};

const startShake = () => {
  const box = document.querySelector('.filter-box');
  box.classList.add('shake');
};

const stopShake = () => {
  const box = document.querySelector('.filter-box');
  box.classList.remove('shake');
};
</script>


<style scoped>
.filter-container h2 {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #007BFF;
}

.filter-search-container {
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  margin-left: 5px;
}

.filter-container {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s;
  margin-left: 1px;
  width: 400px;
}

.filter-container:hover {
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
}

.filter-box {
  display: flex;
  flex-direction: row;
  align-items: center; 
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  transition: box-shadow 0.3s;
  width: 100%; 
  padding-right: 10px;
}

.filter-box:hover {
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
}

select,
button {
  padding: 15px;
  border: none;
  border-radius: 0; 
  cursor: pointer;
  transition: background-color 0.3s;
  font-family: 'Arial', sans-serif;
}

select {
  margin-right: 10px;
  min-width: 330px;
}

button {
  background-color: #007BFF;
  color: #fff;
  margin-left: 10px;
  border-radius: 5px;
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

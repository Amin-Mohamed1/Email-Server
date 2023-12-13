<template>
    <div class="contact-container">
      <h2>Add to Contact</h2>
      <form @submit.prevent="submitForm" class="contact-form">
        <div class="input-box">

          <input type="text" id="name" v-model="formData.name" required placeholder="Enter name">
        </div>
  
        <div class="input-box">
          <input type="email" id="email" v-model="formData.email" required placeholder="Enter email">
        </div>
  
        <div class="input-box button">
          <button type="submit" class="submit-btn">Add Contact</button>
        </div>
      </form>
  
      <h2>Contact</h2>
      <table class="user-list">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(user, index) in userList" :key="index" class="hoverable-row">
            <td>{{ user.name }}</td>
            <td>{{ user.email }}</td>
            <td>
              <button @click="deleteContact(index)" class="delete-btn">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  
  const formData = ref({
    name: '',
    email: '',
  });
  
  const userList = ref([
    { name: 'John Doe', email: 'john.doe@example.com' },
    { name: 'Jane Smith', email: 'jane.smith@example.com' },
    { name: 'Alice Johnson', email: 'alice.johnson@example.com' },
    { name: 'Bob Wilson', email: 'bob.wilson@example.com' },
  ]);
  
  const submitForm = () => {
    userList.value.push({
      name: formData.value.name,
      email: formData.value.email,
    });
  
    formData.value = {
      name: '',
      email: '',
    };
  };
  
  const deleteContact = (index) => {
    userList.value.splice(index, 1);
  };
  </script>
  <style scoped>
form {
    max-width: 400px;
    margin: auto;
    margin-bottom: 20px;
  }
  
  .input-box {
    height: 25px;
    margin: 12px 0;
  }
  
  form label {
    color: #333;
    font-size: 17px;
    font-weight: 500;
    margin-bottom: 5px;
  }
  
  form input {
    height: 100%;
    width: 100%;
    outline: none;
    padding: 0 15px;
    font-size: 17px;
    font-weight: 400;
    color: #333;
    border: 1.5px solid #C7BEBE;
    border-bottom-width: 2.5px;
    border-radius: 6px;
    transition: all 0.3s ease;
  }
  
  form input:focus,
  form input:valid {
    border-color: #4070f4;
  }
  
  .submit-btn {
    color: #fff;
    letter-spacing: 1px;
    border: none;
    background: #4070f4;
    cursor: pointer;
    margin-top: 20px;
    padding: 10px;
    border-radius: 4px;
  }
  
  .submit-btn:hover {
    background: #0e4bf1;
  }
  
  .table-container {
    margin-top: 20px;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }
  
  th, td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: left;
  }
  
  th {
    background-color: #f2f2f2;
  }
  
  .hoverable-row:hover {
    background-color: #f0f0f0;
  }
  
  .delete-btn {
    background-color: #ff5555;
    color: #fff;
    padding: 5px;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .delete-btn:hover {
    background-color: #dd4444;
  }

  </style>
  

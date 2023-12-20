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
            <button @click="editContact(user.id)" class="edit-btn">Edit</button>
            <button @click="deleteContact(user.id)" class="delete-btn">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

  <p>Contact Information: {{ profileContactInfo  }}</p>
  <p>Contact Information: {{ userList }}</p>
  
</template>

<script setup>

import { ref } from 'vue';

const formData = ref({
  name: '',
  email: '',
});

const userList = ref([]);

const nextId = ref(1); 

const submitForm = () => {
  const addedUser = {
    id: nextId.value,
    name: formData.value.name,
    email: formData.value.email,
  };

  userList.value.push(addedUser);
  nextId.value += 1;
  formData.value = {
    name: '',
    email: '',
  };
  sendAddedDataToBackend(addedUser);
};

const sendAddedDataToBackend = async (addedUser) => {
  console.log('Sending added data to the backend:', addedUser);
  const gmail = profileContactInfo.value;
  console.log(profileContactInfo.value);
  try {
    await fetch('http://localhost/add', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        gmail: gmail,
        id: addedUser.id,
        name: addedUser.name,
        email: addedUser.email,
      }),
    });

    console.log('Data successfully sent to backend');
  } catch (error) {
    console.error('Error sending data to backend:', error.message);
  }
};

const deleteContact = (id) => {
  const index = userList.value.findIndex(contact => contact.id === id);
  console.log(id);
  if (index !== -1) {
    userList.value.splice(index, 1);
    sendDeletedDataToBackend(userList.value[index]);
  }
};

const editContact = (id) => {
  const index = userList.value.findIndex(contact => contact.id === id);
  console.log(id);
  if (index !== -1) {
    const newName = prompt('Enter the new name:', userList.value[index].name);
    const newEmail = prompt('Enter the new email:', userList.value[index].email);

    if (newName !== null && newEmail !== null) {
      userList.value[index].name = newName;
      userList.value[index].email = newEmail;
      sendEditedDataToBackend(userList.value[index]);
    }
  }
};

import { defineProps } from 'vue';
const props = defineProps(['profileContactInfo'] , ['userList']);
const sendEditedDataToBackend = async (editedUser) => {
  console.log('Sending edited data to the backend:', editedUser);
  const gmail = props.profileContactInfo;
  console.log(gmail);
  try {
    await fetch('http://localhost/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        gmail: gmail,
        id: editedUser.id,
        name: editedUser.name,
        email: editedUser.email,
      }),
    });

    console.log('Data successfully sent to backend');
  } catch (error) {
    console.error('Error sending data to backend:', error.message);
  }
};

const sendDeletedDataToBackend = async (deletedUser) => {
  console.log('Sending deleted data to the backend:', deletedUser);
  const gmail = profileContactInfo.value;
  console.log(gmail);
  try {
    await fetch('http://localhost/delete', {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        gmail: gmail,
        id: deletedUser.id,
      }),
    });

    console.log('Data successfully sent to backend');
  } catch (error) {
    console.error('Error sending data to backend:', error.message);
  }
};
</script>

<style scoped>
.contact-container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
}

.contact-form {
  max-width: 400px;
  margin: auto;
  margin-bottom: 20px;
}

.input-box {
  margin: 12px 0;
}

input {
  height: 100%;
  width: 100%;
  outline: none;
  padding: 10px;
  font-size: 16px;
  color: #333;
  border: 1.5px solid #C7BEBE;
  border-bottom-width: 2.5px;
  border-radius: 6px;
  transition: all 0.3s ease;
}

input:focus,
input:valid {
  border-color: #4070f4;
}

.submit-btn,
.edit-btn,
.delete-btn {
  color: #fff;
  letter-spacing: 1px;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
  margin-right: 5px ;
}

.submit-btn {
  background: #4070f4;
  margin-left: 170px ;
}

.submit-btn:hover {
  background: #0e4bf1;
}

.edit-btn {
  background: #4caf50;
}

.edit-btn:hover {
  background: #45a049;
}

.delete-btn {
  background: #ff5555;
}

.delete-btn:hover {
  background: #dd4444;
}

.user-list {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th,
td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

.hoverable-row:hover {
  background-color: #f0f0f0;
}
</style>

  

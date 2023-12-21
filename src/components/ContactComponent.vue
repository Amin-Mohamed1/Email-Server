<template>
  <div class="contact-container">
    <h2>Add to Contact</h2>
    <form @submit.prevent="submitForm" class="contact-form">
      <div class="input-box">
        <input type="text" id="name" v-model="formData.name" required placeholder="Enter name">
      </div>
      <div class="input-box">
        <input type="email" id="emailaccount" v-model="formData.emailaccount" required placeholder="Enter emailaccount">
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
          <td>{{ user.emailaccount }}</td>
          <td>
            <button @click="editContact(user.id)" class="edit-btn">Edit</button>
            <button @click="deleteContact(user.id)" class="delete-btn">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import axios from 'axios';

export default defineComponent({
  props: {
    profileContactInfo: String,
    userList: Array,
  },
  data() {
    return {
      formData: {
        name: '',
        emailaccount: '',
      },
      nextId: 1,
    };
  },
  methods: {
    async submitForm() {
      const addedUser = {
        id: this.nextId,
        name: this.formData.name,
        emailaccount: this.formData.emailaccount,
      };
      this.userList.push(addedUser);
      this.nextId += 1;
      this.formData = {
        name: '',
        emailaccount: '',
      };

      await this.sendAddedDataToBackend(addedUser);
    },
    async sendAddedDataToBackend(addedUser) {
      const account = this.profileContactInfo;

      try {
        const response = await fetch(`http://localhost:8081/mail/addContact/${account}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: addedUser.id,
          emailaccount: addedUser.emailaccount,
          name: addedUser.name
        }),
      });
      console.log(response.data);
     }catch(error){
      console.log(error + " why!");
     }
    },
    deleteContact(id) {
      const index = this.userList.findIndex(contact => contact.id === id);
      if (index !== -1) {
        const deletedUser = this.userList[index];
        this.userList.splice(index, 1);
        this.sendDeletedDataToBackend(deletedUser);
      }
    },
    editContact(id) {
      const index = this.userList.findIndex(contact => contact.id === id);
      if (index !== -1) {
        const newName = prompt('Enter the new name:', this.userList[index].name);
        const newEmail = prompt('Enter the new email:', this.userList[index].emailaccount);
        if (newName !== null && newEmail !== null) {
          this.userList[index].name = newName;
          this.userList[index].emailaccount = newEmail;
          this.sendEditedDataToBackend(this.userList[index]);
        }
      }
    },
    async sendEditedDataToBackend(editedUser) {
      const account = this.profileContactInfo;
      console.log(editedUser.name);
      try {
        const response = await fetch(`http://localhost:8081/mail/editContact/${account}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: editedUser.id,
          emailaccount: editedUser.emailaccount,
          name: editedUser.name
        }),
      });
      console.log(response.data);
     }catch(error){
      console.log(error + " why!");
     }
    },

    async sendDeletedDataToBackend(deletedUser) {
      const account = this.profileContactInfo;
        try {
          const response = await fetch(`http://localhost:8081/mail/deleteContact/${account}/${deletedUser.id}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({

          }),
        });
        console.log(response.data);
      }catch(error){
        console.log(error + " why!");
      }
    },

  },
});
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

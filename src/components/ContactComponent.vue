<template>
  <div class="contact-container">
    
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="Search by name or email">
      <button class="search-btn" @click="searchContacts"><i class="fas fa-search"></i></button>
    </div>

    <h2>Add to Contact</h2>
    <form @submit.prevent="submitForm" class="contact-form">
      <div class="input-box">
        <input type="text" id="name" v-model="formData.name" required placeholder="Enter name">
      </div>
      <div class="input-box">
        <input type="email" id="emailaccount" v-model="formData.emailaccount" required placeholder="Enter email account">
      </div>
      <div class="input-box button">
        <button type="submit" class="submit-btn">Add Contact</button>
      </div>
    </form>

    <h2>Contact</h2>
    <table class="user-list">
      <thead>
        <tr>
          <th class="sort-header" @click="sortByName('asc')">
            Name
            <i class="fas fa-sort-alpha-down sort-icon" :class="{ 'rotate-icon': sortOrder === 'asc' }"></i>
          </th>
          <th class="sort-header" @click="sortByGmail('asc')">
            Email
            <i class="fas fa-sort-alpha-down sort-icon" :class="{ 'rotate-icon': sortOrder === 'asc' }"></i>
          </th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user, index) in userList" :key="index" :class="{ 'searched-contact': copyUserList.includes(user) }" class="hoverable-row">
          <td>{{ user.name }}</td>
          <td>{{ user.emailaccount }}</td>
          <td>
            <button @click="editContact(user.id)" class="edit-btn"><i class="fas fa-edit"></i></button>
            <button @click="deleteContact(user.id)" class="delete-btn"><i class="fas fa-trash"></i></button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>


<script>
import { defineComponent } from 'vue';
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
      sortOrder: 'asc',
      searchQuery: '',
      copyUserList:[]
    };
  },
  methods: {
    async submitForm() {
      const validEmailDomains = ['@gmail.com', '@alexu.edu.eg'];
      const isValidEmail = validEmailDomains.some(domain => this.formData.emailaccount.endsWith(domain));

      if (!isValidEmail) {
        alert('Please enter a valid email ending with @gmail.com or @alexu.edu.eg');
        return;
      }
      
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
      const responseData = await response.json();
        console.log(responseData);
        responseData.forEach(contact => {
          console.log(contact.id, contact.name, contact.emailaccount);
        });
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
    sortByName(order) {
      if (order === this.sortOrder) {
        this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
      } else {
        this.sortOrder = order;
      }
      this.userList.sort((a, b) => {
        const nameA = a.name.toUpperCase();
        const nameB = b.name.toUpperCase();
        if (this.sortOrder === 'asc') {
          return nameA.localeCompare(nameB);
        } else {
          return nameB.localeCompare(nameA);
        }
      });
    },
    sortByGmail(order) {
      if (order === this.sortOrder) {
        this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
      } else {
        this.sortOrder = order;
      }
      this.userList.sort((a, b) => {
        const gmailnameA = a.emailaccount.toUpperCase();
        const gmailnameB = b.emailaccount.toUpperCase();
        if (this.sortOrder === 'asc') {
          return gmailnameA.localeCompare(gmailnameB);
        } else {
          return gmailnameB.localeCompare(gmailnameA);
        }
      });
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
      const responseData = await response.json();
      console.log(responseData);
      responseData.forEach(contact => {
        console.log(contact.id, contact.name, contact.emailaccount);
      });
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
        const responseData = await response.json();
        console.log(responseData);
        responseData.forEach(contact => {
          console.log(contact.id, contact.name, contact.emailaccount);
        });
      }catch(error){
        console.log(error + " why!");
      }
    },
    searchContacts() {
      this.copyUserList = this.userList;
      const query = this.searchQuery.toLowerCase().trim();
      if (query === '') {
        return;
      }

      this.copyUserList = this.copyUserList.filter((user) => {
        const nameMatch = user.name.toLowerCase().includes(query);
        const emailMatch = user.emailaccount.toLowerCase().includes(query);
        console.log(nameMatch + " " + emailMatch);
        return nameMatch || emailMatch;
      });
      console.log(this.copyUserList);
    },

  },
});
</script>


<style scoped>

.searched-contact {
  background-color: #007BFF; 
  font-weight: bold;
  color:#ffffff;
}
.searched-contact:hover {
  background-color: #1d59a8 !important; 
  font-weight: bold;
  color:#ffffff;
}

.search-bar{
    display: flex;
    margin-top: 20px;
    transition: transform 0.3s;
    margin-bottom: 20px;
    margin-left: 90px;
    margin-right: 2px;
    width:100%;
    border: 1px solid #ffffff;
    border-radius: 4px;
    overflow: hidden;
    width:30%;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }

  .search-bar:hover{
    transform: scale(1.05);
  }


  .search-bar,
  .search-bar input
  {
    transition: border-color 0.3s;
  }

  .search-bar{
    padding: 8px;
    border: 0px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    width:600px;
    color: #000000;
    background-color: #ffffff;
    outline: none;
  }

  .search-bar input {
    flex: 1;
    padding: 8px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    color: #000000;
    outline: none;
  }

  .search-bar .search-btn{
    background-color: #007BFF;
    color: #fff;
    border: none;
    padding: 8px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    outline: none;
  }

  .search-bar .search-btn i {
    font-size: 18px;
  }

  .search-bar .search-input::placeholder {
    color: #007BFF;
  }

  .search-bar,
  .search-bar input:focus{
    border-color: #053a72;
  }


.contact-container {
  max-width: 800px;
  margin: auto;
  font-weight: bold;
  padding: 20px;
}
.contact-form {
  max-width: 400px;
  margin: auto;
  font-weight: bold;
  margin-bottom: 20px;
}
.input-box {
  margin: 12px 0;
  font-weight: bold;
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
.rotate-icon {
  transform: rotate(180deg);
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
.sort-header {
  cursor: pointer;
  position: relative;
}

.sort-icon {
  position: absolute;
  right: 5px;
  top: 50%;
  transform: translateY(-50%);
  color: #555; 
}
.hoverable-row:hover {
  background-color: #f0f0f0;
}

</style>

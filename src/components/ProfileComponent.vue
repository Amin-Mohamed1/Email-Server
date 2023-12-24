<template>
  <div class="app-container">
    <aside class="sidebar">

      <div class="logo">
        <img src="@/assets/logo.png" alt="Logo" />
        <span>Gmail</span>
      </div>

      <div class="user-info">
        <div class="user-avatar">
          <i class="fas fa-user-circle"></i>
        </div>
        <div class="user-details">
          <p class="username" @mouseover="highlightText" @mouseleave="resetText">{{ profileUsername }}</p>
        </div>
      </div>

      <nav class="nav-links">

          <router-link to="/profile/search" class="nav-link" style="color: #0e5809;" active-class="router-link-active">
            <i class="fas fa-search animated-icon"></i> Search
          </router-link>


          <router-link to="/profile/compose" class="nav-link" style="color: #3498db;" active-class="router-link-active">
            <i class="fas fa-pen animated-icon"></i> Compose
          </router-link>

          <router-link to="/profile/inbox" class="nav-link" style="color: #2ecc71;" active-class="router-link-active">
            <i class="fas fa-inbox animated-icon"></i> Inbox
          </router-link>

          <router-link to="/profile/sent" class="nav-link" style="color: #e67e22;" active-class="router-link-active">
            <i class="fas fa-paper-plane animated-icon"></i> Sent
          </router-link>

          <router-link to="/profile/contact" class="nav-link" style="color: #9b59b6;" active-class="router-link-active">
            <i class="fas fa-address-book animated-icon"></i> Contacts
          </router-link>

          <router-link to="/profile/draft" class="nav-link" style="color: #e74c3c;" active-class="router-link-active">
            <i class="fas fa-file-alt animated-icon"></i> Drafts
          </router-link>

          <router-link to="/profile/trash" class="nav-link" style="color: #34495e;" active-class="router-link-active">
            <i class="fas fa-trash animated-icon"></i> Trash
          </router-link>

          <router-link to="/profile/folders" class="nav-link" style="color: #7782b2;" active-class="router-link-active">
            <i class="fas fa-folder animated-icon"></i> Folders
          </router-link>

          <router-link to="/logout" class="nav-link" style="color: #e74c3c;" active-class="router-link-active">
            <i class="fas fa-sign-out-alt animated-icon"></i> Logout
          </router-link>
      </nav>
      

    </aside>
    <main class="main-content">
      <router-view 
        :profileContactInfo="profileContactInfo" 
        :userList = "userList" 
        :Inboxemails = "Inboxemails"
        :Trashemails = "Trashemails"
        :Draftemails = "Draftemails"
        :Sentemails = "Sentemails">
      </router-view>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';
import FilterComponent from './FilterComponent.vue';
const router = useRouter();
const isSidebarExpanded = ref(false);
const username = ref('');
const email = ref('');
const folders = ref([]); 
const contacts = ref([]);
const idMessage = ref(0);


//sent info
const profileContactInfo = ref('');
const profileUsername = ref('');
const profileIdmessage = ref(0);
const userList = ref([]);
const Inboxemails = ref([]);
const Sentemails = ref([]);
const Draftemails = ref([]);
const Trashemails = ref([]);

onMounted(() => {
  const cookies = document.cookie.split('; ');
  for (const cookie of cookies) {
    const [name, value] = cookie.split('=');
    if (name === 'username') {
      username.value = value;
      profileUsername.value = value;
      console.log(profileUsername.value);
      
    } else if (name === 'email') {
      email.value = value;
      profileContactInfo.value = value;
      console.log(profileContactInfo.value);
    }
    else if(name === 'idMessage'){
      idMessage.value = parseInt(value);
      profileIdmessage.value = idMessage.value;
    }
    else if (name === 'folders') {
  try {
    const parsedValue = Array.isArray(value) ? value : JSON.parse(value);
      folders.value = parsedValue;
      console.log("Folders:", folders.value);

      const inboxFolder = folders.value.find(folder => folder.name === 'inbox');
      if (inboxFolder) {
        Inboxemails.value = inboxFolder.emails || [];
        console.log("Inbox emails:", Inboxemails.value);
      }

      const sentFolder = folders.value.find(folder => folder.name === 'sent');
      if (sentFolder) {
        Sentemails.value = sentFolder.emails || [];
        console.log("Sent emails:", Sentemails.value);

      }

      const draftFolder = folders.value.find(folder => folder.name === 'draft');
      if (draftFolder) {
        Draftemails.value = draftFolder.emails || [];
        console.log("Draft emails:", Draftemails.value);
      }

      const trashFolder = folders.value.find(folder => folder.name === 'trash');
      if (trashFolder) {
        Trashemails.value = trashFolder.emails || [];
        console.log("Trash emails:", Trashemails.value);
      }
    } catch (error) {
      console.error('Error parsing folders:', error);
    }
  }else if (name === 'contacts') {
    userList.value = JSON.parse(value);
    if (userList.value.length > 0) {
      console.log(userList.value[0] + " contact");
    } else {
      console.log("No contacts available");
    }
  }
  }
});

const expandSidebar = () => {
  isSidebarExpanded.value = true;
};

const collapseSidebar = () => {
  isSidebarExpanded.value = false;
};


</script>

<style scoped>
.app-container {
  display: flex;
}

.utility{
  display: flex;
}

.sidebar {
  width: 150px;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  height: 100vh;
  overflow-y: auto;
  transition: width 0.3s;
}

.sidebar.expanded {
  width: 250px; 
}

.logo {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.logo img {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.nav-links {
  display: flex;
  flex-direction: column;
}

.nav-links .nav-link {
  display: flex;
  align-items: center;
  padding: 8px;
  margin: 4px 0;
  text-decoration: none;
  color: #007BFF;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-links .nav-link .animated-icon {
  margin-right: 12px;
  transition: transform 0.2s ease-out;
}

.nav-links .nav-link .animated-icon img {
  width: 20px;
  height: 20px;
}

.nav-links .nav-link.router-link-active {
  background-color: #ddd;
}

.nav-links .nav-link.router-link-active .animated-icon {
  transform: scale(1.2);
  animation: pulse 1s infinite;
}
@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}
.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px; /* Adjust the size of the Font Awesome icon */
  color: #1e0c93; /* Text color */
  background-color: transparent !important;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Optional: Add a subtle box shadow */
}
.user-details {
  flex: 1;
  margin-left: 10px; /* Add some spacing between avatar and details */
}

.username,
.email {
  font-family: 'Arial', sans-serif; /* Set font to Arial */
  cursor: pointer; /* Add cursor style for hoverable text */
  transition: color 0.3s; /* Smooth transition for text color change */
}

.username:hover {
  color: #3498db; /* Change text color on hover */
}

.username {
  font-size: 18px;
  font-weight: bold;
  color: #333; /* Dark text color */
}
.nav-links .nav-link .animated-icon {
  margin-right: 12px;
  font-size: 1.2em; /* Adjust the size of the Font Awesome icon */
  transition: transform 0.2s ease-out;
}

.user-avatar span {
  font-size: 1.5em; /* Adjust the size of the Font Awesome icon */
}

.main-content {
  flex: 1;
  padding: 20px;
  height: 100vh;
  overflow-y: auto;
}
</style>

<template>
  <div class="app-container">
    <aside class="sidebar" :class="{ 'expanded': isSidebarExpanded }" @mouseover="expandSidebar" @mouseleave="collapseSidebar">
      <!-- Logo or application name -->
      <div class="logo">
        <img src="@/assets/logo.png" alt="Logo" />
        <span>Mail</span>
      </div>

      <!-- Navigation links with blue GIF icons -->
      <nav class="nav-links">

        <!-- 
        <router-link to="/compose" class="nav-link">
          <img src="@/assets/compose.gif" class="animated-icon" alt="Compose Icon" /> Compose
        </router-link>

      
        <router-link to="/drafts" class="nav-link">
          <img src="@/assets/drafts.gif" class="animated-icon" alt="Drafts Icon" /> Drafts
        </router-link>
        -->
        <router-link to="/inbox" class="nav-link">
          <img src="@/assets/inbox.gif" class="animated-icon" alt="Inbox Icon" width="50" height="50" />
          Inbox
        </router-link>
        <router-link to="/sent" class="nav-link">
          <img src="@/assets/sent.gif" class="animated-icon" alt="Sent Icon" width="50" height="50" />
          Sent
        </router-link>
        <router-link to="/contacts" class="nav-link">
          <img src="@/assets/contact.gif" class="animated-icon" alt="Contacts Icon" width="50" height="50" />
          Contacts
        </router-link>
        <!-- 
        <router-link to="/trash" class="nav-link">
          <img src="@/assets/trash.gif" class="animated-icon" alt="Trash Icon" /> Trash
        </router-link>

      -->
        <router-link to="/folders" class="nav-link">
          <img src="@/assets/folders.gif" class="animated-icon" alt="Folders Icon" width="50" height="50" />
          Folders
        </router-link>

        <!-- 
        <router-link to="/logout" class="nav-link">
          <img src="@/assets/logout.gif" class="animated-icon" alt="Logout Icon" /> Logout
        </router-link>

      -->
      </nav>
    </aside>

    <main class="main-content">
      <!-- Your main content goes here -->
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();
const isSidebarExpanded = ref(false);

const goToHomePage = () => {
  router.push('/home');
};

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
  color: #007BFF; /* Blue color */
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-links .nav-link .animated-icon {
  margin-right: 12px;
  transition: transform 0.2s ease-out;
}

.nav-links .nav-link .animated-icon img {
  width: 20px; /* Adjust the width as needed */
  height: 20px; /* Adjust the height as needed */
}

.nav-links .nav-link:hover {
  background-color: #ddd;
}

.nav-links .nav-link:hover .animated-icon {
  transform: scale(1.2); /* Increase the size on hover */
  animation: pulse 1s infinite; /* Pulse animation on hover */
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

.main-content {
  flex: 1;
  padding: 20px;
  height: 100vh; /* Set the height to 100% of the viewport height */
  overflow-y: auto; /* Add scrollbar if content exceeds the height */
}
</style>

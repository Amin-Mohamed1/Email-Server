<template>
  <div class="app-container">
    <aside class="sidebar" :class="{ 'expanded': isSidebarExpanded }" @mouseover="expandSidebar" @mouseleave="collapseSidebar">

      <div class="logo">
        <img src="@/assets/logo.png" alt="Logo" />
        <span>Gmail</span>
      </div>

      <nav class="nav-links">
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

          <router-link to="/profile/folders" class="nav-link" style="color: #0a1e78;" active-class="router-link-active">
            <i class="fas fa-folder animated-icon"></i> Folders
          </router-link>

          <router-link to="/logout" class="nav-link" style="color: #e74c3c;" active-class="router-link-active">
            <i class="fas fa-sign-out-alt animated-icon"></i> Logout
          </router-link>
      </nav>

      
    </aside>
    <main class="main-content">
      <div class = "utility" >
        <FilterComponent />
      </div>
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import FilterComponent from './FilterComponent.vue';
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

.main-content {
  flex: 1;
  padding: 20px;
  height: 100vh;
  overflow-y: auto;
}
</style>

<template>
    <div>
      <header class="header-bar">
        <div class="logo-container" @click="goToHomePage">
          <div class="logo">
            <img src="@/assets/logo.png" alt="Gmail Logo" class="logo-image" />
            <span class="logo-text">Mail</span>
          </div>
        </div>
        <div class="buttons-container">
          <button class="action-button create-account" @click="goToSignUp">
            Create an account
          </button>
          <button class="action-button login" @click="goToLogin">Login</button>
        </div>
      </header>
      <main>
        <div class="animated-background">
            <div class="wave"></div>
        </div>
        <div class="content">
            <h2>Welcome to Gmail</h2>
            <p>Explore the new features and stay connected with your friends and colleagues.</p>
            <p>Efficiently organize your emails, enjoy powerful search functionality, and collaborate with Google Workspace.</p>
        </div>
        <div id="loading">
            <div class="gmail-opening-animation"></div>
        </div>
    </main>
      <footer class="footer">
        <p>&copy; 2024 Gmail. All rights reserved.</p>
      </footer>
    </div>
  </template>
  <script setup>
  import { useRouter } from 'vue-router';
  
  const router = useRouter();
  
  const goToHomePage = () => {
    router.push('/home');
  };
  
  const goToSignUp = () => {
    router.push('/signup');
  };
  
  const goToLogin = () => {
    router.push('/login');
  };
  </script>
  
<style scoped>
  section {
    padding: 20px;
    border-bottom: 1px solid #ddd;
  }
  
  h2 {
    color: #333;
    font-size: 24px;
    margin-bottom: 16px;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin-bottom: 8px;
  }
  
  .header-bar {
    background-color: #ffffff;
    color: rgb(255, 255, 255);
    padding: 16px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
    cursor: pointer;
  }
  
  .logo-container {
    display: flex;
    align-items: center;
  }
  
  .logo {
    display: flex;
    align-items: center;
    cursor: pointer;
    transition: transform 0.3s;
  }
  
  .logo-image {
    width: 40px;
    height: 40px;
    margin-right: 8px;
  }
  
  .logo-text {
    font-size: 24px;
    font-weight: bold;
    color: rgba(0, 0, 0);
  }
  
  .buttons-container {
    display: flex;
    align-items: center;
  }
  
  .action-button {
    background-color: #1a73e8;
    color: white;
    padding: 12px 24px;
    border: none;
    border-radius: 8px;
    margin-left: 8px;
    cursor: pointer;
    transition: background-color 0.3s, transform 0.3s;
  }
  
  .action-button:hover {
    background-color: #1558a6;
    transform: translateY(-2px);
  }
  
  .create-account {
    background-color: #fff;
    color: #1a73e8;
    border: 1px solid #1a73e8;
  }
  
  .create-account:hover {
    background-color: #e5f0fd;
  }
  
  .login {
    background-color: #1a73e8;
    color: white;
  }
  
  .login:hover {
    background-color: #1a73e8;
    transform: translateY(-2px);
  }
  
  /* Styling for footer */
  .footer {
    background-color: #1a73e8;
    color: #ffffff;
    padding: 16px;
    text-align: center;
  }
  
  .footer a {
    color: #ffffff;
    text-decoration: none;
  }
  
  .footer a:hover {
    text-decoration: underline;
  }

  /* New styles for animated background */
.animated-background {
  position: relative;
  overflow: hidden;
  height: 150px; /* Adjust the height as needed */
}

.wave {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100px; /* Adjust the height as needed */
  background: linear-gradient(to right, #1a73e8, #ffffff);
  animation: wave-animation 2s infinite linear;
}

@keyframes wave-animation {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-100%);
  }
}

.content {
  padding: 20px;
  text-align: center;
}

.content h2 {
  color: #1a73e8;
  font-size: 28px;
  margin-bottom: 16px;
}

.content p {
  color: #555;
  font-size: 16px;
  line-height: 1.6;
}

.gmail-opening-animation {
    background-image: url('@/assets/mail.gif');
    background-size: cover;
    width: 100px;
    height: 100px;
    animation: gmail-opening 1s forwards;
    margin: 20px auto;
  }

  @keyframes gmail-opening {
    0% {
      transform: scale(0);
    }
    100% {
      transform: scale(1);
    }
  }

</style>
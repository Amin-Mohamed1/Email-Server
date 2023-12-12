<template>
  <header class="header-bar">
        <div class="logo-container" @click="goToHomePage">
          <div class="logo">
            <img src="@/assets/logo.png" alt="Gmail Logo" class="logo-image" />
            <span class="logo-text">Gmail</span>
          </div>
        </div>
  </header>

  <div class="parent">
      <div class="child">
        <main>
          <div class="wrapper">
            <h2>Registration</h2>
            <form action="#">
              <div class="input-box">
                <input type="text" placeholder="Enter your name" required>
              </div>
              <div class="input-box">
                <input type="email" placeholder="Enter your email" required>
              </div>
              <div class="input-box">
                <input type="password" placeholder="Create password" required>
              </div>
              <div class="input-box">
                <input type="password" placeholder="Confirm password" required>
              </div>
              <div class="policy">
                <input type="checkbox">
                <h3>I accept all terms & conditions</h3>
              </div>
              <div class="input-box button">
                <input type="Submit" value="Register Now">
              </div>
              <div class="text">
                <h3>Already have an account? <router-link to="/login">Login</router-link></h3>
              </div>
            </form>
          </div>
        </main>
      </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
  
  const router = useRouter();
  
  const goToHomePage = () => {
    router.push('/home');
  };
  
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap');
  .parent {
    height: 10px;
    border: 5px solid #ffffff;
    display: flex;
    flex-direction: column;
  }
  .child {
    height: 0px;
    width: 100%;
    background: rgb(255, 255, 255);
    margin-top: auto;
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
  
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins', sans-serif;
}
body{
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #4070f4;
  animation: fadeIn 1s ease-in-out; /* Fade-in animation */
}
.wrapper {
  position: relative;
  max-width: 400px; /* Adjust the width as needed */
  width: 100%;
  background: #fff;
  padding: 34px;
  border-radius: 6px;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  text-align: center;
  opacity: 0;
  margin: auto; /* Center the form */
  animation: slideIn 1s forwards ease-in-out;
  transform: translateY(20px); /* Adjust this value to move it upward */
}
.wrapper h2{
  position: relative;
  font-size: 22px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
}
.wrapper h2::before{
  content: '';
  position: absolute;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
  height: 3px;
  width: 28px;
  border-radius: 12px;
  background: #4070f4;
}
.wrapper form{
  margin-top: 30px;
}
.wrapper form .input-box{
  height: 52px;
  margin: 18px 0;
}
form .input-box input{
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
.input-box input:focus,
.input-box input:valid{
  border-color: #4070f4;
}
form .policy{
  display: flex;
  align-items: center;
  justify-content: center; /* Center the policy checkbox and text */
  margin-top: 20px;
}
form h3{
  color: #707070;
  font-size: 14px;
  font-weight: 500;
  margin-left: 10px;
}
.input-box.button input{
  color: #fff;
  letter-spacing: 1px;
  border: none;
  background: #4070f4;
  cursor: pointer;
  margin-top: 20px;
}
.input-box.button input:hover{
  background: #0e4bf1;
}
form .text h3{
 color: #333;
 width: 100%;
 text-align: center;
 margin-top: 20px;
}
form .text h3 a{
  color: #4070f4;
  text-decoration: none;
}
form .text h3 a:hover{
  text-decoration: underline;
}

/* Animations */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>

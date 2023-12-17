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
          <form @submit.prevent="validateSignup">
            <div class="input-box">
              <input v-model="username" type="text" placeholder="Enter your name" required>
            </div>
            <div class="input-box">
              <input v-model="emailaccount" type="email" placeholder="Enter your email" required>
            </div>
            <div class="input-box">
              <input v-model="birth" type="date" id="birthdate" required>
            </div>
            <div class="input-box">
              <input v-model="password" type="password" placeholder="Create password" required>
            </div>
            <div class="input-box">
              <input v-model="confirmPassword" type="password" placeholder="Confirm password" required>
            </div>
            <div class="policy">
              <input v-model="acceptTerms" type="checkbox">
              <h3>I accept all terms & conditions</h3>
            </div>
            <div class="input-box button">
              <input type="submit" value="Register Now">
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
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

let username = ref('');
let emailaccount = ref('');
let password = ref('');
let confirmPassword = ref('');
let birth = ref('');
let acceptTerms = ref(false);

const validateSignup = async () => {
  if (password.value !== confirmPassword.value) {
    console.log("password " + password.value);
    console.log("confirmPassword " + confirmPassword.value);
    alert('Passwords do not match.');
    return;
  }
  const validBirthdate = isValidBirthdate(birth.value);
  console.log("isValidBirthdate " + validBirthdate);
  if (!validBirthdate) {
    alert('Invalid birth date. Please enter a valid birth date above 18 years.');
    return;
  }
  if (!acceptTerms) {
    alert('Please accept the terms and conditions.');
    return;
  }
  try {
    const response = await fetch('http://localhost:8081/mail/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username: username.value,
        emailaccount: emailaccount.value,
        password: password.value,
        birth: birth.value,
      }),
    });
    if (!response.ok) {
      const errorMessage = await response.text() || 'Unknown error';
      alert(`HTTP error! Status: ${response.status}, Message: ${errorMessage}`);
      return;
    }
    const responseData = await response.text();
    console.log(responseData);
    const message = responseData;
    if (message === 'Signed up successfully') {
      alert('Signup successful!');
      goToProfilePage();
    } else {
      alert(`Signup failed: ${message}`);
    }
  } catch (error) {
    console.error('Signup failed:', error);
    alert('An unexpected error occurred. Please try again.');
  }
};

const isValidBirthdate = (selectedDate) => {
    const today = new Date();
    const birthdate = new Date(selectedDate);

    if (isNaN(birthdate.getTime()) || birthdate > today) {
        return false; 
    }

    const age = today.getFullYear() - birthdate.getFullYear();
    return age >= 18;
};

const goToProfilePage = () => {
  router.push('/profile');
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

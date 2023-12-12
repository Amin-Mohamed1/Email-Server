<template>
      <h2 class="compose-title">New Message <i class="fa fa-envelope" style="margin-left: 10px; font-size: 40px; color: black"></i> </h2>
    <transition-group name="fade" mode="out-in">
      <div class="sender" >
        <label for="to">To :</label>
        <input v-model="to" id="to" type="email" name="to" placeholder="Enter recipient's email address" required/>
      </div>

      <div class="subject">
        <label for="subject">Subject :</label>
        <input v-model="subject" id="subject" type="text" name="subject" maxlength="30" placeholder="Enter subject here (max 30 characters)" required/>
      </div>

      <textarea v-model="message" class="messagebox" id="message" name="message" placeholder="Enter your message here .."  required></textarea>

        
          <div class="priorities">
            <h3 class="priority-title" for="priority" >Priority</h3>
            <input class="priority-input" name="exampleRadios" type="radio" id="p1" value="1"/>
              <label class="priority-input" id="priority" for="p1">1 (High)</label>
            <input class="priority-input" name="exampleRadios" type="radio" id="p2" value="2"/>
              <label class="priority-input"  id="priority" for="p2">2</label>
            <input class="priority-input" name="exampleRadios" type="radio" id="p3" value="3"/>
              <label class="priority-input"  id="priority"  for="p3">3 (Medium)</label>
            <input class="priority-input" name="exampleRadios" type="radio" id="p4" value="4"/>
              <label class="priority-input"  id="priority"  for="p4">4</label>
            <input class="priority-input" name="exampleRadios" type="radio" id="p5" value="5"/>
              <label class="priority-input"  id="priority"  for="p5">5 (Low)</label>
          </div>
          <div class="choosefile">
            <label class="attachment" for="file">Attachment <i class="fa fa-paperclip"></i></label>
            <input type="file" id="file" multiple />
          </div>
          <div class="send-draft">
            <button class="send-button" @click="sendEmail">Send <i class="fa fa-paper-plane"></i> </button>
            <button class="draft-button" @click="saveDraft">Save as draft <i class="fa fa-file"></i></button>
          </div>
      </transition-group>
</template>

<script>
import { ref } from 'vue';
// const to = document.getElementById('to').value;
// const subject = document.getElementById('subject').value;
// const message = document.getElementById('message').value;
// const priority = document.getElementById('priority').value;
export default {
  setup() {
    const emailData = ref({
      to: '',
      subject: '',
      message: '',
      priority: 1, // default priority
      file: null,
    });

    const sendEmail = async () => {
      const apiKey = 'YOUR_SENDGRID_API_KEY'; // Replace with your SendGrid API key
      const apiUrl = 'https://api.sendgrid.com/v3/mail/send';

      const data = {
        personalizations: [{
          to: [{ email: emailData.value.to }],
          subject: emailData.value.subject,
        }],
        from: { email: 'your-email@example.com' }, // Replace with your email
        content: [{
          type: 'text/plain',
          value: emailData.value.message,
        }],
      };
    };
  }
}

</script>

<style scoped>
.compose-title {
    font-size: 24px;
    margin-bottom: 20px;
    color: #333;
  }

  .priority-title {
    font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
  }

  .sender, .subject, .messagebox, .choosefile, .priorities, .send-draft {
    border: 1px solid #ddd;
    padding: 15px;
    margin-bottom: 15px;
    background-color: #fff;
    border-radius: 8px;
    transition: transform 0.3s ease-in-out;
  }

  .sender:hover, .subject:hover, .messagebox:hover, .choosefile:hover, .priorities:hover, .send-draft:hover {
    transform: scale(1.02);
  }

  label {
    font-weight: bold;
    color: #007BFF;
    font-size: larger;
    margin: 0px;
  }

  #to, #subject, .messagebox {
    width: 100%;
    padding: 12px 20px;
    margin: 4px 0;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 17px;
  }

  .messagebox {
    height: 200px;
    font-size: 30px;
  }

  .priority-input {
    margin: 8px;
  }

  .attachment {
    font-size: larger;
    margin: 7px;
    
  }

  #file {
    margin: 15px;
    font-size: medium;
    color:#333;
    font-weight: bolder;
  }
  .send-button, .draft-button {
    background-color: #007BFF;
    border: none;
    color: white;
    padding: 15px 30px;
    margin: 8px;
    text-decoration: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: large;
  }
  
  .send-button:hover, .draft-button:hover {
    background-color: #ddd;
    color: #007BFF;
  }
</style>
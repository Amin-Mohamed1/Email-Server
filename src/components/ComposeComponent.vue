<template>
  <div class="compose-container">
    <div class="header">
      <h2>
        <i class="fas fa-envelope"></i> Compose Email
      </h2>
        <div class="input-group to-group">
        <label for="to">
          <i class="fas fa-users"></i> To:
        </label>
        <div class="to-info" >
          <ul>
            <li v-for="(user, index) in to" :key="index">
              <i class="fas fa-user"></i> {{ user.email }}
            </li>
          </ul>
          <div class="add-recipient" @click="openAddUserModal">
            <i class="fas fa-plus"></i>
          </div>
        </div>
        </div>
      
      
      <div class="input-group from-group">
        <label for="from">
          <i class="fas fa-user"></i> From:
        </label>
        <input type="text" id="from" v-model="from" :placeholder="profileContactInfo" :readonly="true" />
      </div>
    </div>

    <div class="input-group subject-group">
        <label for="subject">
          <i class="fa-solid fa-pen"></i> Subject:
        </label>
        <input type="text" id="subject" v-model="subject" maxlength="30" placeholder="Enter subject here (max 30 characters)" required/>
      </div>

    <textarea v-model="emailBody" placeholder="Compose your email..." class="compose-textarea"></textarea>

    <div v-if="showAddUserModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeAddUserModal">&times;</span>
        <label for="newUserEmail">Enter User Gmail:</label>
        <input type="text" id="newUserEmail" v-model="newUserEmail" placeholder="Enter user's email" />
        <button @click="addUser" class="add-user-button">Add User</button>
      </div>
    </div>

    <div class="attachment-container">
      <label for="attachments">
        <i class="fas fa-paperclip"></i> Choose Attachments:
      </label>
      <input type="file" id="attachments" multiple @change="handleFileUpload" class="file-input" />
      <div v-if="attachments.length > 0">
        <h3>
          <i class="fas fa-paperclip"></i> Attachments:
        </h3>
        <ul>
          <li v-for="(attachment, index) in attachments" :key="index">
            <i class="fas fa-file"></i>{{ attachment }}
          </li>
        </ul>
      </div>
    </div>

    <button @click="submitForm" class="send-button">
    <i class="fas fa-paper-plane"></i> Send Email
  </button>
    <button @click="saveForm" class="send-button">
        <i class="fas fa-file"></i> Save Draft
      </button>
    <div>
      <p>{{ dateTime }}</p>
    </div>
  </div>
</template>
<script>
import { ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
import { format } from 'date-fns';
const router = useRouter();
export default {
  props: ['profileContactInfo'],
  data() {
    return {
      from: '',
      emailBody: '',
      attachments: [],
      showAddUserModal: false,
      newUserEmail: '',
      to: [],
      subject: '',
      dateTime: '',
    };
  },
  mounted() {
    this.from = this.profileContactInfo;
  },
  methods: {
    handleFileUpload(event) {
      const files = event.target.files;
      this.attachments = Array.from(files).map(file => file.name);

      this.fileBlobs = Array.from(files);

      console.log(this.fileBlobs);
    
    },
     async submitForm() {
      const validEmailDomains = ['@gmail.com', '@alexu.edu.eg'];

      if (this.to.length === 0) {
        alert('Please add at least one recipient');
        return;
       }

      if (this.subject.length === 0) {
        alert('Please enter a subject');
        return;
       }

       if (this.emailBody.length === 0) {
        alert('Please enter an email body');
        return;
       }

      const isValidEmails = this.to.every(user => {
        const atIndex = user.email.indexOf('@');
        const hasLocalPart = atIndex > 0; // Check if "@" is not at the beginning
        const hasDomain = atIndex < user.email.length - 1; // Check if "@" is not at the end

        return hasLocalPart && hasDomain && validEmailDomains.some(domain => user.email.endsWith(domain));
      });

      if (!isValidEmails) {
        alert('Please enter valid email addresses with a proper format ending with @gmail.com or @alexu.edu.eg');
        this.to = [];
        return;

      }

      await this.sendEmail();
    },

    async saveForm() {
      const validEmailDomains = ['@gmail.com', '@alexu.edu.eg'];

      if (this.to.length === 0 && this.subject.length === 0 && this.emailBody.length === 0) {
        alert('There is nothing to save');
        return;
      }

      const isValidEmails = this.to.every(user => {
        return validEmailDomains.some(domain => user.email.endsWith(domain));
      });

      if (!isValidEmails) {
        alert('Please enter valid email addresses ending with @gmail.com or @alexu.edu.eg');
        this.to = [];
        return;
      }

      await this.saveDraft();
    },
    
    async sendEmail() {
      console.log('To:', this.to);
      console.log('From:', this.from);
      console.log('Subject:', this.subject);
      console.log('Sending email with body:', this.emailBody);
      console.log('Attachments:', this.attachments);
      const currentDate = new Date();
      const formattedDateTime = format(currentDate, "yyyy-MM-dd h:mm a");
      this.dateTime = formattedDateTime;

      
      try {
        const response = await fetch('http://localhost:8081/mail/sendEmail', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            to: this.to,
            from: this.from,
            subject: this.subject,
            emailBody: this.emailBody,
            attachments: this.attachments,
            dateTime: this.dateTime,
          }),
        });

        if (response.ok) {
          console.log('Email sent successfully');
          alert('Email sent successfully');
        } else {
          console.error('Failed to send email');
          alert('Failed to send email');
        }
      } catch (error) {
        console.error('Error sending email:', error);
        alert('Error sending email');
      }
    },
    openAddUserModal() {
      this.showAddUserModal = true;
    },
    closeAddUserModal() {
      this.showAddUserModal = false;
    },
    addUser() {
      console.log('Adding user with email:', this.newUserEmail);
      this.to.push({ email: this.newUserEmail });
      this.closeAddUserModal();
    },
    saveDraft() {
      console.log('To:', this.to);
      console.log('From:', this.from);
      console.log('Saving draft with body:', this.emailBody);
      console.log('Attachments:', this.attachments);
      const currentDate = new Date();
      const formattedDateTime = format(currentDate, "yyyy-MM-dd h:mm a");
      this.dateTime = formattedDateTime;
      const draft = {
        to: this.to,
        from: this.from,
        emailBody: this.emailBody,
        attachments: this.attachments,
        dateTime: this.dateTime,
      };
      // Convert the draft object to a JSON string
      const draftJson = JSON.stringify(draft);
      localStorage.setItem('emailDraft', draftJson);
      console.log('Draft saved successfully');
      console.log('Draft:', draft);
      alert('Draft saved successfully');
    },
    
  },
};
</script>


<style scoped>
.compose-container {
  max-width: 900px;
  max-height: 900px;
  margin: 50px auto;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 6px 6px rgba(4, 4, 4, 0.1);
}

/* Heading styling */
h2 {
  color: #187dc1;
  font-size: 28px;
  margin-bottom: 15px;
}

/* Textarea styling */
.compose-textarea {
  width: 600px;
  height: 300px;
  padding: 10px;
  margin-bottom: 15px;
  margin-left: 15px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(255, 255, 255, 0.1), 0 4px 8px rgba(0, 0, 0, 0);
  border-radius: 6px;
  font-size: 16px;
  color: #000000;
  transition: box-shadow 0.3s ease-in-out;
}

/* Attachment styling */
.attachment-container {
  margin-bottom: 20px;
}

.file-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 6px;
}

.file-input:hover {
  border-color: #ffffff;
  color: black;
}

.modal {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}

.modal-content {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 6px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  max-width: 600px;
  width: 90%;
  font-family: Arial, sans-serif;
  position: relative;
  /* Added to handle positioning of the button */
}

/* Add User button styling */
.add-user-button {
  background-color: #3498db;
  color: #ffffff;
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
  /* Adjust as needed */
  transition: background-color 0.3s ease-in-out;
}

.add-user-button:hover {
  background-color: #2980b9;
}

.close {
  color: #ff0000;
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: #ca0202;
}

/* Button styling */
.send-button {
  background-color: #3498db;
  color: #ffffff;
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease-in-out;
  margin: 0 15px;
}

.send-button:hover {
  background-color: #2ecc71;
}

.input-group,
.to-group,
.from-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

.input-group label,
.to-group label,
.from-group label {
  margin-bottom: 10px;
  font-size: 16px;
  color: #000000;
}

.input-group input,
.to-group input,
.from-group input,
.subject-group input {
  width: 80%;
  padding: 10px;
  font-size: 16px;
  border: 2px solid #ffffff;
  border-radius: 6px;
  transition: border-color 0.3s ease-in-out;
}

.input-group input:focus,
.to-group input:focus,
.from-group input:focus,
.subject-group input:focus {
  border-color: #ffffff;
}

.add-recipient {
  cursor: pointer;
  display: inline-block;
  margin-left: 10px;
  font-size: 20px;
  color: #3498db;
}

.add-recipient:hover {
  color: #2ecc71;
}

/* User list styling */
.to-info {
  margin-top: 10px;
}

.to-info ul {
  list-style-type: none;
  color: black;
  padding: 0;
}

.to-info ul li {
  font-size: 16px;
  color: #000000;
  margin-bottom: 5px;
  display: flex;
  align-items: center;
}

.to-info ul li i {
  margin-right: 5px;
}</style>
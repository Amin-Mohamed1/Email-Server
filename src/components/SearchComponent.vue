<template>
  <div class="search-bar">
    <select v-model="searchCategory" class="search-category">
      <option value="all">All</option>
      <option value="reciever">Reciever</option>
      <option value="body">Body</option>
      <option value="datetime">Date</option>
      <option value="priority">Priority</option>
      <option value="subject">Subject</option>
    </select>
    <input v-model="searchQuery" type="text" placeholder="Search Query...">
    <button @click="search" class="search-btn">
      <i class="fas fa-search"></i>
    </button>
  </div>
  <transition-group name="fade" mode="out-in">
    <div v-for="email in emails" :key="email.id" :class="{ 'email-item': true, 'unread-email': !email.read }"
      @click="showEmailDetails(email)">
      <div class="info">
        <div class="sender">{{ email.sender }}</div>
        <div class="date-time">{{ email.dateTime }}</div>
      </div>
      <div class="subject">{{ email.subject }}</div>
      <div class="body">
        <div v-if="!email.expanded" class="truncated-body">
          {{ truncateBody(email.body, 120) }}
          <span v-if="shouldTruncate(email.body)">
            <span @click.stop="showEmailDetails(email)">See more</span>
          </span>
        </div>
        <div v-else>
          {{ email.body }}
          <span @click.stop="expandBody(email)">See less</span>
        </div>
        <div v-if="hasAttachment(email.attachments)" class="attachment-section">
          <strong class="attachment-label">Attachments:</strong>
          <ul>
            <li v-for="(attachment, index) in email.attachments" :key="index">
              {{ getAttachmentIcon(attachment) }} <strong>{{ attachment }}</strong>
            </li>
          </ul>
        </div>
        <div v-else class="no-attachments">No attachments</div>
      </div>
      <div class="meta">
        <div class="priority">Priority: {{ email.priority }}</div>
      </div>
    </div>
  </transition-group>

  <div v-if="show" class="modal">
    <div class="modal-content">
      <div class="info">
        <div class="sender">{{ selectedEmail.sender }}</div>
        <div class="date-time">{{ selectedEmail.dateTime }}</div>
      </div>
      <div class="subject">{{ selectedEmail.subject }}</div>
      <div class="body">
        {{ selectedEmail.body }}
        <div v-if="hasAttachment(selectedEmail.attachments)" class="attachment-section">
          <strong class="attachment-label">Attachments:</strong>
          <ul>
            <li v-for="(attachment, index) in selectedEmail.attachments" :key="index">
              {{ getAttachmentIcon(attachment) }} <strong>{{ attachment }}</strong>
            </li>
          </ul>
        </div>
        <div v-else class="no-attachments">No attachments</div>
      </div>


      <div class="meta">
        <div class="priority">Priority: {{ selectedEmail.priority }}</div>
        <button @click.stop="closeEmailDetails" class="close-btn">
          <i class="fas fa-times"></i>
        </button>
      </div>
    </div>
  </div>

  <!--
    <div class="pagination">
      <button @click="changePage('prev')" :disabled="currentPage === 1">Prev</button>
      <span>{{ currentPage }}</span>
      <button @click="changePage('next')" :disabled="currentPage === totalPages">Next</button>
    </div>
-->
</template>

<script setup>
import { ref } from 'vue';
import { defineProps } from 'vue';

const props = defineProps(['profileContactInfo']);
const emails = ref([]);

const searchCategory = ref('all');
const searchQuery = ref('');

const search = () => {
  console.log('Search Category:', searchCategory.value);
  console.log('Search Query:', searchQuery.value);
  fetchEmails();
};


const fetchEmails = async () => {
  try {
    const EmailAddress = props.profileContactInfo;

    const response = await fetch(`http://localhost:8081/mail/search/${EmailAddress}/${searchCategory.value}/${searchQuery.value}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
      body: JSON.stringify({}),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    emails.value = data;
    console.log(data);
  } catch (error) {
    console.error('Error fetching emails:', error);
  }
};


const hasAttachment = (attachments) => attachments && attachments.length > 0;
let show = ref(false);
const showEmailDetails = (email) => {
  makeRead(email);
  show.value = true;
  selectedEmail = email;
  console.log(selectedEmail);
};
const makeRead = async (email) => {
  try {
    const EmailAddress = props.profileContactInfo;
    const response = await fetch(`http://localhost:8081/mail/makeRead/${EmailAddress}/${email.id}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    emails.value = data;
    console.log(data);
  } catch (error) {
    console.error('Error:', error);
  }
};
const closeEmailDetails = () => {
  show.value = false;
  selectedEmail = null;
};
const getAttachmentIcon = (attachment) => {
  if (attachment.endsWith('.jpeg') || attachment.endsWith('.png') || attachment.endsWith('.jpg')) {
    return '📷';
  } else if (attachment.endsWith('.docx')) {
    return '📃';
  } else if (attachment.endsWith('.pdf')) {
    return '📃';
  } else {
    return '📎';
  }
};

const shouldTruncate = (body) => body.length > 120;
const truncateBody = (body, maxLength) => {
  return body.length > maxLength ? body.slice(0, maxLength) + '...' : body;
};
//const truncateBody = (body) => shouldTruncate(body) ? body.slice(0, 120) : body;

const expandBody = (email) => {
  email.expanded = !email.expanded;
};


const changePage = (direction) => {
  if (direction === 'prev' && currentPage.value > 1) {
    currentPage.value -= 1;
  } else if (direction === 'next' && currentPage.value < totalPages.value) {
    currentPage.value += 1;
  }
};


</script>

<style scoped>
.search-bar {
  display: flex;
  margin-top: 20px;
  transition: transform 0.3s;
  margin-bottom: 20px;
  margin-left: 200px;
  margin-right: 2px;
  width: 100%;
  border: 1px solid #ffffff;
  border-radius: 4px;
  overflow: hidden;
  width: 30%;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.search-bar:hover {
  transform: scale(1.05);
}

.search-category {
  padding: 10px;
  border: 1px solid #ffffff;
  border-radius: 4px;
  background-color: #ffffff;
  color: #000000;
  cursor: pointer;
  appearance: none;
  width: 150px;
  -webkit-appearance: none;
  background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>');
  /* Add custom arrow icon */
  background-repeat: no-repeat;
  background-position: right 10px center;
}

.search-category:focus {
  outline: none;
  border-color: #ffffff;
}

.search-bar,
.search-bar input {
  transition: border-color 0.3s;
}

.search-bar {
  padding: 8px;
  border: 0px solid #ffffff;
  border-radius: 4px;
  margin-right: 10px;
  width: 600px;
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

.search-bar .search-btn {
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
.search-bar input:focus {
  border-color: #053a72;
}

.email-item {
  border: 1px solid #ddd;
  padding: 15px;
  margin-bottom: 15px;
  background-color: #fff;
  border-radius: 8px;
  transition: transform 0.3s ease-in-out;
}

.email-item:hover {
  transform: scale(1.02);
}

.info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.sender {
  font-weight: bold;
  color: #007BFF;
}

.date-time {
  color: #888;
}

.subject {
  margin-top: 5px;
  font-size: 18px;
  color: #333;
}

.body {
  margin-top: 10px;
  color: #555;
}

.meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.priority {
  font-weight: bold;
}

.delete-btn {
  background-color: #ff5555;
  color: #fff;
  border: none;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.delete-btn:hover {
  background-color: #dd4444;
}

.truncated-body {
  overflow: hidden;
  text-overflow: ellipsis;
  max-height: 3em;
  line-height: 1.5em;
}



.truncated-body span {
  cursor: pointer;
  color: #007BFF;
  margin-left: 5px;
}

.attachment-indicator {
  margin-left: 5px;
}

.attachment-section {
  margin-top: 10px;
}

.attachment-label {
  font-weight: bold;
}

.no-attachments {
  color: #888;
  margin-top: 10px;
}

ul {
  list-style: none;
  padding: 0;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.pagination button {
  margin: 0 5px;
  padding: 8px;
  border: 1px solid #ddd;
  background-color: #fff;
  cursor: pointer;
}

.pagination button:disabled {
  cursor: not-allowed;
  background-color: #ffffff;
}
</style>

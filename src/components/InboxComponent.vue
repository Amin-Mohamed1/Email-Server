<template>
  <div>
    <h2 class="inbox-title"><i class="fa-solid fa-inbox"></i> Inbox</h2>
    <div class="search-bars-container">
      <div class="search-bar">
        <select v-model="searchCategory" class="search-category">
          <option value="sender">Sender</option>
          <option value="body">Body</option>
          <option value="date">Date</option>
          <option value="date">Attachments</option>
          <option value="subject">Subject</option>
        </select>
        <input v-model="searchQuery" type="text" placeholder="Search Query...">
        <button @click="search" class="search-btn">
          <i class="fas fa-search"></i>
        </button>
      </div>

      <div class="sort-bar">
        <select v-model="sortCategory" class="sort-category">
          <option value="sender">Sender</option>
          <option value="priority">Priority</option>
          <option value="subject">Subject</option>
        </select>
        <i class="fas fa-sort-alpha-down sort-icon" :class="{ 'rotate-icon': sortOrder === 'asc' }"></i>
      </div>

      <div class="filter-bar">
        <select v-model="filterCategory" class="filter-category">
          <option value="sender">Sender</option>
          <option value="date">Date</option>
          <option value="subject">Subject</option>
        </select>
        <input v-model="filterQuery" type="text" placeholder="Filter Query...">
        <button @click="filter" class="filter-btn">
          <i class="fas fa-filter"></i>
        </button>
      </div>

    </div>

    <transition-group name="fade" mode="out-in">
      <div v-for="email in displayedEmails" :key="email.id" class="email-item" @click="expandBody(email)">
        <div class="info">
          <div class="sender">{{ email.sender }}</div>
          <div class="date-time">{{ email.dateTime }}</div>
        </div>
        <div class="subject">{{ email.subject }}</div>
        <div class="body">
          <div v-if="!email.expanded" class="truncated-body">
            {{ truncateBody(email.body) }}
            <span v-if="shouldTruncate(email.body)">
              ... <span @click.stop="expandBody(email)">See more</span>
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
                {{ getAttachmentIcon(attachment.type) }} <strong>{{ attachment.name }}</strong>
              </li>
            </ul>
          </div>
          <div v-else class="no-attachments">No attachments</div>
        </div>
        <div class="meta">
          <div class="priority">Priority: {{ email.priority }}</div>
          <button @click.stop="deleteEmail(email.id)" class="delete-btn">Delete</button>
        </div>
      </div>
    </transition-group>
    <div class="pagination">
      <button @click="changePage('prev')" :disabled="currentPage === 1">Prev</button>
      <span>{{ currentPage }}</span>
      <button @click="changePage('next')" :disabled="currentPage === totalPages">Next</button>
    </div>
  </div>
</template>

<script setup>

import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { defineProps } from 'vue';

let searchCategory = 'sender';
let defaultSortCategory = 'priority';
let sortCategory = ref(defaultSortCategory);
let defaultSortOrder = 'asc';
let sortOrder = ref(defaultSortOrder);
let filterCategory = 'sender';
const currentUserEmail = 'user@gmail.com';
const emailsPerPage = 5;

const props = defineProps(['profileContactInfo', 'Inboxemails']);

const fetchEmails = async () => {
  try {
    const response = await axios.get(`http://localhost/emails/${currentUserEmail}`);
    emails.value = response.data;
  } catch (error) {
    console.error('Error fetching emails:', error);
  }
};

const emails = ref([]);
props.Inboxemails.forEach((inboxEmail) => {
  const transformedEmail = {
    id: inboxEmail.id,
    sender: inboxEmail.sender,
    subject: inboxEmail.subject,
    body: inboxEmail.body,
    attachments: inboxEmail.attatchments || [],
    priority: inboxEmail.priority,
    dateTime: inboxEmail.dateTime,
  };
  emails.value.push(transformedEmail);
});

onMounted(() => {
  fetchEmails();
});

const currentPage = ref(1);
const totalPages = computed(() => Math.ceil(emails.value.length / emailsPerPage));
const displayedEmails = computed(() => {
  const startIndex = (currentPage.value - 1) * emailsPerPage;
  const endIndex = startIndex + emailsPerPage;
  return emails.value.slice(startIndex, endIndex);
});
const deleteEmail = (id) => {
  emails.value = emails.value.filter(email => email.id !== id);
};
const shouldTruncate = (body) => body.length > 120;
const truncateBody = (body) => shouldTruncate(body) ? body.slice(0, 120) : body;
const expandBody = (email) => {
  email.expanded = !email.expanded;
};
const hasAttachment = (attachments) => attachments.length > 0;
const getAttachmentIcon = (type) => {
  if (type.toLowerCase() === 'pdf') {
    return '📄'; // PDF icon
  } else if (type.toLowerCase() === 'docx') {
    return '📃'; // DOCX icon
  } else if (type.toLowerCase() === 'jpg' || type.toLowerCase() === 'jpeg' || type.toLowerCase() === 'png') {
    return '📷'; // Image icon
  } else {
    return '📎'; // Default attachment icon
  }
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


.sort-icon {
  margin-top: 14px;
}


.search-bars-container {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px; /* Adjust this margin as needed */
  }

  .inbox-title {
    color: #187dc1;
  font-size: 28px;
  margin-bottom: 15px;
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
  .sort-bar {
    display: flex;
    margin-top: 20px;
    transition: transform 0.3s;
    margin-bottom: 20px;
    margin-left: 2px;
    margin-right: 2px;
    width: 600px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }

  .sort-bar:hover {
    transform: scale(1.05);
  }

  .sort-category {
    padding: 10px;
    border: 1px solid #a3a3a3;
    border-radius: 4px;
    background-color: #a3a3a3;
    color: #000000;
    cursor: pointer;
    appearance: none;
    width:170px;
    -webkit-appearance: none;
    background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>'); /* Add custom arrow icon */
    background-repeat: no-repeat;
    background-position: right 10px center;
  }

  .sort-category:focus {
    outline: none;
    border-color: #a3a3a3;
  }

  .sort-bar,
  .sort-bar input {
    transition: border-color 0.3s;
  }

  .sort-bar  {
    padding: 8px;
    border: 0px solid #ffffff;
    border-radius: 4px;
    margin-left: 2px;
    margin-right: 2px;
    color: #000000;
    background-color: #a3a3a3;
    outline: none;
    width:16%;
  }

  .sort-bar input {
    flex: 1;
    padding: 8px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    color: #000000;
    outline: none;
  }

  .sort-bar .sort-btn {
    background-color: #007BFF;
    color: #fff;
    border: none;
    padding: 8px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    outline: none;
  }

  .sort-bar .sort-btn i {
    font-size: 18px;
  }

  .sort-bar .sort-input::placeholder {
    color: #007BFF;
  }

  .sort-bar,
  .sort-bar input:focus {
    border-color: #053a72;
  }

  .search-bar{
    display: flex;
    margin-top: 20px;
    transition: transform 0.3s;
    margin-bottom: 20px;
    margin-left: 2px;
    margin-right: 2px;
    width: 600px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    overflow: hidden;
    width:45%;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }
    .filter-bar {
    display: flex;
    margin-top: 20px;
    transition: transform 0.3s;
    margin-bottom: 20px;
    margin-left: 10px;
    margin-right: 2px;
    width: 600px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    overflow: hidden;
    width:30px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }


  .search-bar:hover, .filter-bar:hover {
    transform: scale(1.05);
  }

  .search-category{
    padding: 10px;
    border: 1px solid #a3a3a3;
    border-radius: 4px;
    background-color: #a3a3a3;
    color: #000000;
    cursor: pointer;
    appearance: none;
    width:150px;
    -webkit-appearance: none;
    background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>'); /* Add custom arrow icon */
    background-repeat: no-repeat;
    background-position: right 10px center;
  }

   .filter-category {
    padding: 10px;
    border: 1px solid #a3a3a3;
    border-radius: 4px;
    background-color: #a3a3a3;
    color: #000000;
    cursor: pointer;
    appearance: none;
    width:100px;
    -webkit-appearance: none;
    background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>'); /* Add custom arrow icon */
    background-repeat: no-repeat;
    background-position: right 10px center;
  }


  .search-category:focus, .filter-category:focus {
    outline: none;
    border-color: #a3a3a3;
  }

  .search-bar,
  .search-bar input, 
  .filter-bar,
  .filter-bar input
  {
    transition: border-color 0.3s;
  }

  .search-bar , .filter-bar  {
    padding: 8px;
    border: 0px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    width:600px;
    color: #000000;
    background-color: #a3a3a3;
    outline: none;
  }

  .search-bar input, .filter-bar input  {
    flex: 1;
    padding: 8px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    color: #000000;
    outline: none;
  }

  .search-bar .search-btn, .filter-bar .filter-btn {
    background-color: #007BFF;
    color: #fff;
    border: none;
    padding: 8px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    outline: none;
  }

  .search-bar .search-btn i, .filter-bar .filter-btn i  {
    font-size: 18px;
  }

  .search-bar .search-input::placeholder , .filter-bar .filter-input::placeholder {
    color: #007BFF;
  }

  .search-bar,
  .search-bar input:focus, .filter-bar,
  .filter-bar input:focus{
    border-color: #053a72;
  }

</style>
  
